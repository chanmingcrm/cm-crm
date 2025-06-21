package com.platform.mesh.app.biz.modules.app.formcolumnsetevent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.po.AppFormColumn;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.dto.AppFormColumnSetEventDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.po.AppFormColumnSetEvent;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.vo.AppFormColumnSetEventVO;

import java.util.Map;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 单字段事件信息
 * @author 蝉鸣
 */
public interface IAppFormColumnSetEventService extends IService<AppFormColumnSetEvent> {


    /**
     * 功能描述:
     * 〈获取当前单字段事件信息〉
     * @param formColumnSetEventId formColumnSetEventId
     * @return 正常返回:{@link AppFormColumnSetEventVO}
     * @author 蝉鸣
     */
    AppFormColumnSetEventVO getFormColumnSetEventInfoById(Long formColumnSetEventId);

    /**
     * 功能描述:
     * 〈新增单字段事件〉
     * @param formColumnSetEventDTO formColumnSetEventDTO
     * @return 正常返回:{@link AppFormColumnSetEventVO}
     * @author 蝉鸣
     */
    AppFormColumnSetEventVO addFormColumnSetEvent(AppFormColumnSetEventDTO formColumnSetEventDTO);

    /**
     * 功能描述:
     * 〈修改单字段事件〉
     * @param formColumnSetEventDTO formColumnSetEventDTO
     * @return 正常返回:{@link AppFormColumnSetEventVO}
     * @author 蝉鸣
     */
    AppFormColumnSetEventVO editFormColumnSetEvent(AppFormColumnSetEventDTO formColumnSetEventDTO);

    /**
     * 功能描述:
     * 〈删除单字段事件〉
     * @param formColumnSetEventId formColumnSetEventId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteFormColumnSetEvent(Long formColumnSetEventId);

    /**
     * 功能描述:
     * 〈复制字段事件〉
     * @param sourceModuleId sourceModuleId
     * @param targetModuleId targetModuleId
     * @param copyColumn copyColumn
     * @author 蝉鸣
     */
    Map<Long, AppFormColumnSetEvent> copyFormColumnSetEvent(Long sourceModuleId, Long targetModuleId, Map<Long, AppFormColumn> copyColumn);
}