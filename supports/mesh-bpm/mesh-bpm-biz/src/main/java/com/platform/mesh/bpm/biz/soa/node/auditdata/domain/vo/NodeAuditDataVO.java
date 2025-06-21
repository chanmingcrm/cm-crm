package com.platform.mesh.bpm.biz.soa.node.auditdata.domain.vo;


import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 动作VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程节点审批信息VO")
public class NodeAuditDataVO extends BaseVO {

    /**
     * 处理ID
     */
    @Schema(description = "处理ID")
    private Long auditDataId;

    /**
     * 处理名称
     */
    @Schema(description = "处理名称")
    private String auditDataName;

}
