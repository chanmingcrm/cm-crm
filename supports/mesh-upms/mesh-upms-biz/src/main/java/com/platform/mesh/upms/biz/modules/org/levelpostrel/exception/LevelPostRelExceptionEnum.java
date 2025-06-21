package com.platform.mesh.upms.biz.modules.org.levelpostrel.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "层级岗位异常枚举",enumAsRef = true)
public enum LevelPostRelExceptionEnum implements BaseExceptionEnum<LevelPostRelExceptionEnum, String>  {

    /**
     * 异常信息
     */
    ADD_NO_ARGS("upms-level-post-rel",500, null,  "层级岗位参数为空"),
    ADD_NO_INVALID("upms-level-post-rel",501, null,  "层级岗位参数异常"),
    ADD_EXISTS_REL_DATA("upms-level-post-rel",502, null,  "层级岗位已经存在绑定关系"),
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


    LevelPostRelExceptionEnum(String module, Integer code, Object[] args, String desc) {
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
