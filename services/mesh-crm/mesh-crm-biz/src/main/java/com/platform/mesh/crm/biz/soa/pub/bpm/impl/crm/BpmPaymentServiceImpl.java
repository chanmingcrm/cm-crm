package com.platform.mesh.crm.biz.soa.pub.bpm.impl.crm;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.service.manual.AppServiceManual;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.bpm.api.pub.bpm.BpmFeedbackService;
import com.platform.mesh.bpm.api.pub.bpm.domain.bo.MsgBpmBO;
import com.platform.mesh.core.enums.bpm.ProcessPassEnum;
import com.platform.mesh.crm.biz.modules.crm.onpayment.domain.po.CrmOnPayment;
import com.platform.mesh.crm.biz.modules.crm.onpayment.service.ICrmOnPaymentService;
import com.platform.mesh.crm.biz.soa.event.crm.onpayment.CrmOnPaymentCreatedEvent;
import com.platform.mesh.es.constant.EsConst;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 发票审批回调
 * @author 蝉鸣
 */
@Service
public class BpmPaymentServiceImpl implements BpmFeedbackService {

    @Autowired
    private ICrmOnPaymentService crmOnPaymentService;

    /**
     * 功能描述:
     * 〈业务名称〉
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public String actionName(){
        return SqlUtil.getTableName(CrmOnPayment.class, TableName.class);
    }

    /**
     * 功能描述:
     * 〈添加回调处理〉
     * @param msgBpmBO msgBpmBO
     * @author 蝉鸣
     */
    @Override
    public void add(MsgBpmBO msgBpmBO){
        AppServiceManual appServiceManual = crmOnPaymentService.getAppServiceManual();
        AppModuleBaseBO moduleInfo = appServiceManual.getModuleInfo(msgBpmBO.getModuleId());
        if(ObjectUtil.isEmpty(moduleInfo)){
            return;
        }
        DataScopeHandler.setEnableDataScope(Boolean.FALSE);
        CrmOnPayment crmOnPayment = crmOnPaymentService.getById(msgBpmBO.getDataId());
        if(ObjectUtil.isEmpty(crmOnPayment)){
            return;
        }
        crmOnPayment.setInstProcessId(msgBpmBO.getInstProcessId());
        crmOnPayment.setProcessPass(msgBpmBO.getProcessPass());
        crmOnPaymentService.updateById(crmOnPayment);
        //回款通过则，初始化财务收款:状态待核销
        if(ProcessPassEnum.PASS.getValue().equals(msgBpmBO.getProcessPass())){
            SpringContextHolderUtil.publishEvent(new CrmOnPaymentCreatedEvent(crmOnPayment,new HashMap<>()));
        }
        DataScopeHandler.unEnableDataScope();

        Map<String, Object> docMap = AppUtil.beanToMap(crmOnPayment);
        JSONArray array = AppUtil.getProcessPassEsData(crmOnPayment.getProcessPass());
        docMap.put(EsConst.BPM_PROCESS_PASS_JSON,array);
        //修改ES数据
        appServiceManual.editEsData(moduleInfo.getModuleIndex(),crmOnPayment,docMap);
    }

}
