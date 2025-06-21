package com.platform.mesh.upms.api.modules.sys.account.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @description 账户类型枚举
 * @author 蝉鸣
 */
@Schema(description = "账户类型枚举",enumAsRef = true)
public enum SourceFlagEnum implements BaseEnum<SourceFlagEnum, Integer> {

    /**
     * 系统
     */
    SYSTEM(1,  "system","系统"),
    /**
     * 短信
     */
    SMS(2,  "sms","短信"),
    /**
     * 钉钉
     */
    DING(3,  "ding","钉钉"),
    /**
     * 企业微信
     */
    WX_WORK(4,  "wxwork","企业微信"),
    /**
     * 飞书
     */
    FEI_SHU(5,  "feishu","飞书"),
    ;


    private final Integer value;

    private final String desc;

    @Getter
    private final String name;

    SourceFlagEnum(Integer value, String desc, String name) {
        this.value = value;
        this.desc = desc;
        this.name = name;
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
