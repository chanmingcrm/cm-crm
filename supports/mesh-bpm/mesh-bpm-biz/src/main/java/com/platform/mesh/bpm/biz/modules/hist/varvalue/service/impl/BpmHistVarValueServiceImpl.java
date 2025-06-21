package com.platform.mesh.bpm.biz.modules.hist.varvalue.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.hist.varvalue.mapper.BpmHistVarValueMapper;
import com.platform.mesh.bpm.biz.modules.hist.varvalue.service.IBpmHistVarValueService;
import com.platform.mesh.bpm.biz.modules.hist.varvalue.service.manual.BpmHistVarValueServiceManual;
import com.platform.mesh.bpm.biz.modules.inst.varvalue.service.manual.BpmInstVarValueServiceManual;
import com.platform.mesh.bpm.biz.modules.hist.varvalue.domain.po.BpmHistVarValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 变量值信息
 * @author 蝉鸣
 */
@Service()
public class BpmHistVarValueServiceImpl extends ServiceImpl<BpmHistVarValueMapper, BpmHistVarValue> implements IBpmHistVarValueService {


    @Autowired
    private BpmHistVarValueServiceManual bpmHistVarValueServiceManual;

}

