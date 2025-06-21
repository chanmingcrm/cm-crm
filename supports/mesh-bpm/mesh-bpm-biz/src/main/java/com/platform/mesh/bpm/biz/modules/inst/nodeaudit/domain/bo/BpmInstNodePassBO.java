package com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.bo;


import com.platform.mesh.bpm.biz.soa.node.pass.enums.NodePassEnum;
import com.platform.mesh.core.application.domain.bo.BaseBO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 节点审批BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程节点信息BO")
public class BpmInstNodePassBO extends BaseBO {


    /**
     * 是否通过进行下一 节点
     */
    @Schema(description = "节点是否通过进行下一节点")
    private Boolean canNext = Boolean.FALSE;

    /**
     * 是否通过审批
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "是否通过审批")
    private Boolean canPass = Boolean.FALSE;

    /**
     * 节点审批状态
     */
    @SchemaEnum(value = NodePassEnum.class, description = "节点审批状态")
    private Integer auditPass = NodePassEnum.INIT.getValue();

}
