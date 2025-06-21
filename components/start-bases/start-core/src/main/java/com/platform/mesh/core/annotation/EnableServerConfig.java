package com.platform.mesh.core.annotation;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.*;

/**
 * @description 自定义核心注解
 * @author 蝉鸣
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
// 开启线程异步执行
@EnableAsync
public @interface EnableServerConfig {

}
