package com.platform.mesh.uaa.biz.auth.support.extention;

import com.platform.mesh.uaa.biz.auth.support.handler.FormAuthenticationFailureHandler;
import com.platform.mesh.uaa.biz.auth.support.handler.SsoLogoutSuccessHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

/**
 * @description 基于授权码模式 统一认证登录 spring security & sas 都可以使用 所以抽取成 HttpConfigurer
 * @author 蝉鸣
 */
public final class FormIdentityLoginConfigurer
		extends AbstractHttpConfigurer<FormIdentityLoginConfigurer, HttpSecurity> {

	/**
	 * 功能描述:
	 * 〈初始化〉
	 * @param http http
	 * @author 蝉鸣
	 */
	@Override
	public void init(HttpSecurity http) {
		// SSO登录成功处理
		http.formLogin(formLogin -> {
			formLogin.loginPage("/token/login");
			formLogin.loginProcessingUrl("/token/form");
			formLogin.failureHandler(new FormAuthenticationFailureHandler());

		});
		// SSO登出成功处理
		http.logout(logOut->{
			logOut.logoutSuccessHandler(new SsoLogoutSuccessHandler())
					.deleteCookies("JSESSIONID")
					.invalidateHttpSession(true);
				});
		http.cors(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable);

	}

}
