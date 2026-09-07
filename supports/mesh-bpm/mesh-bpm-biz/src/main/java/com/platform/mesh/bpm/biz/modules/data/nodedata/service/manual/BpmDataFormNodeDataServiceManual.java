package com.platform.mesh.bpm.biz.modules.data.nodedata.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.bpm.api.pub.bpm.domain.bo.MsgBpmBO;
import com.platform.mesh.bpm.api.pub.bpm.enums.BpmActionEnum;
import com.platform.mesh.bpm.biz.modules.data.inst.domain.po.BpmDataInstRel;
import com.platform.mesh.bpm.biz.modules.data.inst.service.IBpmDataInstRelService;
import com.platform.mesh.bpm.biz.modules.data.nodedata.domain.bo.FormData;
import com.platform.mesh.bpm.biz.modules.data.nodedata.domain.po.BpmDataFormNodeData;
import com.platform.mesh.bpm.biz.modules.data.nodedata.domain.vo.BpmDataFormNodeDataVO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.process.service.IBpmInstProcessService;
import com.platform.mesh.bpm.biz.modules.temp.process.enums.ProcessFlagEnum;
import com.platform.mesh.core.application.domain.bo.SimpBO;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.bpm.ProcessPassEnum;
import com.platform.mesh.redis.service.RedissonUtil;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 业务数据实例流程节点表单数据
 * @author 蝉鸣
 */
@Service
public class BpmDataFormNodeDataServiceManual{

    @Autowired
    private IBpmDataInstRelService bpmDataInstRelService;
    
    /**
     * 功能描述: 
     * 〈获取当前业务数据实例流程节点表单数据信息〉
     * @param bpmDataFormNodeData bpmDataFormNodeData 
     * @return 正常返回:{@link BpmDataFormNodeDataVO}
     * @author 蝉鸣
     */
    public BpmDataFormNodeDataVO getDataFormNodeDataInfoById(BpmDataFormNodeData bpmDataFormNodeData) {
        BpmDataFormNodeDataVO bpmDataFormNodeDataVO = new BpmDataFormNodeDataVO();
        if(ObjectUtil.isEmpty(bpmDataFormNodeDataVO)){
            return bpmDataFormNodeDataVO;
        }
        //转换VO
        BeanUtil.copyProperties(bpmDataFormNodeData, bpmDataFormNodeDataVO);
        return bpmDataFormNodeDataVO;
    }

    /**
     * 功能描述:
     * 〈发送审批回调消息〉
     * @param bpmDataFormNodeData bpmDataFormNodeData
     * @author 蝉鸣
     */
    public void sendBpmMsg(BpmDataFormNodeData bpmDataFormNodeData) {
        if(ObjectUtil.isEmpty(bpmDataFormNodeData.getFormData())){
            return;
        }
        IBpmInstProcessService processService = SpringContextHolderUtil.getBean(IBpmInstProcessService.class);
        BpmInstProcess bpmInstProcess = processService.getById(bpmDataFormNodeData.getInstProcessId());
        if(ObjectUtil.isEmpty(bpmInstProcess)){
            return;
        }
        //如果不是阶段流程不通知消息
        if(!ProcessFlagEnum.STAGE.getValue().equals(bpmInstProcess.getProcessFlag())){
            return;
        }
        //获取数关联
        List<BpmDataInstRel> dataInstRelList = bpmDataInstRelService.lambdaQuery().eq(BpmDataInstRel::getInstProcessId, bpmDataFormNodeData.getInstProcessId()).list();
        if(CollUtil.isEmpty(dataInstRelList)) {
            return;
        }
        BpmDataInstRel bpmDataInstRel = CollUtil.getFirst(dataInstRelList);
        //封装消息体
        MsgBpmBO msgBpmBO = BeanUtil.copyProperties(bpmDataInstRel,MsgBpmBO.class);
        //设置类型
        msgBpmBO.setBpmAction(BpmActionEnum.PROCESS_STAGE.getValue());
        //如果流程是初始状态则提示数据设置为运行中
        if(ProcessPassEnum.INIT.getValue().equals(bpmInstProcess.getPassFlag())){
            msgBpmBO.setProcessPass(bpmInstProcess.getRunFlag());
        }else{
            msgBpmBO.setProcessPass(bpmInstProcess.getPassFlag());
        }
        //向额外参数添加阶段名称
        String stage = getProcessStageName(bpmDataFormNodeData);
        Map<String, Object> extendMap = new HashMap<>();
        extendMap.put(StrConst.BPM_PROCESS_STAGE,stage);
        msgBpmBO.setExtendMap(extendMap);
        if(ObjectUtil.isEmpty(msgBpmBO.getModuleSchema())){
            return;
        }
        //发送订阅消息
        RedissonUtil.publish(msgBpmBO.getModuleSchema(),msgBpmBO);

    }

    /**
     * 功能描述:
     * 〈获取阶段信息〉
     * @param nodeData nodeData
     * @author 蝉鸣
     */
    public String getProcessStageName(BpmDataFormNodeData nodeData){
        String stage = SymbolConst.BLANK;
        if(ObjectUtil.isNotEmpty(nodeData) && ObjectUtil.isNotEmpty(nodeData.getFormData())){
            List<FormData> formData = JSONUtil.toList(nodeData.getFormData(), FormData.class);
            stage = formData.stream().filter(data -> data.getColumnMac().equals(StrConst.BPM_STAGE_COLUMN)).map(data -> {
                List<SimpBO> simpBO = JSONUtil.toList(data.getColumnData(), SimpBO.class);
                return simpBO.stream().map(SimpBO::getName).collect(Collectors.joining());
            }).collect(Collectors.joining());
        }
        return stage;
    }
}