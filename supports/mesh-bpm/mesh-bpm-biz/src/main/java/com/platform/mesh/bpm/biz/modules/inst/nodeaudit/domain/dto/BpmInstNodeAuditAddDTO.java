package com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.dto;


import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @description 流程节点审批信息DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程节点信息DTO")
public class BpmInstNodeAuditAddDTO extends BaseDTO {

    /**
     * 节点实例ID
     */
    @Schema(description = "节点实例ID")
    private Long instNodeId;

    /**
     * 审批数据ID
     */
    @Schema(description = "审批数据ID")
    private List<Long> auditDataIds;
}

