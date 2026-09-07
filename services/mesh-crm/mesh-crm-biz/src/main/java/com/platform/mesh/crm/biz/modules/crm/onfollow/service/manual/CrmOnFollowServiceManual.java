package com.platform.mesh.crm.biz.modules.crm.onfollow.service.manual;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.modules.crm.onfollowdata.domain.po.CrmOnFollowData;
import com.platform.mesh.crm.biz.modules.crm.onfollowdata.service.ICrmOnFollowDataService;
import com.platform.mesh.crm.biz.modules.crm.onfollowrel.po.CrmOnFollowRel;
import com.platform.mesh.crm.biz.modules.crm.onfollowrel.service.ICrmOnFollowRelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系跟进拜访
 * @author 蝉鸣
 */
@Service
public class CrmOnFollowServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmOnFollowServiceManual.class);

    @Autowired
    private ICrmOnFollowDataService crmOnFollowDataService;

    @Autowired
    private ICrmOnFollowRelService crmOnFollowRelService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param onFollowDataList onFollowDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmOnFollowData> onFollowDataList) {
        if(CollUtil.isEmpty(onFollowDataList)){
            return;
        }
        CrmOnFollowData data = CollUtil.getFirst(onFollowDataList);
        //删除旧数据
        crmOnFollowDataService.lambdaUpdate().eq(CrmOnFollowData::getDataId,data.getDataId()).remove();
        //批量新增信息
        crmOnFollowDataService.saveBatch(onFollowDataList);
    }

    /**
     * 功能描述:
     * 〈跟进数据关系保存〉
     * @param followRelList followRelList
     * @author 蝉鸣
     */
    public void addDbRelBatch(List<CrmOnFollowRel> followRelList) {
        if(CollUtil.isEmpty(followRelList)){
            return;
        }
        CrmOnFollowRel followRel = CollUtil.getFirst(followRelList);
        //删除旧数据
        crmOnFollowRelService.lambdaUpdate()
                .eq(CrmOnFollowRel::getModuleId, followRel.getModuleId())
                .eq(CrmOnFollowRel::getDataId, followRel.getDataId())
                .remove();
        //修改关联数据
        crmOnFollowRelService.saveBatch(followRelList);
    }

    /**
     * 功能描述:
     * 〈跟进数据关系保存〉
     * @param dataIds dataIds
     * @author 蝉鸣
     */
    public void delFollowRel(List<Long> dataIds) {
        //删除旧数据
        crmOnFollowRelService.lambdaUpdate()
                .in(CrmOnFollowRel::getDataId, dataIds)
                .remove();
    }
}