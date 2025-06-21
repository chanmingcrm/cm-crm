package com.platform.mesh.mybatis.plus.annotation;

import com.platform.mesh.core.constants.SymbolConst;

import java.lang.annotation.*;

/**
 * @description 忽略数据权限注解
 * @author 蝉鸣
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD,ElementType.FIELD})
public @interface IgnoreDataScope {

    String value() default SymbolConst.BLANK;
}
