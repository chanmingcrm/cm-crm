package com.platform.mesh.crm.biz.modules.tmp.appr.approval.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.tmp.appr.approval.domain.po.TmpOaApproval;
import com.platform.mesh.crm.biz.modules.tmp.appr.approval.mapper.TmpOaApprovalMapper;
import com.platform.mesh.crm.biz.modules.tmp.appr.approval.service.ITmpOaApprovalService;
import com.platform.mesh.crm.biz.modules.tmp.appr.approval.service.manual.TmpOaApprovalServiceManual;
import com.platform.mesh.crm.biz.modules.tmp.appr.approvaldata.domain.po.TmpOaApprovalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description OA办公审批
 * @author 蝉鸣
 */
@Service
public class TmpOaApprovalServiceImpl extends AppServiceAbstract<TmpOaApprovalMapper, TmpOaApproval> implements ITmpOaApprovalService {

    @Autowired
    private TmpOaApprovalServiceManual tmpOaApprovalServiceManual;


    /**
     * 功能描述:
     * 〈新增工作审批〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<TmpOaApprovalData> tmpOaApprovalDataData = BeanUtil.copyToList(dataList, TmpOaApprovalData.class);
        //批量保存data表数据
        tmpOaApprovalServiceManual.addDbDataBatch(tmpOaApprovalDataData);
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
    public void transDbScopeBatch(List<Long> dataIds, Long scopeUserId, Long scopeOrgId) {
        //修改DB
        this.lambdaUpdate()
                .set(TmpOaApproval::getScopeUserId,scopeUserId)
                .set(TmpOaApproval::getScopeOrgId,scopeOrgId)
                .in(TmpOaApproval::getId,dataIds)
                .update();
    }
}