package com.platform.mesh.bpm.biz.modules.inst.event.domain.vo;


import com.platform.mesh.bpm.biz.soa.event.enums.EventTypeEnum;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @description 动作VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="动作VO")
public class BpmInstEventVO extends BaseVO {


    /**
     * id
     */
    @Schema(description = "")
    private Long id;

    /**
     * 流程模板Id
     */
    @Schema(description = "流程模板Id")
    private Long instProcessId;

    /**
     * 流程模板节点Id
     */
    @Schema(description = "流程模板节点Id")
    private Long instNodeId;

    /**
     * 流程模板动作Id
     */
    @Schema(description = "流程模板动作Id")
    private Long instActionId;

    /**
     * 事件类型
     */
    @SchemaEnum(value = EventTypeEnum.class, description = "事件类型")
    private Integer eventType;
}
