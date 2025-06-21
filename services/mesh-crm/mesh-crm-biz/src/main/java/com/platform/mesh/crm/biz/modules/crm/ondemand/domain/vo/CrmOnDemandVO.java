package com.platform.mesh.crm.biz.modules.crm.ondemand.domain.vo;

import com.platform.mesh.app.api.modules.app.domain.vo.AppVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 客户关系需求整理VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="客户关系需求整理VO")
public class CrmOnDemandVO extends AppVO {


    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private Long customerId;

}