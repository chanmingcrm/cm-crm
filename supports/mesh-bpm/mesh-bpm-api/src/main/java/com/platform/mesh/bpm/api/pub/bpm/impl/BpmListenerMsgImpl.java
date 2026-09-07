package com.platform.mesh.bpm.api.pub.bpm.impl;

import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.bpm.api.pub.bpm.BpmFeedbackService;
import com.platform.mesh.bpm.api.pub.bpm.enums.BpmActionEnum;
import com.platform.mesh.bpm.api.pub.bpm.factory.BpmFeedbackFactory;
import com.platform.mesh.bpm.api.pub.bpm.domain.bo.MsgBpmBO;
import com.platform.mesh.core.enums.base.BaseEnum;
import org.redisson.api.listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description C工厂实现
 * @author 蝉鸣
 */
@Service
public class BpmListenerMsgImpl implements MessageListener<MsgBpmBO> {

    private final static Logger log = LoggerFactory.getLogger(BpmListenerMsgImpl.class);

    private final BpmFeedbackFactory bpmFeedbackFactory;

    @Autowired
    public BpmListenerMsgImpl(BpmFeedbackFactory bpmFeedbackFactory) {
        this.bpmFeedbackFactory = bpmFeedbackFactory;
    }

    /**
     * @description 消息处理
     * @param msgBpmBO msgBpmBO
     * @author 蝉鸣
     */
    @Override
    public void onMessage(CharSequence charSequence, MsgBpmBO msgBpmBO) {
        try {
            doOnMessage(charSequence, msgBpmBO);
        } catch (Exception e) {
            log.error("BpmListenerMsgImpl:: handle bpm message failed, channel={}, msg={}", charSequence, msgBpmBO, e);
        }
    }

    /**
     * @description 消息处理
     * @param charSequence charSequence
     * @param msgBpmBO msgBpmBO
     * @author 蝉鸣
     */
    private void doOnMessage(CharSequence charSequence, MsgBpmBO msgBpmBO) {
        // 1. 解析消息
        log.info("BpmListenerMsgImpl:: receive bpm message, channel={}, msg={}", charSequence, msgBpmBO);
        if(ObjectUtil.isEmpty(charSequence) || ObjectUtil.isEmpty(msgBpmBO)){
            return;
        }
        //获取当前模块的module_schema
        String channelName = charSequence.toString();
        //根据module_schema 获取对应的回调业务
        BpmFeedbackService feedbackService = bpmFeedbackFactory.getFeedbackService(channelName);
        if(ObjectUtil.isEmpty(feedbackService)){
            return;
        }
        BpmActionEnum enumByValue = BaseEnum.getEnumByValue(BpmActionEnum.class, msgBpmBO.getBpmAction(), BpmActionEnum.ADD);
        if(ObjectUtil.isEmpty(enumByValue)){
            return;
        }
        switch (enumByValue){
            case EDIT->feedbackService.edit(msgBpmBO);
            case DELETE,DELETE_BATCH->feedbackService.del(msgBpmBO);
            case TRANS_DATA->feedbackService.transData(msgBpmBO);
            case PROCESS_STAGE->feedbackService.processStage(msgBpmBO);
            default->feedbackService.add(msgBpmBO);
        }

    }
}
