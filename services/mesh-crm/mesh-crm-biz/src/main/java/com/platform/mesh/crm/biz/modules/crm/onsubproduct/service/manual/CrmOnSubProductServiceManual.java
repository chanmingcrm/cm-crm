package com.platform.mesh.crm.biz.modules.crm.onsubproduct.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.biz.modules.crm.onsubproduct.domain.po.CrmOnSubProduct;
import com.platform.mesh.crm.biz.modules.crm.onsubproductdata.domain.po.CrmOnSubProductData;
import com.platform.mesh.crm.biz.modules.crm.onsubproductdata.service.ICrmOnSubProductDataService;
import com.platform.mesh.utils.excel.enums.DataTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系关联子产品
 * @author 蝉鸣
 */
@Service
public class CrmOnSubProductServiceManual {

    private final static Logger log = LoggerFactory.getLogger(CrmOnSubProductServiceManual.class);


    @Autowired
    private ICrmOnSubProductDataService crmOnSubProductDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param preProposalProductDataList preProposalProductDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmOnSubProductData> preProposalProductDataList) {
        if(CollUtil.isEmpty(preProposalProductDataList)){
            return;
        }
        CrmOnSubProductData data = CollUtil.getFirst(preProposalProductDataList);
        //删除旧数据
        crmOnSubProductDataService.lambdaUpdate().eq(CrmOnSubProductData::getRelDataId,data.getRelDataId()).remove();
        //批量新增信息
        crmOnSubProductDataService.saveBatch(preProposalProductDataList);
    }

    /**
     * 功能描述:
     * 〈简易字段信息保存〉
     * @param docData docData
     * @author 蝉鸣
     */
    public List<CrmOnSubProductData> parseSubProductData(CrmOnSubProduct subProduct, Map<String, Object> docData) {
        //扩展字段收集
        List<CrmOnSubProductData> dataList = CollUtil.newArrayList();
        //转化存储信息
        Map<String, Object> beanToMap = BeanUtil.beanToMap(subProduct, Boolean.TRUE, Boolean.TRUE);
        Set<String> keySet = beanToMap.keySet();
        // 从 docData 中移除这些 key
        docData.keySet().removeAll(keySet);
        docData.forEach((key, value) -> {
            if(ObjectUtil.isNotEmpty(AppUtil.getMapKey(docData,key)) && ObjectUtil.isNotEmpty(value)) {
                CrmOnSubProductData dataPO = BeanUtil.copyProperties(new AppDataPO(), CrmOnSubProductData.class);
                dataPO.setRelModuleId(subProduct.getRelModuleId());
                dataPO.setRelDataId(subProduct.getRelDataId());
                dataPO.setDataId(subProduct.getId());
                dataPO.setDataType(DataTypeEnum.INIT.getValue());
                dataPO.setColumnMac(key);
                if(ObjectUtil.isNotEmpty(value)){
                    dataPO.setDataValue(value);
                    dataList.add(dataPO);
                }
            }
        });
        return dataList;
    }

    /**
     * 功能描述:
     * 〈根据Ids获取Data数据列表〉
     * @param ids ids
     * @author 蝉鸣
     */
    public List<CrmOnSubProductData> getSubProductData(List<Long> ids) {
        return crmOnSubProductDataService.lambdaQuery().in(CrmOnSubProductData::getDataId, ids).list();
    }


}