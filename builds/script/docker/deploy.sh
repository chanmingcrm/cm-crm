#!/bin/bash

# 配置参数
BASE_DIR=$(cd ../.. && pwd)
JAR_SOURCE_SUPPORTS="$BASE_DIR/supports"
JAR_SOURCE_SERVICES="$BASE_DIR/services"
DEST_DB_DIR="$BASE_DIR/mysql/db"
DEST_JAR_BASE="$BASE_DIR/server"

# 服务列表
SUPPORT_SERVICES=("mesh-gateway" "mesh-uaa" "mesh-upms" "mesh-bpm")
SERVICE_SERVICES=("mesh-app" "mesh-crm" "mesh-tmp")
ALL_BACKEND_SERVICES=("nacos" "${SUPPORT_SERVICES[@]}" "${SERVICE_SERVICES[@]}")

# 使用说明，用来提示输入参数
usage() {
  echo "Usage: sh 执行脚本.sh [init|port|base|server|nginx|stop|rm]"
  exit 1
}

# 初始化
init() {
  # 创建目标目录
  mkdir -p "$DEST_DB_DIR" || {
    echo  "创建文件夹失败 $DEST_DB_DIR"
    exit 1
  }
  # 创建网络（如果不存在）
  if ! docker network inspect mesh-network &> /dev/null; then
    docker network create mesh-network || {
      log_error "docker network 创建失败"
      exit 1
    }
    echo "Docker network 'mesh-network' 已创建"
  else
    echo "Docker network 'mesh-network' 已存在"
  fi

  # 复制supports下的JAR
  for service in "${SUPPORT_SERVICES[@]}"; do
    copy_jar_file "$JAR_SOURCE_SUPPORTS" "$service"
  done

  # 复制services下的JAR
  for service in "${SERVICE_SERVICES[@]}"; do
    copy_jar_file "$JAR_SOURCE_SERVICES" "$service"
  done
}

# 复制JAR文件辅助函数
copy_jar_file() {
  local source_dir=$1
  local service=$2
  local source_jar="$source_dir/$service/target/$service.jar"
  local dest_dir="$DEST_JAR_BASE/$service/$service/jar"

  mkdir -p "$dest_dir" || {
    echo  "创建文件夹失败 $dest_dir"
    return 1
  }

  if [[ -f "$source_jar" ]]; then
    cp -v "$source_jar" "$dest_dir/" || {
      echo  "拷贝文件失败 $source_jar"
      return 1
    }
  else
    echo  "未发现文件: $source_jar"
  fi
}

# 开启所需端口
port() {
  local ports=(8080 8848 6379 3306)
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
  docker-compose up -d mysql redis elasicsearch
}

# 启动后端程序模块（必须）
backend() {
  docker-compose up -d "${ALL_BACKEND_SERVICES[@]}" || {
    echo "服务启动失败"
    exit 1
  }
}

# 启动前端程序模块（必须）
frontend() {
  docker-compose up -d nginx
}

# 关闭所有环境/模块
stop() {
  docker-compose stop
}

# 删除所有环境/模块
rm() {
  docker-compose rm
}

# 根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
"init")
  init
  ;;
"port")
  port
  ;;
"base")
  base
  ;;
"server")
  backend
  frontend
  ;;
"nginx")
  nginx
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

