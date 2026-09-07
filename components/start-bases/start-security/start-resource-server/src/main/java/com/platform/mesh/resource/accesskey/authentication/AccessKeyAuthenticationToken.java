package com.platform.mesh.resource.accesskey.authentication;

import com.platform.mesh.security.accesskey.model.AuthenticatedAccessKey;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * 功能描述:
 * 〈保存 Access Key 认证后的用户、账户及密钥绑定信息〉
 *
 * @author qingfeng
 */
public class AccessKeyAuthenticationToken extends AbstractAuthenticationToken {

    private final LoginUserBO principal;

    /**
     *  功能描述:
     *  〈获取认证后的 Access Key 绑定信息〉
     */
    @Getter
    private final AuthenticatedAccessKey accessKey;

    /**
     * 功能描述:
     * 〈创建已认证的 Access Key 身份〉
     * @param principal Access Key 绑定的登录用户
     * @param accessKey 已认证的 Access Key 信息
     * @author qingfeng
     */
    public AccessKeyAuthenticationToken(LoginUserBO principal, AuthenticatedAccessKey accessKey) {
        super(principal.getAuthorities());
        this.principal = principal;
        this.accessKey = accessKey;
        setAuthenticated(true);
    }

    /**
     * 功能描述:
     * 〈Access Key 原始密钥认证后不再保留〉
     * @return 空凭证
     * @author qingfeng
     */
    @Override
    public Object getCredentials() {
        return "";
    }

    /**
     * 功能描述:
     * 〈获取 Access Key 绑定的登录用户〉
     * @return 登录用户
     * @author qingfeng
     */
    @Override
    public LoginUserBO getPrincipal() {
        return principal;
    }

}
