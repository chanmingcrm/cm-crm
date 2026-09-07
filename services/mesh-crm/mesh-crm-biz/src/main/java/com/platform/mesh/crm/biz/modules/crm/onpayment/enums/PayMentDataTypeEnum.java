package com.platform.mesh.crm.biz.modules.crm.onpayment.enums;

import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "款项类型枚举",enumAsRef = true)
public enum PaymentDataTypeEnum implements BaseEnum<PaymentDataTypeEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0,0,  "元状态"),
    /**
     * 收账
     */
    ADVANCE_RECEIVED(1,100,  "预收款项"),
    ACCOUNTS_RECEIVABLE(1,101,  "应收款项"),
    RECEIVED_PAYMENT(1,102,  "已收款项"),
    TEMPORARY_RECEIPTS(1,103,  "暂收款项"),
    /**
     * 付账
     */
    PREPAID_EXPENSES(2,200,  "预付账款"),
    ACCOUNTS_PAYABLE(2,201,  "应付账款"),
    PAID_PAYMENT(2,202,  "已付款项"),
    TEMPORARY_PAYMENTS(2,203,  "暂付款项"),
    ;


    @Getter
    private final Integer code;

    private final Integer value;

    private final String desc;

    PaymentDataTypeEnum(Integer code, Integer value, String desc) {
        this.code = code;
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    public BigDecimal getResultMoney(BigDecimal sourceMoney,BigDecimal targetMoney) {
        if(ObjectUtil.isEmpty(sourceMoney)){
            sourceMoney = BigDecimal.ZERO;
        }
        if(ObjectUtil.isEmpty(targetMoney)){
            targetMoney = BigDecimal.ZERO;
        }
        if(NumberConst.NUM_1.equals(code)){
            return sourceMoney.add(targetMoney);
        }else{
            return sourceMoney.subtract(targetMoney);
        }
    }


}
