package com.platform.mesh.crm.biz.modules.plm.product.base.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @description 供应链产品DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "plm_product", autoResultMap = true)
public class PlmProduct extends AppPO {

    /**
     * 成本金额
     */
    private BigDecimal costMoney = BigDecimal.ZERO;

    /**
     * 销售金额
     */
    private BigDecimal saleMoney = BigDecimal.ZERO;

    /**
     * 折扣金额
     */
    private BigDecimal discountMoney = BigDecimal.ZERO;

    /**
     * 利润金额
     */
    private BigDecimal profitMoney = BigDecimal.ZERO;

    /**
     * 上架/下架
     */
    private Integer onOffFlag;

    /**
     * 产品标识
     */
    private Integer productFlag;

    /**
     * 产品数量
     */
    private Long productNum;

}