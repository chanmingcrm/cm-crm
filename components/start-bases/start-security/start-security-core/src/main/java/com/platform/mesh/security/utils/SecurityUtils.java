package com.platform.mesh.security.utils;

import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.security.constants.SecurityConstant;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.security.exception.SecurityExceptionEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

/**
 * @author 蝉鸣
 * @description 安全工具类
 */
public class SecurityUtils {

	/**
	 * 功能描述:
	 * 〈获取Authentication〉
	 * @return 正常返回:{@link Authentication}
	 * @author 蝉鸣
	 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 功能描述:
	 * 〈获取用户ID〉
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	public static Long getUserId() {
		return getLoginUser().getUserId();
	}

	/**
	 * 功能描述:
	 * 〈获取用户名〉
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	public static String getUsername() {
		return getLoginUser().getUsername();
	}

	/**
	 * 功能描述:
	 * 〈获取用户〉
	 * @param authentication authentication
	 * @return 正常返回:{@link LoginUserBO}
	 * @author 蝉鸣
	 */
	public static LoginUserBO getLoginUser(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof LoginUserBO) {
			return (LoginUserBO) principal;
		}
		return null;
	}

	/**
	 * 功能描述:
	 * 〈获取用户〉
	 * @return 正常返回:{@link LoginUserBO}
	 * @author 蝉鸣
	 */
	public static LoginUserBO getLoginUser() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			throw SecurityExceptionEnum.SECURITY_NO_USER_INFO.getBaseException();
		}
		return getLoginUser(authentication);
	}

	/**
	 * 功能描述:
	 * 〈生成BCryptPasswordEncoder密码〉
	 * @param password 密码
	 * @return 正常返回:{@link String} 加密字符串
	 * @author 蝉鸣
	 */
	public static String encryptPassword(String password) {
		//自定义加密
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		return passwordEncoder.encode(password);
		//默认加密方式
		return PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password);
	}

	/**
	 * 功能描述:
	 * 〈移除生成密码类型前缀〉
	 * @param encryptPassword 密码
	 * @return 正常返回:{@link String} 加密字符串
	 * @author 蝉鸣
	 */
	public static String removePrefix(String encryptPassword) {
		//默认加密方式
		return encryptPassword.replaceAll(SymbolConst.PATTERN_ENCRYPT_TYPE, SymbolConst.BLANK);
	}

	/**
	 * 功能描述:
	 * 〈判断密码是否相同〉
	 * @param rawPassword 真实密码
	 * @param encodedPassword 加密后字符
	 * @return 正常返回:{@link boolean} 结果
	 * @author 蝉鸣
	 */
	public static boolean matchesPassword(String rawPassword, String encodedPassword) {
		if(!encodedPassword.startsWith(SecurityConstant.BCRYPT)){
			encodedPassword = SecurityConstant.BCRYPT.concat(encodedPassword);
		}
		//默认校验方式
		return PasswordEncoderFactories.createDelegatingPasswordEncoder().matches(rawPassword,encodedPassword);
	}

	/**
	 * 功能描述:
	 * 〈是否为管理员〉
	 * @param userId 用户ID
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	public static boolean isAdmin(Long userId) {
		return userId != null && 1831933911445983232L == userId;
//		return userId != null && 1L == userId;
	}

}