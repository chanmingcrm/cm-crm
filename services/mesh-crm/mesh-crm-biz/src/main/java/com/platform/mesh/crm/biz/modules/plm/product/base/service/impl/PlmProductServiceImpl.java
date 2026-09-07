package com.platform.mesh.crm.biz.modules.plm.product.base.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.domain.vo.AppVO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.api.modules.crm.constants.CrmConst;
import com.platform.mesh.crm.biz.modules.plm.product.base.domain.po.PlmProduct;
import com.platform.mesh.crm.biz.modules.plm.product.base.mapper.PlmProductMapper;
import com.platform.mesh.crm.biz.modules.plm.product.base.service.IPlmProductService;
import com.platform.mesh.crm.biz.modules.plm.product.base.service.manual.PlmProductServiceManual;
import com.platform.mesh.crm.biz.modules.plm.product.basedata.domain.po.PlmProductData;
import com.platform.mesh.utils.function.FutureHandleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 供应链产品
 * @author 蝉鸣
 */
@Service
public class PlmProductServiceImpl extends AppServiceAbstract<PlmProductMapper, PlmProduct> implements IPlmProductService {

    @Autowired
    private PlmProductServiceManual plmProductServiceManual;


    /**
     * 功能描述:
     * 〈新增供应链产品〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<PlmProductData> plmProductData = BeanUtil.copyToList(dataList, PlmProductData.class);
        //批量保存data表数据
        plmProductServiceManual.addDbDataBatch(plmProductData);
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
    public  void transDbScopeBatch(List<Long> dataIds,Long scopeUserId,Long scopeOrgId){
        //修改DB
        this.lambdaUpdate()
                .set(PlmProduct::getScopeUserId,scopeUserId)
                .set(PlmProduct::getScopeOrgId,scopeOrgId)
                .in(PlmProduct::getId,dataIds)
                .update();
    }

    /**
     * 功能描述:
     * 〈保存产品明细〉
     * @param dataPO dataPO
     * @param dataAddDTO dataAddDTO
     * @author 蝉鸣
     */
    @Override
    public void addOtherAction(PlmProduct dataPO, DataAddSimpDTO dataAddDTO) {
        //处理产品明细数据
        plmProductServiceManual.saveProductDesignList(dataPO,dataAddDTO);
    }

    /**
     * 功能描述:
     * 〈获取保存产品明细〉
     * @param dataVO dataVO
     * @author 蝉鸣
     */
    @Override
    public <E extends AppVO> E getOtherAction(E dataVO){
        //填充产品明细数据
        List<Object> dataList = plmProductServiceManual.getProductDesignList(dataVO.getId());
        dataVO.getEsData().put(CrmConst.PRODUCT_DESIGN,dataList);
        return dataVO;
    }

    /**
     * 功能描述:
     * 〈删除保存产品明细〉
     * @param dataIds dataIds
     * @author 蝉鸣
     */
    @Override
    public void delOtherAction(List<Long> dataIds){
        //删除产品明细数据
        FutureHandleUtil.runNoResult(dataIds,plmProductServiceManual::delProductDesignList);
    }

}
