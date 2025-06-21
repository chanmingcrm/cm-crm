package com.platform.mesh.uaa.biz.auth.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.platform.mesh.security.utils.OAuth2AuthorizationUtils;
import com.platform.mesh.security.utils.OAuth2ErrorCodesExpand;
import com.platform.mesh.uaa.biz.auth.domain.dto.AuthCallbackDTO;
import com.platform.mesh.uaa.biz.auth.domain.dto.AuthRenderDTO;
import com.platform.mesh.uaa.biz.auth.service.ITokenService;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.utils.format.FormatUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.core.http.converter.OAuth2ErrorHttpMessageConverter;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description 统一登录管理
 * @author 蝉鸣
 */
@Tag(description = "TokenController", name = "统一登录管理")
@RestController
public class TokenController {

	private final HttpMessageConverter<OAuth2AccessTokenResponse> accessTokenHttpResponseConverter = new OAuth2AccessTokenResponseHttpMessageConverter();

	private final HttpMessageConverter<OAuth2Error> errorHttpResponseConverter = new OAuth2ErrorHttpMessageConverter();

	@Autowired
	private OAuth2AuthorizationService authorizationService;

	@Autowired
	private ITokenService tokenService;

	/**
	 * 功能描述:
	 * 〈认证页面〉
	 *  （localhost:8888/oauth2/authorize?response_type=code&client_id=my_client&scope=server&redirect_uri=http://www.baidu.cn）
	 * @param modelAndView modelAndView
	 * @param error 表单登录失败处理回调的错误信息
	 * @return 正常返回:{@link ModelAndView}
	 * @author 蝉鸣
	 */
	@Operation(summary = "认证页面")
	@GetMapping("/token/login")
	public ModelAndView require(ModelAndView modelAndView, @RequestParam(name = "error",required = false) String error) {
		return tokenService.loginPage(modelAndView,error);
	}

	/**
	 * 功能描述:
	 * 〈确认页面〉
	 * @param principal principal
	 * @param modelAndView modelAndView
	 * @param clientId clientId
	 * @param scope scope
	 * @param state state
	 * @return 正常返回:{@link ModelAndView}
	 * @author 蝉鸣
	 */
	@Operation(summary = "确认页面")
	@GetMapping("/token/confirm_access")
	public ModelAndView confirm(Principal principal, ModelAndView modelAndView,
			@RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
			@RequestParam(OAuth2ParameterNames.SCOPE) String scope,
			@RequestParam(OAuth2ParameterNames.STATE) String state) {

		return tokenService.confirmPage(modelAndView,principal,clientId,scope,state);
	}

	/**
	 * 功能描述:
	 * 〈获取登录token〉
	 * 	目的，Oauth2 直接返回access_token等信息不符合系统统一返回Result风格，特此添加一步，调用封装
	 * 	获取登录token,暂支持密码登录
	 * @param map map
	 * @param request request
	 * @return 正常返回:{@link Result}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取登录token")
	@PostMapping("/token/login")
	public Result<JSONObject> login(@RequestBody Map<String,Object> map, HttpServletRequest request) {
		//HttpServletRequest request,HttpServletResponse response 不向下传递
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		JSONObject accessToken = tokenService.getToken(map,authorization);
		if(ObjectUtil.isEmpty(accessToken) || ObjectUtil.isEmpty(accessToken.get(OAuth2ParameterNames.ACCESS_TOKEN))){
			return Result.error();
		}
		//将返回值的下划线改为驼峰-给前端用
		JSONObject camelCaseJson = FormatUtil.convertKeysToCamelCase(accessToken);
		return Result.success(camelCaseJson);
	}

	/**
	 * 功能描述:
	 * 〈退出并删除token〉
	 * @param authHeader authHeader
	 * @return 正常返回:{@link Result}
	 * @author 蝉鸣
	 */
	@Operation(summary = "退出并删除token")
	@DeleteMapping("/token/logout")
	public Result<Boolean> logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
		if (StrUtil.isBlank(authHeader)) {
			return Result.success();
		}
		String tokenValue = authHeader.replace(OAuth2AccessToken.TokenType.BEARER.getValue(), StrUtil.EMPTY).trim();
		return removeToken(tokenValue);
	}

	/**
	 * 功能描述:
	 * 〈校验token〉
	 * @param token token
	 * @param response response
	 * @author 蝉鸣
	 */
	@Operation(summary = "校验token")
	@GetMapping("/token/check_token")
	public void checkToken(String token, HttpServletResponse response) {
		//HttpServletRequest request,HttpServletResponse response 不向下传递
		try {
			ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);

			if (StrUtil.isBlank(token)) {
				httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
				this.errorHttpResponseConverter.write(new OAuth2Error(OAuth2ErrorCodesExpand.TOKEN_MISSING), null,
						httpResponse);
			}
			OAuth2Authorization authorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);

			// 如果令牌不存在 返回401
			if (authorization == null) {
				httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
				this.errorHttpResponseConverter.write(new OAuth2Error(OAuth2ErrorCodesExpand.TOKEN_MISSING), null,
						httpResponse);
			}

            assert authorization != null;
            Map<String, Object> claims = authorization.getAccessToken().getClaims();
			OAuth2AccessTokenResponse sendAccessTokenResponse = OAuth2AuthorizationUtils
					.sendAccessTokenResponse(authorization, claims);
			this.accessTokenHttpResponseConverter.write(sendAccessTokenResponse, MediaType.APPLICATION_JSON,
					httpResponse);
		}
		catch (Exception e) {
			throw new RuntimeException("返回信息错误");
		}

	}

	/**
	 * 功能描述:
	 * 〈删除token〉
	 * @param token token
	 * @return 正常返回:{@link Result<Boolean>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "删除token")
	@DeleteMapping("/token/{token}")
	public Result<Boolean> removeToken(@PathVariable("token") String token) {
		return Result.success(tokenService.removeToken(token));
	}

	/**
	 * 功能描述:
	 * 〈第三方登录授权页面渲染〉
	 * @param renderDTO renderDTO
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取登录渲染")
	@PostMapping("/token/render")
	public void renderAuth(@RequestBody AuthRenderDTO renderDTO, HttpServletResponse response) throws IOException {
		String authorizeUrl = tokenService.renderAuth(renderDTO);
		response.sendRedirect(authorizeUrl);
	}

	/**
	 * 功能描述:
	 * 〈第三方登录账号与系统账户绑定〉
	 * @param callbackDTO callbackDTO
	 * @return 正常返回:{@link Result<SysAccountBO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取登录渲染")
	@PostMapping("/third/bind/account")
	public Result<SysAccountBO> bindAccount(@RequestBody AuthCallbackDTO callbackDTO){
		return Result.success(tokenService.bindAccount(callbackDTO));
	}
}
