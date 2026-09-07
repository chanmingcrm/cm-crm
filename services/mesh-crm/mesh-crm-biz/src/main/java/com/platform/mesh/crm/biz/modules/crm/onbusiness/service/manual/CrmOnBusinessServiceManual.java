package com.platform.mesh.crm.biz.modules.crm.onbusiness.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.crm.api.modules.crm.constants.CrmConst;
import com.platform.mesh.crm.biz.modules.crm.onbusiness.domain.po.CrmOnBusiness;
import com.platform.mesh.crm.biz.modules.crm.onbusinessdata.domain.po.CrmOnBusinessData;
import com.platform.mesh.crm.biz.modules.crm.onbusinessdata.service.ICrmOnBusinessDataService;
import com.platform.mesh.crm.biz.modules.crm.onsubproduct.service.ICrmOnSubProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系商机跟进
 * @author 蝉鸣
 */
@Service
public class CrmOnBusinessServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmOnBusinessServiceManual.class);

    @Autowired
    private ICrmOnBusinessDataService crmOnBusinessDataService;

    @Autowired
    private ICrmOnSubProductService crmOnSubProductService;

    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param onBusinessDataList onBusinessDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmOnBusinessData> onBusinessDataList) {
        if(CollUtil.isEmpty(onBusinessDataList)){
            return;
        }
        CrmOnBusinessData data = CollUtil.getFirst(onBusinessDataList);
        //删除旧数据
        crmOnBusinessDataService.lambdaUpdate().eq(CrmOnBusinessData::getDataId,data.getDataId()).remove();
        //批量新增信息
        crmOnBusinessDataService.saveBatch(onBusinessDataList);
    }


    /**
     * 功能描述:
     * 〈保存商机下的产品数据〉
     * @param dataPO dataPO
     * @param dataAddDTO dataAddDTO
     * @author 蝉鸣
     */
    public void saveSubProductList(CrmOnBusiness dataPO, DataAddSimpDTO dataAddDTO) {
        //将当前信息冗余
        JSONArray array = JSONUtil.createArray();
        JSONObject order = JSONUtil.createObj();
        order.set(StrConst.ID,dataPO.getId());
        order.set(StrConst.NAME,dataPO.getDataName());
        array.add(order);
        dataAddDTO.getDocData().put(AppUtil.getJsonName(CrmConst.BUSINESS),array);
        crmOnSubProductService.addSubProduct(dataPO,dataAddDTO.getDocData());
        //移除子表数据，不再保存当前数据中
        dataAddDTO.getDocData().remove(CrmConst.PRODUCT_LIST);
    }

    /**
     * 功能描述:
     * 〈获取商机下的产品列表〉
     * @param businessId businessId
     * @author 蝉鸣
     */
    public List<Object> getSubProductList(Long businessId) {
        return crmOnSubProductService.getSubProductByRelDataId(businessId);
    }

}