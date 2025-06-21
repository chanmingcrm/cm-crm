#!/bin/bash

# 获取脚本第一个参数
APP_OPT=${1}
# 程序根目录
APP_HOME='/opt/mesh-platform'
# 运行参数
JAVA_OPTS="-XX:+UseG1GC -Xms512m -Xmx2G -Xmn768m -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=512m -XX:MaxGCPauseMillis=200 -XX:ParallelGCThreads=8 -Dfile.encoding=\"UTF-8\" -Ddefault.client.encoding=\"UTF-8\" -Dfile.encoding=\"UTF-8\" -Duser.language=\"Zh\" -Duser.region=\"CN\""
# 服务名数组
APP_NAMES="gateway upms uaa bpm app crm"

# 使用说明，用来提示输入参数
usage() {
  echo "Usage: sh 执行脚本.sh [init|build|copy|start|stop]"
  exit 1
}

# 初始化
init() {
  # 开启所需端口
   echo ">>>所需端口已经开启！！！"
   firewall-cmd --add-port=8080/tcp --permanent
   firewall-cmd --add-port=8848/tcp --permanent
   firewall-cmd --add-port=6379/tcp --permanent
   firewall-cmd --add-port=3306/tcp --permanent
   service firewalld restart
   echo ">>>项目已经拉取！！！"
   # 拉取项目
#   git clone ${GIT_URL}
   echo ">>>请确定数据库脚本已经执行！！！"

}

# 构建项目
build() {
  #拉取代码

  # 构建服务
  echo ">>>开始构建项目"
  echo "<-------------------------------------->"
  sudo mvn -f ../../../pom.xml clean install -Dmaven.test.skip=true
  echo ">>>项目已经构建完成！！！"
}

# 拷贝项目
copy() {
  echo "当前路径: $(pwd)"
  echo ">>>开始拷贝项目"
  # 使用兼容性更好的循环方式
  for app in $APP_NAMES; do
    run_name="mesh-${app}"
    echo "拷贝服务： [${run_name}]"
    # 判断是否为特殊处理的服务
    case "$app" in
      gateway|upms|uaa|bpm)
        echo "基础服务处理"
        # 进入支持服务目录
        if [ "$app" = "gateway" ]; then
          echo "网关特殊处理"
          cd "${APP_HOME}/supports/${run_name}" || {
            echo "错误：无法进入目录 ${APP_HOME}/supports/${run_name}" >&2
            continue
          }
          target_dir="../../../server/${run_name}"
        else
          cd "${APP_HOME}/supports/${run_name}/${run_name}-biz" || {
            echo "错误：无法进入目录 ${APP_HOME}/supports/${run_name}/${run_name}-biz" >&2
            continue
          }
          target_dir="../../../../server/${run_name}"
        fi
        ;;
      *)
        echo "普通服务处理"
        # 进入普通服务目录
        cd "${APP_HOME}/service/${run_name}/${run_name}-biz" || {
          echo "错误：无法进入目录 ${APP_HOME}/service/${run_name}/${run_name}-biz" >&2
          continue
        }
        target_dir="../../../../server/${run_name}"
        ;;
    esac
    echo "当前路径: $(pwd)"
    # 创建目标目录（如果不存在）
    mkdir -p "${target_dir}" || {
      echo "错误：无法创建目标目录 ${target_dir}" >&2
      continue
    }
    # 拷贝jar文件
    if [ -f "target/${run_name}.jar" ]; then
      cp "target/${run_name}.jar" "${target_dir}/" || {
        echo "错误：拷贝jar文件失败" >&2
        continue
      }
    else
      echo "错误：找不到target/${run_name}.jar" >&2
      continue
    fi
    # 拷贝run.sh脚本
    if [ -f "../../../../script/shell/run.sh" ]; then
      cp "../../../../script/shell/run.sh" "${target_dir}/" || {
        echo "错误：拷贝run.sh失败" >&2
        continue
      }
    else
      echo "错误：找不到../../../../script/shell/run.sh" >&2
      continue
    fi
    echo "[${run_name}] 项目拷贝完成！"
  done
}

# 启动服务
start() {
  # 停止服务
  stop
  # 进入服务器目录
  if ! cd "${APP_HOME}/build/server"; then
    echo "错误：无法进入目录 ${APP_HOME}/build/server" >&2
    exit 1
  fi
  echo "当前路径: $(pwd)"
  echo ">>>开始启动项目"
  # 使用兼容性更好的循环方式
  for app in $APP_NAMES; do
    local app_name="mesh-${app}"
    local app_jar="${app_name}.jar"
    echo "开始启动项目:模块: ${app_name}"
    # 进入应用目录
    if ! cd "${app_name}"; then
      echo "错误：无法进入目录 ${app_name}" >&2
      continue
    fi
    echo "运行服务： [${app_name}]"
    echo "当前路径: $(pwd)"
    # 检查jar文件是否存在
    if [ ! -f "${app_jar}" ]; then
      echo "错误：找不到jar文件 ${app_jar}" >&2
      cd ..
      continue
    fi
    # 设置权限（比777更安全的权限）
    chmod 755 "${app_jar}"
    # 启动命令
    echo "启动命令: nohup java -jar ${app_jar} ${JAVA_OPTS} > /dev/null 2>&1 &"
    # 启动服务并检查结果
    # 获取并检查进程
    nohup java -jar "${app_jar}" "${JAVA_OPTS}" > /dev/null 2>&1 &
    local pid=$!
    if ps -p "${pid}" > /dev/null; then
      echo "[${app_name}] 启动成功！PID: ${pid}"
    else
      echo "[${app_name}] 启动失败！" >&2
    fi
    # 返回上级目录
    cd ..
  done
}

# 关闭所有环境/模块
stop() {
    echo ">>>开始停止项目"
    for app in $APP_NAMES; do
        local app_name="mesh-$app"
        local app_jar="${app_name}.jar"
        local pid
        pid=$(pgrep -f "${app_jar}")
        if [ -n "${pid}" ]; then
            if kill -9 "${pid}"; then
                echo "[$app_name] 停止成功 (PID: $pid)"
            else
                echo "[$app_name] 停止失败 (PID: $pid)"
            fi
        else
            echo "[$app_name] 未运行"
        fi
    done
}

# 根据输入参数，选择执行对应方法，不输入则执行使用说明
case "${APP_OPT}" in
"init")
  init
  ;;
"copy")
  copy
  ;;
"build")
  build
  ;;
"start")
  start
  ;;
"stop")
  stop
  ;;
*)
  usage
  ;;
esac

