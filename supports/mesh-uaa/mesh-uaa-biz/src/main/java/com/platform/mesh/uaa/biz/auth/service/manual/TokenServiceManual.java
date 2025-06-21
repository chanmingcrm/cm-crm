package com.platform.mesh.uaa.biz.auth.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.properties.EnvironmentProperty;
import com.platform.mesh.security.constants.GrantTypeConstant;
import com.platform.mesh.security.constants.SecurityConstant;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.uaa.api.constants.UaaParamsConstant;
import com.platform.mesh.uaa.biz.auth.constants.AuthConst;
import com.platform.mesh.uaa.biz.auth.domain.dto.AuthCallbackDTO;
import com.platform.mesh.uaa.biz.auth.domain.dto.AuthRenderDTO;
import com.platform.mesh.uaa.biz.auth.exception.AuthExceptionEnum;
import com.platform.mesh.uaa.biz.auth.support.grant.third.AuthStateRedisCache;
import com.platform.mesh.uaa.biz.modules.client.domain.po.Oauth2RegisteredClient;
import com.platform.mesh.uaa.biz.modules.client.service.IOauth2RegisteredClientService;
import com.platform.mesh.uaa.biz.modules.tenant.client.domain.po.TenantClient;
import com.platform.mesh.uaa.biz.modules.tenant.client.service.ITenantClientService;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import com.platform.mesh.upms.api.modules.sys.user.feign.RemoteUserService;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthDingTalkV2Request;
import me.zhyd.oauth.request.AuthFeishuRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.request.AuthWeChatEnterpriseQrcodeRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Set;

/**
 * @description 自定义获取第三方code换信息
 * @author 蝉鸣
 */
@Service
public class TokenServiceManual {

	private static final Logger log = LoggerFactory.getLogger(TokenServiceManual.class);

	@Autowired
	private EnvironmentProperty environmentProperty;

	@Autowired
	private OAuth2AuthorizationService authorizationService;

	@Autowired
	private IOauth2RegisteredClientService sysClientDetailsService;

	@Autowired
	private AuthStateRedisCache authStateRedisCache;

	@Autowired
	private ITenantClientService tenantClientService;

	@Autowired
	private RemoteUserService remoteUserService;



	/**
	 * 功能描述:
	 * 〈填充确认页面信息〉
	 * @param modelAndView modelAndView
	 * @param clientId clientId
	 * @return 正常返回:{@link ModelAndView}
	 * @author 蝉鸣
	 */
	public ModelAndView paddingConfirmPage(ModelAndView modelAndView, String clientId) {
		Oauth2RegisteredClient clientDetails = sysClientDetailsService.selectSysClientDetailsById(clientId);
		if(ObjectUtil.isEmpty(clientDetails)){
			throw AuthExceptionEnum.AUTH_CLIENT_INVALID.getBaseException();
		}
		Set<String> authorizedScopes = StringUtils.commaDelimitedListToSet(clientDetails.getScopes());
		modelAndView.addObject(OAuth2ParameterNames.SCOPE, authorizedScopes);
		return modelAndView;
	}


	/**
	 * 功能描述:
	 * 〈获取登录token〉
	 * 	目的，Oauth2 直接返回access_token等信息不符合系统同意返回Result风格，特此添加一步，调用封装,并不是真实产生token地方
	 * @param map map
	 * @param authorization authorization
	 * @return 正常返回:{@link JSONObject}
	 * @author 蝉鸣
	 */
	public JSONObject getSysPasswordTypeToken(Map<String, Object> map, String authorization) {
		JSONObject accessToken = getPasswordTypeToken(map,authorization,environmentProperty.getAccessTokenUri());
		return accessToken;
	}

	/**
	 * 功能描述:
	 * 〈获取登录token〉
	 * 目的，Oauth2 直接返回access_token等信息不符合系统同意返回Result风格，特此添加一步，调用封装,并不是真实产生token地方
	 * 方便获取其他授权方token
	 * @param map map
	 * @param authorization authorization
	 * @param accessTokenUrl accessTokenUrl
	 * @return 正常返回:{@link JSONObject}
	 * @author 蝉鸣
	 */
	public JSONObject getPasswordTypeToken(Map<String, Object> map,String authorization,String accessTokenUrl) {
		if(CollUtil.isEmpty(map)){
			throw AuthExceptionEnum.ADD_NO_ARGS.getBaseException();
		}
		MultiValueMap<String, Object> paramsMap = new LinkedMultiValueMap<>();
		if (OAuth2ParameterNames.PASSWORD.equals(map.get(OAuth2ParameterNames.GRANT_TYPE))) {
			paramsMap.set(OAuth2ParameterNames.USERNAME,map.get(OAuth2ParameterNames.USERNAME));
			paramsMap.set(OAuth2ParameterNames.PASSWORD,map.get(OAuth2ParameterNames.PASSWORD));
		}else if(OAuth2ParameterNames.REFRESH_TOKEN.equals(map.get(OAuth2ParameterNames.GRANT_TYPE))){
			paramsMap.set(OAuth2ParameterNames.REFRESH_TOKEN,map.get(OAuth2ParameterNames.REFRESH_TOKEN));
		}else if(GrantTypeConstant.SMS.equals(map.get(OAuth2ParameterNames.GRANT_TYPE))){
			paramsMap.set(UaaParamsConstant.SMS_PARAMETER_NAME,map.get(UaaParamsConstant.SMS_PARAMETER_NAME));
			paramsMap.set(UaaParamsConstant.SMS_CODE,map.get(UaaParamsConstant.SMS_CODE));
		}else if(GrantTypeConstant.THIRD.equals(map.get(OAuth2ParameterNames.GRANT_TYPE))){
			paramsMap.set(UaaParamsConstant.THIRD_PARAMETER_UUID,map.get(UaaParamsConstant.THIRD_PARAMETER_UUID));
			paramsMap.set(UaaParamsConstant.THIRD_PARAMETER_CLIENT_ID,map.get(UaaParamsConstant.THIRD_PARAMETER_CLIENT_ID));
			paramsMap.set(UaaParamsConstant.THIRD_PARAMETER_CLIENT_CODE,map.get(UaaParamsConstant.THIRD_PARAMETER_CLIENT_CODE));
			paramsMap.set(SecurityConstant.SOURCE_FLAG,map.get(SecurityConstant.SOURCE_FLAG));
		}
		paramsMap.set(OAuth2ParameterNames.GRANT_TYPE,map.get(OAuth2ParameterNames.GRANT_TYPE));
		paramsMap.set(OAuth2ParameterNames.SCOPE,map.get(OAuth2ParameterNames.SCOPE));
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		// 需求需要传参为form-data格式
		header.setContentType(MediaType.MULTIPART_FORM_DATA);
		if(ObjectUtil.isNotEmpty(authorization) && authorization.startsWith(SecurityConstant.BASIC)){
			header.add(HttpHeaders.AUTHORIZATION,authorization);
		} else if (ObjectUtil.isNotEmpty(authorization) && authorization.startsWith(SecurityConstant.BEARER)) {
			throw AuthExceptionEnum.AUTH_CLIENT_LOGIN_PREFIX_INVALID.getBaseException();
		} else{
			restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(map.get(OAuth2ParameterNames.CLIENT_ID).toString(),map.get(OAuth2ParameterNames.CLIENT_SECRET).toString()));
		}
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramsMap, header);
		JSONObject accessToken = restTemplate.postForObject(accessTokenUrl, httpEntity, JSONObject.class);
		//对token进行自定义更改
        assert accessToken != null;
        log.info(accessToken.toString());
		return accessToken;
	}

	/**
	 * 功能描述:
	 * 〈获取登录token〉
	 * @param map map
	 * @param authorization authorization
	 * @return 正常返回:{@link JSONObject}
	 * @author 蝉鸣
	 */
	public JSONObject getRedirectUriTypeToken(Map<String, Object> map,String authorization) {
        return new JSONObject();
	}

	/**
	 * 功能描述:
	 * 〈删除token〉
	 * @param token token
	 * @return 正常返回:{@link Boolean}
	 * @author 蝉鸣
	 */
	public Boolean removeToken(String token) {
		OAuth2Authorization authorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
		if(ObjectUtil.isEmpty(authorization)){
			return Boolean.TRUE;
		}
		OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
		if (ObjectUtil.isEmpty(accessToken) || StrUtil.isBlank(accessToken.getToken().getTokenValue())) {
			return Boolean.TRUE;
		}
		// 清空用户缓存信息
		UserCacheUtil.clearSysAccountInfoCache(Long.valueOf(authorization.getPrincipalName()));
		// 清空access token
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		authorizationService.remove(authorization);
		// 处理自定义退出事件，保存相关日志
		SpringContextHolderUtil.publishEvent(new LogoutSuccessEvent(new PreAuthenticatedAuthenticationToken(
				authorization.getPrincipalName(), authorization.getRegisteredClientId())));
		return Boolean.TRUE;
	}

	/**
	 * 功能描述:
	 * 〈第三方登录授权页面渲染地址〉
	 * @param renderDTO renderDTO
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
    public String renderAuth(AuthRenderDTO renderDTO) {
		//获取来源类型
		SourceFlagEnum sourceEnum = BaseEnum.getEnumByValue(SourceFlagEnum.class, renderDTO.getSource());
		if(ObjectUtil.isEmpty(sourceEnum)){
			throw AuthExceptionEnum.AUTH_CLIENT_SOURCE_INVALID.getBaseException();
		}
		//获取授权配置
		AuthConfig authConfig = getAuthConfig(renderDTO.getClientId());
		//获取授权请求实例
		AuthRequest authRequest = getAuthRequest(sourceEnum, authConfig);
		//返回授权认证地址
		return authRequest.authorize(AuthStateUtils.createState());
    }

	/**
	 * 功能描述:
	 * 〈第三方登录账号与系统账户绑定〉
	 * @param callbackDTO callbackDTO
	 * @return 正常返回:{@link SysAccountBO}
	 * @author 蝉鸣
	 */
	public SysAccountBO bindAccount(AuthCallbackDTO callbackDTO) {
		//获取来源类型
		SourceFlagEnum sourceEnum = BaseEnum.getEnumByValue(SourceFlagEnum.class, callbackDTO.getSource());
		if(ObjectUtil.isEmpty(sourceEnum)){
			throw AuthExceptionEnum.AUTH_CLIENT_SOURCE_INVALID.getBaseException();
		}
		//获取授权配置
		AuthConfig authConfig = getAuthConfig(callbackDTO.getClientId());
		//获取授权请求实例
		AuthRequest authRequest = getAuthRequest(sourceEnum, authConfig);
		//获取响应值
		AuthResponse<?> authResponse = authRequest.login(callbackDTO);
		if (!authResponse.ok()) {
			throw AuthExceptionEnum.AUTH_CLIENT_LOGIN_INVALID.getBaseException();
		}
		JSONObject jsonObject = JSONUtil.parseObj(authResponse.getData());
		Object object = jsonObject.get(UaaParamsConstant.THIRD_PARAMETER_UUID);
		//校验本地账户是否存在
		SysAccountBO accountBO = new SysAccountBO();
		accountBO.setAccountCode(object.toString());
		accountBO.setSourceFlag(sourceEnum.getValue());
		if(jsonObject.containsKey(AuthConst.NICKNAME)){
			accountBO.setNickName(jsonObject.get(AuthConst.NICKNAME).toString());
		}
		if(jsonObject.containsKey(AuthConst.USERNAME)){
			if(ObjectUtil.isEmpty(accountBO.getNickName())){
				accountBO.setNickName(jsonObject.get(AuthConst.USERNAME).toString());
			}
		}
		if(jsonObject.containsKey(AuthConst.AVATAR)){
			accountBO.setAvatar(jsonObject.get(AuthConst.AVATAR).toString());
		}
		accountBO.setUserId(callbackDTO.getUserId());
		remoteUserService.thirdBindAccount(accountBO);
		return accountBO;
	}

	/**
	 * 功能描述:
	 * 〈根据客户端ID获取授权配置〉
	 * @param clientId clientId
	 * @return 正常返回:{@link SysAccountBO}
	 * @author 蝉鸣
	 */
	public AuthConfig getAuthConfig(Long clientId) {
		//获取数据库配置的客户端信息
		TenantClient tenantClient = tenantClientService.getById(clientId);
		//返回第三方授权配置
		return AuthConfig.builder()
				.agentId(tenantClient.getAgentId())
				.clientId(tenantClient.getClientId())
				.clientSecret(tenantClient.getClientSecret())
				.redirectUri(tenantClient.getRedirectUri())
				.ignoreCheckState(true)
				.build();
	}


	/**
	 * 根据具体授权来源，获取授权请求工具类
	 * @param sourceFlag sourceFlag
	 * @return AuthRequest
	 */
	public AuthRequest getAuthRequest(SourceFlagEnum sourceFlag,AuthConfig authConfig) {
		AuthRequest authRequest = null;
		switch (sourceFlag) {
			case DING:
				authRequest = new AuthDingTalkV2Request(authConfig);
				break;
			case WX_WORK:
				authRequest = new AuthWeChatEnterpriseQrcodeRequest(authConfig);
				break;
			case FEI_SHU:
				authRequest = new AuthFeishuRequest(authConfig);
				break;
			default:
				break;
		}
		if (ObjectUtil.isEmpty(authRequest)) {
			throw AuthExceptionEnum.AUTH_CLIENT_SOURCE_INVALID.getBaseException();
		}
		return authRequest;
	}
}