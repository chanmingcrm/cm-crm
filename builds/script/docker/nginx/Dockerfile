# 基础镜像
FROM nginx
# author
MAINTAINER MESH-PLATFORM

# 复制conf文件到路径
COPY conf/crm_nginx.conf /etc/nginx/nginx.conf
# 复制html文件到路径
COPY ./html /home/mesh-platform/mesh-ui
