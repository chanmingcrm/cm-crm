#!/bin/bash
set -euo pipefail

# 使用说明
usage() {
  echo "用法: bash run.sh [start|stop|restart|status|copy] [app_name] [jvm_type] [app_dir]"
  echo "  jvm_type: 0-小内存（默认），1-大内存"
  echo "  app_name: JAR 文件名（不含 .jar），例如 mesh-crm-biz"
  echo "  app_dir: 默认使用 run.sh 所在目录；copy 仅备份 JAR"
}

# 配置参数
[[ $# -ge 2 ]] || { usage; exit 1; }
RUN_OPT="$1"
RUN_APP="$2"
JVM_OPTS_TYPE="${3:-0}"
SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)
APP_DIR="${4:-$SCRIPT_DIR}"
[[ "$RUN_APP" =~ ^[a-zA-Z0-9_-]+$ ]] || { echo "无效的应用名称"; exit 1; }
[[ "$JVM_OPTS_TYPE" == 0 || "$JVM_OPTS_TYPE" == 1 ]] || { echo "jvm_type 只能为 0 或 1"; exit 1; }
[[ -d "$APP_DIR" ]] || { echo "运行目录不存在: $APP_DIR"; exit 1; }
APP_DIR=$(cd "$APP_DIR" && pwd)
# 可选的本机连接配置；统一保存在运行目录，重启时自动加载
if [[ -f "$APP_DIR/runtime.env" ]]; then
  set -a
  source "$APP_DIR/runtime.env"
  set +a
fi
APP_JAR="$APP_DIR/$RUN_APP.jar"
PID_FILE="$APP_DIR/$RUN_APP.pid"
LOG_FILE="$APP_DIR/logs/$RUN_APP.log"

# 避免同一服务的启停、备份并发执行
exec 9>"$APP_DIR/.$RUN_APP.run.lock"
flock -n 9 || { echo "[$RUN_APP] 另一个管理操作正在执行"; exit 1; }

# JVM 参数必须放在 -jar 前；允许 JAVA_OPTS 覆盖简单的空格分隔参数
JAVA_ARGS=(-XX:+UseG1GC -Dfile.encoding=UTF-8 -Duser.language=zh -Duser.region=CN)
if [[ "$JVM_OPTS_TYPE" == 1 ]]; then
  JAVA_ARGS+=(-Xms1g -Xmx1g)
else
  JAVA_ARGS+=(-Xms128m -Xmx640m)
fi
if [[ -n "${JAVA_OPTS:-}" ]]; then
  read -r -a JAVA_ARGS <<< "$JAVA_OPTS"
fi

# 只识别 PID 文件记录且 -jar 参数为本目录绝对路径的进程
is_running() {
  [[ -s "$PID_FILE" ]] || return 1
  read -r PID < "$PID_FILE" || return 1
  [[ "$PID" =~ ^[0-9]+$ && "$PID" -gt 1 ]] || return 1
  kill -0 "$PID" 2>/dev/null || return 1
  [[ -r "/proc/$PID/cmdline" ]] || return 1
  local args=() index
  mapfile -d '' -t args < "/proc/$PID/cmdline"
  for ((index=0; index<${#args[@]}-1; index++)); do
    [[ "${args[index]}" != -jar || "${args[index+1]}" != "$APP_JAR" ]] || return 0
  done
  return 1
}

# 备份旧版本，不移动正在运行的 JAR
copy() {
  [[ -f "$APP_JAR" ]] || return 0
  mkdir -p "$APP_DIR/backup"
  cp -p "$APP_JAR" "$APP_DIR/backup/$RUN_APP.jar.$(date +%Y%m%d%H%M%S%N)"
  echo "[$RUN_APP] JAR 已备份"
}

# 启动并保存真实子进程 PID，保留标准输出和错误日志
start() {
  if is_running; then
    echo "[$RUN_APP] 已运行，PID: $PID"
    return
  fi
  [[ -s "$APP_JAR" ]] || { echo "缺少 $APP_JAR"; return 1; }
  command -v java >/dev/null
  mkdir -p "$APP_DIR/logs"
  (
    cd "$APP_DIR"
    # 子进程不继承管理锁，也不占用终端标准输入
    exec nohup java "${JAVA_ARGS[@]}" -jar "$APP_JAR" 9>&-
  ) >> "$LOG_FILE" 2>&1 < /dev/null &
  echo "$!" > "$PID_FILE"
  sleep 2
  if is_running; then
    echo "[$RUN_APP] 进程已启动，PID: $PID，日志: $LOG_FILE"
  else
    rm -f "$PID_FILE"
    echo "[$RUN_APP] 启动失败，请查看 $LOG_FILE"
    return 1
  fi
}

# 优先正常关闭，超时则保留进程并报告，不强制杀死或误杀同名服务
stop() {
  if ! is_running; then
    echo "[$RUN_APP] 未运行或 PID 已失效"
    return
  fi
  kill -TERM "$PID"
  local count
  for count in {1..30}; do
    if ! is_running; then
      rm -f "$PID_FILE"
      echo "[$RUN_APP] 已停止"
      return
    fi
    sleep 1
  done
  echo "[$RUN_APP] 30 秒内未退出，请检查日志；已停止后续操作"
  return 1
}

status() {
  if is_running; then
    echo "[$RUN_APP] 运行中，PID: $PID"
  else
    echo "[$RUN_APP] 未运行"
    return 1
  fi
}

case "$RUN_OPT" in
  start)   start ;;
  stop)    stop ;;
  restart) stop; start ;;
  status)  status ;;
  copy)    copy ;;
  *) usage; exit 1 ;;
esac
