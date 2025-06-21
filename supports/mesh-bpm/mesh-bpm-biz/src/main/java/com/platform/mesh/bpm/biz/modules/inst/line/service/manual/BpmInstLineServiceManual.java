package com.platform.mesh.bpm.biz.modules.inst.line.service.manual;


import com.platform.mesh.bpm.biz.modules.inst.line.domain.po.BpmInstLine;
import com.platform.mesh.bpm.biz.modules.inst.variable.service.IBpmInstVariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service()
public class BpmInstLineServiceManual {

    @Autowired
    IBpmInstVariableService bpmInstVariableService;

    public Boolean checkLinePass(BpmInstLine instLine) {
        //校验当前线是否通过
        return bpmInstVariableService.checkVariableByLine(instLine);
    }
}

