package com.platform.mesh.bpm.biz.modules.inst.action.domain.vo;


import com.platform.mesh.bpm.biz.soa.action.enums.ActionTypeEnum;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


/**
 * @description 动作VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="动作VO")
public class BpmInstActionVO extends BaseVO {


    /**
     * id
     */
    @Schema(description = "")
    private Long id;

    /**
     * 动作Id
     */
    @Schema(description = "动作Id")
    private Long actionId;

    /**
     * 流程实例节点Id
     */
    @Schema(description = "流程实例节点Id")
    private String instNodeId;

    /**
     * 动作类型
     */
    @SchemaEnum(value = ActionTypeEnum.class, description = "动作类型")
    private Integer actionType;
}
