package com.platform.mesh.gateway.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.redis.service.constants.CacheConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
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
public class GatewayRequestFilter implements GlobalFilter, Ordered {

	private static final Logger log = LoggerFactory.getLogger(GatewayRequestFilter.class);

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

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
		// 放行返回值
		Mono<Void> filter = chain.filter(exchange.mutate().request(request.mutate().build()).build());
		// 获取token
		List<String> tokens = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
		//token为空放行
		if(CollUtil.isEmpty(tokens)){
			return filter;
		}
		//验证是否是BEARER类型token
		String token = CollUtil.getFirst(tokens);
		if(!token.contains(StrConst.BEARER+ SymbolConst.SPACE)){
			return filter;
		}
		token = token.replace(StrConst.BEARER+ SymbolConst.SPACE, StrUtil.EMPTY);
		//构建redis TokenKey
		String tokenKey = this.buildKey(token);
		//
		redisTemplate.setValueSerializer(RedisSerializer.java());
		Boolean hasKey = redisTemplate.hasKey(tokenKey);
		if(Boolean.FALSE.equals(hasKey)){
			return filter;
		}
		//TODO 模拟查询后端配置
//		Boolean confBool = Boolean.TRUE?token.contains(StrConst.BEARER):token.contains(StrConst.VALUE);
//		//查询配置 1：不允许续期 2：允许续期系统配置默认2小时 3：允许续期自定义配置N小时
//		if(Boolean.FALSE.equals(confBool)){
//			return filter;
//		}
		//条件允许则续期
		Duration duration = Duration.ofHours(NumberConst.NUM_2);
		//设置token过期时间
		redisTemplate.expire(tokenKey,duration);
		return filter;
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	private String buildKey(String id) {
		return String.format(CacheConstants.OAUTH_ACCESS_PREFIX+":%s", id);
	}
}
