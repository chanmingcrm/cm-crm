package com.platform.mesh.bpm.biz.soa.process.type.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.bpm.biz.modules.inst.varvalue.domain.po.BpmInstVarValue;
import com.platform.mesh.bpm.biz.modules.temp.action.domain.po.BpmTempAction;
import com.platform.mesh.bpm.biz.modules.temp.event.domain.po.BpmTempEvent;
import com.platform.mesh.bpm.biz.modules.temp.line.domain.po.BpmTempLine;
import com.platform.mesh.bpm.biz.modules.temp.node.domain.po.BpmTempNode;
import com.platform.mesh.bpm.biz.modules.temp.nodesub.domain.po.BpmTempNodeSub;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.po.BpmTempProcess;
import com.platform.mesh.bpm.biz.modules.temp.variable.domain.po.BpmTempVariable;
import com.platform.mesh.bpm.biz.modules.temp.varrefer.domain.po.BpmTempVarRefer;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 过程类型枚举
 * @author 蝉鸣
 */
@Schema(description = "过程类型枚举",enumAsRef = true)
public enum ProcessTypeEnum implements BaseEnum<ProcessTypeEnum, Integer> {

    /**
     * 过程
     */
    PROCESS(1, BpmTempProcess.class.getTypeName()),
    /**
     * 节点
     */
    NODE(2, BpmTempNode.class.getTypeName()),
    /**
     * 线
     */
    LINE(3, BpmTempLine.class.getTypeName()),
    /**
     * 动作
     */
    ACTION(4, BpmTempAction.class.getTypeName()),
    /**
     * 事件
     */
    EVENT(5, BpmTempEvent.class.getTypeName()),
    /**
     * 变量
     */
    VARIABLE(6, BpmTempVariable.class.getTypeName()),

    /**
     * 变量参照
     */
    VAR_REFER(7, BpmTempVarRefer.class.getTypeName()),


    /**
     * 变量值
     */
    VAR_VALUE(8, BpmInstVarValue.class.getTypeName()),

    /**
     * 节点子项
     */
    NODE_SUB(9, BpmTempNodeSub.class.getTypeName()),
    ;


    private final Integer value;

    private final String desc;

    ProcessTypeEnum(Integer value, String desc) {
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
