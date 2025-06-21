package com.platform.mesh.tmp.biz.modules.appr.approvaldata.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.tmp.biz.modules.appr.approvaldata.domain.po.OaApprovalData;
import com.platform.mesh.tmp.biz.modules.appr.approvaldata.mapper.OaApprovalDataMapper;
import com.platform.mesh.tmp.biz.modules.appr.approvaldata.service.IOaApprovalDataService;
import com.platform.mesh.tmp.biz.modules.appr.approvaldata.service.manual.OaApprovalDataServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description OA办公审批数据
 * @author 蝉鸣
 */
@Service
public class IOaApprovalDataServiceImpl extends ServiceImpl<OaApprovalDataMapper, OaApprovalData> implements IOaApprovalDataService {

    @Autowired
    private OaApprovalDataServiceManual oaApprovalDataServiceManual;

}