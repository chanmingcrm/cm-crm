package com.platform.mesh.crm.biz.soa.event.crm.onpayment;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @description 合同删除事件
 * @author 蝉鸣
 */
@Getter
public class CrmOnPaymentDeletedEvent extends ApplicationEvent {

    private final List<Long> paymentIds;

    public CrmOnPaymentDeletedEvent(List<Long> paymentIds) {
        super(paymentIds);
        this.paymentIds = paymentIds;
    }

}
