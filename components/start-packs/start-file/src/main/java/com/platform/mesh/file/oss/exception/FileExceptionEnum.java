package com.platform.mesh.file.oss.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;

/**
 * @description 动作异常枚举
 * @author 蝉鸣
 */
public enum FileExceptionEnum implements BaseExceptionEnum<FileExceptionEnum, String>  {

    /**
     * 异常信息
     */
    FILE_NO_ARGS("packs-file",500, null,  "参数为空"),
    FILE_NO_INVALID("packs-file",501, null,  "参数异常"),
    FILE_INIT_INVALID("packs-file",502, null,  "初始化断点续传对象未实现，默认不支持此方法"),
    FILE_UPLOAD_INVALID("packs-file",503, null,  "上传文件分片方法未实现，默认不支持此方法"),
    FILE_MULTI_INVALID("packs-file",504, null,  "合并文件分片方法未实现，默认不支持此方法"),
    FILE_DOWN_INVALID("packs-file",505, null,  "下载文件分片方法未实现，默认不支持此方法"),
    FILE_MKDIR_ERROR("packs-file",506, null,  "目录创建失败"),
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


    FileExceptionEnum(String module, Integer code, Object[] args, String desc) {
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
