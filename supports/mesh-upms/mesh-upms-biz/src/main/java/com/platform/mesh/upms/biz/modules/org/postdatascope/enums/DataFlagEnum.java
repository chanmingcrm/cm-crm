package com.platform.mesh.upms.biz.modules.org.postdatascope.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 数据类型枚举
 * @author 蝉鸣
 */
@Schema(description = "数据类型枚举",enumAsRef = true)
public enum DataFlagEnum implements BaseEnum<DataFlagEnum, Integer> {

    /**
     * 人员
     */
    USER(1,  "人员"),
    /**
     * 岗位
     */
    POST(2,  "岗位"),
    /**
     * 部门
     */
    LEVEL(3,  "部门"),
    /**
     * 角色
     */
    ROLE(4,  "角色"),

    ;


    private Integer value;

    private String desc;

    DataFlagEnum(Integer value, String desc) {
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
