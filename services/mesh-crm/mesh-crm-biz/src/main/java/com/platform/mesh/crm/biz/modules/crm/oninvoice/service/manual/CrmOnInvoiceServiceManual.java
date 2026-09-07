package com.platform.mesh.crm.biz.modules.crm.oninvoice.service.manual;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.modules.crm.oninvoicedata.domain.po.CrmOnInvoiceData;
import com.platform.mesh.crm.biz.modules.crm.oninvoicedata.service.ICrmOnInvoiceDataService;
import com.platform.mesh.crm.biz.modules.crm.onorder.service.ICrmOnOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系发票回执
 * @author 蝉鸣
 */
@Service
public class CrmOnInvoiceServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmOnInvoiceServiceManual.class);

    @Autowired
    private ICrmOnInvoiceDataService crmOnInvoiceDataService;

    @Autowired
    private ICrmOnOrderService crmOnOrderService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param onInvoiceDataList onInvoiceDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmOnInvoiceData> onInvoiceDataList) {
        if(CollUtil.isEmpty(onInvoiceDataList)){
            return;
        }
        CrmOnInvoiceData data = CollUtil.getFirst(onInvoiceDataList);
        //删除旧数据
        crmOnInvoiceDataService.lambdaUpdate().eq(CrmOnInvoiceData::getDataId,data.getDataId()).remove();
        //批量新增信息
        crmOnInvoiceDataService.saveBatch(onInvoiceDataList);
    }

    /**
     * 功能描述:
     * 〈同步订单开票金额〉
     * @param orderId orderId
     * @param invoiceMoney invoiceMoney
     * @author 蝉鸣
     */
    public void updateInvoiceMoney(Long orderId, BigDecimal invoiceMoney) {
        crmOnOrderService.updateInvoiceMoney(orderId,invoiceMoney);
    }
}