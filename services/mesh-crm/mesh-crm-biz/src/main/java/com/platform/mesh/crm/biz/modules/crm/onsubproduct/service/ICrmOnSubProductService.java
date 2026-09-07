package com.platform.mesh.crm.biz.modules.crm.onsubproduct.service;

import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import com.platform.mesh.app.api.modules.app.service.IAppService;
import com.platform.mesh.crm.biz.modules.crm.onsubproduct.domain.po.CrmOnSubProduct;

import java.util.List;
import java.util.Map;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 客户关系关联子产品信息
 * @author 蝉鸣
 */
public interface ICrmOnSubProductService extends IAppService<CrmOnSubProduct> {

    /**
     * 功能描述:
     * 〈从关联业务子表新增数据〉
     * @param dataPO dataPO
     * @param docData docData
     * @author 蝉鸣
     */
    <T extends AppPO> void addSubProduct(T dataPO, Map<String, Object> docData);

    /**
     * 功能描述:
     * 〈获取关联业务下的产品列表〉
     * @param proposalId proposalId
     * @author 蝉鸣
     */
    List<Object> getSubProductByRelDataId(Long proposalId);

    void delSubProductList(List<Long> contractIds);
}