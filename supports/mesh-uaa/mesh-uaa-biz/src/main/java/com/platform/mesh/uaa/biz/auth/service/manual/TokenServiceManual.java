package com.platform.mesh.uaa.biz.auth.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.core.properties.EnvironmentProperty;
import com.platform.mesh.security.constants.GrantTypeConstant;
import com.platform.mesh.security.constants.SecurityConstant;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.security.utils.SecurityUtils;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.uaa.api.constants.UaaParamsConstant;
import com.platform.mesh.uaa.biz.auth.constants.AuthConst;
import com.platform.mesh.uaa.biz.auth.domain.dto.AuthCallbackDTO;
import com.platform.mesh.uaa.biz.auth.domain.dto.AuthClientDTO;
import com.platform.mesh.uaa.biz.auth.domain.dto.AuthRenderDTO;
import com.platform.mesh.uaa.biz.auth.exception.AuthExceptionEnum;
import com.platform.mesh.uaa.biz.modules.client.domain.po.Oauth2RegisteredClient;
import com.platform.mesh.uaa.biz.modules.client.service.IOauth2RegisteredClientService;
import com.platform.mesh.uaa.biz.modules.tenant.client.domain.po.TenantClient;
import com.platform.mesh.uaa.biz.modules.tenant.client.service.ITenantClientService;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import com.platform.mesh.upms.api.modules.sys.user.feign.RemoteUserService;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import com.platform.mesh.wxwork.app.service.IWxWorkAppService;
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
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
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

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.time.Duration;

/**
 * @description 自定义获取第三方code换信息
 * @author 蝉鸣
 */
@Service
public class TokenServiceManual {

	private static final Logger log = LoggerFactory.getLogger(TokenServiceManual.class);
	private static final Duration TOKEN_CONNECT_TIMEOUT = Duration.ofSeconds(3);
	private static final Duration TOKEN_READ_TIMEOUT = Duration.ofSeconds(10);

	private final RestTemplate tokenEndpointRestTemplate = createTokenEndpointRestTemplate();

	@Autowired
	private EnvironmentProperty environmentProperty;

	@Autowired
	private OAuth2AuthorizationService authorizationService;

	@Autowired
	private IOauth2RegisteredClientService sysClientDetailsService;

	@Autowired
	private ITenantClientService tenantClientService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private IWxWorkAppService wxWorkAppService;



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
	 * @param headMap headMap
	 * @return 正常返回:{@link JSONObject}
	 * @author 蝉鸣
	 */
	public JSONObject getSysPasswordTypeToken(Map<String, Object> map, Map<String,String> headMap) {
		return getPasswordTypeToken(map,headMap,environmentProperty.getAccessTokenUri());
	}

	/**
	 * 功能描述:
	 * 〈获取登录token〉
	 * 目的，Oauth2 直接返回access_token等信息不符合系统同意返回Result风格，特此添加一步，调用封装,并不是真实产生token地方
	 * 方便获取其他授权方token
	 * @param map map
	 * @param headMap headMap
	 * @param accessTokenUrl accessTokenUrl
	 * @return 正常返回:{@link JSONObject}
	 * @author 蝉鸣
	 */
	public JSONObject getPasswordTypeToken(Map<String, Object> map, Map<String,String> headMap,String accessTokenUrl) {
		if(CollUtil.isEmpty(map)){
			throw AuthExceptionEnum.ADD_NO_ARGS.getBaseException();
		}
		MultiValueMap<String, Object> paramsMap = new LinkedMultiValueMap<>();
		if (GrantTypeConstant.PASSWORD.equals(map.get(OAuth2ParameterNames.GRANT_TYPE))) {
			paramsMap.set(GrantTypeConstant.USERNAME,map.get(GrantTypeConstant.USERNAME));
			paramsMap.set(GrantTypeConstant.PASSWORD,map.get(GrantTypeConstant.PASSWORD));
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
			paramsMap.set(UaaParamsConstant.SILENCE,map.get(UaaParamsConstant.SILENCE));
			paramsMap.set(UaaParamsConstant.AGENT_ID,map.get(UaaParamsConstant.AGENT_ID));
		}
		paramsMap.set(OAuth2ParameterNames.GRANT_TYPE,map.get(OAuth2ParameterNames.GRANT_TYPE));
		paramsMap.set(OAuth2ParameterNames.SCOPE,map.get(OAuth2ParameterNames.SCOPE));
		HttpHeaders header = new HttpHeaders();
		// 标准 Token Endpoint 使用 application/x-www-form-urlencoded 提交参数。
		header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		String authorization = headMap.get(HttpHeaders.AUTHORIZATION);
		if (StrUtil.startWithIgnoreCase(authorization, SecurityConstant.BASIC)) {
			// 仅转发客户端 Basic 认证头，避免外部请求头进入自身 Token Endpoint。
			header.set(HttpHeaders.AUTHORIZATION, authorization);
		} else if (StrUtil.startWithIgnoreCase(authorization, SecurityConstant.BEARER)) {
			throw AuthExceptionEnum.AUTH_CLIENT_LOGIN_PREFIX_INVALID.getBaseException();
		} else{
			Object clientId = map.get(OAuth2ParameterNames.CLIENT_ID);
			Object clientSecret = map.get(OAuth2ParameterNames.CLIENT_SECRET);
			if (ObjectUtil.hasEmpty(clientId, clientSecret)) {
				throw AuthExceptionEnum.AUTH_CLIENT_INVALID.getBaseException();
			}
			header.setBasicAuth(clientId.toString(), clientSecret.toString());
		}
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramsMap, header);
		try {
			JSONObject accessToken = tokenEndpointRestTemplate.postForObject(accessTokenUrl, httpEntity, JSONObject.class);
			if (accessToken == null) {
				throw AuthExceptionEnum.AUTH_CLIENT_LOGIN_INVALID.getBaseException();
			}
			return accessToken;
		}
		catch (BaseException exception) {
			throw exception;
		}
		catch (Exception exception) {
			log.warn("调用 Token Endpoint 失败: {}", exception.getClass().getSimpleName());
			throw AuthExceptionEnum.AUTH_CLIENT_LOGIN_INVALID.getBaseException();
		}
	}

	/**
	 * 功能描述:
	 * 〈创建带连接、读取超时限制的 Token Endpoint 客户端〉
	 * @return Token Endpoint 专用 RestTemplate
	 * @author qingfeng
	 */
	private static RestTemplate createTokenEndpointRestTemplate() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(TOKEN_CONNECT_TIMEOUT);
		requestFactory.setReadTimeout(TOKEN_READ_TIMEOUT);
		return new RestTemplate(requestFactory);
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
		// 处理自定义退出事件，保存相关日志
		SpringContextHolderUtil.publishEvent(new LogoutSuccessEvent(new PreAuthenticatedAuthenticationToken(
				authorization.getPrincipalName(), authorization.getRegisteredClientId())));
		// 清空用户缓存信息

		Authentication authentication =
				authorization.getAttribute(Principal.class.getName());
		LoginUserBO loginUser = authentication == null
				? null
				: SecurityUtils.getLoginUser(authentication);
		if (loginUser != null && loginUser.getAccountId() != null) {
			UserCacheUtil.clearSysAccountInfoCache(loginUser.getAccountId());
		}
		authorizationService.remove(authorization);
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
		SysAccountBO accountCache = UserCacheUtil.getAccountInfoCache(UserCacheUtil.getAccountId());
		accountBO.setUserId(accountCache.getUserId());
		accountBO.setScopeRootId(accountCache.getScopeRootId());
		accountBO.setScopeOrgId(accountCache.getScopeOrgId());
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
		if(ObjectUtil.isEmpty(tenantClient)){
			throw AuthExceptionEnum.AUTH_CLIENT_INVALID.getBaseException();
		}
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
	 * 功能描述:
	 * 〈根据客户端ID获取授权配置〉
	 * @param clientId clientId
	 * @return 正常返回:{@link SysAccountBO}
	 * @author 蝉鸣
	 */
	public AuthConfig getAuthConfig(String agentId,String clientId) {
		//获取数据库配置的客户端信息
		List<TenantClient> tenantClients = tenantClientService.lambdaQuery()
				.eq(TenantClient::getAgentId,agentId)
				.eq(TenantClient::getClientId,clientId)
				.list();
		if(CollUtil.isEmpty(tenantClients)){
			throw AuthExceptionEnum.AUTH_CLIENT_INVALID.getBaseException();
		}
		TenantClient tenantClient = CollUtil.getFirst(tenantClients);
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


	/**
	 * 根据具体授权来源，获取授权请求工具类
	 * @param clientDTO clientDTO
	 * @return AuthRequest
	 */
	public Object getWxWorkTicket(AuthClientDTO clientDTO) {
		//获取配置
		AuthConfig authConfig = getAuthConfig(clientDTO.getAgentId(), clientDTO.getClientId());
		//获取token
		String token = wxWorkAppService.getToken(authConfig.getClientId(), authConfig.getClientSecret());
		//获取ticket
		return wxWorkAppService.getJsapiTicket(token,clientDTO.getTicketType());
	}

	/**
	 * 获取企业微信签名
	 * @param clientDTO clientDTO
	 * @return AuthRequest
	 */
	public Object getWxWorkSign(AuthClientDTO clientDTO) {
		//获取配置
		AuthConfig authConfig = getAuthConfig(clientDTO.getAgentId(), clientDTO.getClientId());
		//获取token
		String token = wxWorkAppService.getToken(authConfig.getClientId(), authConfig.getClientSecret());
		//获取ticket
		Object jsapiTicket = wxWorkAppService.getJsapiTicket(token,clientDTO.getTicketType());
		if(ObjectUtil.isEmpty(jsapiTicket)){
			return jsapiTicket;
		}
		JSONObject jsonObject = JSONUtil.parseObj(jsapiTicket);
		String ticket = StrUtil.EMPTY;
		if(jsonObject.containsKey("ticket")){
			ticket = jsonObject.get("ticket").toString();
		}
		return wxWorkAppService.getJsapiSign(ticket,clientDTO.getUrl());

	}

}
