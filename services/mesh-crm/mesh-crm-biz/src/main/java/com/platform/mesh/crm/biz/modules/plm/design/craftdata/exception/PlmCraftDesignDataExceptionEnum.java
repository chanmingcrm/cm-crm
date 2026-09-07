package com.platform.mesh.crm.biz.modules.plm.design.craftdata.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 工艺设计异常枚举
 * @author 蝉鸣
 */
@Schema(description = "工艺设计异常枚举", enumAsRef = true)
public enum PlmCraftDesignDataExceptionEnum implements BaseExceptionEnum<PlmCraftDesignDataExceptionEnum, String> {

    /**
     * 异常信息
     */
    ADD_NO_ARGS("plm_design_craft_data", 500, null, "工艺设计参数为空"),
    ADD_NO_INVALID("plm_design_craft_data", 501, null, "工艺设计参数异常"),
    ;

    /**
     * 所属模块
     */
    private final String module;

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误码对应的参数
     */
    private final Object[] args;

    /**
     * 错误消息
     */
    private final String desc;

    PlmCraftDesignDataExceptionEnum(String module, Integer code, Object[] args, String desc) {
        this.module = module;
        this.code = code;
        this.args = args;
        this.desc = desc;
    }

    @Override
    public String getModule() {
        return module;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
