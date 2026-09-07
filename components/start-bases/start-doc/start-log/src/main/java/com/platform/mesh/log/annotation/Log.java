package com.platform.mesh.log.annotation;

import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.core.enums.custom.LoginTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 自定义操作日志记录注解
 * @author 蝉鸣
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

	/**
	 * 模块名称
	 */
	String moduleName() default "Unknown Service";

	/**
	 * 功能
	 */
	OperateTypeEnum operateType() default OperateTypeEnum.OTHER;

	/**
	 * 操作人类别
	 */
	LoginTypeEnum loginType() default LoginTypeEnum.PC;

	/**
	 * 是否保存请求的参数
	 */
	boolean isSaveRequestData() default true;

	/**
	 * 是否保存返回数据
	 */
	boolean isSaveResponseData() default true;

}
