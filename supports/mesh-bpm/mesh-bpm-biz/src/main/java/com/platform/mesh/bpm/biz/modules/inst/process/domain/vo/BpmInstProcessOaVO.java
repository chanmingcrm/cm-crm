package com.platform.mesh.bpm.biz.modules.inst.process.domain.vo;


import com.platform.mesh.bpm.biz.modules.temp.process.enums.ProcessFlagEnum;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 流程过程OA信息VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程过程OA信息VO")
public class BpmInstProcessOaVO extends BaseVO {

    /**
     * 流程实例Id
     */
    @Schema(description = "流程实例Id")
    private Long processId;

    /**
     * 父流程模板Id
     */
    @Schema(description = "父流程模板Id")
    private Long parentProcessId;

    /**
     * 流程Hash
     */
    @Schema(description = "流程Hash")
    private String processHash;

    /**
     * 流程名称
     */
    @Schema(description = "流程名称")
    private String processName;

    /**
     * 版本
     */
    @Schema(description = "流程版本")
    private String processVersion;

    /**
     * 流程图标
     */
    @Schema(description = "流程图标")
    private String processSvg;

    /**
     * 流程类型
     */
    @SchemaEnum(value = ProcessFlagEnum.class, description = "流程类型")
    private Integer processFlag;

    /**
     * 流程主题
     */
    @Schema(description = "流程主题")
    private Integer processTheme;

    /**
     * 运行标识
     */
    @Schema(description = "运行标识")
    private Integer runFlag;

    /**
     * 通过标识
     */
    @Schema(description = "通过标识")
    private Integer passFlag;

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
     * 数据ID
     */
    @Schema(description = "数据ID")
    private Long dataId;

    /**
     * 数据名称
     */
    @Schema(description = "数据名称")
    private String dataName;
}
