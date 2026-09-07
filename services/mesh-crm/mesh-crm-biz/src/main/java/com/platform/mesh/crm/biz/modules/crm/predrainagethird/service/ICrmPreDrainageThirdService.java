package com.platform.mesh.crm.biz.modules.crm.predrainagethird.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.api.modules.init.db.domain.bo.DbTransResBO;
import com.platform.mesh.crm.biz.modules.crm.predrainagethird.domain.po.CrmPreDrainageThird;

import java.util.List;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 客户关系活动引流数据信息
 * @author 蝉鸣
 */
public interface ICrmPreDrainageThirdService extends IService<CrmPreDrainageThird> {

    /**
     * 功能描述:
     * 〈转移第三方数据〉
     * @author 蝉鸣
     */
    void transThirdData(DbTransResBO dbTransResBO);

    /**
     * 功能描述:
     * 〈删除第三方数据〉
     * @author 蝉鸣
     */
    void delCrmSyncThirdDataRel(Long moduleId, List<Long> dataIs);

}
