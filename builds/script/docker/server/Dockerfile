# 指定基础镜像
FROM openjdk:21-jdk
# 作者
LABEL maintainer="mesh-platform"
# 构建镜像时传参数据
ARG APP_NAME
ARG APP_PORT
ARG JAVA_OPTS
# 设置环境变量-运行时也可传参进来
ENV APP_NAME=${APP_NAME} \
    APP_JAR=${APP_NAME}.jar \
    APP_PORT=${APP_PORT} \
    JAVA_OPTS=${JAVA_OPTS} \
    APP_HOME=/opt/mesh-platform
# 设置时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# 挂载目录
VOLUME ${APP_HOME}/${APP_NAME}
# 创建目录
RUN mkdir -p ${APP_HOME}/${APP_NAME}
# 指定路径
WORKDIR ${APP_HOME}/${APP_NAME}
# 复制jar文件到路径
# docker-compose  context 上下文相对目录,如果单独运行，修改相对dockerfile目录
COPY ./server/${APP_NAME}/${APP_JAR} ${APP_HOME}/${APP_NAME}/${APP_JAR}
# 对外暴漏的端口号
EXPOSE ${APP_PORT}
# 使用 exec 形式启动应用（更优雅的进程管理）
ENTRYPOINT ["sh", "-c"]
# 启动服务
CMD ["echo \"****** 启动应用: java ${JAVA_OPTS} -jar ${APP_HOME}/${APP_NAME}/${APP_JAR}\" && \
      exec java ${JAVA_OPTS} -jar ${APP_HOME}/${APP_NAME}/${APP_JAR} > /proc/1/fd/1 2>&1"]