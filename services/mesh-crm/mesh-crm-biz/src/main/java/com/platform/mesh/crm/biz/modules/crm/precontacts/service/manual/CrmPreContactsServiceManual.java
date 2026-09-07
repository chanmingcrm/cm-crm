package com.platform.mesh.crm.biz.modules.crm.precontacts.service.manual;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.modules.crm.precontactsdata.domain.po.CrmPreContactsData;
import com.platform.mesh.crm.biz.modules.crm.precontactsdata.service.ICrmPreContactsDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系联系人
 * @author 蝉鸣
 */
@Service
public class CrmPreContactsServiceManual {

    private final static Logger log = LoggerFactory.getLogger(CrmPreContactsServiceManual.class);

    @Autowired
    private ICrmPreContactsDataService crmPreContactsDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param preContactsDataList preContactsDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmPreContactsData> preContactsDataList) {
        if(CollUtil.isEmpty(preContactsDataList)){
            return;
        }
        CrmPreContactsData data = CollUtil.getFirst(preContactsDataList);
        //删除旧数据
        crmPreContactsDataService.lambdaUpdate().eq(CrmPreContactsData::getDataId,data.getDataId()).remove();
        //批量新增信息
        crmPreContactsDataService.saveBatch(preContactsDataList);
    }

}