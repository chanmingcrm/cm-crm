package com.platform.mesh.bpm.biz.data.inst.domain.dto;


import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 业务数据实例流程数据DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="业务数据实例流程数据DTO")
public class BpmDataInstRelDTO extends BaseDTO {


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
    @Schema(description = "流程类型")
    private Integer processFlag;
}
