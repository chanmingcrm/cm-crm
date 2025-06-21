package com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.dto;


import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessDesignDTO;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @description 流程节点子项信息DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程节点信息DTO")
public class BpmInstNodeSubDTO extends BaseDTO {

    /**
     * 节点ID
     */
    @Schema(description = "节点ID")
    private Long instNodeId;

    /**
     * 流程子项
     */
    @Schema(description = "流程子项")
    private List<BpmTempProcessDesignDTO> processAddDTOs;
}
