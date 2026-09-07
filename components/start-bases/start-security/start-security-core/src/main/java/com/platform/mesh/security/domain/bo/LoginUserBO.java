package com.platform.mesh.security.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.io.Serial;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 登录用户身份
 * @author 蝉鸣
 */
@Setter
@Getter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="ES单体查询DTO")
public class LoginUserBO extends User implements OAuth2AuthenticatedPrincipal {

	@Serial
	private static final long serialVersionUID = 620L;

	/**
	 * 用户ID
	 */
	@Schema(description ="用户ID")
	private Long userId;

	/**
	 * 账户ID
	 */
	@Schema(description ="账户ID")
	private Long accountId;

	/**
	 * 昵称
	 */
	@Schema(description ="昵称")
	private String nickname;

	public LoginUserBO(Long userId, Long accountId,String nickname,
					   String username, String password, boolean enabled,
					   boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
					   Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userId = userId;
		this.accountId = accountId;
		this.nickname = nickname;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return new HashMap<>(16);
	}

	@Override
	public String getName() {
		return this.getUsername();
	}

}
