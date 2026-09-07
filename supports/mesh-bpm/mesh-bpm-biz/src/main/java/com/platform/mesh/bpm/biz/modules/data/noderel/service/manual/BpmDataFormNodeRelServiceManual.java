package com.platform.mesh.bpm.biz.modules.data.noderel.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.bpm.biz.modules.data.noderel.domain.po.BpmDataFormNodeRel;
import com.platform.mesh.bpm.biz.modules.data.noderel.domain.vo.BpmDataFormNodeRelVO;
import org.springframework.stereotype.Service;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 业务数据模板流程节点表单关系
 * @author 蝉鸣
 */
@Service
public class BpmDataFormNodeRelServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前业务数据模板流程节点表单关系信息〉
     * @param bpmDataFormNodeRel bpmDataFormNodeRel 
     * @return 正常返回:{@link BpmDataFormNodeRelVO}
     * @author 蝉鸣
     */
    public BpmDataFormNodeRelVO getDataFormNodeRelInfoById(BpmDataFormNodeRel bpmDataFormNodeRel) {
        BpmDataFormNodeRelVO bpmDataFormNodeRelVO = new BpmDataFormNodeRelVO();
        if(ObjectUtil.isEmpty(bpmDataFormNodeRelVO)){
            return bpmDataFormNodeRelVO;
        }
        //转换VO
        BeanUtil.copyProperties(bpmDataFormNodeRel, bpmDataFormNodeRelVO);
        return bpmDataFormNodeRelVO;
    }

}