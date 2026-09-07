package com.platform.mesh.security.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;

/**
 * @description 动作异常枚举
 * @author 蝉鸣
 */
public enum SecurityExceptionEnum implements BaseExceptionEnum<SecurityExceptionEnum, String>  {

    /**
     * 异常信息
     */
    SECURITY_NO_ARGS("base-security",500, null,  "参数为空"),
    SECURITY_NO_USER_INFO("base-security",501, null,  "获取用户信息主体失败"),
    SECURITY_SMS_TYPE_INVALID("base-security",502, null,  "当前非短信验证方式"),
    SECURITY_SMS_CODE_INVALID("base-security",502, null,  "短信验证码无效"),
    SECURITY_SMS_CODE_EXPIRE("tenant-base",504, null,  "短信验证码已过期"),
    AUTH_CHANNEL_MULTIPLE("base-security", 510, null, "存在多个 Authorization 请求头"),
    AUTH_CREDENTIAL_EMPTY("base-security", 513, null, "认证凭证不能为空"),
    AUTH_SCHEME_UNSUPPORTED("base-security", 514, null, "不支持的认证方式"),
    ACCESS_KEY_TYPE_UNSUPPORTED("base-security", 520, null, "不支持的 Access Key 类型"),
    ACCESS_KEY_VERIFIER_CONFLICT("base-security", 521, null, "Access Key 校验器配置冲突"),
    ACCESS_KEY_IDENTITY_INVALID("base-security", 522, null, "Access Key 身份无效"),
    ACCESS_KEY_ENDPOINT_FORBIDDEN("base-security", 523, null, "Access Key 无权访问当前接口"),
    ACCESS_KEY_FORMAT_INVALID("base-security", 524, null, "Access Key 格式无效"),
    ACCESS_KEY_CREDENTIAL_TYPE_EMPTY("base-security", 525, null, "Access Key 类型不能为空"),
    ACCESS_KEY_SECRET_EMPTY("base-security", 526, null, "Access Key 密钥不能为空"),
    ACCESS_KEY_CREDENTIAL_ID_EMPTY("base-security", 527, null, "Access Key 标识不能为空"),
    ACCESS_KEY_BINDING_INVALID("base-security", 528, null, "Access Key 身份绑定不完整"),
    INNER_ENDPOINT_INVALID("base-security", 530, null, "内部接口请求方法或路径不合法"),
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


    SecurityExceptionEnum(String module, Integer code, Object[] args, String desc) {
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
