package com.platform.mesh.crm.biz.modules.crm.oninvoice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.bpm.ProcessPassEnum;
import com.platform.mesh.crm.api.modules.crm.constants.CrmConst;
import com.platform.mesh.crm.biz.modules.crm.oninvoice.domain.po.CrmOnInvoice;
import com.platform.mesh.crm.biz.modules.crm.oninvoice.mapper.CrmOnInvoiceMapper;
import com.platform.mesh.crm.biz.modules.crm.oninvoice.service.ICrmOnInvoiceService;
import com.platform.mesh.crm.biz.modules.crm.oninvoice.service.manual.CrmOnInvoiceServiceManual;
import com.platform.mesh.crm.biz.modules.crm.oninvoicedata.domain.po.CrmOnInvoiceData;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系发票回执
 * @author 蝉鸣
 */
@Service
public class CrmOnInvoiceServiceImpl extends AppServiceAbstract<CrmOnInvoiceMapper, CrmOnInvoice> implements ICrmOnInvoiceService  {

    @Autowired
    private CrmOnInvoiceServiceManual crmOnInvoiceServiceManual;


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
        aggregations.add(ObjFieldUtil.getColumnName(CrmOnInvoice::getRealMoney));
        pageDTO.setAggregations(aggregations);
        return this.getAppServiceManual().selectEsPage(pageDTO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系发票回执〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmOnInvoiceData> crmOnInvoiceDataList = BeanUtil.copyToList(dataList, CrmOnInvoiceData.class);
        //批量保存data表数据
        crmOnInvoiceServiceManual.addDbDataBatch(crmOnInvoiceDataList);
    }

    /**
     * 功能描述:
     * 〈新增其他逻辑〉
     * @param crmOnInvoice crmOnInvoice
     * @param dataAddSimpDTO dataAddSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void addOtherAction(CrmOnInvoice crmOnInvoice, DataAddSimpDTO dataAddSimpDTO) {
        Map<String, Object> docData = dataAddSimpDTO.getDocData();
        //获取客户关联数据
        Long customerId = AppUtil.getSingleColumnIdValue(CrmConst.CUSTOMER, docData);
        crmOnInvoice.setCustomerId(customerId);
        //获取合同关联数据
        Long contractId = AppUtil.getSingleColumnIdValue(CrmConst.CONTRACT, docData);
        crmOnInvoice.setContractId(contractId);
        //获取订单关联数据
        Long orderId = AppUtil.getSingleColumnIdValue(CrmConst.ORDER, docData);
        crmOnInvoice.setOrderId(orderId);
        //设置发票金额
        crmOnInvoice.setRealMoney(crmOnInvoice.getTotalMoney().subtract(crmOnInvoice.getDiscountMoney()));
        this.updateById(crmOnInvoice);
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
                .set(CrmOnInvoice::getScopeUserId,scopeUserId)
                .set(CrmOnInvoice::getScopeOrgId,scopeOrgId)
                .in(CrmOnInvoice::getId,dataIds)
                .update();
    }

    /**
     * 功能描述:
     * 〈修改订单开票金额〉
     * @param orderId orderId
     * @author 蝉鸣
     */
    @Override
    public void updateInvoiceMoney(Long orderId) {
        //查询订单下所有发票金额总和
        BigDecimal invoiceMoney = BigDecimal.ZERO;
        List<CrmOnInvoice> invoiceList = this.lambdaQuery()
                .eq(CrmOnInvoice::getOrderId, orderId)
                .eq(CrmOnInvoice::getProcessPass, ProcessPassEnum.PASS.getValue())
                .list();
        if(CollUtil.isNotEmpty(invoiceList)){
            invoiceMoney = invoiceList.stream().map(CrmOnInvoice::getRealMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        //同步发票金额
        crmOnInvoiceServiceManual.updateInvoiceMoney(orderId,invoiceMoney);
    }



}