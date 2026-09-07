package com.platform.mesh.upms.biz.modules.team.base.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 团队类型枚举
 * @author 蝉鸣
 */
@Schema(description = "团队类型枚举",enumAsRef = true)
public enum TeamFlagEnum implements BaseEnum<TeamFlagEnum, Integer> {

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

    TeamFlagEnum(Integer value, String desc) {
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
