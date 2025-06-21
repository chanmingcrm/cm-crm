package com.platform.mesh.bpm.biz.modules.inst.node.domain.dto;


import com.platform.mesh.bpm.biz.soa.node.pass.enums.NodePassEnum;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Map;


/**
 * @description 流程节点处理DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程节点处理DTO")
public class BpmInstNodeHandleDTO extends BaseDTO {

    /**
     * 节点ID
     */
    @Schema(description = "节点ID")
    private Long nodeId;

    /**
    * 当前账户ID
    */
    @Schema(description = "当前账户ID")
    private Long auditAccountId;

    /**
    * 处理标识
    */
    @SchemaEnum(value = NodePassEnum.class, description = "处理标识")
    private Integer auditPass;

    /**
    * 参数
    */
    @Schema(description = "参数")
    private Map<String,String> params;
}
