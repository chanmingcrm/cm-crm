package com.platform.mesh.security.accesskey.model;

import com.platform.mesh.security.exception.SecurityExceptionEnum;

/**
 * 功能描述:
 * 〈从外部请求提取、尚未通过业务模块校验的访问密钥〉
 *
 * @param credentialType 凭证类型，用于路由对应的业务校验 SPI
 * @param secret 外部请求携带的原始密钥
 * @author qingfeng
 */
public record AccessKeyCredential(String credentialType, String secret) {

    public AccessKeyCredential {
        // 凭证类型和密钥均为统一认证协议的必填字段。
        if (credentialType == null || credentialType.isBlank()) {
            throw SecurityExceptionEnum.ACCESS_KEY_CREDENTIAL_TYPE_EMPTY.getBaseException();
        }
        if (secret == null || secret.isBlank()) {
            throw SecurityExceptionEnum.ACCESS_KEY_SECRET_EMPTY.getBaseException();
        }
    }
}
