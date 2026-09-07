package com.platform.mesh.security.accesskey.spi;

import com.platform.mesh.security.accesskey.model.AccessKeyCredential;
import com.platform.mesh.security.accesskey.model.AuthenticatedAccessKey;

/**
 * 功能描述:
 * 〈Access Key 业务校验扩展接口，由持有密钥数据的业务模块实现〉
 *
 * @author qingfeng
 */
public interface AccessKeyVerifier {

    /**
     * 功能描述:
     * 〈获取当前校验器负责的凭证类型〉
     * @return 凭证类型
     * @author qingfeng
     */
    String credentialType();

    /**
     * 功能描述:
     * 〈判断当前校验器是否支持指定凭证类型〉
     * @param credentialType 待判断的凭证类型
     * @return true 支持，false 不支持
     * @author qingfeng
     */
    default boolean supports(String credentialType) {
        return credentialType().equals(credentialType);
    }

    /**
     * 功能描述:
     * 〈校验业务访问密钥并返回统一身份绑定〉
     * @param credential 待校验访问密钥
     * @return 已认证的访问密钥身份
     * @author qingfeng
     */
    AuthenticatedAccessKey verify(AccessKeyCredential credential);

    /**
     * 功能描述:
     * 〈判断认证后的 Access Key 是否允许访问目标接口，默认拒绝〉
     * @param accessKey 已认证的访问密钥身份
     * @param method HTTP 请求方法
     * @param path 应用内规范化请求路径
     * @return true 允许访问，false 拒绝访问
     * @author qingfeng
     */
    default boolean isRequestAllowed(AuthenticatedAccessKey accessKey, String method, String path) {
        return false;
    }
}
