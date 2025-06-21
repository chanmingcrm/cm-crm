package com.platform.mesh.bpm.biz.modules.inst.varrefer.domain.vo;


import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.core.enums.logic.ref.LogicRefEnum;
import com.platform.mesh.core.enums.logic.type.LogicTypeEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 变量值VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="变量值VO")
public class BpmInstVarReferVO extends BaseVO {

    /**
     * id
     */
    @Schema(description = "")
    private Long id;

    /**
     * 流程实例Id
     */
    @Schema(description = "流程实例Id")
    private String instProcessId;

    /**
     * 变量父Id
     */
    @Schema(description = "变量父Id")
    private String parentId;

    /**
     * 流程实例线Id
     */
    @Schema(description = "流程实例线Id")
    private Long instLineId;

    /**
     * 流程实例变量Id
     */
    @Schema(description = "流程实例变量Id")
    private Long instVariableId;

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
