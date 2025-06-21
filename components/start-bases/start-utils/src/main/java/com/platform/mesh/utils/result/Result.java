package com.platform.mesh.utils.result;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;

/**
 * @description 统一操作信息实体(支持Swagger响应泛型)
 * @author 蝉鸣
 */
@Data
public class Result<T> implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 模块
	 */
	private String module;

	/**
	 * 状态码
	 */
	private Integer code;

	/**
	 * 返回内容信息
	 */
	private String msg;

	/**
	 * 数据对象
	 */
	private T data;

	/**
	 * 返回成功消息
	 * @return 成功消息
	 */
	public static <T> Result<T> success() {
		return Result.success("操作成功");
	}

	/**
	 * 返回成功数据
	 * @return 成功消息
	 */
	public static <T> Result<T> success(T data) {
		return Result.success("操作成功", data);
	}

	/**
	 * 返回成功消息
	 * @param msg 返回内容
	 * @return 成功消息
	 */
	public static <T> Result<T> success(String msg) {
		return Result.success(msg, null);
	}

	/**
	 * 返回成功消息
	 * @param msg 返回内容
	 * @param data 数据对象
	 * @return 成功消息
	 */
	public static <T> Result<T> success(String msg, T data) {
		return restResult(null,HttpStatus.OK.value(), msg, data);
	}

	/**
	 * 返回错误消息
	 * @return JsonResult
	 */
	public static <T> Result<T> error() {
		return Result.error("操作失败");
	}

	/**
	 * 返回错误消息
	 * @param msg 返回内容
	 * @return 警告消息
	 */
	public static <T> Result<T> error(String msg) {
		return Result.error(msg, null);
	}

	/**
	 * 返回错误消息
	 * @param msg 返回内容
	 * @param data 数据对象
	 * @return 警告消息StringUtil
	 */
	public static <T> Result<T> error(String msg, T data) {
		return restResult(null,HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, data);
	}

	/**
	 * 返回错误消息
	 * @param code 状态码
	 * @param msg 返回内容
	 * @return 警告消息
	 */
	public static <T> Result<T> error(int code, String msg) {
		return restResult(null,code, msg, null);
	}


	/**
	 * 实例化Json
	 * @param data 返回数据
	 * @param code 响应码
	 * @param msg 响应信息
	 * @return 响应Json数据
	 */
	public static <T> Result<T> restResult(String module, int code, String msg, T data) {
		Result<T> apiJson = new Result<>();
		apiJson.setModule(module);
		apiJson.setCode(code);
		apiJson.setMsg(msg);
		apiJson.setData(data);
		return apiJson;
	}

}
