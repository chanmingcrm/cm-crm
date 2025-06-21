package com.platform.mesh.app.biz.modules.data.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.app.biz.modules.data.common.domain.po.DataCommon;
import com.platform.mesh.app.biz.modules.data.common.mapper.DataCommonMapper;
import com.platform.mesh.app.biz.modules.data.common.service.IDataCommonService;
import com.platform.mesh.app.biz.modules.data.common.service.manual.DataCommonServiceManual;
import com.platform.mesh.app.biz.modules.data.commondata.domain.po.DataCommonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 单字段数据
 * @author 蝉鸣
 */
@Service
public class DataCommonServiceImpl extends AppServiceAbstract<DataCommonMapper, DataCommon> implements IDataCommonService {


    @Autowired
    private DataCommonServiceManual dataCommonServiceManual;

    /**
     * 功能描述:
     * 〈新增通用数据〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<DataCommonData> crmOnBusinessData = BeanUtil.copyToList(dataList,DataCommonData.class);
        //批量保存data表数据
        dataCommonServiceManual.addDbDataBatch(crmOnBusinessData);
    }

    /**
     * 功能描述:
     * 〈新增通用数据〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //批量保存data表数据
        dataCommonServiceManual.editDbDataBatch(dataId,dataEditSimpDTO);
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
                .set(DataCommon::getScopeUserId,scopeUserId)
                .set(DataCommon::getScopeOrgId,scopeOrgId)
                .in(DataCommon::getId,dataIds)
                .update();
        //修改DB Data
        dataCommonServiceManual.transDbDataBatch(dataIds,scopeUserId,scopeOrgId);
    }

}