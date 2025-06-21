package com.platform.mesh.bpm.biz.modules.temp.action.domain.dto;


import com.platform.mesh.bpm.biz.soa.action.enums.ActionTypeEnum;
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
public class BpmTempActionDTO extends BaseDTO {


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
     * 动作Id
     */
    @Schema(description = "动作Id")
    private Long actionId;

    /**
     * 动作Hash
     */
    @Schema(description = "动作Hash")
    private String actionHash;

    /**
     * 动作类型
     */
    @SchemaEnum(value = ActionTypeEnum.class, description = "动作类型")
    private Integer actionType;
}
