package com.platform.mesh.bpm.biz.modules.inst.variable.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.core.enums.logic.type.LogicTypeEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @description 变量VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="变量VO")
public class BpmInstVariableVO extends BaseVO {

    /**
     * id
     */
    @Schema(description = "")
    private Long id;

    /**
     * 流程实例Hash
     */
    @Schema(description = "流程实例Hash")
    private Long instProcessId;

    /**
     * 变量Id
     */
    @Schema(description = "变量Id")
    private Long variableId;

    /**
     * 变量父Id
     */
    @Schema(description = "变量父Id")
    private Long parentId;

    /**
     * 流程实例线Hash
     */
    @Schema(description = "流程实例线Hash")
    private Long instLineId;

    /**
     * 变量类型
     */
    @Schema(description = "变量类型")
    private String variableName;

    /**
     * 变量类型
     */
    @SchemaEnum(value = LogicTypeEnum.class, description = "变量逻辑类型")
    private Integer variableType;
}
