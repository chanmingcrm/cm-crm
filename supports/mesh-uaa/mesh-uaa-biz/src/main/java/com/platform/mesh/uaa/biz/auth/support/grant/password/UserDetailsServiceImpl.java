package com.platform.mesh.uaa.biz.auth.support.grant.password;

import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.security.constants.SecurityConstant;
import com.platform.mesh.security.service.BaseUserDetailsService;
import com.platform.mesh.uaa.biz.auth.exception.AuthExceptionEnum;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysAccountInfoBO;
import com.platform.mesh.upms.api.modules.sys.user.feign.RemoteUserService;
import com.platform.mesh.utils.result.Result;
import com.platform.mesh.utils.result.ResultUtil;
import com.platform.mesh.utils.spring.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description 自定义用户信息处理
 * @author 蝉鸣
 */
@Primary
@Service
public class UserDetailsServiceImpl implements BaseUserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 识别是否使用此登录器
	 * @param clientId 目标客户端
	 * @param grantType 登录类型
	 * @return boolean
	 */
	@Override
	public boolean support(String clientId, String grantType) {
		return AuthorizationGrantType.PASSWORD.getValue().equals(grantType);
	}

	/**
	 * 功能描述:
	 * 〈启动排序〉
	 * @return 正常返回:{@link int}
	 */
	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

	/**
	 * 用户名称登录
	 * @param accountCode String
	 * @return UserDetails
	 */
	@Override
	public UserDetails loadUserByUsername(String accountCode) {
		//获取请求参数
		Map<String, String> paramMap = ServletUtil.getMapParam();
		//获取账号来源
		String sourceFlag = paramMap.get(SecurityConstant.SOURCE_FLAG);
		SourceFlagEnum enumByDesc = BaseEnum.getEnumByDesc(SourceFlagEnum.class, sourceFlag, SourceFlagEnum.SYSTEM);
		//获取用户信息
		Result<SysAccountInfoBO> userResult = remoteUserService.getUserInfoByAccountCode(accountCode, enumByDesc.getValue());
		//校验用户信息
		checkAccount(userResult, accountCode);
		//转换用户对象
		return getUserDetails(userResult);
	}

	/**
	 * 自定义账号状态检测
	 * @param userInfo userResult
	 * @param accountCode username
	 */
	private void checkAccount(Result<SysAccountInfoBO> userInfo, String accountCode) {
		SysAccountBO accountBO = ResultUtil.of(userInfo).getData().orElseThrow(() -> {
			log.info("登录用户：{} 不存在.", accountCode);
			return AuthExceptionEnum.USER_NO_EXIST.getBaseException();
		}).getAccountBO();

		// 获取用户状态信息
		if (accountBO.getAccountFlag().equals(YesOrNoEnum.NO.getValue())) {
			log.info("{}： 用户已被冻结.", accountCode);
			throw  AuthExceptionEnum.USER_IN_FREEZE.getBaseException();
		}
	}

}

