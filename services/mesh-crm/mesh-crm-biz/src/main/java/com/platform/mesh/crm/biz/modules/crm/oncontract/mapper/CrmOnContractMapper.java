package com.platform.mesh.crm.biz.modules.crm.oncontract.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.crm.biz.modules.crm.oncontract.domain.po.CrmOnContract;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @description 客户关系合同签订
 * @author 蝉鸣
 */
public interface CrmOnContractMapper extends BaseMapper<CrmOnContract> {

    BigDecimal getTotalMoneyByCustomerId(@Param("customerId") Long customerId, @Param("passFlag") Integer passFlag);
}