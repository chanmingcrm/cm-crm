package com.platform.mesh.crm.biz.modules.crm.onpayment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.bpm.ProcessPassEnum;
import com.platform.mesh.crm.api.modules.crm.constants.CrmConst;
import com.platform.mesh.crm.biz.modules.crm.onpayment.domain.bo.PaymentSumBO;
import com.platform.mesh.crm.biz.modules.crm.onpayment.domain.po.CrmOnPayment;
import com.platform.mesh.crm.biz.modules.crm.onpayment.mapper.CrmOnPaymentMapper;
import com.platform.mesh.crm.biz.modules.crm.onpayment.service.ICrmOnPaymentService;
import com.platform.mesh.crm.biz.modules.crm.onpayment.service.manual.CrmOnPaymentServiceManual;
import com.platform.mesh.crm.biz.modules.crm.onpaymentdata.domain.po.CrmOnPaymentData;
import com.platform.mesh.crm.biz.soa.event.crm.onpayment.CrmOnPaymentCreatedEvent;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系款项记录
 * @author 蝉鸣
 */
@Service
public class CrmOnPaymentServiceImpl extends AppServiceAbstract<CrmOnPaymentMapper, CrmOnPayment> implements ICrmOnPaymentService  {

    @Autowired
    private CrmOnPaymentServiceManual crmOnPaymentServiceManual;

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
        aggregations.add(ObjFieldUtil.getColumnName(CrmOnPayment::getRealMoney));
        pageDTO.setAggregations(aggregations);
        return this.getAppServiceManual().selectEsPage(pageDTO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系款项记录〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmOnPaymentData> crmOnPaymentDataList = BeanUtil.copyToList(dataList, CrmOnPaymentData.class);
        //批量保存data表数据
        crmOnPaymentServiceManual.addDbDataBatch(crmOnPaymentDataList);
    }

    /**
     * 功能描述:
     * 〈新增其他逻辑〉
     * @param crmOnPayment crmOnPayment
     * @param dataAddSimpDTO dataAddSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void addOtherAction(CrmOnPayment crmOnPayment, DataAddSimpDTO dataAddSimpDTO) {
        Map<String, Object> docData = dataAddSimpDTO.getDocData();
        //获取客户关联数据
        Long customerId = AppUtil.getSingleColumnIdValue(CrmConst.CUSTOMER, docData);
        crmOnPayment.setCustomerId(customerId);
        //获取合同关联数据
        Long contractId = AppUtil.getSingleColumnIdValue(CrmConst.CONTRACT, docData);
        crmOnPayment.setContractId(contractId);
        //获取订单关联数据
        Long orderId = AppUtil.getSingleColumnIdValue(CrmConst.ORDER, docData);
        crmOnPayment.setOrderId(orderId);
        //设置合同金额
        crmOnPayment.setRealMoney(crmOnPayment.getTotalMoney().subtract(crmOnPayment.getDiscountMoney()));
        if(ObjectUtil.isEmpty(dataAddSimpDTO.getTempProcessId())){
            crmOnPayment.setProcessPass(ProcessPassEnum.PASS.getValue());
            //发布事件
            SpringContextHolderUtil.publishEvent(new CrmOnPaymentCreatedEvent(crmOnPayment,dataAddSimpDTO.getDocData()));
        }
        this.updateById(crmOnPayment);
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
                .set(CrmOnPayment::getScopeUserId,scopeUserId)
                .set(CrmOnPayment::getScopeOrgId,scopeOrgId)
                .in(CrmOnPayment::getId,dataIds)
                .update();
    }

    /**
     * 功能描述:
     * 〈修改回款核销状态〉
     * @param paymentId paymentId
     * @param verifyStatus verifyStatus
     * @author 蝉鸣
     */
    @Override
    public void updatePaymentStatus(Long paymentId, Integer verifyStatus) {
        CrmOnPayment crmOnPayment = this.getById(paymentId);
        if(ObjectUtil.isEmpty(crmOnPayment)){
            return;
        }
        crmOnPayment.setVerifyStatus(verifyStatus);
        this.updateById(crmOnPayment);
        //修改ES数据
        AppModuleBaseBO moduleBaseBO = getAppServiceManual().getModuleInfo(crmOnPayment.getModuleId());
        HashMap<String, Object> map = new HashMap<>();
        map.put(ObjFieldUtil.getColumnName(CrmOnPayment::getVerifyStatus), verifyStatus);
        getAppServiceManual().editEsData(moduleBaseBO.getModuleIndex(),crmOnPayment,map);
        //获取客户已收金额
        PaymentSumBO sumMoney = this.getBaseMapper().getSumReceivedMoneyById(paymentId,ProcessPassEnum.PASS.getValue());
        //如何核销状态为通过则更改合同/订单已收金额
        crmOnPaymentServiceManual.updateReceivedMoney(crmOnPayment,sumMoney);
    }

}
