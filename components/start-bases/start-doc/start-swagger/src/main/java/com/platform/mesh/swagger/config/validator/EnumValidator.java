package com.platform.mesh.swagger.config.validator;

import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.io.Serializable;
import java.util.List;

/**
 * @description 枚举类校验器
 * @author 蝉鸣
 */
public class EnumValidator implements ConstraintValidator<SchemaEnum, Object> {

    /**
     * 枚举类的类对象
     */
    private Class<? extends BaseEnum<? extends Enum<?>,? extends Serializable>> enumObject;

    /**
     * 是否必须
     */
    private boolean required;

    /**
     * 功能描述:
     * 〈初始化校验〉
     * @param enumItem enumItem
     * @author 蝉鸣
     */
    @Override
    public void initialize(SchemaEnum enumItem) {
        // 获取注解传入的枚举类对象
        enumObject = enumItem.value();
        // 获取是否需要校验
        required = enumItem.required();
    }

    /**
     * 功能描述:
     * 〈校验枚举〉
     * @param value value
     * @param constraintValidatorContext constraintValidatorContext
     * @return 正常返回:{@link boolean}
     * @author 蝉鸣
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        // 判断是否必须校验
        if(!required){
            return true;
        }
        //判断是否为空
        if(isNull(value)){
            return false;
        }
        // 如果为 List 集合数据
        if (value instanceof List<?> list) {
            return this.checkList(list);
        }
        // 校验是否为合法的枚举值
        return this.hasEnum(value);
    }

    /**
     * 功能描述:
     * 〈校验集合类型〉
     * @param list list
     * @return 正常返回:{@link boolean}
     * @author 蝉鸣
     */
    private boolean checkList(List<?> list) {

        //需要校验情况下，集合是否为空
        if (required && list.isEmpty()) {
            return false;
        }

        //需要校验情况下，枚举值是否符合
        for (Object obj : list) {
            boolean hasEnum = this.hasEnum(obj);
            if (!hasEnum) {
                return false;
            }
        }
        return true;
    }

    /**
     * 功能描述:
     * 〈校验值是否为空〉
     * @param value value
     * @return 正常返回:{@link boolean}
     * @author 蝉鸣
     */
    private boolean isNull(Object value) {
        return value == null;
    }

    /**
     * 功能描述:
     * 〈校验枚举值是否符合〉
     * @param value value
     * @return 正常返回:{@link boolean}
     * @author 蝉鸣
     */
    private boolean hasEnum(Object value) {
        BaseEnum<? extends Enum<?>,? extends Serializable>[] enums = enumObject.getEnumConstants();
        for (BaseEnum<? extends Enum<?>,? extends Serializable> baseEnum : enums) {
            if (baseEnum.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

}
