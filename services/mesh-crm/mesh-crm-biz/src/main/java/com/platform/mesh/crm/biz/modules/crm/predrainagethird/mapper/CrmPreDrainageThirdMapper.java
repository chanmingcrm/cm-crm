package com.platform.mesh.crm.biz.modules.crm.predrainagethird.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.app.api.modules.init.db.domain.bo.DbTransResBO;
import com.platform.mesh.crm.biz.modules.crm.predrainagethird.domain.po.CrmPreDrainageThird;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 客户关系活动引流数据
 * @author 蝉鸣
 */
public interface CrmPreDrainageThirdMapper extends BaseMapper<CrmPreDrainageThird> {

    void transThirdData(@Param("dbTransResBO")DbTransResBO dbTransResBO);

    void delCrmSyncThirdDataRel(@Param("moduleId")Long moduleId,@Param("dataIs")List<Long> dataIs);
}
