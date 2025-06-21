package com.platform.mesh.bpm.biz.soa.node.type.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "节点类型枚举",enumAsRef = true)
public enum NodeTypeEnum implements BaseEnum<NodeTypeEnum, Integer> {

    /**
     * 结束节点
     */
    END_NODE(-1,  "结束节点"),
    /**
     * 网关节点
     */
    GATEWAY_NODE(0,  "网关节点"),
    /**
     * 开始节点
     */
    START_NODE(1,  "开始节点"),
    /**
     * 基础通用节点
     */
    BASE_NODE(2,  "基础通用节点"),
    /**
     * 流程节点
     */
    AUDIT_NODE(3,  "审批节点"),
    /**
     * 时间节点
     */
    TIME_NODE(4,  "时间节点"),
    ;


    private final Integer value;

    private final String desc;

    NodeTypeEnum(Integer value, String desc) {
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
