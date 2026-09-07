package com.platform.mesh.resource.authentication;

import com.platform.mesh.security.domain.bo.LoginUserBO;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Objects;

/**
 * 在异步或内部调用上下文中恢复已认证的单体用户身份。
 */
public class UserAuthenticationToken extends AbstractAuthenticationToken {

    private final LoginUserBO principal;

    public UserAuthenticationToken(LoginUserBO principal) {
        super(requirePrincipal(principal).getAuthorities());
        this.principal = principal;
        setAuthenticated(true);
    }

    private static LoginUserBO requirePrincipal(LoginUserBO principal) {
        return Objects.requireNonNull(principal, "登录用户不能为空");
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public LoginUserBO getPrincipal() {
        return principal;
    }
}
