package com.platform.mesh.bpm.biz.modules.inst.process.domain.vo;


import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @description 流程节点审批信息VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程节点审批信息VO")
public class BpmInstProcessAuditVO extends BaseVO {

    /**
     * 节点实例ID
     */
    @Schema(description = "节点实例ID")
    private Long instNodeId;

    /**
     * 是否可以审批
     */
    @Schema(description = "是否可以审批")
    private Boolean canAudit = Boolean.FALSE;
}
