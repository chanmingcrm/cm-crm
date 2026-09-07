package com.platform.mesh.crm.biz.soa.event.crm.oncontract;

import com.platform.mesh.crm.biz.modules.crm.oncontract.domain.po.CrmOnContract;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * @description 合同创建完成事件
 * @author 蝉鸣
 */
@Getter
public class CrmOnContractCreatedEvent extends ApplicationEvent {

    private final CrmOnContract crmOnContract;

    private final Map<String, Object> docData;

    public CrmOnContractCreatedEvent(CrmOnContract crmOnContract, Map<String, Object> docData) {
        super(crmOnContract);
        this.crmOnContract = crmOnContract;
        this.docData = docData;
    }

}
