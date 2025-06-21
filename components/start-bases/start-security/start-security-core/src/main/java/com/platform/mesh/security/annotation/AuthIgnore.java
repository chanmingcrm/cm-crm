package com.platform.mesh.security.annotation;

import java.lang.annotation.*;

/**
 * @description 开放服务间认证权限（支持外部完全开放以及限制内部开放）
 * @author 蝉鸣
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthIgnore {

	/**
	 * 功能描述:
	 * 〈默认只允许服务间访问,完全开放设置false〉
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	boolean value() default true;

}
