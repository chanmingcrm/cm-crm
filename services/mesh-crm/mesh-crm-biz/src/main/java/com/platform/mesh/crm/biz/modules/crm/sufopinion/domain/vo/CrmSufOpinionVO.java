package com.platform.mesh.crm.biz.modules.crm.sufopinion.domain.vo;

import com.platform.mesh.app.api.modules.app.domain.vo.AppVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 客户关系意见评价VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="客户关系意见评价VO")
public class CrmSufOpinionVO extends AppVO {


    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private Long customerId;


    /**
     * 合同ID
     */
    @Schema(description = "合同ID")
    private Long contractId;


    /**
     * 订单ID
     */
    @Schema(description = "订单ID")
    private Long orderId;

}