package com.platform.mesh.security.accesskey.model;

import java.time.Instant;
import java.util.Set;
import com.platform.mesh.security.exception.SecurityExceptionEnum;

/**
 * 功能描述:
 * 〈业务模块校验 Access Key 后返回的统一身份绑定信息〉
 *
 * @param credentialType 凭证类型
 * @param credentialId 业务密钥唯一标识，不包含原始密钥
 * @param userId 绑定用户 ID
 * @param accountId 绑定账号 ID
 * @param scopes 授权范围
 * @param expiresAt 凭证到期时间，由业务模块负责校验
 * @author qingfeng
 */
public record AuthenticatedAccessKey(
        String credentialType,
        String credentialId,
        Long userId,
        Long accountId,
        Set<String> scopes,
        Instant expiresAt) {

    public AuthenticatedAccessKey {
        // 认证结果必须携带可识别的凭证类型和业务凭证标识。
        if (credentialType == null || credentialType.isBlank()) {
            throw SecurityExceptionEnum.ACCESS_KEY_CREDENTIAL_TYPE_EMPTY.getBaseException();
        }
        if (credentialId == null || credentialId.isBlank()) {
            throw SecurityExceptionEnum.ACCESS_KEY_CREDENTIAL_ID_EMPTY.getBaseException();
        }
        if (userId == null || accountId == null) {
            throw SecurityExceptionEnum.ACCESS_KEY_BINDING_INVALID.getBaseException();
        }
        // 对授权范围执行防御性复制，避免认证后被外部集合修改。
        scopes = scopes == null ? Set.of() : Set.copyOf(scopes);
    }
}
