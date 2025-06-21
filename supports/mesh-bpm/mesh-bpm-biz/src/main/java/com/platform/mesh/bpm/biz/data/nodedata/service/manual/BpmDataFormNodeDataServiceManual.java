package com.platform.mesh.bpm.biz.data.nodedata.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.bpm.biz.data.nodedata.domain.po.BpmDataFormNodeData;
import com.platform.mesh.bpm.biz.data.nodedata.domain.vo.BpmDataFormNodeDataVO;
import org.springframework.stereotype.Service;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 业务数据实例流程节点表单数据
 * @author 蝉鸣
 */
@Service
public class BpmDataFormNodeDataServiceManual{

    
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

}