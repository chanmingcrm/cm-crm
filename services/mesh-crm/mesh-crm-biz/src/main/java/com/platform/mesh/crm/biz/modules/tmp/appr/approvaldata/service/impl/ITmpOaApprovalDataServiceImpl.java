package com.platform.mesh.crm.biz.modules.tmp.appr.approvaldata.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.crm.biz.modules.tmp.appr.approvaldata.domain.po.TmpOaApprovalData;
import com.platform.mesh.crm.biz.modules.tmp.appr.approvaldata.mapper.TmpOaApprovalDataMapper;
import com.platform.mesh.crm.biz.modules.tmp.appr.approvaldata.service.ITmpOaApprovalDataService;
import com.platform.mesh.crm.biz.modules.tmp.appr.approvaldata.service.manual.TmpOaApprovalDataServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description OA办公审批数据
 * @author 蝉鸣
 */
@Service
public class ITmpOaApprovalDataServiceImpl extends ServiceImpl<TmpOaApprovalDataMapper, TmpOaApprovalData> implements ITmpOaApprovalDataService {

    @Autowired
    private TmpOaApprovalDataServiceManual tmpOaApprovalDataServiceManual;

}
