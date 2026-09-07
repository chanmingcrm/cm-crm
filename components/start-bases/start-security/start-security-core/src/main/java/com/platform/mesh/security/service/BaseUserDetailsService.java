package com.platform.mesh.security.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.custom.SmsFlagEnum;
import com.platform.mesh.redis.service.RedissonUtil;
import com.platform.mesh.redis.service.constants.CacheConstants;
import com.platform.mesh.security.constants.GrantTypeConstant;
import com.platform.mesh.security.constants.SecurityConstant;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.security.exception.SecurityExceptionEnum;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.uaa.api.constants.UaaParamsConstant;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysAccountInfoBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysMenuBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysUserBO;
import com.platform.mesh.utils.result.Result;
import com.platform.mesh.utils.spring.ServletUtil;
import org.springframework.core.Ordered;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;

import java.util.Collection;
import java.util.List;

/**
 * @description 自定义实现spring security用户体系
 * @author 蝉鸣
 */
public interface BaseUserDetailsService extends UserDetailsService, Ordered {

	/**
	 * 重写此方法，以此支持登录器是否支持此客户端校验
	 * @param clientId 目标客户端
	 * @return true/false
	 */
	default boolean support(String clientId, String grantType) {
		return true;
	}

	/**
	 * 排序值 默认取最大的
	 * @return 排序值
	 */
	default int getOrder() {
		return 0;
	}

	/**
	 * 构建userdetails
	 * @param result 用户信息
	 * @return UserDetails
	 */
	default UserDetails getUserDetails(Result<SysAccountInfoBO> result) {
		SysAccountInfoBO info = result.getData();

		List<String> authorityList = CollUtil.newArrayList();
		if (CollUtil.isNotEmpty(info.getMenuBOS())) {
			List<SysMenuBO> menuBOS = info.getMenuBOS();
			// 获取权限
			List<String> permissions = menuBOS.stream().map(SysMenuBO::getMenuMac).distinct().toList();
			authorityList.addAll(permissions);
		}

		Collection<? extends GrantedAuthority> authorities = AuthorityUtils
				.createAuthorityList(authorityList);

		SysUserBO user = info.getSysUserBO();
		UserCacheUtil.setSysUserInfoCache(user.getUserId(),user);
		SysAccountBO account = info.getAccountBO();
		UserCacheUtil.setAccountInfoCache(account);
		return new LoginUserBO( account.getUserId(), account.getAccountId(),user.getNickName(),
				account.getAccountCode(), SecurityConstant.BCRYPT + account.getCheckCode(), true,
				true, true, true, authorities);
	}

	/**
	 * 通过用户实体查询
	 * @param loginUserBO user
	 * @return UserDetails
	 */
	default UserDetails loadUserByUser(LoginUserBO loginUserBO) {
		return this.loadUserByUsername(loginUserBO.getUsername());
	}

	/**
	 * 验证手机验证码
	 * @param phone phone
	 * @return Boolean
	 */
	default Boolean checkSmsCode(String phone){
		String code = ServletUtil.getRequestInst().getParameter(UaaParamsConstant.SMS_CODE);
		if(ObjectUtil.isEmpty(code)){
			throw SecurityExceptionEnum.SECURITY_SMS_CODE_INVALID.getBaseException();
		}
		String grantType = ServletUtil.getRequestInst().getParameter(OAuth2ParameterNames.GRANT_TYPE);
		if (!StrUtil.equals(GrantTypeConstant.SMS, grantType)) {
			throw SecurityExceptionEnum.SECURITY_SMS_TYPE_INVALID.getBaseException();
		}
		// sms 模式校验Code
		// TODO 实现手机验证码校验
//		if ("1234".equals(code)) {
//			return Boolean.TRUE;
//		}
		//验证码缓存KEY
		String phoneKey = CacheConstants.SMS_PHONE_CACHE.concat(SymbolConst.COLON).concat(phone).concat(SymbolConst.COLON).concat(SmsFlagEnum.LOGIN.getDesc());
		//验证码是否过期
		if(!RedissonUtil.hasKey(phoneKey)){
			throw SecurityExceptionEnum.SECURITY_SMS_CODE_EXPIRE.getBaseException();
		}
		//获取验证码
		Object cacheObject = RedissonUtil.getCacheObject(phoneKey);
		if(code.equals(cacheObject.toString())){
			return Boolean.TRUE;
		}else{
			throw SecurityExceptionEnum.SECURITY_SMS_CODE_INVALID.getBaseException();
		}
	}
}
