package com.platform.mesh.crm.biz.modules.plm.product.base.domain.vo;

import com.platform.mesh.app.api.modules.app.domain.vo.AppVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @description 供应链产品VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="供应链产品VO")
public class PlmProductVO extends AppVO {


    /**
     * 成本金额
     */
    @Schema(description = "成本金额")
    private BigDecimal costMoney = BigDecimal.ZERO;

    /**
     * 销售金额
     */
    @Schema(description = "销售金额")
    private BigDecimal saleMoney = BigDecimal.ZERO;

    /**
     * 折扣金额
     */
    @Schema(description = "折扣金额")
    private BigDecimal discountMoney = BigDecimal.ZERO;

    /**
     * 利润金额
     */
    @Schema(description = "利润金额")
    private BigDecimal profitMoney = BigDecimal.ZERO;

    /**
     * 上架/下架
     */
    @Schema(description = "上架/下架")
    private Integer onOffFlag;

    /**
     * 产品数量
     */
    @Schema(description = "产品数量")
    private Long productNum;

}