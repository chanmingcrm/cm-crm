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
        super(buildMessage(module, code, desc, args));
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

    /**
     * 构建错误消息
     */
    private static String buildMessage(String module, Integer code, String desc, List<Object> args) {
        StringBuilder message = new StringBuilder();

        // 模块信息
        if (module != null && !module.trim().isEmpty()) {
            message.append("[").append(module).append("] ");
        }

        // 错误码
        if (code != null) {
            message.append("CODE:").append(code);
        }

        // 参数信息
        if (args != null && !args.isEmpty()) {
            if (code != null) {
                message.append(" ");
            }
            message.append("ARGS:").append(args);
        }

        // 错误描述
        if (desc != null && !desc.trim().isEmpty()) {
            if (!message.isEmpty()) {
                message.append(" - ");
            }
			if(desc.contains("{}")){
				desc = desc.replace("{}","%s");
				desc = String.format(desc, args);
			}
			message.append(desc);
		}

        // 如果所有字段都为空，返回默认消息
        if (message.isEmpty()) {
            return "Unknown error";
        }

        return message.toString();
    }

}
