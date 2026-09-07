package com.platform.mesh.gateway.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.redis.service.constants.CacheConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

/**
 * @description 全局拦截器
 * @author 蝉鸣
 */
@Component
@ConditionalOnClass(ReactiveStringRedisTemplate.class)
public class GatewayRequestFilter implements GlobalFilter, Ordered {

	private final ReactiveStringRedisTemplate redisTemplate;

	/**
	 * 功能描述:
	 * 〈创建网关请求过滤器〉
	 * @param redisTemplate 响应式字符串 Redis 操作模板
	 * @author qingfeng
	 */
	public GatewayRequestFilter(ReactiveStringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 功能描述:
	 * 〈对请求token进行续期〉
	 * @param exchange exchange
	 * @param chain chain
	 * @return 正常返回:{@link Mono<Void>}
	 * @author 蝉鸣
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// 获取 ServerHttpRequest
		ServerHttpRequest request = exchange.getRequest().mutate()
				.headers(httpHeaders -> httpHeaders.remove(HttpConst.REQUEST_SOURCE)).build();
		// 构建移除内部来源标识后的网关请求
		ServerWebExchange filteredExchange = exchange.mutate().request(request).build();
		// 获取token
		List<String> tokens = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
		//token为空放行
		if(CollUtil.isEmpty(tokens)){
			return chain.filter(filteredExchange);
		}
		//验证是否是BEARER类型token
		String token = CollUtil.getFirst(tokens);
		if(!token.contains(StrConst.BEARER+ SymbolConst.SPACE)){
			return chain.filter(filteredExchange);
		}
		token = token.replace(StrConst.BEARER+ SymbolConst.SPACE, StrUtil.EMPTY);
		//构建redis TokenKey
		String tokenKey = this.buildKey(token);
		//TODO 模拟查询后端配置
//		Boolean confBool = Boolean.TRUE?token.contains(StrConst.BEARER):token.contains(StrConst.VALUE);
//		//查询配置 1：不允许续期 2：允许续期系统配置默认2小时 3：允许续期自定义配置N小时
//		if(Boolean.FALSE.equals(confBool)){
//			return filter;
//		}
		//如果小于2小时则自动续期
		//条件允许则续期
		Duration duration = Duration.ofHours(NumberConst.NUM_2);
		// 使用响应式 Redis 查询并续期，避免阻塞 WebFlux 事件线程
		Mono<Void> renewToken = redisTemplate.getExpire(tokenKey)
				.filter(expire -> !expire.isNegative()
						&& expire.compareTo(duration) < NumberConst.NUM_0)
				.flatMap(expire -> redisTemplate.expire(tokenKey, duration))
				.then();
		// Redis 操作完成后再继续网关过滤链，保证整个处理过程保持非阻塞
		return renewToken.then(Mono.defer(() -> chain.filter(filteredExchange)));
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	private String buildKey(String id) {
		return String.format(CacheConstants.OAUTH_ACCESS_PREFIX+":%s", id);
	}
}
