package com.platform.mesh.upms.biz.modules.conf.userui.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;



 /**
 * @description 配置UI异常枚举
 * @author 蝉鸣
 */
@Schema(description = "配置UI异常枚举",enumAsRef = true)
public enum ConfUserUiExceptionEnum implements BaseExceptionEnum<ConfUserUiExceptionEnum, String>  {

     /**
     * 异常信息
     */
    ADD_NO_ARGS("conf-user-ui",500, null,  "配置UI参数为空"),
    ADD_NO_INVALID("conf-user-ui",501, null,  "配置UI参数异常"),
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


    ConfUserUiExceptionEnum(String module, Integer code, Object[] args, String desc) {
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