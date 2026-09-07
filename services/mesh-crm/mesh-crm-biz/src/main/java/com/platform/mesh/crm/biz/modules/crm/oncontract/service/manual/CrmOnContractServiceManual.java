package com.platform.mesh.crm.biz.modules.crm.oncontract.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.crm.api.modules.crm.constants.CrmConst;
import com.platform.mesh.crm.biz.modules.crm.oncontract.domain.po.CrmOnContract;
import com.platform.mesh.crm.biz.modules.crm.oncontractdata.domain.po.CrmOnContractData;
import com.platform.mesh.crm.biz.modules.crm.oncontractdata.service.ICrmOnContractDataService;
import com.platform.mesh.crm.biz.modules.crm.oncontractrel.po.CrmOnContractRel;
import com.platform.mesh.crm.biz.modules.crm.oncontractrel.service.ICrmOnContractRelService;
import com.platform.mesh.crm.biz.modules.crm.onsubproduct.service.ICrmOnSubProductService;
import com.platform.mesh.crm.biz.modules.crm.precustomer.enums.ConfirmFlagEnum;
import com.platform.mesh.crm.biz.modules.crm.precustomer.service.ICrmPreCustomerService;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系合同签订
 * @author 蝉鸣
 */
@Service
public class CrmOnContractServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmOnContractServiceManual.class);

    @Autowired
    private ICrmOnContractDataService crmOnContractDataService;

    @Autowired
    private ICrmOnContractRelService crmOnContractRelService;

    @Autowired
    private ICrmOnSubProductService crmOnSubProductService;

    @Autowired
    private ICrmPreCustomerService crmPreCustomerService;

    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param onContractDataList onContractDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmOnContractData> onContractDataList) {
        if(CollUtil.isEmpty(onContractDataList)){
            return;
        }
        CrmOnContractData data = CollUtil.getFirst(onContractDataList);
        //删除旧数据
        crmOnContractDataService.lambdaUpdate().eq(CrmOnContractData::getDataId,data.getDataId()).remove();
        //批量新增信息
        crmOnContractDataService.saveBatch(onContractDataList);
    }

    /**
     * 功能描述:
     * 〈合同数据关系保存〉
     * @param contractRelList contractRelList
     * @author 蝉鸣
     */
    public void addDbRelBatch(List<CrmOnContractRel> contractRelList) {
        if(CollUtil.isEmpty(contractRelList)){
            return;
        }
        CrmOnContractRel contractRel = CollUtil.getFirst(contractRelList);
        //删除旧数据
        crmOnContractRelService.lambdaUpdate()
                .eq(CrmOnContractRel::getModuleId, contractRel.getModuleId())
                .eq(CrmOnContractRel::getDataId, contractRel.getDataId())
                .remove();
        //修改关联数据
        crmOnContractRelService.saveBatch(contractRelList);
    }

    /**
     * 功能描述:
     * 〈保存合同下的产品数据〉
     * @param dataPO dataPO
     * @param dataAddDTO dataAddDTO
     * @author 蝉鸣
     */
    public void saveSubChildList(CrmOnContract dataPO, DataAddSimpDTO dataAddDTO) {
        //将当前信息冗余
        JSONArray array = JSONUtil.createArray();
        JSONObject contract = JSONUtil.createObj();
        contract.set(StrConst.ID,dataPO.getId());
        contract.set(StrConst.NAME,dataPO.getDataName());
        array.add(contract);
        dataAddDTO.getDocData().put(AppUtil.getJsonName(CrmConst.CONTRACT),array);
        //添加产品
        crmOnSubProductService.addSubProduct(dataPO,dataAddDTO.getDocData());
        //移除子表数据，不再保存当前数据中
        dataAddDTO.getDocData().remove(CrmConst.PRODUCT_LIST);
    }

    /**
     * 功能描述:
     * 〈获取合同下的产品列表〉
     * @param contractId contractId
     * @author 蝉鸣
     */
    public List<Object> getSubProductList(Long contractId) {
        return crmOnSubProductService.getSubProductByRelDataId(contractId);
    }

    /**
     * 功能描述:
     * 〈删除合同下的产品列表〉
     * @param contractIds contractIds
     * @author 蝉鸣
     */
    public void delSubProductList(List<Long> contractIds) {
        crmOnSubProductService.delSubProductList(contractIds);
    }

    /**
     * 功能描述:
     * 〈解析返回数据〉
     * @param pageVO pageVO
     * @author 蝉鸣
     */
    public PageVO<Object> parseVO(PageVO<Object> pageVO) {
        //处理统计金额,为保证合同金额 = 已回款 + 未回款,进行计算处理
        Map<String, Object> voAggregations = pageVO.getAggregations();
        BigDecimal realMoney = BigDecimal.ZERO;
        BigDecimal receivedMoney = BigDecimal.ZERO;
        //合同金额
        if(voAggregations.containsKey(ObjFieldUtil.getColumnName(CrmOnContract::getRealMoney))){
            Object object = voAggregations.get(ObjFieldUtil.getColumnName(CrmOnContract::getRealMoney));
            realMoney = new BigDecimal(object.toString());
        }
        //回款金额
        if(voAggregations.containsKey(ObjFieldUtil.getColumnName(CrmOnContract::getReceivedMoney))){
            Object object = voAggregations.get(ObjFieldUtil.getColumnName(CrmOnContract::getReceivedMoney));
            receivedMoney = new BigDecimal(object.toString());
        }
        //未回款金额
        BigDecimal unreceivedMoney = realMoney.subtract(receivedMoney);
        pageVO.getAggregations().put(ObjFieldUtil.getColumnName(CrmOnContract::getUnreceivedMoney),unreceivedMoney);
        return pageVO;
    }

    /**
     * 功能描述:
     * 〈设置客户成交状态〉
     * @param customerId customerId
     * @param confirmFlagEnum confirmFlagEnum
     * @param totalMoney totalMoney
     * @author 蝉鸣
     */
    public void setCustomerStatusAndMoney(Long customerId, ConfirmFlagEnum confirmFlagEnum, BigDecimal totalMoney) {
        crmPreCustomerService.updateConfirmFlag(customerId,confirmFlagEnum,totalMoney);
    }

}