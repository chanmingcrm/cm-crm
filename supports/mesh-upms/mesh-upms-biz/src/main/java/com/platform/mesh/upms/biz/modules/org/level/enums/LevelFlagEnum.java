package com.platform.mesh.upms.biz.modules.org.level.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 层级类型枚举
 * @author 蝉鸣
 */
@Schema(description = "层级类型枚举",enumAsRef = true)
public enum LevelFlagEnum implements BaseEnum<LevelFlagEnum, Integer> {

    /**
     * 源状态
     */
    INIT(0,  "源状态"),
    /**
     * 公司/顶层类型
     */
    COMPANY(1,  "公司/顶层层级"),
    /**
     * 部门类型
     */
    DEPT(2,  "部门类型"),
    ;


    private final Integer value;

    private final String desc;

    LevelFlagEnum(Integer value, String desc) {
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
