package com.platform.mesh.security.authentication;

/** 请求使用的唯一认证通道。 */
public enum AuthenticationChannel {
    BEARER,
    ACCESS_KEY,
    INNER,
    ANONYMOUS
}
