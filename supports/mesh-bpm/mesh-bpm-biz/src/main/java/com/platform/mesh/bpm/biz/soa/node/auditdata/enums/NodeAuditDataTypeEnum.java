package com.platform.mesh.bpm.biz.soa.node.auditdata.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "节点处理类型枚举",enumAsRef = true)
public enum NodeAuditDataTypeEnum implements BaseEnum<NodeAuditDataTypeEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0,0,  "元状态"),
    /**
     * 人员
     */
    USER_CUSTOM(1,100,  "自定义人员"),
    USER_LEADER(1,101,  "发起人直接领导"),
    USER_LOOP(1,102,  "发起人多级领导"),
    /**
     * 组织
     */
    ORG_CUSTOM(2,200,  "自定义组织"),
    /**
     * 角色
     */
    ROLE_CUSTOM(3,300,  "自定义角色"),
    ;


    @Getter
    private final Integer code;

    private final Integer value;

    private final String desc;

    NodeAuditDataTypeEnum(Integer code, Integer value, String desc) {
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
