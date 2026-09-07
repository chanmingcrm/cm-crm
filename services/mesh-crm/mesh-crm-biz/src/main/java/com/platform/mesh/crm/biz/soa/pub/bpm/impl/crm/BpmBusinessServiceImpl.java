package com.platform.mesh.crm.biz.soa.pub.bpm.impl.crm;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.service.manual.AppServiceManual;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.bpm.api.pub.bpm.BpmFeedbackService;
import com.platform.mesh.bpm.api.pub.bpm.domain.bo.MsgBpmBO;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.crm.biz.modules.crm.onbusiness.domain.po.CrmOnBusiness;
import com.platform.mesh.crm.biz.modules.crm.onbusiness.service.ICrmOnBusinessService;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;

import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description 客户审批回调
 * @author 蝉鸣
 */
@Service
public class BpmBusinessServiceImpl implements BpmFeedbackService {

    @Autowired
    private ICrmOnBusinessService crmOnBusinessService;

    /**
     * 功能描述:
     * 〈业务名称〉
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public String actionName(){
        return SqlUtil.getTableName(CrmOnBusiness.class, TableName.class);
    }

    /**
     * 功能描述:
     * 〈添加回调处理〉
     * @param msgBpmBO msgBpmBO
     * @author 蝉鸣
     */
    @Override
    public void processStage(MsgBpmBO msgBpmBO){
        AppServiceManual appServiceManual = crmOnBusinessService.getAppServiceManual();
        AppModuleBaseBO moduleInfo = appServiceManual.getModuleInfo(msgBpmBO.getModuleId());
        if(ObjectUtil.isEmpty(moduleInfo)){
            return;
        }
        DataScopeHandler.setEnableDataScope(Boolean.FALSE);
        CrmOnBusiness crmOnBusiness = crmOnBusinessService.getById(msgBpmBO.getDataId());
        if(ObjectUtil.isEmpty(crmOnBusiness)){
            return;
        }
        String stage = AppUtil.getExtendJsonValue(msgBpmBO.getExtendMap(), StrConst.BPM_PROCESS_STAGE).toString();
        crmOnBusiness.setInstProcessId(msgBpmBO.getInstProcessId());
        crmOnBusiness.setProcessPass(msgBpmBO.getProcessPass());
        crmOnBusiness.setProcessStage(stage);
        crmOnBusinessService.updateById(crmOnBusiness);
        DataScopeHandler.unEnableDataScope();

        Map<String, Object> docMap = AppUtil.beanToMap(crmOnBusiness);
        docMap.put(StrConst.BPM_PROCESS_STAGE,stage);
        //修改ES数据
        appServiceManual.editEsData(moduleInfo.getModuleIndex(),crmOnBusiness,docMap);
    }

}
