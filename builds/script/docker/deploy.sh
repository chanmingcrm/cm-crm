#!/bin/bash
set -euo pipefail

# 配置参数
SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)
BASE_DIR=$(cd "$SCRIPT_DIR/../../.." && pwd)
JAR_SOURCE_SUPPORTS="$BASE_DIR/supports"
JAR_SOURCE_SERVICES="$BASE_DIR/services"
DEST_DB_DIR="$SCRIPT_DIR/volumes/mysql/db"
DEST_JAR_BASE="$BASE_DIR/builds/server"

# 服务列表
SUPPORT_SERVICES=("mesh-gateway" "mesh-uaa" "mesh-upms" "mesh-bpm")
SERVICE_SERVICES=("mesh-app" "mesh-crm" "mesh-ai")
ALL_BACKEND_SERVICES=("nacos" "${SUPPORT_SERVICES[@]}" "${SERVICE_SERVICES[@]}")

# Docker Compose v2，所有相对路径以配置文件所在目录为准
compose() {
  docker compose -p "${COMPOSE_PROJECT_NAME:-mesh-platform}" -f "$SCRIPT_DIR/docker-compose.yml" "$@"
}

# 使用说明，用来提示输入参数
usage() {
  echo "Usage: bash deploy.sh [init|port|es|server|nginx|stop|rm|deploy]"
  exit 1
}

# 准备数据库初始化脚本，Shell 后端也可单独使用 base 启动基础环境
init_database() {
  # 创建目标目录
  mkdir -p "$DEST_DB_DIR" || {
    echo  "创建文件夹失败 $DEST_DB_DIR"
    exit 1
  }
  # MySQL 官方镜像仅在空数据目录首次执行初始化 SQL。
  for db in mesh_ai mesh_app mesh_bpm mesh_crm mesh_uaa mesh_upms; do
    [[ -s "$BASE_DIR/builds/data/sql/$db.sql" ]] || { echo "缺少 $db.sql"; return 1; }
    { printf 'CREATE DATABASE IF NOT EXISTS %s CHARACTER SET utf8mb4;\nUSE %s;\n' "$db" "$db"; cat "$BASE_DIR/builds/data/sql/$db.sql"; } > "$DEST_DB_DIR/$db.sql"
  done
}

# 初始化：准备配置和运行包，并启动基础环境
init() {
  [[ -s "$BASE_DIR/builds/web/index.html" ]] || { echo "缺少 builds/web 前端产物"; return 1; }
  mkdir -p "$SCRIPT_DIR/volumes/nginx/conf.d"
  cat > "$SCRIPT_DIR/volumes/nginx/conf.d/default.conf" <<'NGINX'
server {
  listen 80;
  root /usr/share/nginx/html;
  client_max_body_size 512m;
  location / { try_files $uri $uri/ /index.html; }
  location /pro-api/ {
    proxy_pass http://mesh-gateway:8080/;
    proxy_http_version 1.1;
    proxy_set_header Host $host;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header Upgrade $http_upgrade;
    proxy_set_header Connection "upgrade";
    proxy_read_timeout 300s;
    proxy_buffering off;
  }
}
NGINX
  # 网络由 Compose 统一创建，无需手动创建第二个网络。

  # 复制supports下的JAR
  for service in "${SUPPORT_SERVICES[@]}"; do
    copy_jar_file "$JAR_SOURCE_SUPPORTS" "$service"
  done

  # 复制services下的JAR
  for service in "${SERVICE_SERVICES[@]}"; do
    copy_jar_file "$JAR_SOURCE_SERVICES" "$service"
  done
  base
}

# 复制JAR文件辅助函数
copy_jar_file() {
  local source_dir=$1
  local service=$2
  local jar_name="$service-biz"
  [[ "$service" != mesh-gateway ]] || jar_name="$service"
  local source_jar="$source_dir/$service/$jar_name/target/$jar_name.jar"
  [[ "$service" != mesh-gateway ]] || source_jar="$source_dir/$service/target/$service.jar"
  local dest_dir="$DEST_JAR_BASE/$service"

  mkdir -p "$dest_dir" || {
    echo  "创建文件夹失败 $dest_dir"
    return 1
  }

  if [[ -f "$source_jar" ]]; then
    cp -v "$source_jar" "$dest_dir/$service.jar" || {
      echo  "拷贝文件失败 $source_jar"
      return 1
    }
  else
    echo  "未发现文件: $source_jar，请先构建后端项目"
    return 1
  fi
}

# 开启所需端口
port() {
  local ports=(80)
  for port in "${ports[@]}"; do
    firewall-cmd --add-port="${port}/tcp" --permanent || {
      echo  "端口 $port 添加失败"
      exit 1
    }
    echo "端口 $port/tcp 已经添加"
  done
  firewall-cmd --reload
  service firewalld restart
}

# 启动基础环境（必须）
base() {
  init_database
  compose up -d --wait --wait-timeout 600 mysql redis elasticsearch nacos
  import_nacos
}

# 导入发布包中的 Nacos 配置（服务就绪由 Compose healthcheck 保证）
import_nacos() {
  python3 - "$BASE_DIR/builds/data/nacos/nacos_config.zip" <<'PY'
import sys, urllib.request, urllib.parse, zipfile, json, os
base = os.environ.get("NACOS_SERVER_ADDR", "http://127.0.0.1:8848").rstrip("/") + "/nacos/v1"
def post(path, data):
    result = urllib.request.urlopen(base + path, urllib.parse.urlencode(data).encode(), timeout=30).read().decode()
    if result.strip() != "true":
        raise RuntimeError(result)
namespaces = json.load(urllib.request.urlopen(base + "/console/namespaces", timeout=30))
if not any(x["namespace"] == "pro" for x in namespaces["data"]):
    post("/console/namespaces", {"customNamespaceId": "pro", "namespaceName": "pro"})
with zipfile.ZipFile(sys.argv[1]) as archive:
    for name in archive.namelist():
        if "/" not in name or name.endswith("/"):
            continue
        group, data_id = name.split("/", 1)
        content = archive.read(name).decode("utf-8-sig").replace("\u200c", "")
        content = content.replace("http://localhost:8090/mcp", os.environ.get("MCP_CRM_URL", "http://mesh-crm:8090/mcp"))
        post("/cs/configs", {"tenant": "pro", "group": group, "dataId": data_id, "type": "yaml", "content": content})
PY
}

# 只启动 Elasticsearch
es() {
  compose up -d elasticsearch
}

# 启动后端程序模块（必须）
backend() {
  # 只构建业务镜像，避免每次发布都重新构建 Elasticsearch 等基础镜像。
  compose build "${SUPPORT_SERVICES[@]}" "${SERVICE_SERVICES[@]}"
  compose up -d --no-build --wait --wait-timeout 600 "${ALL_BACKEND_SERVICES[@]}" || {
    echo "服务启动失败"
    exit 1
  }
}

# 启动前端程序模块（必须）
frontend() {
  compose up -d --build nginx-ui
}

# 关闭所有环境/模块
stop() {
  compose stop
}

# 删除所有环境/模块
rm() {
  compose rm
}

# 使用当前本地源码一键构建、部署
deploy() {
  if [[ "${BUILD_LOCAL:-0}" == 1 ]]; then
    mvn -f "$BASE_DIR/pom.xml" clean package -DskipTests
  else
    docker run --rm -v "$BASE_DIR:/workspace" -v mesh-maven-cache:/root/.m2 \
      -w /workspace maven:3.9.9-eclipse-temurin-21 mvn clean package -DskipTests
  fi
  init
  backend
  frontend
}

# 根据输入参数，选择执行对应方法，不输入则执行使用说明
case "${1:-}" in
"deploy")
  deploy
  ;;
"init")
  init
  ;;
"port")
  port
  ;;
"base")
  # 兼容原有仅启动基础服务的命令（供 Shell 部署复用）。
  base
  ;;
"es")
  es
  ;;
"server")
  backend
  frontend
  ;;
"nginx")
  frontend
  ;;
"stop")
  stop
  ;;
"rm")
  rm
  ;;
*)
  usage
  ;;
esac
