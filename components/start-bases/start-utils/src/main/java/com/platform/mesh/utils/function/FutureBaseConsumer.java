package com.platform.mesh.utils.function;

import org.jspecify.annotations.Nullable;

import java.io.Serializable;

/**
 * @description 函数式接口
 * @author 蝉鸣
 */
@FunctionalInterface
public interface FutureBaseConsumer<T> extends Serializable {

	/**
	 * 功能描述:
	 * 〈函数接口〉
	 * @param param param
	 * @author 蝉鸣
	 */
	void handle(@Nullable T param) throws Throwable;

}
