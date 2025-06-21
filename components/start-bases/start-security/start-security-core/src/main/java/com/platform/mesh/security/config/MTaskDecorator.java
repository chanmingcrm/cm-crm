package com.platform.mesh.security.config;

import cn.hutool.core.util.ObjectUtil;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;

/**
 * @description 自定义上下文传递装饰器
 * @author 蝉鸣
 */
public class MTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        // 获取所有需要传递的上下文
        //传递 RequestAttributes
        RequestAttributes requestAttributes = null;
        try {
            requestAttributes = RequestContextHolder.currentRequestAttributes();
        } catch (IllegalStateException ignore) {
        }
        //传递 SecurityContext
        SecurityContext securityContext = null;
        try {
            securityContext = SecurityContextHolder.getContext();
        } catch (IllegalStateException ignore) {
        }
        //传递 MDC 上下文
        Map<String, String> mdcContext = null;
        try {
            mdcContext = MDC.getCopyOfContextMap();
        } catch (IllegalStateException ignore) {
        }
        RequestAttributes finalRequestAttributes = requestAttributes;
        SecurityContext finalSecurityContext = securityContext;
        Map<String, String> finalMdcContext = mdcContext;
        return () -> {
            try {
                // 设置上下文
                if (ObjectUtil.isNotNull(finalRequestAttributes)) {
                    RequestContextHolder.setRequestAttributes(finalRequestAttributes);
                }
                if (ObjectUtil.isNotNull(finalSecurityContext)) {
                    SecurityContextHolder.setContext(finalSecurityContext);
                }
                if (ObjectUtil.isNotNull(finalMdcContext)) {
                    MDC.setContextMap(finalMdcContext);
                }
                runnable.run();
            } finally {
                // 清理上下文
                RequestContextHolder.resetRequestAttributes();
                SecurityContextHolder.clearContext();
                MDC.clear();
            }
        };
    }
}
