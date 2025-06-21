package com.platform.mesh.crm.biz.modules.crm.sufreview.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.sufreview.domain.po.CrmSufReview;
import com.platform.mesh.crm.biz.modules.crm.sufreview.mapper.CrmSufReviewMapper;
import com.platform.mesh.crm.biz.modules.crm.sufreview.service.ICrmSufReviewService;
import com.platform.mesh.crm.biz.modules.crm.sufreview.service.manual.CrmSufReviewServiceManual;
import com.platform.mesh.crm.biz.modules.crm.sufreviewdata.domain.po.CrmSufReviewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系复盘总结
 * @author 蝉鸣
 */
@Service
public class CrmSufReviewServiceImpl extends AppServiceAbstract<CrmSufReviewMapper, CrmSufReview> implements ICrmSufReviewService  {

    @Autowired
    private CrmSufReviewServiceManual crmSufReviewServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系复盘总结〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmSufReviewData> crmSufReviewDataList = BeanUtil.copyToList(dataList, CrmSufReviewData.class);
        //批量保存data表数据
        crmSufReviewServiceManual.addDbDataBatch(crmSufReviewDataList);
    }

    /**
     * 功能描述:
     * 〈修改客户关系复盘总结〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //批量保存data表数据
        crmSufReviewServiceManual.editDbDataBatch(dataId,dataEditSimpDTO);
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
    public  void transDbDataBatch(List<Long> dataIds,Long scopeUserId,Long scopeOrgId){
        //修改DB
        this.lambdaUpdate()
                .set(CrmSufReview::getScopeUserId,scopeUserId)
                .set(CrmSufReview::getScopeOrgId,scopeOrgId)
                .in(CrmSufReview::getId,dataIds)
                .update();
        //修改DB Data
        crmSufReviewServiceManual.transDbDataBatch(dataIds,scopeUserId,scopeOrgId);
    }
}