package com.platform.mesh.crm.biz.modules.crm.predrainage.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 客户关系活动引流VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="客户关系活动引流VO")
public class CrmPreDrainageGetDTO extends BaseDTO {

    /**
     * 第三方ID
     */
    private String thirdId;

}