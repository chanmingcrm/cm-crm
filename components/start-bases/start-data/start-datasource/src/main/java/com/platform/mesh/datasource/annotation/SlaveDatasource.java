package com.platform.mesh.datasource.annotation;

import com.baomidou.dynamic.datasource.annotation.DS;

import java.lang.annotation.*;

/**
 * @description 从库数据源
 * @author 蝉鸣
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DS("slave")
public @interface SlaveDatasource {

}