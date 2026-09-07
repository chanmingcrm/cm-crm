package com.platform.mesh.utils.function;

import org.jspecify.annotations.Nullable;

import java.io.Serializable;
import java.util.List;

/**
 * @description 函数式接口
 * @author 蝉鸣
 */
@FunctionalInterface
public interface FutureNoResultConsumer<T> extends Serializable {
	
	/**
	 * 功能描述: 
	 * 〈函数接口〉
	 * @param paramList paramList
	 * @author 蝉鸣
	 */
    void handle(@Nullable List<T> paramList);
}
