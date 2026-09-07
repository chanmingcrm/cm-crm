package com.platform.mesh.uaa.biz.auth.support.grant.third;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.security.constants.GrantTypeConstant;
import com.platform.mesh.security.constants.SecurityConstant;
import com.platform.mesh.security.service.BaseUserDetailsService;
import com.platform.mesh.uaa.api.constants.UaaParamsConstant;
import com.platform.mesh.uaa.biz.auth.domain.dto.AuthCallbackDTO;
import com.platform.mesh.uaa.biz.auth.exception.AuthExceptionEnum;
import com.platform.mesh.uaa.biz.auth.service.ITokenService;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysAccountInfoBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysUserBO;
import com.platform.mesh.upms.api.modules.sys.user.feign.RemoteUserService;
import com.platform.mesh.utils.result.Result;
import com.platform.mesh.utils.result.ResultUtil;
import com.platform.mesh.utils.spring.ServletUtil;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description 自定义手机登录处理
 * @author 蝉鸣
 */
@Service
public class ThirdDetailsServiceImpl implements BaseUserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(ThirdDetailsServiceImpl.class);

	@Autowired
	private ITokenService tokenService;

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
		return GrantTypeConstant.THIRD.equals(grantType);
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
		SourceFlagEnum sourceFlagEnum = BaseEnum.getEnumByValue(SourceFlagEnum.class, Integer.parseInt(sourceFlag), SourceFlagEnum.SYSTEM);
		//是否静默登录
		String silence = paramMap.get(UaaParamsConstant.SILENCE);
		if(YesOrNoEnum.YES.getValue().toString().equals(silence)){
			//解析code值,后续可以进行二次验证
			accountCode = paramMap.get(UaaParamsConstant.THIRD_PARAMETER_CLIENT_CODE);
		}else{
			String agentId = paramMap.get(UaaParamsConstant.AGENT_ID);
			String clientId = paramMap.get(UaaParamsConstant.THIRD_PARAMETER_CLIENT_ID);
			String clientCode = paramMap.get(UaaParamsConstant.THIRD_PARAMETER_CLIENT_CODE);
			//获取第三方用户信息
			AuthConfig authConfig = tokenService.getServiceManual().getAuthConfig(agentId,clientId);
			AuthRequest authRequest = tokenService.getServiceManual().getAuthRequest(sourceFlagEnum, authConfig);
			AuthCallbackDTO callbackDTO = new AuthCallbackDTO();
			callbackDTO.setCode(clientCode);
			//获取响应值
			AuthResponse<?> authResponse = authRequest.login(callbackDTO);
			if (!authResponse.ok()) {
				throw AuthExceptionEnum.AUTH_CLIENT_LOGIN_INVALID.getBaseException();
			}
			JSONObject jsonObject = JSONUtil.parseObj(authResponse.getData());
			accountCode = jsonObject.get(UaaParamsConstant.THIRD_PARAMETER_UUID).toString();
		}
		//获取用户信息
		Result<SysAccountInfoBO> userResult = remoteUserService.getUserInfoByAccountCode(accountCode, sourceFlagEnum.getValue());
		//校验用户信息
		checkAccount(userResult, accountCode);
		//转换用户对象
		return getUserDetails(userResult);
	}

	/**
	 * 自定义账号状态检测
	 * @param userInfo userResult
	 * @param username username
	 */
	private void checkAccount(Result<SysAccountInfoBO> userInfo, String username) {
		SysUserBO sysUserBO = ResultUtil.of(userInfo).getData().orElseThrow(() -> {
			log.info("登录用户：{} 不存在.", username);
            return AuthExceptionEnum.USER_NO_EXIST.getBaseException();
		}).getSysUserBO();

		// 获取用户状态信息
		if (sysUserBO.getUserFlag().equals(YesOrNoEnum.NO.getValue())) {
			log.info("{}： 用户已被冻结.", username);
			throw AuthExceptionEnum.USER_IN_FREEZE.getBaseException();
		}
	}


}
