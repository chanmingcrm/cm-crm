package com.platform.mesh.bpm.biz.data.form.domain.vo;


import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 数据流程表单VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="数据流程表单VO")
public class BpmDataFormRelVO extends BaseVO {


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
     * 流程模板ID
     */
    @Schema(description = "流程模板ID")
    private Long tempProcessId;

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
}
