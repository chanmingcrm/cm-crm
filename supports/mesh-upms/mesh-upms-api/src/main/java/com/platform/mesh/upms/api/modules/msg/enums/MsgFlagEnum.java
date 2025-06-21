package com.platform.mesh.upms.api.modules.msg.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "消息标识枚举",enumAsRef = true)
public enum MsgFlagEnum implements BaseEnum<MsgFlagEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0,0,  "元状态"),
    /**
     * 日志类型
     */
    LOG_OPR(1,101,  "操作日志"),
    /**
     * 提醒类型
     */
    NOTICE_TODO(2,201,  "业务待办"),
    /**
     * 审批类型
     */
    AUDIT_TODO(3,301,  "审批待办"),
    /**
     * 日程类型
     */
    PLAN_TODO(4,401,  "日程计划"),
    ;


    @Getter
    private final Integer code;

    private final Integer value;

    private final String desc;

    MsgFlagEnum(Integer code, Integer value, String desc) {
        this.code = code;
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
