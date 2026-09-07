package com.platform.mesh.app.api.modules.pub.type.app.impl;

import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.pub.type.app.AppFeedbackService;
import com.platform.mesh.app.api.modules.pub.type.app.domain.bo.MsgAppBO;
import com.platform.mesh.app.api.modules.pub.type.app.enums.AppActionEnum;
import com.platform.mesh.app.api.modules.pub.type.app.factory.AppFeedbackFactory;
import com.platform.mesh.core.enums.base.BaseEnum;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description C工厂实现
 * @author 蝉鸣
 */
@Service
public class AppListenerMsgImpl implements MessageListener<MsgAppBO> {

    private final AppFeedbackFactory appFeedbackFactory;

    @Autowired
    public AppListenerMsgImpl(AppFeedbackFactory appFeedbackFactory) {
        this.appFeedbackFactory = appFeedbackFactory;
    }

    /**
     * @description 消息处理
     * @param msgAppBO msgAppBO
     * @author 蝉鸣
     */
    @Override
    public void onMessage(CharSequence charSequence, MsgAppBO msgAppBO) {
        // 1. 解析消息
        System.out.println(charSequence + ":" + msgAppBO );
        if(ObjectUtil.isEmpty(charSequence)){
            return;
        }
        //获取当前模块的module_schema
        String channelName = charSequence.toString();
        //根据module_schema 获取对应的回调业务
        AppFeedbackService feedbackService = appFeedbackFactory.getFeedbackService(channelName);
        if(ObjectUtil.isEmpty(feedbackService)){
            return;
        }
        AppActionEnum enumByValue = BaseEnum.getEnumByValue(AppActionEnum.class, msgAppBO.getActionType());
        if(ObjectUtil.isEmpty(enumByValue)){
            return;
        }
        switch (enumByValue){
            case SYNC_NAME->feedbackService.syncName(msgAppBO);
        }

    }
}