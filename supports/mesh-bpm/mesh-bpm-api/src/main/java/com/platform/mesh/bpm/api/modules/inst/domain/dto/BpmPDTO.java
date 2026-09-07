package com.platform.mesh.bpm.api.modules.inst.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 流程实例查询DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="流程实例查询DTO")
public class BpmPDTO extends PageDTO {


    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

    /**
     * 模块域
     */
    @Schema(description = "模块域")
    private String moduleSchema;

    /**
     * 流程运行状态
     */
    @Schema(description = "流程运行状态")
    private Integer processRunFlag;

    /**
     * 审批人
     */
    @Schema(description = "审批人")
    private List<Long> auditDataIds;

}