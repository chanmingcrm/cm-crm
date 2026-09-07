package com.platform.mesh.upms.api.pub.upms.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description  应用广播事件处理类型枚举
 * @author 蝉鸣
 */
@Schema(description = "应用广播事件处理类型枚举",enumAsRef = true)
public enum UpmsActionEnum implements BaseEnum<UpmsActionEnum, Integer> {

    /**
     * 自定义
     */
    CUSTOM(0,  "自定义"),

    /**
     * 初始化租户
     */
    INIT_TENANT(1,  "初始化租户"),

    /**
     * 初始化租户应用
     */
    INIT_TENANT_APP(2,  "初始化租户应用"),

    /**
     * 初始化租户流程
     */
    INIT_TENANT_BPM(3,  "初始化租户流程"),

    /**
     * 同步字典名称
     */
    SYNC_DICT_NAME(4,  "同步字典名称"),

    /**
     * 同步人员名称
     */
    SYNC_USER_NAME(5,  "同步名称"),

    /**
     * 同步组织名称
     */
    SYNC_ORG_NAME(6,  "同步组织名称"),

    /**
     * 转移数据
     */
    TRANS_ORG_DATA(7,  "转移数据"),

    /**
     * 删除流程关联数据
     */
    DEL_BPM_DATA_REL(8,  "删除流程关联数据"),

    /**
     * 删除第三方关联数据
     */
    DEL_CRM_SYNC_THIRD_REL(9,  "删除第三方关联数据"),

    ;


    private final Integer value;

    private final String desc;

    UpmsActionEnum(Integer value, String desc) {
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
