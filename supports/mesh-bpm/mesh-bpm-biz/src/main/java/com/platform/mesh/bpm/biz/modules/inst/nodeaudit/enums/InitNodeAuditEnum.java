package com.platform.mesh.bpm.biz.modules.inst.nodeaudit.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "节点审批创建类型枚举",enumAsRef = true)
public enum InitNodeAuditEnum implements BaseEnum<InitNodeAuditEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0,  "元状态"),

    ADD(1,  "添加审批"),

    ;

    private final Integer value;

    private final String desc;

    InitNodeAuditEnum(Integer value, String desc) {
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
