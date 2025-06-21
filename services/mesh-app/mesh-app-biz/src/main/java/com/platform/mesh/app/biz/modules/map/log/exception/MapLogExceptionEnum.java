package com.platform.mesh.app.biz.modules.map.log.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;

 /**
 * @description 地图打卡异常枚举
 * @author 蝉鸣
 */
@Schema(description = "地图打卡异常枚举",enumAsRef = true)
public enum MapLogExceptionEnum implements BaseExceptionEnum<MapLogExceptionEnum, String>  {

     /**
     * 异常信息
     */
     ADD_NO_ARGS("map-log",500, null,  "地图打卡参数为空"),
     ADD_NO_INVALID("map-log",501, null,  "地图打卡参数异常"),
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


    MapLogExceptionEnum(String module, Integer code, Object[] args, String desc) {
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