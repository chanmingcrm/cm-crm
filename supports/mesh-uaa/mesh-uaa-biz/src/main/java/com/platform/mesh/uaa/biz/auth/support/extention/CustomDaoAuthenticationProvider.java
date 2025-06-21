package com.platform.mesh.uaa.biz.auth.support.extention;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.custom.SmsFlagEnum;
import com.platform.mesh.redis.service.RedissonUtil;
import com.platform.mesh.redis.service.constants.CacheConstants;
import com.platform.mesh.security.constants.GrantTypeConstant;
import com.platform.mesh.security.service.BaseUserDetailsService;
import com.platform.mesh.uaa.api.constants.UaaParamsConstant;
import com.platform.mesh.uaa.biz.auth.exception.AuthExceptionEnum;
import com.platform.mesh.utils.spring.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.util.Assert;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @description 自定义处理校验(默认密码模式)
 * @author 蝉鸣
 */
public class CustomDaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	/**
	 * The plaintext password used to perform PasswordEncoder#matches(CharSequence,
	 * String)} on when the user is not found to avoid SEC-2056.
	 */
	private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

	private final static BasicAuthenticationConverter BASIC_CONVERT = new BasicAuthenticationConverter();

	private PasswordEncoder passwordEncoder;

	/**
	 * The password used to perform {@link PasswordEncoder#matches(CharSequence, String)}
	 * on when the user is not found to avoid SEC-2056. This is necessary, because some
	 * {@link PasswordEncoder} implementations will short circuit if the password is not
	 * in a valid format.
	 */
	private volatile String userNotFoundEncodedPassword;

    /**
     * -- SETTER --
     *  功能描述:
     *  〈设置UserDetailsService〉
     */
    @Setter
    private UserDetailsService userDetailsService;

    /**
     * -- SETTER --
     *  功能描述:
     *  〈设置修改密码UserDetailsPasswordService〉
     */
    @Setter
    private UserDetailsPasswordService userDetailsPasswordService;

	public CustomDaoAuthenticationProvider() {
		setMessageSource(SpringUtil.getBean("securityMessageSource"));
		setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
	}

	/**
	 * 功能描述:
	 * 〈密码校验，短信验证码校验〉
	 * @param userDetails userDetails
	 *        从{@link #retrieveUser(String, UsernamePasswordAuthenticationToken)} or <code>UserCache</code>获取
	 * @param authentication authentication
	 * @throws AuthenticationException AuthenticationException
	 * @author 蝉鸣
	 */
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

		String grantType = ServletUtil.getRequestInst().getParameter(OAuth2ParameterNames.GRANT_TYPE);
		if (StrUtil.equals(GrantTypeConstant.SMS, grantType)) {
			// sms 模式校验Code
			String code = ServletUtil.getRequestInst().getParameter(UaaParamsConstant.SMS_CODE);
//			// TODO 实现手机验证码校验
//			if ("1234".equals(code)) {
//				return;
//			}
			//验证码缓存KEY
			String phoneKey = CacheConstants.SMS_PHONE_CACHE.concat(SymbolConst.COLON).concat(authentication.getName()).concat(SymbolConst.COLON).concat(SmsFlagEnum.LOGIN.getDesc());
			//验证码是否过期
			if(!RedissonUtil.hasKey(phoneKey)){
				throw AuthExceptionEnum.AUTH_LOGIN_SMS_EXPIRE.getBaseException();
			}
			//获取验证码
			Object cacheObject = RedissonUtil.getCacheObject(phoneKey);
			if(code.equals(cacheObject.toString())){
				return;
			}else{
				throw AuthExceptionEnum.AUTH_LOGIN_SMS_EXPIRE.getBaseException();
			}
		}
		if (StrUtil.equals(GrantTypeConstant.THIRD, grantType)) {
			// 第三方登录 模式无密码
			return;
		}
		// 密码模式登录
		if (authentication.getCredentials() == null) {
			this.logger.debug("Failed to authenticate since no credentials provided");
			throw new BadCredentialsException(this.messages
					.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
		String presentedPassword = authentication.getCredentials().toString();
		if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
			this.logger.debug("Failed to authenticate since password does not match stored value");
			throw new BadCredentialsException(this.messages
					.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
	}

	/**
	 * 功能描述:
	 * 〈接收用户详情〉
	 * @param username username
	 * @param authentication authentication
	 * @return 正常返回:{@link UserDetails}
	 * @author 蝉鸣
	 */
	@Override
	protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) {
		prepareTimingAttackProtection();
		HttpServletRequest request = null;
		try {
			request = ServletUtil.getRequest().orElseThrow(
					(Supplier<Throwable>) () -> new InternalAuthenticationServiceException("web request is empty"));
		}
		catch (Throwable e) {
			throw new InternalAuthenticationServiceException("web request is empty");
		}

		Map<String, String> paramMap = ServletUtil.getMapParam();
		String grantType = paramMap.get(OAuth2ParameterNames.GRANT_TYPE);
		String clientId = paramMap.get(OAuth2ParameterNames.CLIENT_ID);

		if (StrUtil.isBlank(clientId)) {
			clientId = BASIC_CONVERT.convert(request).getName();
		}

		Map<String, BaseUserDetailsService> userDetailsServiceMap = SpringUtil.getBeansOfType(BaseUserDetailsService.class);

		String finalClientId = clientId;
		// 获取需要使用的登录器
		Optional<BaseUserDetailsService> optional = userDetailsServiceMap.values().stream()
				.filter(service -> service.support(finalClientId, grantType))
				.max(Comparator.comparingInt(Ordered::getOrder));

		if (optional.isEmpty()) {
			throw new InternalAuthenticationServiceException("UserDetailsService error , not register");
		}

		try {
			UserDetails loadedUser = optional.get().loadUserByUsername(username);
			if (loadedUser == null) {
				throw new InternalAuthenticationServiceException(
						"UserDetailsService returned null, which is an interface contract violation");
			}
			return loadedUser;
		}
		catch (UsernameNotFoundException ex) {
			mitigateAgainstTimingAttack(authentication);
			throw ex;
		}
		catch (InternalAuthenticationServiceException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
		}
	}

	/**
	 * 功能描述:
	 * 〈认证成功授权对象〉
	 * @param principal principal
	 * @param authentication authentication
	 * @param user user
	 * @return 正常返回:{@link Authentication}
	 * @author 蝉鸣
	 */
	@Override
	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
			UserDetails user) {
		boolean upgradeEncoding = this.userDetailsPasswordService != null
				&& this.passwordEncoder.upgradeEncoding(user.getPassword());
		if (upgradeEncoding) {
			String presentedPassword = authentication.getCredentials().toString();
			String newPassword = this.passwordEncoder.encode(presentedPassword);
			user = this.userDetailsPasswordService.updatePassword(user, newPassword);
		}
		return super.createSuccessAuthentication(principal, authentication, user);
	}

	private void prepareTimingAttackProtection() {
		if (this.userNotFoundEncodedPassword == null) {
			this.userNotFoundEncodedPassword = this.passwordEncoder.encode(USER_NOT_FOUND_PASSWORD);
		}
	}

	private void mitigateAgainstTimingAttack(UsernamePasswordAuthenticationToken authentication) {
		if (authentication.getCredentials() != null) {
			String presentedPassword = authentication.getCredentials().toString();
			this.passwordEncoder.matches(presentedPassword, this.userNotFoundEncodedPassword);
		}
	}

	/**
	 * 功能描述:
	 * 〈设置密码加密器〉
	 * @param passwordEncoder 必须是 {@code PasswordEncoder} 的实例
	 *                        {@link PasswordEncoderFactories#createDelegatingPasswordEncoder()}
	 * @author 蝉鸣
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
		this.passwordEncoder = passwordEncoder;
		this.userNotFoundEncodedPassword = null;
	}

	/**
	 * 功能描述:
	 * 〈获取密码加密器〉
	 * @return 正常返回:{@link PasswordEncoder}
	 * @author 蝉鸣
	 */
	protected PasswordEncoder getPasswordEncoder() {
		return this.passwordEncoder;
	}

    /**
	 * 功能描述:
	 * 〈获取UserDetailsService〉
	 * @return 正常返回:{@link UserDetailsService}
	 * @author 蝉鸣
	 */
	protected UserDetailsService getUserDetailsService() {
		return this.userDetailsService;
	}

}
