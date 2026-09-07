package com.platform.mesh.crm.biz.modules.plm.product.category.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @description 产品大类数据
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "plm_category", autoResultMap = true)
public class PlmProductCategory extends AppPO {


    /**
     * 产品ID
     */
    private Long productId;

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

}
