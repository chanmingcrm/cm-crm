package com.platform.mesh.bpm.biz.modules.data.msg.service.impl;

import com.platform.mesh.bpm.biz.modules.data.msg.service.IBpmDataMsgQueueService;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;

import org.redisson.api.listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description 流程消息监听工厂实现
 * @author 蝉鸣
 */
@Service
public class BpmListenerMsgBackImpl implements MessageListener<Long> {

    private final static Logger log = LoggerFactory.getLogger(BpmListenerMsgBackImpl.class);

    private final IBpmDataMsgQueueService bpmDataMsgQueueService;

    @Autowired
    public BpmListenerMsgBackImpl(IBpmDataMsgQueueService bpmDataMsgQueueService) {
        this.bpmDataMsgQueueService = bpmDataMsgQueueService;
    }

    /**
     * @description 消息处理
     * @param msgId msgId
     * @author 蝉鸣
     */
    @Override
    public void onMessage(CharSequence charSequence, Long msgId) {
        if(StrConst.BPM_MSG_ID.equals(charSequence.toString())){
            try {
                DataScopeHandler.setEnableDataScope(Boolean.FALSE);
                bpmDataMsgQueueService.removeById(msgId);
            } catch (Exception e) {
                log.error("BpmListenerMsgBackImpl:: remove bpm message failed, channel={}, msgId={}", charSequence, msgId, e);
            } finally {
                DataScopeHandler.unEnableDataScope();
            }
        }
    }
}
