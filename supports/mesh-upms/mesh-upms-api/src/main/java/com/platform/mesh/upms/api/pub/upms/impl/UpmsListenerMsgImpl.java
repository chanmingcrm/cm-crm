package com.platform.mesh.upms.api.pub.upms.impl;

import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.upms.api.pub.upms.UpmsFeedbackService;
import com.platform.mesh.upms.api.pub.upms.bo.MsgUpmsBO;
import com.platform.mesh.upms.api.pub.upms.enums.UpmsActionEnum;
import com.platform.mesh.upms.api.pub.upms.factory.UpmsFeedbackFactory;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description C工厂实现
 * @author 蝉鸣
 */
@Service
public class UpmsListenerMsgImpl implements MessageListener<MsgUpmsBO> {

    private final UpmsFeedbackFactory upmsFeedbackFactory;

    @Autowired
    public UpmsListenerMsgImpl(UpmsFeedbackFactory upmsFeedbackFactory) {
        this.upmsFeedbackFactory = upmsFeedbackFactory;
    }

    /**
     * @description 消息处理
     * @param msgUpmsBO msgUpmsBO
     * @author 蝉鸣
     */
    @Override
    public void onMessage(CharSequence charSequence, MsgUpmsBO msgUpmsBO) {
        // 1. 解析消息
        System.out.println(charSequence + ":" + msgUpmsBO );
        if(ObjectUtil.isEmpty(charSequence)){
            return;
        }
        //获取当前模块的module_schema
        String channelName = charSequence.toString();
        if(StrConst.ALL.equals(channelName)){
            Map<String, UpmsFeedbackService> typeMaps = upmsFeedbackFactory.getActionTypeMaps();
            typeMaps.forEach((key,value)->{
                execute(value,msgUpmsBO);
            });
        }else{
            //根据module_schema 获取对应的回调业务
            UpmsFeedbackService feedbackService = upmsFeedbackFactory.getFeedbackService(channelName);
            execute(feedbackService,msgUpmsBO);
        }
    }

    public void execute(UpmsFeedbackService feedbackService,MsgUpmsBO msgUpmsBO){
        //根据module_schema 获取对应的回调业务
        if(ObjectUtil.isEmpty(feedbackService)){
            return;
        }
        UpmsActionEnum enumByValue = BaseEnum.getEnumByValue(UpmsActionEnum.class, msgUpmsBO.getActionType());
        if(ObjectUtil.isEmpty(enumByValue)){
            return;
        }
        switch (enumByValue){
            case INIT_TENANT -> feedbackService.initTenant(msgUpmsBO);
            case INIT_TENANT_APP -> feedbackService.initTenantApp(msgUpmsBO);
            case INIT_TENANT_BPM -> feedbackService.initTenantBpm(msgUpmsBO);
            case SYNC_DICT_NAME -> feedbackService.syncDictName(msgUpmsBO);
            case SYNC_USER_NAME -> feedbackService.syncUserName(msgUpmsBO);
            case SYNC_ORG_NAME -> feedbackService.syncOrgName(msgUpmsBO);
            case TRANS_ORG_DATA -> feedbackService.transOrgData(msgUpmsBO);
            case DEL_BPM_DATA_REL -> feedbackService.delBpmDataRel(msgUpmsBO);
            case DEL_CRM_SYNC_THIRD_REL -> feedbackService.delCrmSyncThirdDataRel(msgUpmsBO);
        }
    }
}