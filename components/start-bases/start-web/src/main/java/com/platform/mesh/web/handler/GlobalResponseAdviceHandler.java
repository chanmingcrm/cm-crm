package com.platform.mesh.web.handler;

import com.platform.mesh.utils.result.Result;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @description 配置国际化
 * @author 蝉鸣
 */
@RestControllerAdvice
@Order(Integer.MIN_VALUE)
public class GlobalResponseAdviceHandler implements ResponseBodyAdvice<Result<Object>> {

	private static final Logger log = LoggerFactory.getLogger(GlobalResponseAdviceHandler.class);

	@Override
	public boolean supports(@NotNull MethodParameter returnType,
							@NotNull Class converterType) {
//		return returnType.getParameterType().getName().equals(Result.class.getName());
//		if (returnType.getMethod() == null) {
//			return false;
//		}
//		return returnType.getMethod().getReturnType()==Result.class;
		//假条件先屏蔽增强
		return converterType.getName().equals(Result.class.getName());
	}

	@Override
	public Result<Object> beforeBodyWrite(Result<Object> body,
							 @NotNull MethodParameter returnType,
							 @NotNull MediaType selectedContentType,
							 @NotNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
							 @NotNull ServerHttpRequest request,
							 @NotNull ServerHttpResponse response) {
		log.info("增强结果返回");
        assert body != null;
		return body;
	}
}