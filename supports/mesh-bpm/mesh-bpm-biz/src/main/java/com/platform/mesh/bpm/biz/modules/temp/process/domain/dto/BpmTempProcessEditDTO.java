package com.platform.mesh.bpm.biz.modules.temp.process.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 流程过程信息编辑DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程过程信息编辑DTO")
public class BpmTempProcessEditDTO extends BpmTempProcessAddDTO {

    /**
     * 原分组ID
     */
    @Schema(description = "原分组ID")
    private Long originGroupId;

    /**
     * 父流程模板Id
     */
    @Schema(description = "父流程模板Id")
    private Long parentProcessId;

    /**
     * 流程模板Id
     */
    @Schema(description = "流程模板Id")
    private Long processId;
}
