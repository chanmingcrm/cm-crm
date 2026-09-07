package com.platform.mesh.upms.biz.modules.sys.account.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 账户异常枚举
 * @author 蝉鸣
 */
@Schema(description = "账户异常枚举",enumAsRef = true)
public enum AccountExceptionEnum implements BaseExceptionEnum<AccountExceptionEnum, String>  {

    /**
     * 异常信息
     */
    ADD_NO_ARGS("upms-account",500, null,  "层级参数为空"),
    ADD_NO_INVALID("upms-account",501, null,  "层级参数异常"),
    RESULT_NO_DATA("upms-account",502, null,  "账户结果异常"),
    ACCOUNT_CHECK_CODE("upms-account",503, null,  "账户密码不符合安全规则"),
    ACCOUNT_CHANGE_SCOPE("upms-account",504, null,  "当前组织与租户不符"),
    ACCOUNT_NUM_LIMIT("upms-account",505, null,  "当前账号可创建数量已达上限"),
    ACCOUNT_CHECK_CODE_INVALID("upms-account",506, null,  "原账户密码验证失败"),
    ACCOUNT_SMS_CODE_INVALID("upms-account",506, null,  "验证码已失效"),
    ACCOUNT_SMS_CODE_ERROR("upms-account",506, null,  "验证码错误"),
    ACCOUNT_CODE_EXISTS("upms-account",506, null,  "账号已存在"),
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


    AccountExceptionEnum(String module, Integer code, Object[] args, String desc) {
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
