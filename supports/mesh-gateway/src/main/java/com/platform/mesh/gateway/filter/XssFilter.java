package com.platform.mesh.gateway.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.gateway.properties.XssProperties;
import com.platform.mesh.utils.format.FormatUtil;
import com.platform.mesh.utils.html.EscapeUtil;
import io.netty.buffer.ByteBufAllocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @description 跨站脚本过滤器
 * @author 蝉鸣
 */
@Component
@ConditionalOnProperty(value = "security.xss.enabled", havingValue = "true")
public class XssFilter implements GlobalFilter, Ordered {

	/**
	 * 跨站脚本的 xss 配置，nacos自行添加
	 */
	@Autowired
	private XssProperties xss;

	/**
	 * 功能描述:
	 * 〈过滤处理〉
	 * @param exchange exchange
	 * @param chain chain
	 * @return 正常返回:{@link Mono<Void>}
	 * @author 蝉鸣
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		// GET DELETE 不过滤
		HttpMethod method = request.getMethod();
		if (ObjectUtil.isNull(method) || method.matches(HttpMethod.GET.name()) || method.matches(HttpMethod.DELETE.name())) {
			return chain.filter(exchange);
		}
		// 非json类型，不过滤
		if (!isJsonRequest(exchange)) {
			return chain.filter(exchange);
		}
		// excludeUrls 不过滤
		String url = request.getURI().getPath();
		if (FormatUtil.matches(url, xss.getExcludeUrls())) {
			return chain.filter(exchange);
		}
		ServerHttpRequestDecorator httpRequestDecorator = requestDecorator(exchange);
		return chain.filter(exchange.mutate().request(httpRequestDecorator).build());

	}

	/**
	 * 功能描述:
	 * 〈参数解密包装〉
	 * @param exchange exchange
	 * @return 正常返回:{@link ServerHttpRequestDecorator}
	 * @author 蝉鸣
	 */
	private ServerHttpRequestDecorator requestDecorator(ServerWebExchange exchange) {
		return new ServerHttpRequestDecorator(exchange.getRequest()) {
			@Override
			public Flux<DataBuffer> getBody() {
				Flux<DataBuffer> body = super.getBody();
				return body.map(dataBuffer -> {
					byte[] content = new byte[dataBuffer.readableByteCount()];
					dataBuffer.read(content);
					DataBufferUtils.release(dataBuffer);
					String bodyStr = new String(content, StandardCharsets.UTF_8);
					// 防xss攻击过滤
					bodyStr = EscapeUtil.clean(bodyStr);
					// 转成字节
					byte[] bytes = bodyStr.getBytes();
					NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(
							ByteBufAllocator.DEFAULT);
					DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
					buffer.write(bytes);
					return buffer;
				});
			}

			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.putAll(super.getHeaders());
				// 由于修改了请求体的body，导致content-length长度不确定，因此需要删除原先的content-length
				httpHeaders.remove(HttpHeaders.CONTENT_LENGTH);
				httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
				return httpHeaders;
			}

		};
	}

	/**
	 * 功能描述:
	 * 〈是否是Json请求〉
	 * @param exchange exchange
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	public boolean isJsonRequest(ServerWebExchange exchange) {
		String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
		return StrUtil.startWithIgnoreCase(header, MediaType.APPLICATION_JSON_VALUE);
	}

	@Override
	public int getOrder() {
		return -100;
	}

}
