package com.platform.mesh.swagger.config.enums;


import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.swagger.config.validator.EnumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 枚举类字段属性的注解
 * @author 蝉鸣
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface SchemaEnum {

    /**
     * 枚举类说明
     */
    String description() default SymbolConst.BLANK;
    /**
     * 枚举类对象
     */
    Class<? extends BaseEnum<? extends Enum<?>,? extends Serializable>> value();
    /**
     * 默认值
     */
    String defaultValue() default SymbolConst.BLANK;
    /**
     * 是否隐藏
     */
    boolean hidden() default false;
    /**
     * 是否必须
     */
    boolean required() default false;
    /**
     * 数据类型
     */
    String dataType() default SymbolConst.BLANK;

    /**
     * 缺少报错：XXX contains Constraint annotation, but does not contain a message parameter
     */
    String message() default SymbolConst.BLANK;

    /**
     * 缺少报错：XXX contains Constraint annotation, but does not contain a groups parameter
     */
    Class<?>[] groups() default {};

    /**
     * 缺少报错：XXX contains Constraint annotation, but does not contain a payload parameter
     */
    Class<? extends Payload>[] payload() default {};

}
