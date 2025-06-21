package com.platform.mesh.bpm.biz.modules.temp.event.domain.dto;


import com.platform.mesh.bpm.biz.soa.event.enums.EventTypeEnum;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 动作层级DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="动作层级DTO")
public class BpmTempEventDTO extends BaseDTO {

    /**
     * 流程模板ID
     */
    @Schema(description = "流程模板ID")
    private Long tempProcessId;

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
     * 事件Hash
     */
    @Schema(description = "事件Hash")
    private String eventHash;

    /**
     * 事件类型
     */
    @SchemaEnum(value = EventTypeEnum.class, description = "事件类型")
    private Integer eventType;
}
