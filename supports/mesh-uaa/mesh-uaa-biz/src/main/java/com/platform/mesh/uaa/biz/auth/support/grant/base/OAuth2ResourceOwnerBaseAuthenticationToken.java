package com.platform.mesh.uaa.biz.auth.support.grant.base;

import com.platform.mesh.core.constants.SymbolConst;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @description 自定义授权模式抽象
 * @author 蝉鸣
 */
public abstract class OAuth2ResourceOwnerBaseAuthenticationToken extends AbstractAuthenticationToken {

	private AuthorizationGrantType authorizationGrantType;

	private Authentication clientPrincipal;

	private Set<String> scopes;

	private Map<String, Object> additionalParameters;

	/**
	 * 功能描述:
	 * 〈构造函数〉
	 * @param authorizationGrantType authorizationGrantType
	 * @param clientPrincipal clientPrincipal
	 * @param scopes scopes
	 * @param additionalParameters additionalParameters
	 * @author 蝉鸣
	 */
	public OAuth2ResourceOwnerBaseAuthenticationToken(AuthorizationGrantType authorizationGrantType,
			Authentication clientPrincipal, @Nullable Set<String> scopes,
			@Nullable Map<String, Object> additionalParameters) {
		super(Collections.emptyList());
		Assert.notNull(authorizationGrantType, "authorizationGrantType cannot be null");
		Assert.notNull(clientPrincipal, "clientPrincipal cannot be null");
		this.authorizationGrantType = authorizationGrantType;
		this.clientPrincipal = clientPrincipal;
		this.scopes = Collections.unmodifiableSet(scopes != null ? new HashSet<>(scopes) : Collections.emptySet());
		this.additionalParameters = Collections.unmodifiableMap(
				additionalParameters != null ? new HashMap<>(additionalParameters) : Collections.emptyMap());
	}

	/**
	 * 功能描述:
	 * 〈授权类型〉
	 * @return 正常返回:{@link AuthorizationGrantType}
	 * @author 蝉鸣
	 */
	public AuthorizationGrantType getAuthorizationGrantType() {
		return authorizationGrantType;
	}

	/**
	 * 功能描述:
	 * 〈授权认证〉
	 * @return 正常返回:{@link Authentication}
	 * @author 蝉鸣
	 */
	public Authentication getClientPrincipal() {
		return clientPrincipal;
	}

	/**
	 * 功能描述:
	 * 〈作用域〉
	 * @return 正常返回:{@link Set<String>}
	 * @author 蝉鸣
	 */
	public Set<String> getScopes() {
		return scopes;
	}

	/**
	 * 功能描述:
	 * 〈参数〉
	 * @return 正常返回:{@link Map<String,Object>}
	 * @author 蝉鸣
	 */
	public Map<String, Object> getAdditionalParameters() {
		return additionalParameters;
	}

	/**
	 * 功能描述:
	 * 〈扩展模式一般不需要密码〉
	 * @return 正常返回:{@link Object}
	 * @author 蝉鸣
	 */
	@Override
	public Object getCredentials() {
		return SymbolConst.BLANK;
	}

	/**
	 * 获取用户名
	 */
	@Override
	public Object getPrincipal() {
		Object clientPrincipal = this.clientPrincipal;
		return clientPrincipal;
	}

}
