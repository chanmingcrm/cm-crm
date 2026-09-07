package com.platform.mesh.crm.biz.modules.crm.onpayment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.crm.biz.modules.crm.onpayment.domain.bo.PaymentSumBO;
import com.platform.mesh.crm.biz.modules.crm.onpayment.domain.po.CrmOnPayment;
import org.apache.ibatis.annotations.Param;

/**
 * @description 客户关系款项记录
 * @author 蝉鸣
 */
public interface CrmOnPaymentMapper extends BaseMapper<CrmOnPayment> {

    PaymentSumBO getSumReceivedMoneyById(@Param("paymentId") Long paymentId, @Param("passFlag") Integer passFlag);
}