# Java 版本与项目 pom.xml 保持一致
FROM openjdk:21-jdk

LABEL maintainer="mesh-platform"

# 构建参数
ARG APP_NAME
ARG APP_PORT
ARG JAVA_OPTS="-Xms128m -Xmx640m"

# 运行参数
ENV APP_NAME=${APP_NAME} \
    APP_JAR=${APP_NAME}.jar \
    APP_PORT=${APP_PORT} \
    JAVA_OPTS=${JAVA_OPTS} \
    APP_HOME=/opt/mesh-platform \
    TZ=Asia/Shanghai

# 健康检查使用 curl；镜像层中不保留 apt 缓存
RUN apt-get update \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/*

WORKDIR ${APP_HOME}
# 构建上下文为 builds，与 deploy.sh 的复制目标保持一致
COPY ./server/${APP_NAME}/${APP_JAR} ./app.jar

EXPOSE ${APP_PORT}

# Compose 可通过 service_healthy 等待应用真正就绪
HEALTHCHECK --interval=10s --timeout=5s --start-period=120s --retries=12 \
    CMD curl -fsS "http://127.0.0.1:${APP_PORT}/actuator/health" > /dev/null || exit 1

# exec 让 Java 接收容器停止信号；日志直接输出到容器标准输出
ENTRYPOINT ["sh", "-c", "exec java ${JAVA_OPTS} -Dserver.port=${APP_PORT} -jar app.jar"]
