package com.platform.mesh.bpm.biz.modules.inst.eventrel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.inst.eventrel.domain.po.BpmInstEventRel;
import com.platform.mesh.bpm.biz.modules.inst.eventrel.mapper.BpmInstEventRelMapper;
import com.platform.mesh.bpm.biz.modules.inst.eventrel.service.IBpmInstEventRelService;
import com.platform.mesh.bpm.biz.modules.inst.eventrel.service.manual.BpmInstEventCcServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 事件关联信息
 * @author 蝉鸣
 */
@Service()
public class BpmInstEventRelServiceImpl extends ServiceImpl<BpmInstEventRelMapper, BpmInstEventRel> implements IBpmInstEventRelService {


    @Autowired
    private BpmInstEventCcServiceManual bpmInstEventCcServiceManual;


}

