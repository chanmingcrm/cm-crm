package com.platform.mesh.upms.biz.modules.msg.leave.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;



 /**
 * @description 留言消息异常枚举
 * @author 蝉鸣
 */
@Schema(description = "留言消息异常枚举",enumAsRef = true)
public enum MsgLeaveExceptionEnum implements BaseExceptionEnum<MsgLeaveExceptionEnum, String>  {

     /**
     * 异常信息
     */
    ADD_NO_ARGS("msg-leave",500, null,  "留言消息参数为空"),
    ADD_NO_INVALID("msg-leave",501, null,  "留言消息参数异常"),
    ADD_ADD_LIMIT("msg-leave",502, null,  "留言消息已经受理,请勿重复操作"),
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
     * 错误留言消息
     */
    private final String desc;


    MsgLeaveExceptionEnum(String module, Integer code, Object[] args, String desc) {
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