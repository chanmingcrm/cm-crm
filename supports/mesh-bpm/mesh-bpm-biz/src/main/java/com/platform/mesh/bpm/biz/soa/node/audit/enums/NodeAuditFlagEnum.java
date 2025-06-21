package com.platform.mesh.bpm.biz.soa.node.audit.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "节点处理类型枚举",enumAsRef = true)
public enum NodeAuditFlagEnum implements BaseEnum<NodeAuditFlagEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0, "元状态"),
    /**
     * 会签是指多个审批人同时收到审批单，需要所有审批人都同意才能进入下一审批环节。
     */
    SIGN_JOINT(1, "会签"),
    /**
     * 串签是指多个审批人按照既定的顺序依次进行审批，前一个审批人审批通过后，下一个审批人才能进行审批。
     */
    SIGN_SERIAL(2,  "串签"),
    /**
     * 并签是指所有会审节点参与人将同时收到流程任务，处理不分前后。
     */
    SIGN_PARALLEL(3, "并签"),
    /**
     * 或签是指同一个审批节点设置多个人，只要其中任意一个人审批通过即可进入下一节点。
     */
    SIGN_SOMEONE(4, "或签"),
    ;

    private final Integer value;

    private final String desc;

    NodeAuditFlagEnum(Integer value, String desc) {
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
