package com.platform.mesh.app.biz.modules.app.formcolumnsetprocess.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.po.AppFormColumn;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.dto.AppFormColumnSetEventDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.po.AppFormColumnSetEvent;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.vo.AppFormColumnSetEventVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetprocess.domain.po.AppFormColumnSetProcess;

import java.util.Map;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 单字段事件信息
 * @author 蝉鸣
 */
public interface IAppFormColumnSetProcessService extends IService<AppFormColumnSetProcess> {

    /**
     * 功能描述:
     * 〈复制字段工作流〉
     * @param sourceModuleId sourceModuleId
     * @param targetModuleId targetModuleId
     * @param copyEvent copyEvent
     * @author 蝉鸣
     */
    void copyFormColumnSetProcess(Long sourceModuleId, Long targetModuleId, Map<Long, AppFormColumnSetEvent> copyEvent);
}