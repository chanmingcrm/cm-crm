#!/bin/bash
set -euo pipefail

# 配置参数：源码路径固定按脚本定位，运行目录可以通过 APP_HOME 指定
SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)
BASE_DIR=$(cd "$SCRIPT_DIR/../../.." && pwd)
APP_HOME="${APP_HOME:-/opt/server}"
JVM_OPTS_TYPE="${JVM_OPTS_TYPE:-0}"

# 服务列表：CRM 在 APP 服务之后启动
SUPPORT_SERVICES=(gateway uaa upms bpm)
SERVICE_SERVICES=(app ai crm)
APP_NAMES=("${SUPPORT_SERVICES[@]}" "${SERVICE_SERVICES[@]}")
declare -A APP_PORTS=([gateway]=8080 [uaa]=8081 [upms]=8082 [bpm]=8084 [app]=8085 [ai]=8092 [crm]=8090)

# 使用说明
usage() {
  echo "用法: bash deploy.sh [init|build|copy|start|stop|restart|status|deploy]"
  echo "  deploy  构建本地源码、复制运行包并启动全部后端"
  echo "  copy    检查全部 JAR 后，停止旧服务并备份、更新运行包"
  echo "  APP_HOME 可指定运行目录；JVM_OPTS_TYPE=0 为小内存，1 为大内存"
}

# 初始化运行目录；基础服务、数据库和 Nacos 配置由宿主机预先准备
init() {
  mkdir -p "$APP_HOME"
  APP_HOME=$(cd "$APP_HOME" && pwd)
}

# 构建本地项目，无需 sudo，不操作 Git
build() {
  command -v java >/dev/null
  command -v mvn >/dev/null
  mvn -f "$BASE_DIR/pom.xml" clean package -DskipTests
}

# 运行文件名保留 Maven 产物名称，所有 JAR 放在同一目录
app_name() {
  if [[ "$1" == gateway ]]; then
    echo mesh-gateway
  else
    echo "mesh-$1-biz"
  fi
}

# 获取实际构建产物路径
source_jar() {
  local app="$1" name="mesh-$1"
  case "$app" in
    gateway) echo "$BASE_DIR/supports/$name/target/$name.jar" ;;
    uaa|upms|bpm) echo "$BASE_DIR/supports/$name/$name-biz/target/$name-biz.jar" ;;
    *) echo "$BASE_DIR/services/$name/$name-biz/target/$name-biz.jar" ;;
  esac
}

# 拷贝项目：全部检查、暂存成功后才停止旧服务
copy() {
  init
  local app name source
  for app in "${APP_NAMES[@]}"; do
    source=$(source_jar "$app")
    [[ -s "$source" ]] || { echo "缺少 $source，请先执行 build"; return 1; }
  done
  for app in "${APP_NAMES[@]}"; do
    name=$(app_name "$app")
    cp "$(source_jar "$app")" "$APP_HOME/$name.jar.next"
  done
  stop
  for app in "${APP_NAMES[@]}"; do
    name=$(app_name "$app")
    # 使用同一份 run.sh 进行备份及进程管理
    bash "$SCRIPT_DIR/run.sh" copy "$name" "$JVM_OPTS_TYPE" "$APP_HOME"
    mv "$APP_HOME/$name.jar.next" "$APP_HOME/$name.jar"
    echo "[$name] 运行包已更新"
  done
  cp "$SCRIPT_DIR/run.sh" "$APP_HOME/run.sh"
}

# 启动服务；已运行的服务不会重复启动
start() {
  init
  command -v curl >/dev/null
  local app name
  for app in "${APP_NAMES[@]}"; do
    name=$(app_name "$app")
    [[ -s "$APP_HOME/$name.jar" ]] || {
      echo "缺少 mesh-$app 运行包，请先执行 copy"; return 1;
    }
  done
  for app in "${APP_NAMES[@]}"; do
    name=$(app_name "$app")
    bash "$SCRIPT_DIR/run.sh" start "$name" "$JVM_OPTS_TYPE" "$APP_HOME"
    wait_ready "$app"
  done
}

# 等待业务服务就绪，尤其确保 APP 就绪后再启动 CRM
wait_ready() {
  local app="$1" count
  for count in {1..120}; do
    if curl -fsS --max-time 3 "http://127.0.0.1:${APP_PORTS[$app]}/actuator/health" >/dev/null 2>&1; then
      echo "[mesh-$app] 健康检查通过"
      return
    fi
    sleep 2
  done
  echo "[mesh-$app] 健康检查超时，请查看 $APP_HOME/logs/"
  return 1
}

# 逆序停止服务，先停止依赖方
stop() {
  local index name
  for ((index=${#APP_NAMES[@]}-1; index>=0; index--)); do
    name=$(app_name "${APP_NAMES[index]}")
    [[ -d "$APP_HOME" ]] || continue
    bash "$SCRIPT_DIR/run.sh" stop "$name" "$JVM_OPTS_TYPE" "$APP_HOME"
  done
}

# 查看全部服务状态
status() {
  local app name result=0
  for app in "${APP_NAMES[@]}"; do
    name=$(app_name "$app")
    if [[ ! -d "$APP_HOME" ]]; then
      echo "[$name] 未部署"
      result=1
    elif ! bash "$SCRIPT_DIR/run.sh" status "$name" "$JVM_OPTS_TYPE" "$APP_HOME"; then
      result=1
    fi
  done
  return "$result"
}

# 使用本地源码一键部署后端
deploy() {
  build
  copy
  start
}

# 根据输入参数执行对应方法
case "${1:-}" in
  init)    init ;;
  build)   build ;;
  copy)    copy ;;
  start)   start ;;
  stop)    stop ;;
  restart) stop; start ;;
  status)  status ;;
  deploy)  deploy ;;
  ""|help|-h|--help) usage ;;
  *) usage; exit 1 ;;
esac
