package com.platform.mesh.bpm.biz.modules.temp.varrefer.domain.dto;


import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.enums.logic.ref.LogicRefEnum;
import com.platform.mesh.core.enums.logic.type.LogicTypeEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 变量值DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="变量参照值DTO")
public class BpmTempVarReferDTO extends BaseDTO {

    /**
     * 流程模板ID
     */
    @Schema(description = "流程模板ID")
    private Long tempProcessId;

    /**
     * 流程模板线Hash
     */
    @Schema(description = "流程模板线Hash")
    private String tempLineHash;

    /**
     * 变量参照Hash
     */
    @Schema(description = "变量参照Hash")
    private String varReferHash;

    /**
     * 变量父Hash
     */
    @Schema(description = "变量父Hash")
    private String parentHash;

    /**
     * 流程模板变量Hash
     */
    @Schema(description = "流程模板变量Hash")
    private String tempVariableHash;

    /**
     * 变量逻辑类型
     */
    @SchemaEnum(value = LogicTypeEnum.class, description = "变量逻辑类型")
    private Integer variableType;

    /**
     * 变量逻辑关系
     */
    @SchemaEnum(value = LogicRefEnum.class, description = "变量逻辑关系")
    private Integer variableRef;

    /**
     * 流程实例变量参照值
     */
    @Schema(description = "流程实例变量参照值")
    private String variableRefer;

    /**
     * 流程实例变量
     */
    @Schema(description = "流程实例变量")
    private String variableName;
}
