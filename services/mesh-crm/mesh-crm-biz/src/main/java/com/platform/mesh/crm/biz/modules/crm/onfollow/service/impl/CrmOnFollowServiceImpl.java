package com.platform.mesh.crm.biz.modules.crm.onfollow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.domain.po.AppRelPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.onfollow.domain.po.CrmOnFollow;
import com.platform.mesh.crm.biz.modules.crm.onfollow.mapper.CrmOnFollowMapper;
import com.platform.mesh.crm.biz.modules.crm.onfollow.service.ICrmOnFollowService;
import com.platform.mesh.crm.biz.modules.crm.onfollow.service.manual.CrmOnFollowServiceManual;
import com.platform.mesh.crm.biz.modules.crm.onfollowdata.domain.po.CrmOnFollowData;
import com.platform.mesh.crm.biz.modules.crm.onfollowrel.po.CrmOnFollowRel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系跟进拜访
 * @author 蝉鸣
 */
@Service
public class CrmOnFollowServiceImpl extends AppServiceAbstract<CrmOnFollowMapper, CrmOnFollow> implements ICrmOnFollowService  {

    @Autowired
    private CrmOnFollowServiceManual crmOnFollowServiceManual;

    /**
     * 功能描述:
     * 〈新增客户关系跟进拜访〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmOnFollowData> crmOnFollowDataList = BeanUtil.copyToList(dataList, CrmOnFollowData.class);
        //批量保存data表数据
        crmOnFollowServiceManual.addDbDataBatch(crmOnFollowDataList);
    }

    /**
     * 功能描述:
     * 〈新增Rel通用数据〉
     * @param relList relList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppRelPO> void addDbRelBatch(List<D> relList) {
        List<CrmOnFollowRel> followRelList = BeanUtil.copyToList(relList, CrmOnFollowRel.class);
        //批量保存rel表数据
        crmOnFollowServiceManual.addDbRelBatch(followRelList);
    }

    /**
     * 功能描述:
     * 〈转移Data数据权限必须重写〉
     * @param dataIds dataIds
     * @param scopeUserId scopeUserId
     * @param scopeOrgId scopeOrgId
     * @author 蝉鸣
     */
    @Override
    public  void transDbScopeBatch(List<Long> dataIds,Long scopeUserId,Long scopeOrgId){
        //修改DB
        this.lambdaUpdate()
                .set(CrmOnFollow::getScopeUserId,scopeUserId)
                .set(CrmOnFollow::getScopeOrgId,scopeOrgId)
                .in(CrmOnFollow::getId,dataIds)
                .update();
    }

    /**
     * 功能描述:
     * 〈转移Data数据权限必须重写〉
     * @param dataIds dataIds
     * @author 蝉鸣
     */
    @Override
    public  void delOtherAction(List<Long> dataIds){
        //修改关系
        crmOnFollowServiceManual.delFollowRel(dataIds);

    }

}