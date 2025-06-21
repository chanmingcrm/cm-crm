package com.platform.mesh.core.exception;

import lombok.Getter;

import java.io.Serial;
import java.util.List;

/**
 * @description 基础异常
 * @author 蝉鸣
 */
@Getter
public class BaseException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 所属模块
	 */
	private String module;

	/**
	 * 错误码
	 */
	private Integer code;

	/**
	 * 错误码对应的参数
	 */
	private List<Object> args;

	/**
	 * 错误消息
	 */
	private String desc;

	public BaseException(String module, Integer code, List<Object> args, String desc) {
		this.module = module;
		this.code = code;
		this.args = args;
		this.desc = desc;
	}


	public BaseException(Throwable e) {
		super(e.getMessage(), e);
	}

	public BaseException(String message,Throwable cause) {
		super(message,cause);
	}

	public BaseException(String message,Throwable cause,boolean enableSuppression,boolean writableStackTrace) {
		super(message,cause,enableSuppression,writableStackTrace);
	}

	public BaseException(String desc) {
		this(null, null, null, desc);
	}

	public BaseException(Integer code, List<Object> args) {
		this(null, code, args, null);
	}

	public BaseException(String module, Integer code, List<Object> args) {
		this(module, code, args, null);
	}

	public BaseException(String module, String desc) {
		this(module, null, null, desc);
	}

	public BaseException(String module,Integer code, String desc) {
		this(module, code, null, desc);
	}

}
