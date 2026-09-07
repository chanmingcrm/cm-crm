package com.platform.mesh.bpm.biz.modules.temp.event.domain.vo;


import com.platform.mesh.bpm.biz.soa.event.rel.enums.EventRelEnum;
import com.platform.mesh.bpm.biz.soa.event.type.enums.EventTypeEnum;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 事件VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="动作VO")
public class BpmTempEventVO extends BaseVO {

    /**
     * id
     */
    @Schema(description = "")
    private Long id;

    /**
     * 事件Hash
     */
    @Schema(description = "事件Hash")
    private String eventHash;

    /**
     * 流程模板Hash
     */
    @Schema(description = "流程模板Hash")
    private String tempProcessHash;

    /**
     * 流程模板节点Hash
     */
    @Schema(description = "流程模板节点Hash")
    private String tempNodeHash;

    /**
     * 流程模板动作Hash
     */
    @Schema(description = "流程模板动作Hash")
    private String tempActionHash;

    /**
     * 事件类型
     */
    @SchemaEnum(value = EventTypeEnum.class, description = "事件类型")
    private Integer eventType;

    /**
     * 关联类型
     */
    @SchemaEnum(value = EventRelEnum.class, description = "关联类型")
    private Integer relDataType;

    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private Object relDataJson;
}
