package com.platform.mesh.upms.biz.modules.doc.file.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;



 /**
 * @description 文件异常枚举
 * @author 蝉鸣
 */
@Schema(description = "文件异常枚举",enumAsRef = true)
public enum DocFileExceptionEnum implements BaseExceptionEnum<DocFileExceptionEnum, String>  {

     /**
     * 异常信息
     */
    ADD_NO_ARGS("doc-file",500, null,  "文件参数为空"),
    ADD_NO_INVALID("doc-file",501, null,  "文件参数异常"),
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


    DocFileExceptionEnum(String module, Integer code, Object[] args, String desc) {
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