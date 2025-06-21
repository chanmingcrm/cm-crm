package com.platform.mesh.mybatis.plus.annotation;

import com.platform.mesh.core.constants.SymbolConst;

import java.lang.annotation.*;

/**
 * @description 自定义核心注解
 * @author 蝉鸣
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TableParentId {


    String value() default SymbolConst.BLANK;
}
