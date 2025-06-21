package com.platform.mesh.bpm.biz.modules.temp.nodesub.domain.vo;


import com.platform.mesh.bpm.biz.soa.node.type.enums.NodeTypeEnum;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 节点子项VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程节点信息VO")
public class BpmTempNodeVO extends BaseVO {


    /**
     * id
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 流程模板ID
     */
    @Schema(description = "流程模板ID")
    private Long tempProcessId;

    /**
     * 流程模板Hash
     */
    @Schema(description = "流程模板Hash")
    private String tempProcessHash;

    /**
     * 节点模板ID
     */
    @Schema(description = "节点模板ID")
    private Long tempNodeId;

    /**
     * 节点模板Hash
     */
    @Schema(description = "节点模板Hash")
    private String tempNodeHash;

    /**
     * 节点标识
     */
    @SchemaEnum(value = NodeTypeEnum.class, description = "节点标识")
    private Integer nodeFlag;

    /**
     * 节点子模板流程ID
     */
    @Schema(description = "节点子模板流程ID")
    private Long tempChildProcessId;

    /**
     * 节点子模板流程Hash
     */
    @Schema(description = "节点子模板流程Hash")
    private String tempChildProcessHash;

}
