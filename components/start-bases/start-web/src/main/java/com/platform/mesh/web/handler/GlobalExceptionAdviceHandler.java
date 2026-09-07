package com.platform.mesh.web.handler;

import com.platform.mesh.utils.result.Result;
import com.platform.mesh.core.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @description 全局异常处理器
 * @author 蝉鸣
 */
@RestControllerAdvice
@Order(Integer.MIN_VALUE)
public class GlobalExceptionAdviceHandler {

	private final Logger log = LoggerFactory.getLogger(GlobalExceptionAdviceHandler.class);

	/**
	 * 功能描述:
	 * 〈全局异常〉
	 * @param e e
	 * @return 正常返回:{@link Result}
	 * @author 蝉鸣
	 */
	@ExceptionHandler(Exception.class)
	public Result<Object> handleException(Exception e) {
		log.error(e.getMessage(), e);
		Throwable cause = e.getCause();
		if (cause instanceof BaseException baseException) {
            return Result.restResult(baseException.getModule(),baseException.getCode(),baseException.getDesc(),baseException.getArgs());
		}
		return Result.error("其他异常,ex = {}",e.getCause());
	}

	/**
	 * 功能描述:
	 * 〈基础异常〉
	 * @param e e
	 * @return 正常返回:{@link Result}
	 * @author 蝉鸣
	 */
	@ExceptionHandler(BaseException.class)
//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Result<Object> fastGoException(BaseException e) {
		log.error(e.getLocalizedMessage(), e);
		return Result.restResult(e.getModule(),e.getCode(),e.getDesc(),e.getArgs());
	}

	/**
	 * 功能描述:
	 * 〈处理业务校验过程中碰到的非法参数异常 该异常基本由{@link org.springframework.util.Assert}抛出〉
	 * @param exception 参数校验异常
	 * @return 正常返回:{@link Result} API返回结果对象包装后的错误输出结果
	 * @author 蝉鸣
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.OK)
	public Result<Object> handleIllegalArgumentException(IllegalArgumentException exception) {
		log.error("非法参数,ex = {}", exception.getMessage(), exception);
		return Result.error(exception.getMessage());
	}

	/**
	 * 功能描述:
	 * 〈参数绑定异常〉
	 * @param exception 参数绑定异常
	 * @return 正常返回:{@link Result}
	 * @author 蝉鸣
	 */
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Result<Object> handleBodyValidException(MethodArgumentNotValidException exception) {
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		log.warn("参数绑定异常,ex = {}", fieldErrors.getFirst().getDefaultMessage());
		return Result.error(fieldErrors.getFirst().getDefaultMessage());
	}

	/**
	 * 功能描述:
	 * 〈(以form-data形式传参) 参数绑定异常〉
	 * @param exception exception
	 * @return 正常返回:{@link Result}
	 * @author 蝉鸣
	 */
	@ExceptionHandler({ BindException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Result<Object> bindExceptionHandler(BindException exception) {
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		log.error("参数绑定异常,ex = {}", fieldErrors.getFirst().getDefaultMessage());
		return Result.error(fieldErrors.getFirst().getDefaultMessage());
	}

	/**
	 * 功能描述:
	 * 〈请求方式不支持〉
	 * @param e HttpRequestMethodNotSupportedException
	 * @return 正常返回:{@link Result<Void>}
	 * @author 蝉鸣
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Result<Void> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
		log.error(e.getLocalizedMessage(), e);
		return Result.error(e.getMessage());
	}

}
