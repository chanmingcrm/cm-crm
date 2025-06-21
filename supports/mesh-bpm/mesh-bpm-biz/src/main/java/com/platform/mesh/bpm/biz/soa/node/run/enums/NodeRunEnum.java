package com.platform.mesh.bpm.biz.soa.node.run.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "节点运行状态枚举",enumAsRef = true)
public enum NodeRunEnum implements BaseEnum<NodeRunEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0,  "元状态"),
    /**
     * 已结束
     */
    END(-1,  "已结束"),
    /**
     * 未执行
     */
    STAND(1,  "未执行"),
    /**
     * 执行中
     */
    RUNNING(2,  "执行中"),
    ;


    private final Integer value;

    private final String desc;

    NodeRunEnum(Integer value, String desc) {
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
