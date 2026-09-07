package com.platform.mesh.crm.biz.modules.crm.predrainagethird.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.api.modules.init.db.domain.bo.DbTransResBO;
import com.platform.mesh.crm.biz.modules.crm.predrainagethird.domain.po.CrmPreDrainageThird;
import com.platform.mesh.crm.biz.modules.crm.predrainagethird.mapper.CrmPreDrainageThirdMapper;
import com.platform.mesh.crm.biz.modules.crm.predrainagethird.service.ICrmPreDrainageThirdService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系活动引流数据
 * @author 蝉鸣
 */
@Service
public class CrmPreDrainageThirdServiceImpl extends ServiceImpl<CrmPreDrainageThirdMapper, CrmPreDrainageThird> implements ICrmPreDrainageThirdService {

    /**
     * 功能描述:
     * 〈转移第三方数据〉
     * @author 蝉鸣
     */
    @Override
    public void transThirdData(DbTransResBO dbTransResBO) {
        if(ObjectUtil.isEmpty(dbTransResBO)
           ||ObjectUtil.isEmpty(dbTransResBO.getFromModuleId())
           || ObjectUtil.isEmpty(dbTransResBO.getToModuleId())
           || CollUtil.isEmpty(dbTransResBO.getDataMap())){
            return;
        }
        this.getBaseMapper().transThirdData(dbTransResBO);
    }

    /**
     * 功能描述:
     * 〈删除第三方数据〉
     * @author 蝉鸣
     */
    @Override
    public void delCrmSyncThirdDataRel(Long moduleId, List<Long> dataIs) {
        this.getBaseMapper().delCrmSyncThirdDataRel(moduleId,dataIs);
    }

}
