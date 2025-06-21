package com.platform.mesh.app.biz.modules.data.common.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;


 /**
 * @description 通过数据异常枚举
 * @author 蝉鸣
 */
@Schema(description = "通过数据异常枚举",enumAsRef = true)
public enum DataCommonExceptionEnum implements BaseExceptionEnum<DataCommonExceptionEnum, String>  {

     /**
     * 异常信息
     */
    ADD_NO_ARGS("app-data-common",500, null,  "应用通用存储参数为空"),
    ADD_NO_INVALID("app-data-common",501, null,  "应用通用存储参数异常"),
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


    DataCommonExceptionEnum(String module, Integer code, Object[] args, String desc) {
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