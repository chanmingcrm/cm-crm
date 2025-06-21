package com.platform.mesh.bpm.biz.modules.temp.action.domain.vo;


import com.platform.mesh.bpm.biz.soa.action.enums.ActionTypeEnum;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 动作VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="动作VO")
public class BpmTempActionVO extends BaseVO {

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
     * 动作Hash
     */
    @Schema(description = "动作Hash")
    private String actionHash;

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
     * 动作类型
     */
    @SchemaEnum(value = ActionTypeEnum.class, description = "动作类型")
    private Integer actionType;
}
