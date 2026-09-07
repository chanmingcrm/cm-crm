package com.platform.mesh.core.enums.bpm;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "流程运行枚举",enumAsRef = true)
public enum ProcessRunEnum implements BaseEnum<ProcessRunEnum, Integer> {

    /**
     * 已结束
     */
    END(-1,  "已结束"),
    /**
     * 元状态
     */
    INIT(0,  "元状态"),
    /**
     * 未执行
     */
    STAND(1,  "未执行"),
    /**
     * 审批中
     */
    RUNNING(2,  "审批中"),
    /**
     * 作废
     */
    CANCEL(3,  "已作废"),
    ;


    private final Integer value;

    private final String desc;

    ProcessRunEnum(Integer value, String desc) {
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
