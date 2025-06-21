package com.platform.mesh.upms.biz.modules.sys.user.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 用户性别枚举
 * @author 蝉鸣
 */
@Schema(description = "用户性别枚举",enumAsRef = true)
public enum UserGenderEnum implements BaseEnum<UserGenderEnum, Integer> {

    /**
     * 男
     */
    MALE(1,  "男"),
    /**
     * 女
     */
    FEMALE(2,  "女"),
    /**
     * 其他
     */
    OTHER(3,  "其他"),
    ;


    private final Integer value;

    private final String desc;

    UserGenderEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
    @Override
    public String getDesc() {
        return this.desc;
    }

}
