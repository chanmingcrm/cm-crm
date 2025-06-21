package com.platform.mesh.gateway.filter;

import com.platform.mesh.core.constants.HttpConst;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @description 全局拦截器
 * @author 蝉鸣
 */
@Component
public class GatewayGlobalFilter implements GlobalFilter, Ordered {

	/**
	 * 功能描述:
	 * 〈1. 对请求头中参数进行处理 from 参数进行清洗 2. 重写StripPrefix = 1,支持全局〉
	 * @param exchange exchange
	 * @param chain chain
	 * @return 正常返回:{@link Mono<Void>}
	 * @author 蝉鸣
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// 1. 清洗请求头中from 参数
		ServerHttpRequest request = exchange.getRequest().mutate()
				.headers(httpHeaders -> httpHeaders.remove(HttpConst.REQUEST_SOURCE)).build();

		return chain.filter(exchange.mutate().request(request.mutate().build()).build());
	}

	@Override
	public int getOrder() {
		return 10;
	}

}
