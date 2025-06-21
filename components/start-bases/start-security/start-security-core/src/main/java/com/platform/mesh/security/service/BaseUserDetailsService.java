package com.platform.mesh.security.service;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysMenuBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysUserBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysAccountInfoBO;
import com.platform.mesh.security.constants.SecurityConstant;
import com.platform.mesh.utils.result.Result;
import org.springframework.core.Ordered;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.*;

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

}
