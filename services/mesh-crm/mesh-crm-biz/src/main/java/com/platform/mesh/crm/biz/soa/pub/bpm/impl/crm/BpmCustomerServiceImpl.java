package com.platform.mesh.crm.biz.soa.pub.bpm.impl.crm;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.service.manual.AppServiceManual;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.app.api.modules.init.db.domain.bo.DbTransResBO;
import com.platform.mesh.app.api.modules.init.db.domain.dto.DbTransDTO;
import com.platform.mesh.app.api.modules.init.db.service.IDbService;
import com.platform.mesh.bpm.api.pub.bpm.BpmFeedbackService;
import com.platform.mesh.bpm.api.pub.bpm.domain.bo.MsgBpmBO;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.enums.bpm.ProcessPassEnum;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.po.CrmPreCustomer;
import com.platform.mesh.crm.biz.modules.crm.precustomer.service.ICrmPreCustomerService;
import com.platform.mesh.crm.biz.modules.crm.predrainagethird.service.ICrmPreDrainageThirdService;
import com.platform.mesh.es.constant.EsConst;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;

import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import com.platform.mesh.redis.service.RedissonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description 客户审批回调
 * @author 蝉鸣
 */
@Service
public class BpmCustomerServiceImpl implements BpmFeedbackService {

    @Autowired
    private ICrmPreCustomerService crmPreCustomerService;

    @Autowired
    private IDbService dbService;

    @Autowired
    private ICrmPreDrainageThirdService crmPreDrainageThirdService;

    /**
     * 功能描述:
     * 〈业务名称〉
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public String actionName(){
        return SqlUtil.getTableName(CrmPreCustomer.class, TableName.class);
    }

    /**
     * 功能描述:
     * 〈添加回调处理〉
     * @param msgBpmBO msgBpmBO
     * @author 蝉鸣
     */
    @Override
    public void add(MsgBpmBO msgBpmBO){
        AppServiceManual appServiceManual = crmPreCustomerService.getAppServiceManual();
        AppModuleBaseBO moduleInfo = appServiceManual.getModuleInfo(msgBpmBO.getModuleId());
        if(ObjectUtil.isEmpty(moduleInfo)){
            return;
        }
        DataScopeHandler.setEnableDataScope(Boolean.FALSE);
        CrmPreCustomer crmPreCustomer = crmPreCustomerService.getById(msgBpmBO.getDataId());
        if(ObjectUtil.isEmpty(crmPreCustomer)){
            return;
        }
        crmPreCustomer.setInstProcessId(msgBpmBO.getInstProcessId());
        crmPreCustomer.setProcessPass(msgBpmBO.getProcessPass());
        crmPreCustomerService.updateById(crmPreCustomer);
        DataScopeHandler.unEnableDataScope();

        Map<String, Object> docMap = AppUtil.beanToMap(crmPreCustomer);
        JSONArray array = AppUtil.getProcessPassEsData(crmPreCustomer.getProcessPass());
        docMap.put(EsConst.BPM_PROCESS_PASS_JSON,array);
        //修改ES数据
        appServiceManual.editEsData(moduleInfo.getModuleIndex(),crmPreCustomer,docMap);
    }

    /**
     * 功能描述:
     * 〈转化处理〉
     * @param msgBpmBO msgBpmBO
     * @author 蝉鸣
     */
    @Override
    public void transData(MsgBpmBO msgBpmBO) {
        if(!ProcessPassEnum.PASS.getValue().equals(msgBpmBO.getProcessPass())){
            return;
        }
        Map<String,Object> extendMap = msgBpmBO.getExtendMap();
        if(ObjectUtil.isEmpty(extendMap)){
            return;
        }
        Long transId;
        if(extendMap.containsKey(StrConst.TRANS_ID)){
            transId = MapUtil.getLong(extendMap, StrConst.TRANS_ID);
        }else{
            return;
        }
        AppServiceManual appServiceManual = crmPreCustomerService.getAppServiceManual();
        AppModuleBaseBO moduleInfo = appServiceManual.getModuleInfo(msgBpmBO.getModuleId());
        if(ObjectUtil.isEmpty(moduleInfo)){
            return;
        }
        DataScopeHandler.setEnableDataScope(Boolean.FALSE);
        CrmPreCustomer crmPreCustomer = crmPreCustomerService.getById(msgBpmBO.getDataId());
        if(ObjectUtil.isEmpty(crmPreCustomer)){
            return;
        }
        crmPreCustomer.setInstProcessId(msgBpmBO.getInstProcessId());
        crmPreCustomer.setProcessPass(msgBpmBO.getProcessPass());
        crmPreCustomerService.updateById(crmPreCustomer);
        Map<String, Object> docMap = AppUtil.beanToMap(crmPreCustomer);
        JSONArray array = AppUtil.getProcessPassEsData(crmPreCustomer.getProcessPass());
        docMap.put(EsConst.BPM_PROCESS_PASS_JSON,array);
        //修改ES数据
        appServiceManual.editEsData(moduleInfo.getModuleIndex(),crmPreCustomer,docMap);
        //将客户转化为学员
        DbTransDTO dbTransDTO = new DbTransDTO();
        dbTransDTO.setTransId(transId);
        dbTransDTO.setDataIds(CollUtil.newArrayList(msgBpmBO.getDataId()));
        DataScopeHandler.unEnableDataScope();
        RedissonUtil.publish(StrConst.BPM_MSG_ID,msgBpmBO.getId());
        //转移第三方数据
        DbTransResBO dbTransResBO = dbService.transDbData(dbTransDTO);
        crmPreDrainageThirdService.transThirdData(dbTransResBO);
    }

}
