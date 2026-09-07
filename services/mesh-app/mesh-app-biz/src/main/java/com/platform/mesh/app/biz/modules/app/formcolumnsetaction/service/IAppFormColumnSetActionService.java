package com.platform.mesh.app.biz.modules.app.formcolumnsetaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.po.AppFormColumn;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.domain.dto.AppFormColumnSetActionDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.domain.po.AppFormColumnSetAction;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.domain.vo.AppFormColumnSetActionVO;

import java.util.Map;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 单字段动作信息
 * @author 蝉鸣
 */
public interface IAppFormColumnSetActionService extends IService<AppFormColumnSetAction> {


    /**
     * 功能描述:
     * 〈获取当前单字段动作信息〉
     * @param formColumnSetActionId formColumnSetActionId
     * @return 正常返回:{@link AppFormColumnSetActionVO}
     * @author 蝉鸣
     */
    AppFormColumnSetActionVO getFormColumnSetActionInfoById(Long formColumnSetActionId);

    /**
     * 功能描述:
     * 〈新增单字段动作〉
     * @param formColumnSetActionDTO formColumnSetActionDTO
     * @return 正常返回:{@link AppFormColumnSetActionVO}
     * @author 蝉鸣
     */
    AppFormColumnSetActionVO addFormColumnSetAction(AppFormColumnSetActionDTO formColumnSetActionDTO);

    /**
     * 功能描述:
     * 〈修改单字段动作〉
     * @param formColumnSetActionDTO formColumnSetActionDTO
     * @return 正常返回:{@link AppFormColumnSetActionVO}
     * @author 蝉鸣
     */
    AppFormColumnSetActionVO editFormColumnSetAction(AppFormColumnSetActionDTO formColumnSetActionDTO);

    /**
     * 功能描述:
     * 〈删除单字段动作〉
     * @param formColumnSetActionId formColumnSetActionId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteFormColumnSetAction(Long formColumnSetActionId);

    /**
     * 功能描述:
     * 〈复制字段动作〉
     * @param sourceModuleId sourceModuleId
     * @param targetModuleId targetModuleId
     * @param copyColumn copyColumn
     * @author 蝉鸣
     */
    void copyFormColumnSetAction(Long sourceModuleId, Long targetModuleId, Map<Long, AppFormColumn> copyColumn);
}
