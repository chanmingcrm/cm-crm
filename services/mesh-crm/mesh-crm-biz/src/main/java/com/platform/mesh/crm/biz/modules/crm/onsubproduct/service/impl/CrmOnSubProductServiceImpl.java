package com.platform.mesh.crm.biz.modules.crm.onsubproduct.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.bo.AppFormColumnBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import com.platform.mesh.app.api.modules.app.enums.comp.FormTypeEnum;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.crm.api.modules.crm.constants.CrmConst;
import com.platform.mesh.crm.biz.modules.crm.onsubproduct.domain.po.CrmOnSubProduct;
import com.platform.mesh.crm.biz.modules.crm.onsubproduct.mapper.CrmOnSubProductMapper;
import com.platform.mesh.crm.biz.modules.crm.onsubproduct.service.ICrmOnSubProductService;
import com.platform.mesh.crm.biz.modules.crm.onsubproduct.service.manual.CrmOnSubProductServiceManual;
import com.platform.mesh.crm.biz.modules.crm.onsubproductdata.domain.po.CrmOnSubProductData;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系关联子产品
 * @author 蝉鸣
 */
@Service
public class CrmOnSubProductServiceImpl extends AppServiceAbstract<CrmOnSubProductMapper, CrmOnSubProduct> implements ICrmOnSubProductService {

    @Autowired
    private CrmOnSubProductServiceManual crmOnSubProductServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系关联子产品〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmOnSubProductData> crmOnSubProductDataList = BeanUtil.copyToList(dataList, CrmOnSubProductData.class);
        //批量保存data表数据
        crmOnSubProductServiceManual.addDbDataBatch(crmOnSubProductDataList);
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
                .set(CrmOnSubProduct::getScopeUserId,scopeUserId)
                .set(CrmOnSubProduct::getScopeOrgId,scopeOrgId)
                .in(CrmOnSubProduct::getId,dataIds)
                .update();
    }

    /**
     * 功能描述:
     * 〈从关联业务子表新增数据〉
     * @param dataPO dataPO
     * @param docMap docMap
     * @author 蝉鸣
     */
    @Override
    public <T extends AppPO> void addSubProduct(T dataPO, Map<String, Object> docMap) {
        Object object = docMap.get(CrmConst.PRODUCT_LIST);
        if(ObjectUtil.isEmpty(object)){
            return;
        }
        //获得module_id,
        AppModuleBaseBO appModuleBaseBO = this.getAppServiceManual().getModuleIdBySchema(SqlUtil.getTableName(CrmOnSubProduct.class, TableName.class));
        if(ObjectUtil.isEmpty(appModuleBaseBO)){
            return;
        }
        List<AppFormColumnBO> columnBOS = this.getAppServiceManual().getFormColumnInfo(appModuleBaseBO.getId(), FormTypeEnum.FORM_ADD.getValue());
        if(CollUtil.isEmpty(columnBOS)){
            return;
        }
        List<Long> ids = this.lambdaQuery().eq(CrmOnSubProduct::getProposalId, dataPO.getId()).list().stream().map(CrmOnSubProduct::getId).toList();
        //删除DB旧数据
        this.lambdaUpdate().eq(CrmOnSubProduct::getRelModuleId,dataPO.getModuleId()).eq(CrmOnSubProduct::getRelDataId,dataPO.getId()).remove();
        //保存Db
        for (Object productRel : JSONUtil.parseArray(object)) {
            Map<String, Object> productMap = AppUtil.beanToMap(productRel);
            productMap.put(AppUtil.getJsonName(CrmConst.CUSTOMER),AppUtil.getColumnValue(CrmConst.CUSTOMER,docMap));
            productMap.put(AppUtil.getJsonName(CrmConst.BUSINESS),AppUtil.getColumnValue(CrmConst.BUSINESS,docMap));
            productMap.put(AppUtil.getJsonName(CrmConst.PROPOSAL),AppUtil.getColumnValue(CrmConst.PROPOSAL,docMap));
            productMap.put(AppUtil.getJsonName(CrmConst.CONTRACT),AppUtil.getColumnValue(CrmConst.CONTRACT,docMap));
            CrmOnSubProduct subProduct = BeanUtil.copyProperties(productMap, CrmOnSubProduct.class);
            subProduct.setRelModuleId(dataPO.getModuleId());
            subProduct.setRelDataId(dataPO.getId());
            subProduct.setCustomerId(AppUtil.getSingleColumnIdValue(CrmConst.CUSTOMER,docMap));
            subProduct.setBusinessId(AppUtil.getSingleColumnIdValue(CrmConst.BUSINESS,docMap));
            subProduct.setProposalId(AppUtil.getSingleColumnIdValue(CrmConst.PROPOSAL,docMap));
            subProduct.setContractId(AppUtil.getSingleColumnIdValue(CrmConst.CONTRACT,docMap));
            subProduct.setProductId(MapUtil.getLong(productMap,StrConst.DATA_ID));
            subProduct.setDelFlag(YesOrNoEnum.YES.getValue());
            //保存Db
            this.saveOrUpdate(subProduct);
            //保存DbData
            List<CrmOnSubProductData> dbDataSimp = crmOnSubProductServiceManual.parseSubProductData(subProduct,productMap);
            //批量保存data表数据
            crmOnSubProductServiceManual.addDbDataBatch(dbDataSimp);
        }
    }

    /**
     * 功能描述:
     * 〈获取关联业务下的产品列表〉
     * @param relDataId relDataId
     * @author 蝉鸣
     */
    @Override
    public List<Object> getSubProductByRelDataId(Long relDataId) {
        //1通过SQL形式，2通过ES形式 参考OMS 订单
        List<CrmOnSubProduct> subProducts = this.lambdaQuery().eq(CrmOnSubProduct::getRelDataId, relDataId).list();
        if(CollUtil.isEmpty(subProducts)){
            return new ArrayList<>();
        }
        List<Long> ids = subProducts.stream().map(CrmOnSubProduct::getId).toList();
        List<CrmOnSubProductData> proposalProductData = crmOnSubProductServiceManual.getSubProductData(ids);
        List<Object> result = CollUtil.newArrayList();
        for (CrmOnSubProduct product : subProducts) {
            Map<String, Object> toMap = AppUtil.beanToMap(product);
            Map<String, Object> dataMap = proposalProductData.stream()
                    .filter(p -> p.getDataId().equals(product.getId()))
                    .collect(Collectors.toMap(CrmOnSubProductData::getColumnMac, CrmOnSubProductData::getDataValue));
            toMap.putAll(dataMap);
            result.add(toMap);
        }
        return result;
    }

    /**
     * 功能描述:
     * 〈删除关联业务下的产品列表〉
     * @param relDataIds relDataIds
     * @author 蝉鸣
     */
    @Override
    public void delSubProductList(List<Long> relDataIds) {
        this.lambdaUpdate()
                .in(CrmOnSubProduct::getRelDataId, relDataIds)
                .remove();
        //没有绑定ES
    }
}