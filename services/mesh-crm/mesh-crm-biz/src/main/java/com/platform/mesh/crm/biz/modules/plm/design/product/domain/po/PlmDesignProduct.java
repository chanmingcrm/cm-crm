package com.platform.mesh.crm.biz.modules.plm.design.product.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description 产品设计数据
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "plm_design_product", autoResultMap = true)
public class PlmDesignProduct extends AppPO {

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 物料ID
     */
    private Long materialId;

    /**
     * 物料数量
     */
    private Long materialNum;

    /**
     * 供应商ID
     */
    private Long supplierId;

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

}