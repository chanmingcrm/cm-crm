package com.platform.mesh.crm.biz.modules.tmp.appr.approval.service.manual;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.modules.tmp.appr.approvaldata.domain.po.TmpOaApprovalData;
import com.platform.mesh.crm.biz.modules.tmp.appr.approvaldata.service.ITmpOaApprovalDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description OA办公审批
 * @author 蝉鸣
 */
@Service
public class TmpOaApprovalServiceManual {

    private final static Logger log = LoggerFactory.getLogger(TmpOaApprovalServiceManual.class);


    @Autowired
    private ITmpOaApprovalDataService oaApprovalDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param tmpOaApprovalDataList oaApprovalDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<TmpOaApprovalData> tmpOaApprovalDataList) {
        if(CollUtil.isEmpty(tmpOaApprovalDataList)){
            return;
        }
        TmpOaApprovalData data = CollUtil.getFirst(tmpOaApprovalDataList);
        //删除旧数据
        oaApprovalDataService.lambdaUpdate().eq(TmpOaApprovalData::getDataId,data.getDataId()).remove();
        //批量新增信息
        oaApprovalDataService.saveBatch(tmpOaApprovalDataList);
    }

}