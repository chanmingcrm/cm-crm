package com.platform.mesh.upms.biz.modules.org.memberuserrel.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 岗位人员关系异常枚举
 * @author 蝉鸣
 */
@Schema(description = "岗位异常枚举",enumAsRef = true)
public enum MemberUserRelExceptionEnum implements BaseExceptionEnum<MemberUserRelExceptionEnum, String>  {

    /**
     * 异常信息
     */
    ADD_NO_ARGS("org-member-user-rel",500, null,  "层级参数为空"),
    ADD_NO_INVALID("org-member-user-rel",501, null,  "层级参数异常"),
    ADD_EXIST_LEAD("org-member-user-rel",502, null,  "已经存在决策人员，请先删除后在添加"),
    ADD_NUM_LEAD("org-member-user-rel",503, null,  "决策人员数量不能大于1人"),
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


    MemberUserRelExceptionEnum(String module, Integer code, Object[] args, String desc) {
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
