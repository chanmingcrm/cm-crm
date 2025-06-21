package com.platform.mesh.bpm.biz.data.nodedata.domain.dto;

import cn.hutool.json.JSONArray;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 业务数据实例流程节点表单数据DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="业务数据实例流程节点表单数据DTO")
public class BpmDataFormNodeDataDTO extends BaseDTO {


    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
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
     * 父模块ID
     */
    @Schema(description = "父模块ID")
    private Long parentModuleId;

    /**
     * 表单ID
     */
    @Schema(description = "表单ID")
    private Long formId;

    /**
     * 顶层流程模板ID
     */
    @Schema(description = "顶层流程模板ID")
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
     * 流程模板节点ID
     */
    @Schema(description = "流程模板节点ID")
    private Long tempNodeId;

    /**
     * 流程实例节点ID
     */
    @Schema(description = "流程实例节点ID")
    private Long instNodeId;

    /**
     * 表单数据
     */
    @Schema(description = "表单数据")
    private JSONArray formData;
}