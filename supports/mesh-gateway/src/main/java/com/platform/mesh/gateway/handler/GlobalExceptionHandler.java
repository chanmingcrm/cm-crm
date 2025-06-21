package com.platform.mesh.gateway.handler;

import cn.hutool.json.JSONUtil;
import com.platform.mesh.utils.result.Result;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @description 异常处理
 * @author 蝉鸣
 */
@Order(-1)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * 功能描述:
	 * 〈异常处理〉
	 * @param exchange exchange
	 * @param ex ex
	 * @return 正常返回:{@link Mono<Void>}
	 * @author 蝉鸣
	 */
	@NotNull
	@Override
	public Mono<Void> handle(ServerWebExchange exchange, @NotNull Throwable ex) {
		ServerHttpResponse response = exchange.getResponse();

		if (response.isCommitted()) {
			return Mono.error(ex);
		}

		// header set
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		if (ex instanceof ResponseStatusException) {
			response.setStatusCode(((ResponseStatusException) ex).getStatusCode());
		}

		return response.writeWith(Mono.fromSupplier(() -> {
			DataBufferFactory bufferFactory = response.bufferFactory();
			log.warn("Error Spring Cloud Gateway : {}", ex.getMessage());
			return bufferFactory.wrap(Objects.requireNonNull(JSONUtil.toJsonStr(Result.error(ex.getMessage())).getBytes()));
		}));
	}

}
