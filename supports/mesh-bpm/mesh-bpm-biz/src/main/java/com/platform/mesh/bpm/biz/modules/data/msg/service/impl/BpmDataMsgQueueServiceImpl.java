package com.platform.mesh.bpm.biz.modules.data.msg.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.api.pub.bpm.enums.BpmActionEnum;
import com.platform.mesh.bpm.biz.modules.data.inst.domain.po.BpmDataInstRel;
import com.platform.mesh.bpm.biz.modules.data.msg.domain.po.BpmDataMsgQueue;
import com.platform.mesh.bpm.biz.modules.data.msg.mapper.BpmDataMsgQueueMapper;
import com.platform.mesh.bpm.biz.modules.data.msg.service.IBpmDataMsgQueueService;
import com.platform.mesh.bpm.biz.modules.data.msg.service.manual.BpmDataMsgQueueServiceManual;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.process.service.IBpmInstProcessService;
import com.platform.mesh.bpm.biz.modules.temp.process.enums.ProcessFlagEnum;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.enums.bpm.ProcessPassEnum;
import com.platform.mesh.core.enums.bpm.ProcessRunEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 数据流程实例消息信息
 * @author 蝉鸣
 */
@Service()
public class BpmDataMsgQueueServiceImpl extends ServiceImpl<BpmDataMsgQueueMapper, BpmDataMsgQueue> implements IBpmDataMsgQueueService {

    @Autowired
    private BpmDataMsgQueueServiceManual bpmDataMsgQueueServiceManual;

    /**
     * 功能描述:
     * 〈持久化消息信息〉
     * @param bpmInstProcess bpmInstProcess
     * @param bpmDataInstRel bpmDataInstRel
     * @return  正常返回:{@link BpmDataMsgQueue}
     * @author 蝉鸣
     */
    @Override
    public BpmDataMsgQueue saveBpmMsg(BpmInstProcess bpmInstProcess, BpmDataInstRel bpmDataInstRel) {
        BpmDataMsgQueue msgQueue = new BpmDataMsgQueue();
        BeanUtil.copyProperties(bpmDataInstRel, msgQueue, ObjFieldUtil.ignoreDefault());
        msgQueue.setHandleUserId(UserCacheUtil.getUserId());
        //如果流程是初始状态则提示数据设置为运行中
        if(ProcessPassEnum.INIT.getValue().equals(bpmInstProcess.getPassFlag())){
            msgQueue.setProcessPass(bpmInstProcess.getRunFlag());
        }else{
            msgQueue.setProcessPass(bpmInstProcess.getPassFlag());
        }
        //如果当前流程是阶段流程则更换columnType
        if(ProcessFlagEnum.STAGE.getValue().equals(bpmInstProcess.getProcessFlag())){
            msgQueue.setColumnType(BpmActionEnum.PROCESS_STAGE.getValue());
            //向额外参数添加阶段名称
            Integer isNodeName = YesOrNoEnum.YES.getValue();
            if(!bpmInstProcess.getPassFlag().equals(ProcessPassEnum.INIT.getValue())){
                isNodeName = YesOrNoEnum.NO.getValue();
            }
            String stage = bpmDataMsgQueueServiceManual.getProcessStageName(bpmInstProcess.getId(), isNodeName);
            Map<String,Object> extendJson = new HashMap<>();
            extendJson.put(StrConst.BPM_PROCESS_STAGE,stage);
            msgQueue.setExtendJson(extendJson);
        }
        this.save(msgQueue);
        return msgQueue;
    }

    /**
     * 功能描述:
     * 〈持久化消息信息〉
     * @param bpmInstNode bpmInstNode
     * @param bpmDataInstRel bpmDataInstRel
     * @return  正常返回:{@link BpmDataMsgQueue}
     * @author 蝉鸣
     */
    @Override
    public BpmDataMsgQueue saveBpmMsg(BpmInstNode bpmInstNode, BpmDataInstRel bpmDataInstRel) {
        IBpmInstProcessService instProcessService = SpringContextHolderUtil.getBean(IBpmInstProcessService.class);
        BpmInstProcess bpmInstProcess = instProcessService.getById(bpmInstNode.getInstProcessId());
        BpmDataMsgQueue msgQueue = new BpmDataMsgQueue();
        BeanUtil.copyProperties(bpmDataInstRel, msgQueue, ObjFieldUtil.ignoreDefault());
        msgQueue.setHandleUserId(UserCacheUtil.getUserId());
        msgQueue.setProcessPass(ProcessRunEnum.RUNNING.getValue());
        //如果当前流程是阶段流程则更换columnType
        if(ProcessFlagEnum.STAGE.getValue().equals(bpmInstProcess.getProcessFlag())){
            msgQueue.setColumnType(BpmActionEnum.PROCESS_STAGE.getValue());
            //向额外参数添加阶段名称
            String stage = bpmDataMsgQueueServiceManual.getProcessStageName(bpmInstProcess.getId(), YesOrNoEnum.YES.getValue());
            if(StrUtil.isBlank(stage)){
                //为空代表不需要发送
                return null;
            }
            Map<String,Object> extendJson = new HashMap<>();
            extendJson.put(StrConst.BPM_PROCESS_STAGE,stage);
            msgQueue.setExtendJson(extendJson);
        }else {
            if(ProcessRunEnum.END.getValue().equals(bpmInstProcess.getRunFlag())){
                msgQueue.setProcessPass(bpmInstProcess.getPassFlag());
            }else{
                if(ProcessPassEnum.PASS.getValue().equals(bpmInstNode.getPassFlag())){
                    msgQueue.setProcessPass(ProcessRunEnum.RUNNING.getValue());
                }else{
                    msgQueue.setProcessPass(bpmInstNode.getPassFlag());
                }
            }
        }
        this.save(msgQueue);
        return msgQueue;
    }

    /**
     * 功能描述:
     * 〈获取持久化信息〉
     * @param instProcessId instProcessId
     * @return  正常返回:{@link BpmDataMsgQueue}
     * @author 蝉鸣
     */
    @Override
    public BpmDataMsgQueue getBpmMsgByInstProcessId(Long instProcessId) {
        List<BpmDataMsgQueue> list = this.lambdaQuery().eq(BpmDataMsgQueue::getInstProcessId, instProcessId).list();
        if(CollUtil.isEmpty(list)){
            return null;
        }
        return CollUtil.getFirst(list);
    }

    /**
     * 功能描述:
     * 〈发送信息〉
     * @param bpmDataMsgQueue bpmDataMsgQueue
     * @author 蝉鸣
     */
    @Override
    public void sendBpmMsg(BpmDataMsgQueue bpmDataMsgQueue) {
        bpmDataMsgQueueServiceManual.sendBpmMsg(bpmDataMsgQueue);
    }


}

