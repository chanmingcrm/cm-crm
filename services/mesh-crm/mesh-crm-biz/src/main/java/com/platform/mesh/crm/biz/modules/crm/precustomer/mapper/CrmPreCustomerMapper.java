package com.platform.mesh.crm.biz.modules.crm.precustomer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.dto.CheckDTO;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.po.CrmPreCustomer;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.vo.AbatractVO;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.vo.CheckVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 客户关系客户对象
 * @author 蝉鸣
 */
public interface CrmPreCustomerMapper extends BaseMapper<CrmPreCustomer> {

    List<CheckVO> checkPreCustomer(@Param("checkDTO") CheckDTO checkDTO);

    AbatractVO abstractPreCustomer(@Param("customerId") Long customerId);
}
