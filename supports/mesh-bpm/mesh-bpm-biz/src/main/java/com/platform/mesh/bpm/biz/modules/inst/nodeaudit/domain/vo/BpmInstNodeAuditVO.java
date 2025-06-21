package com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.vo;


import com.platform.mesh.bpm.biz.soa.node.pass.enums.NodePassEnum;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 节点审批VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程节点信息VO")
public class BpmInstNodeAuditVO extends BaseVO {


    /**
     * 节点是否通过
     */
    @SchemaEnum(value = NodePassEnum.class, description = "节点是否通过")
    private Integer nodePass;

    /**
     * 审批Pass标识
     */
    @SchemaEnum(value = NodePassEnum.class, description = "审批Pass标识")
    private Integer auditPass;

}
