package com.platform.mesh.crm.biz.soa.event.crm.oncontract;


import cn.hutool.json.JSONArray;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.api.modules.crm.constants.CrmConst;
import com.platform.mesh.crm.biz.modules.crm.oncontract.domain.po.CrmOnContract;
import com.platform.mesh.crm.biz.modules.crm.oncontract.service.ICrmOnContractService;
import com.platform.mesh.crm.biz.modules.crm.precustomer.enums.ConfirmFlagEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @description 合同事件监听
 * @author 蝉鸣
 */
@Component
public class CrmOnContractEventListener {


    @Autowired
    private ICrmOnContractService crmOnContractService;

    /**
     * 功能描述:
     * 〈合同创建成功后初始化财务应收〉
     * @param event event
     * @author 蝉鸣
     */
    @EventListener(CrmOnContractCreatedEvent.class)
    public void setCustomerTotalMoney(CrmOnContractCreatedEvent event) {
        CrmOnContract dataPO = event.getCrmOnContract();
        Map<String, Object> docData = event.getDocData();
        JSONArray jsonData = AppUtil.getJsonData(dataPO.getId(), dataPO.getDataName());
        docData.put(AppUtil.getJsonName(CrmConst.CONTRACT),jsonData);
        //查询当前客户下合同总计金额
        BigDecimal totalMoney = crmOnContractService.getTotalMoneyByCustomerId(dataPO.getCustomerId());
        //设置客户成交状态
        crmOnContractService.setTotalMoneyByCustomerId(dataPO.getCustomerId(), ConfirmFlagEnum.YES, totalMoney);
    }

}
