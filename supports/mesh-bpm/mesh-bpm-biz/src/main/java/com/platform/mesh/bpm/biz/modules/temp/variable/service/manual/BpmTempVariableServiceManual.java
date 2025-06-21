package com.platform.mesh.bpm.biz.modules.temp.variable.service.manual;


import com.platform.mesh.bpm.biz.modules.inst.event.domain.po.BpmInstEvent;
import org.springframework.stereotype.Service;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service()
public class BpmTempVariableServiceManual {


    /**
     * 功能描述:
     * 〈执行当前事件信息〉
     * @param instEvent instEvent
     * @author 蝉鸣
     */
    public void handleCurrentEvent(BpmInstEvent instEvent) {
        //获取事件类型
        //获取类型工厂
        //执行事件
    }
}

