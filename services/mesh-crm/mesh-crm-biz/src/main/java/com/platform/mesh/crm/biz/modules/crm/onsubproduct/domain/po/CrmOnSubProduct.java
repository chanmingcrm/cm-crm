package com.platform.mesh.crm.biz.modules.crm.onsubproduct.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @description 客户关系关联子产品DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "crm_on_sub_product", autoResultMap = true)
public class CrmOnSubProduct extends AppPO {


    /**
     * 关联模块ID
     */
    private Long relModuleId;

    /**
     * 关联数据ID
     */
    private Long relDataId;

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 商机ID
     */
    private Long businessId;

    /**
     * 报价ID
     */
    private Long proposalId;

    /**
     * 合同ID
     */
    private Long contractId;

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * 总计金额
     */
    private BigDecimal totalMoney = BigDecimal.ZERO;

    /**
     * 折扣金额
     */
    private BigDecimal discountMoney = BigDecimal.ZERO;

    /**
     * 实际金额
     */
    private BigDecimal realMoney = BigDecimal.ZERO;

    /**
     * 成本金额
     */
    private BigDecimal costMoney = BigDecimal.ZERO;

    /**
     * 销售金额
     */
    private BigDecimal saleMoney = BigDecimal.ZERO;

    /**
     * 利润金额
     */
    private BigDecimal profitMoney = BigDecimal.ZERO;

    /**
     * 数量
     */
    private BigDecimal orderNum = BigDecimal.ZERO;

    /**
     * 单位
     */
    private Long orderUnit;



}