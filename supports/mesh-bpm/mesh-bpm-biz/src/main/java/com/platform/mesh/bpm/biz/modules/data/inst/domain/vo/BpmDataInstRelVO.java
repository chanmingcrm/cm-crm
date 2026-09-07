package com.platform.mesh.bpm.biz.modules.data.inst.domain.vo;


import com.platform.mesh.bpm.biz.modules.temp.process.enums.ProcessFlagEnum;
import com.platform.mesh.bpm.biz.soa.node.pass.enums.NodePassEnum;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.core.enums.bpm.ProcessRunEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 数据流程实例VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="数据流程实例VO")
public class BpmDataInstRelVO extends BaseVO {


    /**
     * 应用ID
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 应用ID
     */
    @Schema(description = "应用ID")
    private Long appId;

    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

    /**
     * 表单ID
     */
    @Schema(description = "表单ID")
    private Long formId;

    /**
     * 表单动作ID
     */
    @Schema(description = "表单动作ID")
    private Long actionId;

    /**
     * 表单事件ID
     */
    @Schema(description = "表单事件ID")
    private Long eventId;

    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private Long dataId;

    /**
     * 数据名称
     */
    @Schema(description = "数据名称")
    private String dataName;

    /**
     * 流程模板ID
     */
    @Schema(description = "流程模板ID")
    private Long tempProcessId;

    /**
     * 流程实例ID
     */
    @Schema(description = "流程实例ID")
    private Long instProcessId;

    /**
     * 流程名称
     */
    @Schema(description = "流程名称")
    private String processName;

    /**
     * 流程版本
     */
    @Schema(description = "流程版本")
    private String processVersion;

    /**
     * 流程类型
     */
    @SchemaEnum(value = ProcessFlagEnum.class, description = "流程类型")
    private Integer processFlag;

    /**
     * 字段类型
     */
    @Schema(description = "字段类型")
    private Integer columnType;

    /**
     * 模块空间
     */
    @Schema(description = "模块空间")
    private String moduleSchema;

    /**
     * 流程运行标识
     */
    @SchemaEnum(value = ProcessRunEnum.class, description = "流程运行标识")
    private Integer processRunFlag;

    /**
     * 节点通过标识
     */
    @SchemaEnum(value = NodePassEnum.class, description = "节点通过标识")
    private Integer nodePassFlag;

    /**
     * 提交标识
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "提交标识")
    private Integer commitFlag;

}
