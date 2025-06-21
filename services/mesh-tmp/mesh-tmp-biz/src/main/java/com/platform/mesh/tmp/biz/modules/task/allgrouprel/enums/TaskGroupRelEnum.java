package com.platform.mesh.tmp.biz.modules.task.allgrouprel.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 任务关系类型枚举
 * @author 蝉鸣
 */
@Schema(description = "任务关系类型枚举",enumAsRef = true)
public enum TaskGroupRelEnum implements BaseEnum<TaskGroupRelEnum, Integer> {

    /**
     * 默认分组
     */
    TASK_DEFAULT(1,  "默认分组"),
    ;


    private final Integer value;

    private final String desc;

    TaskGroupRelEnum(Integer value, String desc) {
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
