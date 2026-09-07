package com.platform.mesh.crm.biz.modules.crm.precustomer.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @description 客户关系客户对象DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "crm_pre_customer", autoResultMap = true)
public class CrmPreCustomer extends AppPO {


    /**
     * 手机号码
     */
    private String phone;

    /**
     * 最新的流程实例ID
     */
    private Long instProcessId;

    /**
     * 最新的流程实例审批状态
     */
    private Integer processPass;

    /**
     * 总金额
     */
    private BigDecimal totalMoney = BigDecimal.ZERO;

    /**
     * 已收金额
     */
    private BigDecimal receivedMoney = BigDecimal.ZERO;

    /**
     * 未收金额
     */
    private BigDecimal unreceivedMoney = BigDecimal.ZERO;
}