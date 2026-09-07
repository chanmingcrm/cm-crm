package com.platform.mesh.crm.biz.modules.crm.onpayment.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @description CRM回款核销状态枚举
 * @author 蝉鸣
 */
@Schema(description = "CRM回款核销状态枚举", enumAsRef = true)
public enum PaymentVerifyStatusEnum implements BaseEnum<PaymentVerifyStatusEnum, Integer> {

    /**
     * 待核对
     */
    INIT(0, 0, "待核对"),
    /**
     * 有效
     */
    VALID(1, 100, "有效"),
    /**
     * 无效
     */
    IN_VALID(2, 200, "无效"),
    ;

    @Getter
    private final Integer code;

    private final Integer value;

    private final String desc;

    PaymentVerifyStatusEnum(Integer code, Integer value, String desc) {
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
}
