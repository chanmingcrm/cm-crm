package com.platform.mesh.gateway.handler;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.buffer.DataBuffer;
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
	 * @param response response
	 * @param exchange exchange
	 * @return 正常返回:{@link Mono<Void>}
	 * @author 蝉鸣
	 */
	private Mono<Void> writeResponse(ServerResponse response, ServerWebExchange exchange) {
		ServerHttpResponse serverHttpResponse = exchange.getResponse();
		serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
		byte[] datas = "{\"status\":429,\"message\":\"请求超过最大数，请稍后再试\"}".getBytes(StandardCharsets.UTF_8);
		DataBuffer buffer = serverHttpResponse.bufferFactory().wrap(datas);
		return serverHttpResponse.writeWith(Mono.just(buffer));
	}

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
		return handleBlockedRequest(exchange, ex).flatMap(response -> writeResponse(response, exchange));
	}

	/**
	 * 功能描述:
	 * 〈熔断异常处理〉
	 * @param exchange exchange
	 * @param throwable throwable
	 * @return 正常返回:{@link Mono<ServerResponse>}
	 * @author 蝉鸣
	 */
	private Mono<ServerResponse> handleBlockedRequest(ServerWebExchange exchange, Throwable throwable) {
		return GatewayCallbackManager.getBlockHandler().handleRequest(exchange, throwable);
	}

}