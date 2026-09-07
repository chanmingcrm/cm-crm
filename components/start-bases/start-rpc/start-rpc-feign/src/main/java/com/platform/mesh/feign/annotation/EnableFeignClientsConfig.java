package com.platform.mesh.feign.annotation;

import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.constants.SystemConst;
import com.platform.mesh.core.config.JavaTimeModule;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @description EnableFeignClients
 * @author 蝉鸣
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ JavaTimeModule.class, FeignAutoConfiguration.class })
@EnableFeignClients(basePackages = SystemConst.BASE_PACKAGE)
public @interface EnableFeignClientsConfig {

	/**
	 * 功能描述:
	 * 〈默认值〉
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	String[] value() default {};

	/**
	 * 功能描述:
	 * 〈根包扫描路径〉
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	@AliasFor(annotation = EnableFeignClients.class, attribute = StrConst.BASE_PACKAGES)
	String[] basePackages() default { SystemConst.BASE_PACKAGE };

	/**
	 * 功能描述:
	 * 〈文件扫描路径〉
	 * @return 正常返回:{@link Class}
	 * @author 蝉鸣
	 */
	Class<?>[] basePackageClasses() default {};

	/**
	 * 功能描述:
	 * 〈默认配置〉
	 * @return 正常返回:{@link Class}
	 * @author 蝉鸣
	 */
	Class<?>[] defaultConfiguration() default {};

	/**
	 * 功能描述:
	 * 〈FeignClient客户端〉
	 * @return 正常返回:{@link Class}
	 * @author 蝉鸣
	 */
	Class<?>[] clients() default {};

}