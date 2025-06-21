package com.platform.mesh.utils.function;

import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * @description 函数式接口
 * @author 蝉鸣
 */
@FunctionalInterface
public interface FutureWithResultFunction<T,R> extends Serializable {
	
	/**
	 * 功能描述: 
	 * 〈函数接口〉
	 * @param param param
	 * @author 蝉鸣
	 */
	@Nullable
	R handle(@Nullable T param);
}
