package com.platform.mesh.crm.biz.soa.event.crm.oncontract;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @description 合同删除事件
 * @author 蝉鸣
 */
@Getter
public class CrmOnContractDeletedEvent extends ApplicationEvent {

    private final List<Long> contractIds;

    public CrmOnContractDeletedEvent(List<Long> contractIds) {
        super(contractIds);
        this.contractIds = contractIds;
    }

}
