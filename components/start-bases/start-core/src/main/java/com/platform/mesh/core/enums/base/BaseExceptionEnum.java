package com.platform.mesh.core.enums.base;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.platform.mesh.core.enums.convert.EnumJsonDeserializer;
import com.platform.mesh.core.enums.convert.EnumJsonSerializer;
import com.platform.mesh.core.exception.BaseException;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @description 枚举基类
 * @param <E> 子枚举
 * @param <V> 枚举value的类型
 * @author 蝉鸣
 */
@JsonSerialize(using = EnumJsonSerializer.class)
@JsonDeserialize(using = EnumJsonDeserializer.class)
public interface BaseExceptionEnum<E extends Enum<E>, V extends Serializable>{

    /**
     * 功能描述:
     * 〈所属模块〉
     */
    String getModule();

    /**
     * 错误码
     */
    Integer getCode();

    /**
     * 错误码对应的参数
     */
    Object[] getArgs();

    /**
     * 错误消息
     */
    String getDesc();


    default BaseException getBaseException(){
        return new BaseException(this.getModule(), this.getCode(), ObjectUtils.isEmpty(this.getArgs())?Collections.emptyList():Arrays.stream(this.getArgs()).toList(), this.getDesc());
    }

    default BaseException getBaseException(List<Object> args){
        return new BaseException(this.getModule(), this.getCode(), args, this.getDesc());
    }

    default BaseException getBaseException(Throwable e){
        return new BaseException(e);
    }

    default BaseException getBaseException(String desc){
        return new BaseException(desc);
    }

    default BaseException getBaseException(String module, String desc){
        return new BaseException(module,desc);
    }

    default BaseException getBaseException(Integer code, List<Object> args){
        return new BaseException(code,args);
    }

    default BaseException getBaseException(String module,Integer code, String desc){
        return new BaseException(module,code,desc);
    }

    default BaseException getBaseException(String module, Integer code, List<Object> args){
        return new BaseException(module,code,args);
    }

    default BaseException getBaseException(String message,Throwable e){
        return new BaseException(message,e);
    }

    default BaseException getBaseException(String message,Throwable e,boolean enableSuppression,boolean writableStackTrace){
        return new BaseException(message,e,enableSuppression,writableStackTrace);
    }















}