package com.platform.mesh.crm.biz.soa.pub.bpm.impl.tmp;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.service.manual.AppServiceManual;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.bpm.api.pub.bpm.BpmFeedbackService;
import com.platform.mesh.bpm.api.pub.bpm.domain.bo.MsgBpmBO;
import com.platform.mesh.crm.biz.modules.tmp.appr.approval.domain.po.TmpOaApproval;
import com.platform.mesh.crm.biz.modules.tmp.appr.approval.service.ITmpOaApprovalService;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description 合同审批回调
 * @author 蝉鸣
 */
@Service
public class BpmApprovalServiceImpl implements BpmFeedbackService {

    @Autowired
    private ITmpOaApprovalService tmpOaApprovalService;

    /**
     * 功能描述:
     * 〈业务名称〉
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public String actionName(){
        return SqlUtil.getTableName(TmpOaApproval.class, TableName.class);
    }

    /**
     * 功能描述:
     * 〈添加回调处理〉
     * @param msgBpmBO msgBpmBO
     * @author 蝉鸣
     */
    @Override
    public void add(MsgBpmBO msgBpmBO){
        AppServiceManual appServiceManual = tmpOaApprovalService.getAppServiceManual();
        AppModuleBaseBO moduleInfo = appServiceManual.getModuleInfo(msgBpmBO.getModuleId());
        if(ObjectUtil.isEmpty(moduleInfo)){
            return;
        }
        DataScopeHandler.setEnableDataScope(Boolean.FALSE);
        TmpOaApproval oaApproval = tmpOaApprovalService.getById(msgBpmBO.getDataId());
        if(ObjectUtil.isEmpty(oaApproval)){
            DataScopeHandler.unEnableDataScope();
            return;
        }
        oaApproval.setInstProcessId(msgBpmBO.getInstProcessId());
        oaApproval.setProcessPass(msgBpmBO.getProcessPass());
        tmpOaApprovalService.updateById(oaApproval);
        DataScopeHandler.unEnableDataScope();
        Map<String, Object> docMap = AppUtil.beanToMap(oaApproval);
        //修改ES数据
        appServiceManual.editEsData(moduleInfo.getModuleIndex(),oaApproval,docMap);
    }

}
