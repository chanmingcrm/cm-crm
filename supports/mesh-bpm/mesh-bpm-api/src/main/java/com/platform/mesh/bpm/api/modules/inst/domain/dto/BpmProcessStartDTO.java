package com.platform.mesh.bpm.api.modules.inst.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 流程实例对象DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="流程实例对象DTO")
public class BpmProcessStartDTO extends BaseDTO {


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
     * 流程模板ID
     */
    @Schema(description = "流程模板ID")
    private Long tempProcessId;

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
     * 是否自动执行
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "是否自动执行")
    private Integer autoStart;

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
     * 扩展参数
     */
    @Schema(description = "扩展参数")
    private Object extendJson;
}