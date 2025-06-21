package com.platform.mesh.upms.biz.modules.sys.role.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 角色异常枚举
 * @author 蝉鸣
 */
@Schema(description = "角色异常枚举",enumAsRef = true)
public enum RoleExceptionEnum implements BaseExceptionEnum<RoleExceptionEnum, String>  {

    /**
     * 异常信息
     */
    ADD_NO_ARGS("upms-role",500, null,  "角色参数为空"),
    ADD_NO_INVALID("upms-role",501, null,  "角色参数异常"),
    ADD_DELETE_INIT_ERROR("upms-role",502, null,  "初始化角色不能删除"),
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


    RoleExceptionEnum(String module, Integer code, Object[] args, String desc) {
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
