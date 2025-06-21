package com.platform.mesh.crm.biz.modules.crm.suffeedback.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.suffeedback.domain.po.CrmSufFeedback;
import com.platform.mesh.crm.biz.modules.crm.suffeedback.mapper.CrmSufFeedbackMapper;
import com.platform.mesh.crm.biz.modules.crm.suffeedback.service.ICrmSufFeedbackService;
import com.platform.mesh.crm.biz.modules.crm.suffeedback.service.manual.CrmSufFeedbackServiceManual;
import com.platform.mesh.crm.biz.modules.crm.suffeedbackdata.domain.po.CrmSufFeedbackData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系市场反馈
 * @author 蝉鸣
 */
@Service
public class CrmSufFeedbackServiceImpl extends AppServiceAbstract<CrmSufFeedbackMapper, CrmSufFeedback> implements ICrmSufFeedbackService  {

    @Autowired
    private CrmSufFeedbackServiceManual crmSufFeedbackServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系市场反馈〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmSufFeedbackData> crmSufFeedbackDataList = BeanUtil.copyToList(dataList, CrmSufFeedbackData.class);
        //批量保存data表数据
        crmSufFeedbackServiceManual.addDbDataBatch(crmSufFeedbackDataList);
    }

    /**
     * 功能描述:
     * 〈修改客户关系市场反馈〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //批量保存data表数据
        crmSufFeedbackServiceManual.editDbDataBatch(dataId,dataEditSimpDTO);
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
                .set(CrmSufFeedback::getScopeUserId,scopeUserId)
                .set(CrmSufFeedback::getScopeOrgId,scopeOrgId)
                .in(CrmSufFeedback::getId,dataIds)
                .update();
        //修改DB Data
        crmSufFeedbackServiceManual.transDbDataBatch(dataIds,scopeUserId,scopeOrgId);
    }
}