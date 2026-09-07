package com.platform.mesh.bpm.biz.soa.event.rel.domain.bo;


import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 流程节点关联信息BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程节点关联信息BO")
public class EventRelBO extends BaseBO {

    /**
     * 关联数据名称
     */
    @Schema(description = "关联数据名称")
    private String relDataName;

    /**
     * 关联数据ID
     */
    @Schema(description = "关联数据ID")
    private String relDataId;

}
