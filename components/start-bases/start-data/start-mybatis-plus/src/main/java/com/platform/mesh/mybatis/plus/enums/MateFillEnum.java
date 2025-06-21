package com.platform.mesh.mybatis.plus.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import lombok.Getter;

/**
 * @description 数据填充处理
 * @author 蝉鸣
 */
public enum MateFillEnum implements BaseEnum<MateFillEnum, Integer> {

    /**
     * 创建人ID
     */
    CREATE_USER_ID(1, "create_user_id", "createUserId"),

    /**
     * 创建人名称
     */
    CREATE_USER_NAME(2, "create_user_name", "createUserName"),

    /**
     * 创建时间
     */
    CREATE_TIME(3, "create_time", "createTime"),

    /**
     * 更新人ID
     */
    UPDATE_USER_ID(4, "update_user_id", "updateUserId"),

    /**
     * 更新人名称
     */
    UPDATE_USER_NAME(5, "update_user_name", "updateUserName"),

    /**
     * 更新时间
     */
    UPDATE_TIME(6, "update_time", "updateTime"),

    /**
     * 逻辑删除标识
     */
    DEL_FLAG(7, "del_flag", "delFlag"),

    /**
     * 数据域人员ID
     */
    SCOPE_USER_ID(8, "scope_user_id", "scopeUserId"),

    /**
     * 数据域组织ID
     */
    SCOPE_ORG_ID(9, "scope_org_id", "scopeOrgId"),
    ;

    private final Integer value;

    private final String desc;

    @Getter
    private final String code;

    MateFillEnum(Integer value, String desc, String code) {
        this.value = value;
        this.desc = desc;
        this.code = code;
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
