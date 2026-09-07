package com.platform.mesh.crm.biz.modules.crm.onprogramme.service.manual;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.modules.crm.onprogrammedata.domain.po.CrmOnProgrammeData;
import com.platform.mesh.crm.biz.modules.crm.onprogrammedata.service.ICrmOnProgrammeDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系方案输出
 * @author 蝉鸣
 */
@Service
public class CrmOnProgrammeServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmOnProgrammeServiceManual.class);

    @Autowired
    private ICrmOnProgrammeDataService crmOnProgrammeDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param onProgrammeDataList onProgrammeDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmOnProgrammeData> onProgrammeDataList) {
        if(CollUtil.isEmpty(onProgrammeDataList)){
            return;
        }
        CrmOnProgrammeData data = CollUtil.getFirst(onProgrammeDataList);
        //删除旧数据
        crmOnProgrammeDataService.lambdaUpdate().eq(CrmOnProgrammeData::getDataId,data.getDataId()).remove();
        //批量新增信息
        crmOnProgrammeDataService.saveBatch(onProgrammeDataList);
    }

}