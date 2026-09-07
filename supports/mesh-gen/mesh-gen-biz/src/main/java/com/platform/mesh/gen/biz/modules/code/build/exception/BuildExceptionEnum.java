package com.platform.mesh.gen.biz.modules.code.build.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 代码构建异常枚举
 * @author 蝉鸣
 */
@Schema(description = "代码构建异常枚举",enumAsRef = true)
public enum BuildExceptionEnum implements BaseExceptionEnum<BuildExceptionEnum, String>  {

    /**
     * 异常信息
     */
    ADD_NO_ARGS("code-build",500, null,  "代码构建参数为空"),
    ADD_NO_INVALID("code-build",501, null,  "代码构建参数异常"),
    BUILD_CODE_ERROR("code-build",502, null,  "渲染模板失败,{}"),
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


    BuildExceptionEnum(String module, Integer code, Object[] args, String desc) {
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
