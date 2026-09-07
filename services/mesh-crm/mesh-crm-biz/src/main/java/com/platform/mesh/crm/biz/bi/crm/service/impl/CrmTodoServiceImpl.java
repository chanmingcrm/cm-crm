package com.platform.mesh.crm.biz.bi.crm.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.crm.biz.bi.crm.domain.dto.TodoPDTO;
import com.platform.mesh.crm.biz.bi.crm.mapper.CrmTodoMapper;
import com.platform.mesh.crm.biz.bi.crm.service.ICrmTodoService;
import com.platform.mesh.crm.biz.bi.crm.service.manual.CrmTodoServiceManual;
import com.platform.mesh.crm.biz.modules.crm.onbusiness.domain.po.CrmOnBusiness;
import com.platform.mesh.crm.biz.modules.crm.oncontract.domain.po.CrmOnContract;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.po.CrmPreCustomer;
import com.platform.mesh.crm.biz.modules.crm.preproposal.domain.po.CrmPreProposal;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系BI
 * @author 蝉鸣
 */
@Service
public class CrmTodoServiceImpl implements ICrmTodoService {

    @Autowired
    private CrmTodoServiceManual crmTodoServiceManual;

    @Autowired
    private CrmTodoMapper crmTodoMapper;


    /**
     * 功能描述:
     * 〈获取待办数量角标〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Object}
     * @author 蝉鸣
     */
    @Override
    public Object todoNum(TodoPDTO pageDTO) {
        //设置请求参数
        crmTodoServiceManual.setTodoDTO(pageDTO);
        //今日需联系客户-待办
        Map<String, Long> busMap = crmTodoMapper.todoNum(pageDTO);
        //获取审批待办
        Map<String, Long> bpmTodoMap = crmTodoServiceManual.getBpmTodoNum(pageDTO);
        busMap.putAll(bpmTodoMap);
        //待办总计
        //只统计需要的数据
        List<String> totalList = CollUtil.newArrayList();
        totalList.add("customer_todo");
        totalList.add("business_todo");
        totalList.add("bpm_crm_pre_customer");
        totalList.add("bpm_crm_on_contract");
        totalList.add("bpm_crm_pre_proposal");
        Long total = busMap
                .entrySet().stream()
                .filter(map->totalList.contains(map.getKey()))
                .mapToLong(Map.Entry::getValue)
                .filter(ObjectUtil::isNotEmpty)
                .sum();
        busMap.put(StrConst.TOTAL,total);
        return busMap;
    }

    /**
     * 功能描述:
     * 〈今日需联系客户〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<Object>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<Object> todoRelCustomerToday(TodoPDTO pageDTO) {
        //重置pageDTO参数
        MPage<CrmPreCustomer> mPage = crmTodoServiceManual.getPageDTO(pageDTO, CrmPreCustomer.class);
        //查询今日需联系的客户
        MPage<CrmPreCustomer> crmPreCustomers= crmTodoMapper.todoRelCustomerToday(mPage, pageDTO);
        //封装返回数据
        return crmTodoServiceManual.packRelVO(crmPreCustomers,crmPreCustomers.getRecords());
    }

    /**
     * 功能描述:
     * 〈今日需联系商机〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<Object>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<Object> todoRelBusinessToday(TodoPDTO pageDTO) {
        //重置pageDTO参数
        MPage<CrmOnBusiness> mPage = crmTodoServiceManual.getPageDTO(pageDTO, CrmOnBusiness.class);
        //查询今日需联系的商机
        MPage<CrmOnBusiness> crmOnBusinesses= crmTodoMapper.todoRelBusinessToday(mPage, pageDTO);
        //封装返回数据
        return crmTodoServiceManual.packRelVO(crmOnBusinesses,crmOnBusinesses.getRecords());
    }

    /**
     * 功能描述:
     * 〈待审核客户〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<Object>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<Object> todoAuditCustomer(TodoPDTO pageDTO) {
        //设置数据参数
        crmTodoServiceManual.setTodoDTO(pageDTO);
        //查询待审核的客户ID
        String moduleSchema = SqlUtil.getTableName(CrmPreCustomer.class, TableName.class);
        PageVO<Long> idPage = crmTodoServiceManual.getBpmDataIds(pageDTO,moduleSchema,pageDTO.getDataIds());
        if(CollUtil.isEmpty(idPage.getRecords())){
            return new PageVO<>();
        }
        //查询今日需联系的客户
        List<CrmPreCustomer> records= crmTodoMapper.todoAuditCustomer(idPage.getRecords());
        //封装返回数据
        return crmTodoServiceManual.packRelVO(idPage,records);
    }

    /**
     * 功能描述:
     * 〈待审核商机〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<Object>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<Object> todoAuditBusiness(TodoPDTO pageDTO) {
        //设置数据参数
        crmTodoServiceManual.setTodoDTO(pageDTO);
        //查询待审核的客户ID
        String moduleSchema = SqlUtil.getTableName(CrmOnBusiness.class, TableName.class);
        PageVO<Long> idPage = crmTodoServiceManual.getBpmDataIds(pageDTO,moduleSchema,pageDTO.getDataIds());
        if(CollUtil.isEmpty(idPage.getRecords())){
            return new PageVO<>();
        }
        //查询今日需联系的客户
        List<CrmOnBusiness> records= crmTodoMapper.todoAuditBusiness(idPage.getRecords());
        //封装返回数据
        return crmTodoServiceManual.packRelVO(idPage,records);
    }

    /**
     * 功能描述:
     * 〈待审核合同〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<Object>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<Object> todoAuditContract(TodoPDTO pageDTO) {
        //设置数据参数
        crmTodoServiceManual.setTodoDTO(pageDTO);
        //查询待审核的客户ID
        String moduleSchema = SqlUtil.getTableName(CrmOnContract.class, TableName.class);
        PageVO<Long> idPage = crmTodoServiceManual.getBpmDataIds(pageDTO,moduleSchema,pageDTO.getDataIds());
        if(CollUtil.isEmpty(idPage.getRecords())){
            return new PageVO<>();
        }
        //查询今日需联系的客户
        List<CrmOnContract> records= crmTodoMapper.todoAuditContract(idPage.getRecords());
        //封装返回数据
        return crmTodoServiceManual.packRelVO(idPage,records);
    }

    /**
     * 功能描述:
     * 〈待审核报价单〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<Object>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<Object> todoAuditProposal(TodoPDTO pageDTO) {
        //设置数据参数
        crmTodoServiceManual.setTodoDTO(pageDTO);
        //查询待审核的客户ID
        String moduleSchema = SqlUtil.getTableName(CrmPreProposal.class, TableName.class);
        PageVO<Long> idPage = crmTodoServiceManual.getBpmDataIds(pageDTO,moduleSchema,pageDTO.getDataIds());
        if(CollUtil.isEmpty(idPage.getRecords())){
            return new PageVO<>();
        }
        //查询今日需联系的客户
        List<CrmPreProposal> records= crmTodoMapper.todoAuditProposal(idPage.getRecords());
        //封装返回数据
        return crmTodoServiceManual.packRelVO(idPage,records);
    }

}
