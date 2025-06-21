package com.platform.mesh.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.github.xiaoymin.knife4j.spring.gateway.Knife4jGatewayProperties;
import com.github.xiaoymin.knife4j.spring.gateway.discover.router.DiscoverClientRouteServiceConvert;
import com.platform.mesh.gateway.handler.SentinelFallbackHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.TimeZone;

/**
 * @description 网关限流配置
 * @author 蝉鸣
 */
@Configuration
public class GatewayConfig {

	/**
	 * 功能描述:
	 * 〈时区配置〉
	 * @return 正常返回:{@link Jackson2ObjectMapperBuilderCustomizer}
	 * @author 蝉鸣
	 */
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
		return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
	}

	/**
	 * 功能描述:
	 * 〈熔断处理〉
	 * @return 正常返回:{@link SentinelFallbackHandler}
	 * @author 蝉鸣
	 */
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SentinelFallbackHandler sentinelGatewayExceptionHandler() {
		return new SentinelFallbackHandler();
	}

	/**
	 * 功能描述:
	 * 〈熔断过滤〉
	 * @return 正常返回:{@link GlobalFilter}
	 * @author 蝉鸣
	 */
	@Order(-1)
	public GlobalFilter sentinelGatewayFilter() {
		return new SentinelGatewayFilter();
	}


	/**
	 * 功能描述:
	 * 〈开启动态配置:
	 * spring-cloud-starter-gateway 遗弃,换为spring-cloud-starter-gateway-server-webflux
	 * springCloud 2025开始spring.cloud.gateway.discovery.locator.enabled
	 * 配置转换成 spring.cloud.gateway.server.webflux.discovery.locator.enabled〉
	 * @return 正常返回:{@link GlobalFilter}
	 * @author 蝉鸣
	 */
	@Bean
	@ConditionalOnProperty(
			name = {"spring.cloud.gateway.server.webflux.discovery.locator.enabled"}
	)
	public DiscoverClientRouteServiceConvert discoverClientRouteServiceConvert(DiscoveryClientRouteDefinitionLocator discoveryClient, Knife4jGatewayProperties knife4jGatewayProperties) {
		return new DiscoverClientRouteServiceConvert(discoveryClient, knife4jGatewayProperties);
	}
}
