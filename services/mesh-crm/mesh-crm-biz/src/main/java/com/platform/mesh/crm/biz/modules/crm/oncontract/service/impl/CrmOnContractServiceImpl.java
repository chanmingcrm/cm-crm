package com.platform.mesh.crm.biz.modules.crm.oncontract.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.domain.po.AppRelPO;
import com.platform.mesh.app.api.modules.app.domain.vo.AppVO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.bpm.ProcessPassEnum;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.crm.api.modules.crm.constants.CrmConst;
import com.platform.mesh.crm.api.modules.fms.base.domain.bo.MoneyBO;
import com.platform.mesh.crm.biz.modules.crm.oncontract.domain.po.CrmOnContract;
import com.platform.mesh.crm.biz.modules.crm.oncontract.mapper.CrmOnContractMapper;
import com.platform.mesh.crm.biz.modules.crm.oncontract.service.ICrmOnContractService;
import com.platform.mesh.crm.biz.modules.crm.oncontract.service.manual.CrmOnContractServiceManual;
import com.platform.mesh.crm.biz.modules.crm.oncontractdata.domain.po.CrmOnContractData;
import com.platform.mesh.crm.biz.modules.crm.oncontractrel.po.CrmOnContractRel;
import com.platform.mesh.crm.biz.modules.crm.precustomer.enums.ConfirmFlagEnum;
import com.platform.mesh.crm.biz.soa.event.crm.oncontract.CrmOnContractCreatedEvent;
import com.platform.mesh.crm.biz.soa.event.crm.oncontract.CrmOnContractDeletedEvent;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.utils.function.FutureHandleUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系合同签订
 * @author 蝉鸣
 */
@Service
public class CrmOnContractServiceImpl extends AppServiceAbstract<CrmOnContractMapper, CrmOnContract> implements ICrmOnContractService  {

    @Autowired
    private CrmOnContractServiceManual crmOnContractServiceManual;


    /**
     * 功能描述:
     * 〈获取ES数据分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO <Object>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<Object> selectEsPage(EsDocPGetDTO pageDTO) {
        
        //添加分析字段
        List<String> aggregations = CollUtil.newArrayList();
        aggregations.add(ObjFieldUtil.getColumnName(CrmOnContract::getRealMoney));
        aggregations.add(ObjFieldUtil.getColumnName(CrmOnContract::getReceivedMoney));
        pageDTO.setAggregations(aggregations);
        PageVO<Object> pageVO = this.getAppServiceManual().selectEsPage(pageDTO);
        //解析列表
        return crmOnContractServiceManual.parseVO(pageVO);

    }

    /**
     * 功能描述:
     * 〈新增客户关系商机跟进〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmOnContractData> crmOnContractData = BeanUtil.copyToList(dataList, CrmOnContractData.class);
        //批量保存data表数据
        crmOnContractServiceManual.addDbDataBatch(crmOnContractData);
    }

    /**
     * 功能描述:
     * 〈新增Rel通用数据〉
     * @param relList relList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppRelPO> void addDbRelBatch(List<D> relList) {
        List<CrmOnContractRel> contractRelList = BeanUtil.copyToList(relList, CrmOnContractRel.class);
        //批量保存rel表数据
        crmOnContractServiceManual.addDbRelBatch(contractRelList);
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
                .set(CrmOnContract::getScopeUserId,scopeUserId)
                .set(CrmOnContract::getScopeOrgId,scopeOrgId)
                .in(CrmOnContract::getId,dataIds)
                .update();
    }

    /**
     * 功能描述:
     * 〈新增其他逻辑〉
     * @param crmOnContract crmOnContract
     * @param dataAddSimpDTO dataAddSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void addOtherAction(CrmOnContract crmOnContract, DataAddSimpDTO dataAddSimpDTO) {
        Map<String, Object> docData = dataAddSimpDTO.getDocData();
        //获取客户关联数据
        Long customerId = AppUtil.getSingleColumnIdValue(CrmConst.CUSTOMER, docData);
        crmOnContract.setCustomerId(customerId);
        //获取商机关联数据
        Long businessId = AppUtil.getSingleColumnIdValue(CrmConst.BUSINESS, docData);
        crmOnContract.setBusinessId(businessId);
        //设置合同金额
        crmOnContract.setRealMoney(crmOnContract.getTotalMoney().subtract(crmOnContract.getDiscountMoney()));
        //设置未收金额
        crmOnContract.setUnreceivedMoney(crmOnContract.getRealMoney().subtract(crmOnContract.getReceivedMoney()));
        //处理子表数据
        crmOnContractServiceManual.saveSubChildList(crmOnContract,dataAddSimpDTO);
        //如果审批ID为空则视为此业务无需审批自动通过,否则在审批后执行
        if(ObjectUtil.isEmpty(dataAddSimpDTO.getTempProcessId())){
            crmOnContract.setProcessPass(ProcessPassEnum.PASS.getValue());
            //发布事件
            SpringContextHolderUtil.publishEvent(new CrmOnContractCreatedEvent(crmOnContract,dataAddSimpDTO.getDocData()));
        }
        this.updateById(crmOnContract);
    }

    /**
     * 功能描述:
     * 〈获取订单下的产品列表〉
     * @param dataVO dataVO
     * @author 蝉鸣
     */
    @Override
    public <E extends AppVO> E getOtherAction(E dataVO){
        //填充订单产品列表数据
        List<Object> dataList = crmOnContractServiceManual.getSubProductList(dataVO.getId());
        dataVO.getEsData().put(CrmConst.PRODUCT_LIST,dataList);
        return dataVO;
    }


    /**
     * 功能描述:
     * 〈删除订单下的产品列表〉
     * @param dataIds dataIds
     * @author 蝉鸣
     */
    @Override
    public void delOtherAction(List<Long> dataIds){
        //删除订单产品列表数据
        FutureHandleUtil.runNoResult(dataIds,crmOnContractServiceManual::delSubProductList);
        //发布事件
        SpringContextHolderUtil.publishEvent(new CrmOnContractDeletedEvent(dataIds));
    }


    /**
     * 功能描述:
     * 〈更新合同合计，折扣，实际〉
     * @param contractId contractId
     * @param moneyBO moneyBO
     * @author 蝉鸣
     */
    @Override
    public void updateReceivableMoney(Long contractId, MoneyBO moneyBO) {
        if (ObjectUtil.isEmpty(moneyBO)) {
            return;
        }
        CrmOnContract crmOnContract = this.getById(contractId);
        if(ObjectUtil.isEmpty(crmOnContract)){
            return;
        }
        crmOnContract.setTotalMoney(moneyBO.getTotalMoney());
        crmOnContract.setDiscountMoney(moneyBO.getDiscountMoney());
        crmOnContract.setRealMoney(moneyBO.getRealMoney());
        crmOnContract.setCostMoney(moneyBO.getCostMoney());
        crmOnContract.setProfitMoney(moneyBO.getProfitMoney());
        //修改未回款金额
        crmOnContract.setUnreceivedMoney(crmOnContract.getRealMoney().subtract(crmOnContract.getReceivedMoney()));
        //修改DB
        this.updateById(crmOnContract);
        //修改ES
        AppModuleBaseBO moduleBaseBO = getAppServiceManual().getModuleInfo(crmOnContract.getModuleId());
        getAppServiceManual().editEsData(moduleBaseBO.getModuleIndex(),crmOnContract,AppUtil.beanToMap(crmOnContract));
    }

    /**
     * 功能描述:
     * 〈订单金额同步合同金额〉
     * @param contractId contractId
     * @param orderMoney orderMoney
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public void updateReceivedMoney(Long contractId, BigDecimal orderMoney) {
        CrmOnContract crmOnContract = getById(contractId);
        if(ObjectUtil.isEmpty(crmOnContract)){
            return;
        }
        crmOnContract.setReceivedMoney(orderMoney);
        crmOnContract.setUnreceivedMoney(crmOnContract.getRealMoney().subtract(crmOnContract.getReceivedMoney()));
        this.updateById(crmOnContract);
        //更新ES
        AppModuleBaseBO moduleBaseBO = getAppServiceManual().getModuleInfo(crmOnContract.getModuleId());
        HashMap<String, Object> map = new HashMap<>();
        map.put(ObjFieldUtil.getColumnName(CrmOnContract::getReceivedMoney), crmOnContract.getReceivedMoney());
        map.put(ObjFieldUtil.getColumnName(CrmOnContract::getUnreceivedMoney), crmOnContract.getUnreceivedMoney());
        getAppServiceManual().editEsData(moduleBaseBO.getModuleIndex(),crmOnContract,map);
    }

    /**
     * 功能描述:
     * 〈订单发票金额同步合同发票金额〉
     * @param contractId contractId
     * @param invoiceMoney invoiceMoney
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public void updateInvoiceMoney(Long contractId, BigDecimal invoiceMoney) {
        CrmOnContract crmOnContract = getById(contractId);
        if(ObjectUtil.isEmpty(crmOnContract)){
            return;
        }
        crmOnContract.setInvoiceMoney(invoiceMoney);
        this.updateById(crmOnContract);
        //更新ES
        AppModuleBaseBO appModuleBaseBO = this.getAppServiceManual().getModuleInfo(crmOnContract.getModuleId());
        HashMap<String, Object> map = new HashMap<>();
        map.put(ObjFieldUtil.getColumnName(CrmOnContract::getInvoiceMoney), crmOnContract.getInvoiceMoney());
        this.getAppServiceManual().addEsData(appModuleBaseBO.getModuleIndex(),crmOnContract,map);
    }

    /**
     * 功能描述:
     * 〈查询客户下的总金额〉
     * @param customerId customerId
     * @author 蝉鸣
     */
    @Override
    public BigDecimal getTotalMoneyByCustomerId(Long customerId) {
        return this.getBaseMapper().getTotalMoneyByCustomerId(customerId,ProcessPassEnum.PASS.getValue());
    }

    /**
     * 功能描述:
     * 〈设置客户下的总金额〉
     * @param customerId customerId
     * @author 蝉鸣
     */
    @Override
    public void setTotalMoneyByCustomerId(Long customerId, ConfirmFlagEnum confirmFlagEnum, BigDecimal totalMoney) {
        crmOnContractServiceManual.setCustomerStatusAndMoney(customerId,confirmFlagEnum,totalMoney);
    }

}