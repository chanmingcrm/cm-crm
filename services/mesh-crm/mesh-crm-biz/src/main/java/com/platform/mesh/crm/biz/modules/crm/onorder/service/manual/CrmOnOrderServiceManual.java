package com.platform.mesh.crm.biz.modules.crm.onorder.service.manual;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.modules.crm.oncontract.service.ICrmOnContractService;
import com.platform.mesh.crm.biz.modules.crm.onorderdata.domain.po.CrmOnOrderData;
import com.platform.mesh.crm.biz.modules.crm.onorderdata.service.ICrmOnOrderDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系订单
 * @author 蝉鸣
 */
@Service
public class CrmOnOrderServiceManual {

    private final static Logger log = LoggerFactory.getLogger(CrmOnOrderServiceManual.class);

    @Autowired
    private ICrmOnOrderDataService crmOnOrderDataService;

    @Autowired
    private ICrmOnContractService crmOnContractService;



    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param onOrderDataList onOrderDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmOnOrderData> onOrderDataList) {
        if(CollUtil.isEmpty(onOrderDataList)){
            return;
        }
        CrmOnOrderData data = CollUtil.getFirst(onOrderDataList);
        //删除旧数据
        crmOnOrderDataService.lambdaUpdate().eq(CrmOnOrderData::getDataId,data.getDataId()).remove();
        //批量新增信息
        crmOnOrderDataService.saveBatch(onOrderDataList);
    }

    /**
     * 功能描述:
     * 〈订单金额同步合同金额〉
     * @param contractId contractId
     * @param orderMoney orderMoney
     * @author 蝉鸣
     */
    public void updateReceivedMoney(Long contractId, BigDecimal orderMoney) {
        crmOnContractService.updateReceivedMoney(contractId, orderMoney);
    }

    /**
     * 功能描述:
     * 〈订单发票金额同步合同发票金额〉
     * @param contractId contractId
     * @param invoiceMoney invoiceMoney
     * @author 蝉鸣
     */
    public void updateInvoiceMoney(Long contractId, BigDecimal invoiceMoney) {
        crmOnContractService.updateInvoiceMoney(contractId, invoiceMoney);
    }
}