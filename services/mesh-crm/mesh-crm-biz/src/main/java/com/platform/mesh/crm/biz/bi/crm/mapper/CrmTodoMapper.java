package com.platform.mesh.crm.biz.bi.crm.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.mesh.crm.biz.bi.crm.domain.dto.TodoPDTO;
import com.platform.mesh.crm.biz.modules.crm.onbusiness.domain.po.CrmOnBusiness;
import com.platform.mesh.crm.biz.modules.crm.oncontract.domain.po.CrmOnContract;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.po.CrmPreCustomer;
import com.platform.mesh.crm.biz.modules.crm.preproposal.domain.po.CrmPreProposal;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @description 客户关系待办
 * @author 蝉鸣
 */
@Mapper
public interface CrmTodoMapper {

    Map<String, Long> todoNum(@Param("pageDTO") TodoPDTO pageDTO);

    MPage<CrmPreCustomer> todoRelCustomerToday(IPage<CrmPreCustomer> iPage, @Param("pageDTO") TodoPDTO pageDTO);

    MPage<CrmOnBusiness> todoRelBusinessToday(MPage<CrmOnBusiness> mPage,@Param("pageDTO") TodoPDTO pageDTO);

    List<CrmPreCustomer> todoAuditCustomer(@Param("ids") List<Long> ids);

    List<CrmOnBusiness> todoAuditBusiness(@Param("ids") List<Long> ids);

    List<CrmOnContract> todoAuditContract(@Param("ids") List<Long> ids);

    List<CrmPreProposal> todoAuditProposal(@Param("ids") List<Long> ids);

}
