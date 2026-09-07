package com.platform.mesh.crm.biz.modules.crm.onpayment.service.manual;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.modules.crm.oncontract.service.ICrmOnContractService;
import com.platform.mesh.crm.biz.modules.crm.onpayment.domain.bo.PaymentSumBO;
import com.platform.mesh.crm.biz.modules.crm.onpayment.domain.po.CrmOnPayment;
import com.platform.mesh.crm.biz.modules.crm.onpaymentdata.domain.po.CrmOnPaymentData;
import com.platform.mesh.crm.biz.modules.crm.onpaymentdata.service.ICrmOnPaymentDataService;
import com.platform.mesh.crm.biz.modules.crm.precustomer.service.ICrmPreCustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系款项记录
 * @author 蝉鸣
 */
@Service
public class CrmOnPaymentServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmOnPaymentServiceManual.class);

    
    @Autowired
    private ICrmOnPaymentDataService crmOnPaymentDataService;

    @Autowired
    private ICrmOnContractService crmOnContractService;

    @Autowired
    private ICrmPreCustomerService crmPreCustomerService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param onPaymentDataList onPaymentDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmOnPaymentData> onPaymentDataList) {
        if(CollUtil.isEmpty(onPaymentDataList)){
            return;
        }
        CrmOnPaymentData data = CollUtil.getFirst(onPaymentDataList);
        //删除旧数据
        crmOnPaymentDataService.lambdaUpdate().eq(CrmOnPaymentData::getDataId,data.getDataId()).remove();
        //批量新增信息
        crmOnPaymentDataService.saveBatch(onPaymentDataList);
    }

    /**
     * 功能描述:
     * 〈修改已收金额〉
     * @param crmOnPayment crmOnPayment
     * @author 蝉鸣
     */
    public void updateReceivedMoney(CrmOnPayment crmOnPayment, PaymentSumBO sumMoney) {
        //更新合同金额
        crmOnContractService.updateReceivedMoney(crmOnPayment.getContractId(),sumMoney.getContractMoney());
        //更新客户金额
        crmPreCustomerService.updateReceivedMoney(crmOnPayment.getCustomerId(),sumMoney.getCustomerMoney());
    }
}
