package com.platform.mesh.bpm.biz.modules.hist.action.service.manual;


import com.platform.mesh.bpm.biz.modules.inst.action.domain.po.BpmInstAction;
import com.platform.mesh.bpm.biz.soa.action.factory.ActionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service()
public class BpmHistActionServiceManual {

    @Autowired
    ActionFactory<BpmInstAction> actionFactory;


}

