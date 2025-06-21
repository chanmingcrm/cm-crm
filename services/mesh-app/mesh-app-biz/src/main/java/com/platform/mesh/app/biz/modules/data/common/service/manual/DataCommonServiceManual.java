package com.platform.mesh.app.biz.modules.data.common.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.app.biz.modules.data.commondata.domain.po.DataCommonData;
import com.platform.mesh.app.biz.modules.data.commondata.service.IDataCommonDataService;
import com.platform.mesh.app.biz.modules.data.commonrel.service.IDataCommonRelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 单字段数据
 * @author 蝉鸣
 */
@Service
public class DataCommonServiceManual {

    private final static Logger log = LoggerFactory.getLogger(DataCommonServiceManual.class);

    @Autowired
    private IDataCommonDataService dataCommonDataService;

    @Autowired
    private IDataCommonRelService dataCommonRelService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param onBusinessDataList onBusinessDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<DataCommonData> onBusinessDataList) {
        if(CollUtil.isEmpty(onBusinessDataList)){
            return;
        }
        //批量扩展数据
        dataCommonDataService.saveBatch(onBusinessDataList);
        //修改关联数据
        dataCommonRelService.saveBatch(CollUtil.newArrayList());
    }

    /**
     * 功能描述:
     * 〈DB Data 数据批量修改〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    public void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //查询已经存在的新增数据
        List<DataCommonData> dataList = dataCommonDataService.lambdaQuery().eq(DataCommonData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(DataCommonData::getDataId, dataId).list();
        if(CollUtil.isEmpty(dataList)) {
            return;
        }
        AppUtil.editDbData(dataList, dataEditSimpDTO);
        if(CollUtil.isEmpty(dataList)){
            return;
        }
        //修改扩展数据
        dataCommonDataService.updateBatchById(dataList);
        //修改关联数据
        dataCommonRelService.saveBatch(CollUtil.newArrayList());

    }

    /**
     * 功能描述:
     * 〈转移Data数据权限必须重写〉
     * @param dataIds dataIds
     * @param scopeUserId scopeUserId
     * @param scopeOrgId scopeOrgId
     * @author 蝉鸣
     */
    public void transDbDataBatch(List<Long> dataIds, Long scopeUserId, Long scopeOrgId) {
        if(CollUtil.isEmpty(dataIds) || ObjectUtil.isEmpty(scopeUserId) || ObjectUtil.isEmpty(scopeOrgId)) {
            return;
        }
        dataCommonDataService.lambdaUpdate()
                .set(DataCommonData::getScopeUserId, scopeUserId)
                .set(DataCommonData::getScopeOrgId, scopeOrgId)
                .in(DataCommonData::getDataId, dataIds)
                .update();
    }
}