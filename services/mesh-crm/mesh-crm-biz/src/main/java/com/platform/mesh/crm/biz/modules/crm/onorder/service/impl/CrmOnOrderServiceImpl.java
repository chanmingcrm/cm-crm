package com.platform.mesh.crm.biz.modules.crm.onorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.bpm.ProcessPassEnum;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.crm.biz.modules.crm.onorder.domain.po.CrmOnOrder;
import com.platform.mesh.crm.biz.modules.crm.onorder.mapper.CrmOnOrderMapper;
import com.platform.mesh.crm.biz.modules.crm.onorder.service.ICrmOnOrderService;
import com.platform.mesh.crm.biz.modules.crm.onorder.service.manual.CrmOnOrderServiceManual;
import com.platform.mesh.crm.biz.modules.crm.onorderdata.domain.po.CrmOnOrderData;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系订单
 * @author 蝉鸣
 */
@Service
public class CrmOnOrderServiceImpl extends AppServiceAbstract<CrmOnOrderMapper, CrmOnOrder> implements ICrmOnOrderService {

    @Autowired
    private CrmOnOrderServiceManual crmOnOrderServiceManual;


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
        aggregations.add(ObjFieldUtil.getColumnName(CrmOnOrder::getRealMoney));
        aggregations.add(ObjFieldUtil.getColumnName(CrmOnOrder::getReceivedMoney));
        aggregations.add(ObjFieldUtil.getColumnName(CrmOnOrder::getUnreceivedMoney));
        pageDTO.setAggregations(aggregations);
        return this.getAppServiceManual().selectEsPage(pageDTO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系商机跟进〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmOnOrderData> crmOnOrderData = BeanUtil.copyToList(dataList, CrmOnOrderData.class);
        //批量保存data表数据
        crmOnOrderServiceManual.addDbDataBatch(crmOnOrderData);
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
                .set(CrmOnOrder::getScopeUserId,scopeUserId)
                .set(CrmOnOrder::getScopeOrgId,scopeOrgId)
                .in(CrmOnOrder::getId,dataIds)
                .update();
    }

    /**
     * 功能描述:
     * 〈同步订单金额〉
     * @param orderId orderId
     * @param realMoney realMoney
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public void updatePaymentMoney(Long orderId, BigDecimal realMoney) {
        CrmOnOrder crmOnOrder = getById(orderId);
        if(ObjectUtil.isEmpty(crmOnOrder)){
            return;
        }
        crmOnOrder.setReceivedMoney(realMoney);
        this.updateById(crmOnOrder);
        //查询合同下所有订单金额总和
        this.updateReceivedMoney(crmOnOrder.getContractId());
    }

    /**
     * 功能描述:
     * 〈同步发票金额〉
     * @param orderId orderId
     * @param realMoney realMoney
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public void updateInvoiceMoney(Long orderId, BigDecimal realMoney) {
        CrmOnOrder crmOnOrder = getById(orderId);
        if(ObjectUtil.isEmpty(crmOnOrder)){
            return;
        }
        crmOnOrder.setInvoiceMoney(realMoney);
        this.updateById(crmOnOrder);
        //更新ES
        AppModuleBaseBO appModuleBaseBO = this.getAppServiceManual().getModuleInfo(crmOnOrder.getModuleId());
        HashMap<String, Object> map = new HashMap<>();
        map.put(ObjFieldUtil.getColumnName(CrmOnOrder::getInvoiceMoney), crmOnOrder.getInvoiceMoney());
        this.getAppServiceManual().addEsData(appModuleBaseBO.getModuleIndex(),crmOnOrder,map);
        //查询合同下所有订单发票金额总和
        BigDecimal invoiceMoney = BigDecimal.ZERO;
        List<CrmOnOrder> orderList = this.lambdaQuery()
                .eq(CrmOnOrder::getContractId, crmOnOrder.getContractId())
                .eq(CrmOnOrder::getProcessPass, ProcessPassEnum.PASS.getValue())
                .list();
        if(CollUtil.isNotEmpty(orderList)){
            invoiceMoney = orderList.stream().map(CrmOnOrder::getInvoiceMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        crmOnOrderServiceManual.updateInvoiceMoney(crmOnOrder.getContractId(), invoiceMoney);
    }

    /**
     * 功能描述:
     * 〈同步合同金额〉
     * @param contractId contractId
     * @author 蝉鸣
     */
    @Override
    public void updateReceivedMoney(Long contractId) {
        BigDecimal orderMoney = BigDecimal.ZERO;
        List<CrmOnOrder> orderList = this.lambdaQuery()
                .eq(CrmOnOrder::getContractId, contractId)
                .eq(CrmOnOrder::getProcessPass, ProcessPassEnum.PASS.getValue())
                .list();
        if(CollUtil.isNotEmpty(orderList)){
            orderMoney = orderList.stream().map(CrmOnOrder::getReceivedMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        crmOnOrderServiceManual.updateReceivedMoney(contractId, orderMoney);
    }
}