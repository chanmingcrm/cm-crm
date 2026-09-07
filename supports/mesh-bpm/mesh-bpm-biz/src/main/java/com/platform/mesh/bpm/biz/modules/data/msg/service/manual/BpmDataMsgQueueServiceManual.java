package com.platform.mesh.bpm.biz.modules.data.msg.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.bpm.api.pub.bpm.domain.bo.MsgBpmBO;
import com.platform.mesh.bpm.biz.modules.data.msg.domain.po.BpmDataMsgQueue;
import com.platform.mesh.bpm.biz.modules.data.nodedata.service.IBpmDataFormNodeDataService;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.vo.BpmInstNodeVO;
import com.platform.mesh.bpm.biz.modules.inst.node.service.IBpmInstNodeService;
import com.platform.mesh.bpm.biz.soa.node.run.enums.NodeRunEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.redis.service.RedissonUtil;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 业务数据实例流程节点表单数据
 * @author 蝉鸣
 */
@Service()
public class BpmDataMsgQueueServiceManual {

    @Autowired
    private IBpmDataFormNodeDataService bpmDataFormNodeDataService;

    /**
     * 功能描述:
     * 〈获取阶段流程名称〉
     * @param instProcessId instProcessId
     * @param isNodeName isNodeName
     * @author 蝉鸣
     */
    public String getProcessStageName(Long instProcessId,Integer isNodeName){
        String stage;
        if(YesOrNoEnum.YES.getValue().equals(isNodeName)){
            //如果运行中则添加节点名称
            //获取当前运行节点
            IBpmInstNodeService bpmInstNodeService = SpringContextHolderUtil.getBean(IBpmInstNodeService.class);
            List<BpmInstNodeVO> bpmInstNodes = bpmInstNodeService.selectNodeVOByInstProcessIdAndRunFlag(instProcessId, NodeRunEnum.RUNNING.getValue());
            stage = bpmInstNodes.stream().map(BpmInstNodeVO::getNodeName).collect(Collectors.joining());
        }else{
            //如果已结束则根据运行状态判定赢单/输单/无效
            //查询表单关联信息
            stage = bpmDataFormNodeDataService.getProcessStageName(instProcessId);
        }
        return stage;
    }

    /**
     * 功能描述:
     * 〈发送审批回调消息〉
     * @param bpmDataMsgQueue bpmDataMsgQueue
     * @author 蝉鸣
     */
    public void sendBpmMsg(BpmDataMsgQueue bpmDataMsgQueue) {
        if(ObjectUtil.isEmpty(bpmDataMsgQueue)){
            return;
        }
        MsgBpmBO msgBpmBO = BeanUtil.copyProperties(bpmDataMsgQueue,MsgBpmBO.class);
        msgBpmBO.setBpmAction(bpmDataMsgQueue.getColumnType());
        if(ObjectUtil.isNotEmpty(bpmDataMsgQueue.getColumnType())){
            msgBpmBO.setExtendMap(JSONUtil.parseObj(bpmDataMsgQueue.getExtendJson()));
        }
        if(ObjectUtil.isEmpty(msgBpmBO.getModuleSchema())){
            return;
        }
        //发送订阅消息
        RedissonUtil.publish(msgBpmBO.getModuleSchema(),msgBpmBO);
    }


}

