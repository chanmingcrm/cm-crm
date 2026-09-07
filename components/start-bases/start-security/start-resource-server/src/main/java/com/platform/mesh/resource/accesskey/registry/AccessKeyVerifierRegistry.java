package com.platform.mesh.resource.accesskey.registry;

import com.platform.mesh.security.accesskey.spi.AccessKeyVerifier;
import com.platform.mesh.security.exception.SecurityExceptionEnum;

import java.util.List;

/**
 * 功能描述:
 * 〈收集各业务模块的 Access Key SPI，并按凭证类型选择唯一实现〉
 *
 * @author qingfeng
 */
public class AccessKeyVerifierRegistry {

    private final List<AccessKeyVerifier> verifiers;

    /**
     * 功能描述:
     * 〈创建 Access Key 校验器注册表〉
     * @param verifiers Spring 容器中全部 Access Key SPI 实现
     * @author qingfeng
     */
    public AccessKeyVerifierRegistry(List<AccessKeyVerifier> verifiers) {
        this.verifiers = List.copyOf(verifiers);
    }

    /**
     * 功能描述:
     * 〈获取指定凭证类型唯一匹配的校验器〉
     * @param credentialType Access Key 凭证类型
     * @return 唯一匹配的业务校验器
     * @author qingfeng
     */
    public AccessKeyVerifier require(String credentialType) {
        // 通过 SPI 声明的类型匹配实现，业务模块之间互不依赖。
        List<AccessKeyVerifier> matches = verifiers.stream()
                .filter(verifier -> verifier.supports(credentialType))
                .toList();
        if (matches.isEmpty()) {
            throw SecurityExceptionEnum.ACCESS_KEY_TYPE_UNSUPPORTED.getBaseException();
        }
        if (matches.size() > 1) {
            throw SecurityExceptionEnum.ACCESS_KEY_VERIFIER_CONFLICT.getBaseException();
        }
        return matches.getFirst();
    }
}
