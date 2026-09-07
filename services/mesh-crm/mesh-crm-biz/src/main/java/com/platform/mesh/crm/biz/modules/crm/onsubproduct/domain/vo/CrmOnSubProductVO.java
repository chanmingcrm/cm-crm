package com.platform.mesh.crm.biz.modules.crm.onsubproduct.domain.vo;

import com.platform.mesh.app.api.modules.app.domain.vo.AppVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 客户关系关联子产品VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="客户关系关联子产品VO")
public class CrmOnSubProductVO extends AppVO {


    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private Long customerId;


    /**
     * 商机ID
     */
    @Schema(description = "商机ID")
    private Long businessId;


    /**
     * 报价单ID
     */
    @Schema(description = "报价单ID")
    private Long proposalId;


    /**
     * 合同ID
     */
    @Schema(description = "合同ID")
    private Long contractId;


    /**
     * 产品ID
     */
    @Schema(description = "产品ID")
    private Long productId;

}