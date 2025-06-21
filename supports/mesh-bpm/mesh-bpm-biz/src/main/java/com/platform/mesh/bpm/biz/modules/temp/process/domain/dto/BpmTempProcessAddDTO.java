package com.platform.mesh.bpm.biz.modules.temp.process.domain.dto;


import com.platform.mesh.bpm.biz.modules.temp.process.enums.ProcessFlagEnum;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 流程过程信息添加DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程过程信息添加DTO")
public class BpmTempProcessAddDTO extends BaseDTO {

    /**
     * 分组ID
     */
    @Schema(description = "分组ID")
    private Long groupId;

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
}
