package com.platform.mesh.bpm.biz.modules.data.msg.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.data.inst.domain.po.BpmDataInstRel;
import com.platform.mesh.bpm.biz.modules.data.msg.domain.po.BpmDataMsgQueue;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 数据流程实例消息
 * @author 蝉鸣
 */
public interface IBpmDataMsgQueueService extends IService<BpmDataMsgQueue> {


    /**
     * 功能描述:
     * 〈持久化消息信息〉
     * @param bpmInstProcess bpmInstProcess
     * @param bpmDataInstRel bpmDataInstRel
     * @return  正常返回:{@link BpmDataMsgQueue}
     * @author 蝉鸣
     */
    BpmDataMsgQueue saveBpmMsg(BpmInstProcess bpmInstProcess, BpmDataInstRel bpmDataInstRel);

    /**
     * 功能描述:
     * 〈持久化消息信息〉
     * @param bpmInstNode bpmInstNode
     * @param bpmDataInstRel bpmDataInstRel
     * @return  正常返回:{@link BpmDataMsgQueue}
     * @author 蝉鸣
     */
    BpmDataMsgQueue saveBpmMsg(BpmInstNode bpmInstNode, BpmDataInstRel bpmDataInstRel);

    /**
     * 功能描述:
     * 〈获取持久化信息〉
     * @param instProcessId instProcessId
     * @return  正常返回:{@link BpmDataMsgQueue}
     * @author 蝉鸣
     */
    BpmDataMsgQueue getBpmMsgByInstProcessId(Long instProcessId);

    /**
     * 功能描述:
     * 〈发送信息〉
     * @param bpmDataMsgQueue bpmDataMsgQueue
     * @author 蝉鸣
     */
    void sendBpmMsg(BpmDataMsgQueue bpmDataMsgQueue);
}

