package com.platform.mesh.crm.biz.soa.pub.upms.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.core.constants.ServiceNameConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.crm.biz.init.db.service.ICrmDbService;
import com.platform.mesh.crm.biz.modules.crm.predrainagethird.service.ICrmPreDrainageThirdService;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgLevelBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberTransBO;
import com.platform.mesh.upms.api.pub.upms.UpmsFeedbackService;
import com.platform.mesh.upms.api.pub.upms.bo.MsgUpmsBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description 客户审批回调
 * @author 蝉鸣
 */
@Service
public class CrmServiceImpl implements UpmsFeedbackService {


    @Autowired
    private ICrmDbService dbService;

    @Autowired
    private ICrmPreDrainageThirdService crmPreDrainageThirdService;

    /**
     * 功能描述:
     * 〈业务名称〉
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public String actionName(){
        return ServiceNameConst.CRM_SERVICE;
    }

    /**
     * 功能描述:
     * 〈转移组织数据回调处理〉
     * @param msgUpmsBO msgUpmsBO
     * @author 蝉鸣
     */
    @Override
    public void syncUserName(MsgUpmsBO msgUpmsBO){
        System.out.println("客户执行执行");
        Map<String, Object> extendJson = msgUpmsBO.getExtendJson();
        if(!extendJson.containsKey(StrConst.VALUE)){
            return;
        }
        Object value = extendJson.get(StrConst.VALUE);
        OrgMemberBO memberBO = BeanUtil.toBean(value, OrgMemberBO.class);
        dbService.syncUserName(memberBO);
    }

    /**
     * 功能描述:
     * 〈转移组织数据回调处理〉
     * @param msgUpmsBO msgUpmsBO
     * @author 蝉鸣
     */
    @Override
    public void syncOrgName(MsgUpmsBO msgUpmsBO){
        System.out.println("客户执行执行");
        Map<String, Object> extendJson = msgUpmsBO.getExtendJson();
        if(!extendJson.containsKey(StrConst.VALUE)){
            return;
        }
        Object value = extendJson.get(StrConst.VALUE);
        OrgLevelBO levelBO = BeanUtil.toBean(value, OrgLevelBO.class);
        dbService.syncOrgName(levelBO);
    }

    /**
     * 功能描述:
     * 〈转移组织数据回调处理〉
     * @param msgUpmsBO msgUpmsBO
     * @author 蝉鸣
     */
    @Override
    public void transOrgData(MsgUpmsBO msgUpmsBO){
        System.out.println("客户执行执行");
        Map<String, Object> extendJson = msgUpmsBO.getExtendJson();
        if(!extendJson.containsKey(StrConst.VALUE)){
            return;
        }
        Object value = extendJson.get(StrConst.VALUE);
        OrgMemberTransBO transBO = BeanUtil.toBean(value, OrgMemberTransBO.class);
        dbService.transOrgData(transBO);
    }

    /**
     * 功能描述:
     * 〈删除第三方关联数据〉
     * @param msgUpmsBO msgUpmsBO
     * @author 蝉鸣
     */
    @Override
    public void delCrmSyncThirdDataRel(MsgUpmsBO msgUpmsBO){
        Map<String, Object> extendJson = msgUpmsBO.getExtendJson();
        if(!extendJson.containsKey(StrConst.VALUE)){
            return;
        }
        Object value = extendJson.get(StrConst.VALUE);
        JSONArray objects = JSONUtil.parseArray(value);
        if(CollUtil.isEmpty(objects)){
            return;
        }
        List<Long> dataIs = objects.toList(Long.class);
        Object moduleStr = extendJson.get(StrConst.MODULE_ID);
        Long moduleId = Long.parseLong(moduleStr.toString());
        crmPreDrainageThirdService.delCrmSyncThirdDataRel(moduleId,dataIs);
    }

}
