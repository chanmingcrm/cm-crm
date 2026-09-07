package com.platform.mesh.app.biz.modules.app.search.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;



 /**
 * @description 查询异常枚举
 * @author 蝉鸣
 */
@Schema(description = "查询异常枚举",enumAsRef = true)
public enum AppSearchExceptionEnum implements BaseExceptionEnum<AppSearchExceptionEnum, String>  {

     /**
     * 异常信息
     */
    ADD_NO_ARGS("app-search",500, null,  "查询参数为空"),
    ADD_NO_INVALID("app-search",501, null,  "查询参数异常"),
    SYS_NO_EDIT("app-search",502, null,  "系统场景无法修改"),
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


    AppSearchExceptionEnum(String module, Integer code, Object[] args, String desc) {
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