package com.platform.mesh.bpm.biz.modules.hist.event.service.manual;


import com.platform.mesh.bpm.biz.modules.hist.event.domain.po.BpmHistEvent;
import com.platform.mesh.bpm.biz.soa.event.factory.EventFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service()
public class BpmHistEventServiceManual {

    @Autowired
    EventFactory<BpmHistEvent> eventFactory;

}

