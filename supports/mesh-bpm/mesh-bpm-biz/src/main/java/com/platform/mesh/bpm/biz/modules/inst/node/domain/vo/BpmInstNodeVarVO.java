package com.platform.mesh.bpm.biz.modules.inst.node.domain.vo;


import com.platform.mesh.bpm.biz.modules.inst.node.enums.InstNodeInEnum;
import com.platform.mesh.bpm.biz.modules.inst.node.enums.InstNodeOutEnum;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessDesignVO;
import com.platform.mesh.bpm.biz.soa.node.pass.enums.NodePassEnum;
import com.platform.mesh.bpm.biz.soa.node.run.enums.NodeRunEnum;
import com.platform.mesh.bpm.biz.soa.node.type.enums.NodeTypeEnum;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @description BpmInstNodeVarVO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程节点变量信息VO")
public class BpmInstNodeVarVO extends BaseVO {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 节点所有变量
     */
    @Schema(description = "节点所有变量")
    private List<String> nodeOutVars;

    /**
     * 节点所有出线
     */
    @Schema(description = "节点所有出线")
    private List<BpmInstNodeLineVO> nodeOutLineVOS;
}
