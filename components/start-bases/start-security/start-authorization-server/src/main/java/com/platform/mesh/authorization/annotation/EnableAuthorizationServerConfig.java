package com.platform.mesh.authorization.annotation;

import com.platform.mesh.authorization.AuthorizationServerAutoConfiguration;
import com.platform.mesh.core.annotation.EnableServerConfig;
import com.platform.mesh.feign.annotation.EnableFeignClientsConfig;
import com.platform.mesh.feign.configuration.FeignConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @description 资源服务注解
 * @author 蝉鸣
 */
@Documented
@Inherited
@EnableServerConfig
@EnableFeignClientsConfig
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Import({AuthorizationServerAutoConfiguration.class, FeignConfiguration.class })
public @interface EnableAuthorizationServerConfig {

}