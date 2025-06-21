package com.platform.mesh.app.biz.modules.app.formcolumnsetprocess.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.po.AppFormColumn;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.po.AppFormColumnSetEvent;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.service.manual.AppFormColumnSetEventServiceManual;
import com.platform.mesh.app.biz.modules.app.formcolumnsetprocess.domain.po.AppFormColumnSetProcess;
import com.platform.mesh.app.biz.modules.app.formcolumnsetprocess.mapper.AppFormColumnSetProcessMapper;
import com.platform.mesh.app.biz.modules.app.formcolumnsetprocess.service.IAppFormColumnSetProcessService;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 单字段事件
 * @author 蝉鸣
 */
@Service
public class AppFormColumnSetProcessServiceImpl extends ServiceImpl<AppFormColumnSetProcessMapper, AppFormColumnSetProcess> implements IAppFormColumnSetProcessService {

    @Autowired
    private AppFormColumnSetEventServiceManual appFormColumnSetEventServiceManual;


    /**
     * 功能描述:
     * 〈复制字段工作流〉
     * @param sourceModuleId sourceModuleId
     * @param targetModuleId targetModuleId
     * @param copyEvent copyEvent
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void copyFormColumnSetProcess(Long sourceModuleId, Long targetModuleId, Map<Long, AppFormColumnSetEvent> copyEvent) {
        if(CollUtil.isEmpty(copyEvent)){
            return;
        }
        List<AppFormColumnSetProcess> sourceSetProcess = this.lambdaQuery().eq(AppFormColumnSetProcess::getModuleId, sourceModuleId).list();
        if(CollUtil.isEmpty(sourceSetProcess)){
            return;
        }
        List<AppFormColumnSetProcess> targetProcesss = sourceSetProcess.stream()
                .filter(sourceProcess->copyEvent.containsKey(sourceProcess.getEventId()))
                .map(sourceProcess -> {
            Long id = IdUtil.getSnowflake().nextId();
            AppFormColumnSetProcess targetProcess = new AppFormColumnSetProcess();
            BeanUtil.copyProperties(sourceProcess, targetProcess, ObjFieldUtil.ignoreDefault());
            targetProcess.setId(id);
            targetProcess.setModuleId(targetModuleId);
            AppFormColumnSetEvent appFormColumnSetEvent = copyEvent.get(sourceProcess.getEventId());
            targetProcess.setFormId(appFormColumnSetEvent.getFormId());
            targetProcess.setColumnId(appFormColumnSetEvent.getColumnId());
            targetProcess.setColumnMac(appFormColumnSetEvent.getColumnMac());
            targetProcess.setColumnName(appFormColumnSetEvent.getColumnName());
            targetProcess.setEventId(appFormColumnSetEvent.getId());
            targetProcess.setEventName(appFormColumnSetEvent.getEventName());
            targetProcess.setEventType(appFormColumnSetEvent.getEventType());
            return targetProcess;
        }).toList();
        //批量保存
        this.saveBatch(targetProcesss);
    }
}