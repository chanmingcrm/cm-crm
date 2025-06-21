package com.platform.mesh.security.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.security.service.BaseUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

import java.security.Principal;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @description 资源服务器toke内省处理器
 * @author 蝉鸣
 */
public class CustomOpaqueTokenIntrospect implements OpaqueTokenIntrospector {

	private static final Logger log = LoggerFactory.getLogger(CustomOpaqueTokenIntrospect.class);

	private final OAuth2AuthorizationService authorizationService;

	public CustomOpaqueTokenIntrospect(OAuth2AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	/**
	 * 功能描述:
	 * 〈通过token获取信息〉
	 * @param token token
	 * @return 正常返回:{@link OAuth2AuthenticatedPrincipal}
	 * @author 蝉鸣
	 */
	@Override
	public OAuth2AuthenticatedPrincipal introspect(String token) {
		OAuth2Authorization oldAuthorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);

		if (Objects.isNull(oldAuthorization)) {
			throw new InvalidBearerTokenException(token);
		}

		// 客户端模式默认返回
		if (AuthorizationGrantType.CLIENT_CREDENTIALS.equals(oldAuthorization.getAuthorizationGrantType())) {
			return new ClientCredentialsOAuth2AuthenticatedPrincipal(oldAuthorization.getAttributes(),
					AuthorityUtils.NO_AUTHORITIES, oldAuthorization.getPrincipalName());
		}

		Map<String, BaseUserDetailsService> userDetailsServiceMap = SpringUtil.getBeansOfType(BaseUserDetailsService.class);

		Optional<BaseUserDetailsService> optional = userDetailsServiceMap.values().stream()
				.filter(service -> service.support(Objects.requireNonNull(oldAuthorization).getRegisteredClientId(),
						oldAuthorization.getAuthorizationGrantType().getValue()))
				.max(Comparator.comparingInt(Ordered::getOrder));

		UserDetails userDetails = null;
		try {
			UsernamePasswordAuthenticationToken principal = (UsernamePasswordAuthenticationToken) Objects
					.requireNonNull(oldAuthorization).getAttributes().get(Principal.class.getName());
			Object tokenPrincipal = principal.getPrincipal();
			return (LoginUserBO) tokenPrincipal;
//			userDetails = optional.get().loadUserByUser((LoginUser) tokenPrincipal);
		}
		catch (UsernameNotFoundException usernameNotFoundException) {
			log.warn("用户不不存在 {}", usernameNotFoundException.getLocalizedMessage());
			throw usernameNotFoundException;
		}
		catch (Exception ex) {
			log.error("资源服务器 introspect Token error {}", ex.getLocalizedMessage());
		}
		return (LoginUserBO) userDetails;
	}

}