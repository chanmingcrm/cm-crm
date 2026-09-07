package com.platform.mesh.gateway.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.platform.mesh.core.constants.HttpConst;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @description 自定义限流异常处理
 * @author 蝉鸣
 */
public class SentinelFallbackHandler implements WebExceptionHandler {

	/**
	 * 功能描述:
	 * 〈熔断异常处理〉
	 * @param exchange exchange
	 * @param ex ex
	 * @return 正常返回:{@link Mono<Void>}
	 * @author 蝉鸣
	 */
	@NotNull
	@Override
	public Mono<Void> handle(ServerWebExchange exchange, @NotNull Throwable ex) {
		if (exchange.getResponse().isCommitted()) {
			return Mono.error(ex);
		}
		if (!BlockException.isBlockException(ex)) {
			return Mono.error(ex);
		}
		return handleBlockedRequest(exchange).flatMap(response -> writeResponse(response,exchange));
	}

	/**
	 * 功能描述:
	 * 〈熔断异常处理〉
	 * @param exchange exchange
	 * @return 正常返回:{@link Mono<Void>}
	 * @author 蝉鸣
	 */
	private Mono<Void> writeResponse(ServerResponse response,ServerWebExchange exchange) {
		ServerHttpResponse serverHttpResponse = exchange.getResponse();
		serverHttpResponse.getHeaders().add(HttpHeaders.CONTENT_TYPE, HttpConst.APPLICATION_JSON_CHARSET);
		serverHttpResponse.setStatusCode(response.statusCode());
		DataBuffer dataBuffer = getDataBuffer(exchange);
		return serverHttpResponse.writeWith(Mono.just(dataBuffer));
	}

	/**
	 * 功能描述:
	 * 〈熔断异常处理〉
	 * @param exchange exchange
	 * @return 正常返回:{@link Mono<ServerResponse>}
	 * @author 蝉鸣
	 */
	private Mono<ServerResponse> handleBlockedRequest(ServerWebExchange exchange) {
		// 构建自定义错误响应
		DataBuffer dataBuffer = getDataBuffer(exchange);
		// 使用 Spring 6.x 兼容的方式构建 ServerResponse
		return ServerResponse
				// 429 限流状态码
				.status(HttpStatus.TOO_MANY_REQUESTS)
				.contentType(MediaType.TEXT_PLAIN)
				.body(Mono.just(dataBuffer), DataBuffer.class);
		//Sentinel引用版本过低
//		return GatewayCallbackManager.getBlockHandler().handleRequest(exchange, throwable);
	}

	/**
	 * 功能描述:
	 * 〈异常信息〉
	 * @param exchange exchange
	 * @return 正常返回:{@link DataBuffer}
	 * @author 蝉鸣
	 */
	private DataBuffer getDataBuffer(ServerWebExchange exchange){
		String errorMsg = "{\"status\":429,\"message\":\"请求超过最大数，请稍后再试\"}";
		return exchange.getResponse()
				.bufferFactory()
				.wrap(errorMsg.getBytes(StandardCharsets.UTF_8));
	}

}