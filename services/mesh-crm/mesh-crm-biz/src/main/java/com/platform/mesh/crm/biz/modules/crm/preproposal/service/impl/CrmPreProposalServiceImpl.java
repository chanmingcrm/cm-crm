package com.platform.mesh.crm.biz.modules.crm.preproposal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.preproposal.domain.po.CrmPreProposal;
import com.platform.mesh.crm.biz.modules.crm.preproposal.mapper.CrmPreProposalMapper;
import com.platform.mesh.crm.biz.modules.crm.preproposal.service.ICrmPreProposalService;
import com.platform.mesh.crm.biz.modules.crm.preproposal.service.manual.CrmPreProposalServiceManual;
import com.platform.mesh.crm.biz.modules.crm.preproposaldata.domain.po.CrmPreProposalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系提案报价
 * @author 蝉鸣
 */
@Service
public class CrmPreProposalServiceImpl extends AppServiceAbstract<CrmPreProposalMapper, CrmPreProposal> implements ICrmPreProposalService  {

    @Autowired
    private CrmPreProposalServiceManual crmPreProposalServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系提案报价〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmPreProposalData> crmPreProposalDataList = BeanUtil.copyToList(dataList, CrmPreProposalData.class);
        //批量保存data表数据
        crmPreProposalServiceManual.addDbDataBatch(crmPreProposalDataList);
    }

    /**
     * 功能描述:
     * 〈修改客户关系提案报价〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //批量保存data表数据
        crmPreProposalServiceManual.editDbDataBatch(dataId,dataEditSimpDTO);
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
                .set(CrmPreProposal::getScopeUserId,scopeUserId)
                .set(CrmPreProposal::getScopeOrgId,scopeOrgId)
                .in(CrmPreProposal::getId,dataIds)
                .update();
        //修改DB Data
        crmPreProposalServiceManual.transDbDataBatch(dataIds,scopeUserId,scopeOrgId);
    }
}