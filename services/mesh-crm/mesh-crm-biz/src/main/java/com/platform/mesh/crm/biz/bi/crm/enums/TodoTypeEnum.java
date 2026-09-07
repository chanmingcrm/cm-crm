package com.platform.mesh.crm.biz.bi.crm.enums;

import com.platform.mesh.core.enums.base.BaseEnum;

/**
 * @description 待办枚举
 * @author 蝉鸣
 */
public enum TodoTypeEnum implements BaseEnum<TodoTypeEnum, Integer> {

    /**
     * 无
     */
    INIT(0,  "无"),
    /**
     * 今日需联系
     */
    TODO(1,  "今日需联系"),
    /**
     * 已逾期
     */
    OVER(2,  "已逾期"),
    /**
     * 已完成
     */
    DONE(3,  "已完成"),
    ;


    private final Integer value;

    private final String desc;

    TodoTypeEnum(Integer value, String desc) {
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
