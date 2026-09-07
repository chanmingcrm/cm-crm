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
import com.platform.mesh.crm.biz.modules.crm.oninvoice.domain.po.CrmOnInvoice;
import com.platform.mesh.crm.biz.modules.crm.oninvoice.service.ICrmOnInvoiceService;
import com.platform.mesh.es.constant.EsConst;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;

import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description 发票审批回调
 * @author 蝉鸣
 */
@Service
public class BpmInvoiceServiceImpl implements BpmFeedbackService {

    @Autowired
    private ICrmOnInvoiceService crmOnInvoiceService;

    /**
     * 功能描述:
     * 〈业务名称〉
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public String actionName(){
        return SqlUtil.getTableName(CrmOnInvoice.class, TableName.class);
    }

    /**
     * 功能描述:
     * 〈添加回调处理〉
     * @param msgBpmBO msgBpmBO
     * @author 蝉鸣
     */
    @Override
    public void add(MsgBpmBO msgBpmBO){
        AppServiceManual appServiceManual = crmOnInvoiceService.getAppServiceManual();
        AppModuleBaseBO moduleInfo = appServiceManual.getModuleInfo(msgBpmBO.getModuleId());
        if(ObjectUtil.isEmpty(moduleInfo)){
            return;
        }
        DataScopeHandler.setEnableDataScope(Boolean.FALSE);
        CrmOnInvoice crmOnInvoice = crmOnInvoiceService.getById(msgBpmBO.getDataId());
        if(ObjectUtil.isEmpty(crmOnInvoice)){
            return;
        }
        crmOnInvoice.setInstProcessId(msgBpmBO.getInstProcessId());
        crmOnInvoice.setProcessPass(msgBpmBO.getProcessPass());
        //汇总订单所有开票金额，并更新订单的开票金额
        if(ProcessPassEnum.PASS.getValue().equals(msgBpmBO.getProcessPass())){
            crmOnInvoiceService.updateInvoiceMoney(crmOnInvoice.getOrderId());
        }
        crmOnInvoiceService.updateById(crmOnInvoice);
        DataScopeHandler.unEnableDataScope();

        Map<String, Object> docMap = AppUtil.beanToMap(crmOnInvoice);
        JSONArray array = AppUtil.getProcessPassEsData(crmOnInvoice.getProcessPass());
        docMap.put(EsConst.BPM_PROCESS_PASS_JSON,array);
        //修改ES数据
        appServiceManual.editEsData(moduleInfo.getModuleIndex(),crmOnInvoice,docMap);
    }

}
