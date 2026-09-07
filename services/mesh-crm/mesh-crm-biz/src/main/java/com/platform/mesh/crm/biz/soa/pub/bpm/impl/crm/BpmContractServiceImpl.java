package com.platform.mesh.crm.biz.soa.pub.bpm.impl.crm;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.service.manual.AppServiceManual;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.core.enums.bpm.ProcessPassEnum;
import com.platform.mesh.bpm.api.pub.bpm.BpmFeedbackService;
import com.platform.mesh.bpm.api.pub.bpm.domain.bo.MsgBpmBO;
import com.platform.mesh.crm.biz.modules.crm.oncontract.domain.po.CrmOnContract;
import com.platform.mesh.crm.biz.modules.crm.oncontract.domain.vo.CrmOnContractVO;
import com.platform.mesh.crm.biz.modules.crm.oncontract.service.ICrmOnContractService;
import com.platform.mesh.crm.biz.modules.crm.precustomer.enums.ConfirmFlagEnum;
import com.platform.mesh.crm.biz.modules.crm.precustomer.service.ICrmPreCustomerService;
import com.platform.mesh.crm.biz.soa.event.crm.oncontract.CrmOnContractCreatedEvent;
import com.platform.mesh.es.constant.EsConst;
import com.platform.mesh.es.domain.dto.EsDocSGetDTO;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;

import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @description 合同审批回调
 * @author 蝉鸣
 */
@Service
public class BpmContractServiceImpl implements BpmFeedbackService {

    @Autowired
    private ICrmOnContractService crmOnContractService;

    @Autowired
    private ICrmPreCustomerService crmPreCustomerService;

    /**
     * 功能描述:
     * 〈业务名称〉
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public String actionName(){
        return SqlUtil.getTableName(CrmOnContract.class, TableName.class);
    }

    /**
     * 功能描述:
     * 〈添加回调处理〉
     * @param msgBpmBO msgBpmBO
     * @author 蝉鸣
     */
    @Override
    public void add(MsgBpmBO msgBpmBO){
        AppServiceManual appServiceManual = crmOnContractService.getAppServiceManual();
        AppModuleBaseBO moduleInfo = appServiceManual.getModuleInfo(msgBpmBO.getModuleId());
        if(ObjectUtil.isEmpty(moduleInfo)){
            return;
        }
        DataScopeHandler.setEnableDataScope(Boolean.FALSE);
        CrmOnContract crmOnContract = crmOnContractService.getById(msgBpmBO.getDataId());
        if(ObjectUtil.isEmpty(crmOnContract)){
            return;
        }
        crmOnContract.setInstProcessId(msgBpmBO.getInstProcessId());
        crmOnContract.setProcessPass(msgBpmBO.getProcessPass());
        BigDecimal totalMoney = crmOnContractService.getTotalMoneyByCustomerId(crmOnContract.getCustomerId());
        //审批通过则修改客户为已成交
        if(ProcessPassEnum.PASS.getValue().equals(msgBpmBO.getProcessPass())){
            crmPreCustomerService.updateConfirmFlag(crmOnContract.getCustomerId(), ConfirmFlagEnum.YES, totalMoney);
            //查询详细信息
            EsDocSGetDTO getDTO = new EsDocSGetDTO();
            getDTO.setModuleId(crmOnContract.getModuleId());
            getDTO.setDataId(crmOnContract.getId());
            CrmOnContractVO dataInfo = crmOnContractService.getDataInfoById(getDTO, CrmOnContractVO.class);
            //发布事件
            SpringContextHolderUtil.publishEvent(new CrmOnContractCreatedEvent(crmOnContract,dataInfo.getEsData()));
        }
        crmOnContractService.updateById(crmOnContract);
        DataScopeHandler.unEnableDataScope();

        Map<String, Object> docMap = AppUtil.beanToMap(crmOnContract);
        JSONArray array = AppUtil.getProcessPassEsData(crmOnContract.getProcessPass());
        docMap.put(EsConst.BPM_PROCESS_PASS_JSON,array);
        //修改ES数据
        appServiceManual.editEsData(moduleInfo.getModuleIndex(),crmOnContract,docMap);
    }

}
