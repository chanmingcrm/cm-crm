package com.platform.mesh.redis.service.annotation;

import java.lang.annotation.*;

/**
 * @description 分布式锁（不支持重入）
 * @author 蝉鸣
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DistributedLock {

	/**
	 * 唯一锁名称
	 */
	String value();

}
