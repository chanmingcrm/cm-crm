#!/bin/bash

# 严格模式，遇到错误立即退出
set -euo pipefail

# 参数检查
if [ $# -lt 1 ]; then
  usage
fi

# 获取脚本第一个参数
RUN_OPT=${1}
# 名称
RUN_APP=${2}
# 运行程序参数
JAVA_OPTS="-XX:+UseG1GC -Xms512m -Xmx2G -Xmn768m -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=512m -XX:MaxGCPauseMillis=200 -XX:ParallelGCThreads=8 -Dfile.encoding=\"UTF-8\" -Ddefault.client.encoding=\"UTF-8\" -Dfile.encoding=\"UTF-8\" -Duser.language=\"Zh\" -Duser.region=\"CN\""

# 使用说明，用来提示输入参数
usage() {
  echo "Usage: 执行脚本 [start|stop|copy] [app_name]"
  exit 1
}

# 备份
copy() {
  # 备份服务
  echo "开始备份项目:模块:${RUN_APP}"
  local app_jar="${RUN_APP}.jar"
  # 检查JAR文件是否存在
  if [ ! -f "${app_jar}" ]; then
    echo "JAR文件 ${app_jar} 不存在,无需备份"
  fi
  # 备份旧版本
  backup_name="${app_jar}.bak_$(date +%Y%m%d%H%M%S)"
  echo "备份命令: mv ${app_jar} ${backup_name}"
  mv "${app_jar}" "${backup_name}"
  echo "已备份旧版本: ${backup_name}"
}

# 启动
start() {
  # 停止服务
  stop
  # 启动服务
  echo "开始启动项目:模块:${RUN_APP}"
  local app_jar="${RUN_APP}.jar"
  # 检查JAR文件是否存在
  if [ ! -f "${app_jar}" ]; then
    echo "JAR文件 ${app_jar} 不存在"
  fi
  # 设置合理权限 (避免使用777)
  chmod 777 "${app_jar}"
  echo "当前路径: $(pwd)"
#  app_log="logs"
#  mkdir -p ${app_log}
#  echo "启动命令: nohup java -jar ${app_jar} ${JAVA_OPTS} >> ${app_log}/${RUN_APP}.log 2>&1 &"
  echo "启动命令: nohup java -jar ${app_jar} ${JAVA_OPTS} > /dev/null 2>&1 &"
  # 后台运行服务
  nohup java -jar "${app_jar}" "${JAVA_OPTS}" > /dev/null 2>&1 &
  # 获取并检查进程
  local pid=$!
  sleep 2  # 等待进程启动
  if ps -p "${pid}" > /dev/null; then
    echo "启动成功! PID: ${pid}"
  else
    echo "启动失败"
  fi
}

# 关闭
stop() {
   echo "开始停止项目:模块:${RUN_APP}"
   local app_jar="${RUN_APP}.jar"
   local pid
   pid=$(pgrep -f "${app_jar}" || true)
   if [ -n "${pid}" ]; then
     echo "找到进程 PID: ${pid}"
     if kill -9 "${pid}"; then
       echo "服务停止成功"
     else
       echo "服务停止失败"
     fi
   else
     echo "进程不存在"
   fi
}

# 根据输入参数，选择执行对应方法，不输入则执行使用说明
# 主逻辑
case "${RUN_OPT}" in
  "start")
    if [ -z "${RUN_APP}" ]; then
      echo "请指定要启动的应用名称"
    fi
    start
    ;;
  "stop")
    if [ -z "${RUN_APP}" ]; then
      echo "请指定要停止的应用名称"
    fi
    stop
    ;;
  "copy")
    if [ -z "${RUN_APP}" ]; then
      echo "请指定要拷贝的应用名称"
    fi
    copy
    ;;
  *)
    usage
    ;;
esac

