package com.platform.mesh.security.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/** 在异步业务边界临时安装并可靠恢复 Spring Security 身份。 */
public final class SecurityContextScope implements AutoCloseable {

    private final SecurityContext previous;

    private SecurityContextScope(Authentication authentication) {
        previous = SecurityContextHolder.getContext();
        SecurityContext current = SecurityContextHolder.createEmptyContext();
        current.setAuthentication(authentication);
        SecurityContextHolder.setContext(current);
    }

    public static SecurityContextScope open(Authentication authentication) {
        return new SecurityContextScope(authentication);
    }

    @Override
    public void close() {
        SecurityContextHolder.setContext(previous);
    }
}
