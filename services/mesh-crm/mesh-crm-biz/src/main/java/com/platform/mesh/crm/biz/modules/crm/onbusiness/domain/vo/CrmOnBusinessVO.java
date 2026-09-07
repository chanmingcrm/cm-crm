package com.platform.mesh.crm.biz.modules.crm.onbusiness.domain.vo;

import com.platform.mesh.app.api.modules.app.domain.vo.AppVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @description 客户关系商机跟进VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="客户关系商机跟进VO")
public class CrmOnBusinessVO extends AppVO {

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private Long customerId;

    /**
     * 总计金额
     */
    @Schema(description = "总计金额")
    private BigDecimal totalMoney = BigDecimal.ZERO;

    /**
     * 折扣金额
     */
    @Schema(description = "折扣金额")
    private BigDecimal discountMoney = BigDecimal.ZERO;

    /**
     * 实际金额
     */
    @Schema(description = "实际金额")
    private BigDecimal realMoney = BigDecimal.ZERO;
}