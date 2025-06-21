package com.platform.mesh.bpm.api.modules.inst.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
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
public class BpmProcessDataDTO extends BaseDTO {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long instProcessId;

    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private Long dataId;
}