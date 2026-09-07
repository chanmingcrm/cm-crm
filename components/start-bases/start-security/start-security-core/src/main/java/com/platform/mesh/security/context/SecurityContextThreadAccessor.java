package com.platform.mesh.security.context;

import com.platform.mesh.utils.context.ThreadContextAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 功能描述:
 * 〈Spring Security 线程上下文传播扩展〉
 * @author 蝉鸣
 */
public class SecurityContextThreadAccessor implements ThreadContextAccessor<Authentication> {

    /**
     * 功能描述:
     * 〈捕获当前认证身份〉
     * @return 当前认证身份
     * @author 蝉鸣
     */
    @Override
    public Authentication capture() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 功能描述:
     * 〈安装认证身份并保留原上下文〉
     * @param authentication 认证身份快照
     * @return 上下文恢复作用域
     * @author 蝉鸣
     */
    @Override
    public Scope install(Authentication authentication) {
        SecurityContext previous = SecurityContextHolder.getContext();
        SecurityContext current = SecurityContextHolder.createEmptyContext();
        current.setAuthentication(authentication);
        SecurityContextHolder.setContext(current);
        return () -> SecurityContextHolder.setContext(previous);
    }
}
