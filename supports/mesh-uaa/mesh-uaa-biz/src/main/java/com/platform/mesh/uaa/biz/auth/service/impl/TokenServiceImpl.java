package com.platform.mesh.uaa.biz.auth.service.impl;

import cn.hutool.json.JSONObject;
import com.platform.mesh.uaa.api.constants.UaaParamsConstant;
import com.platform.mesh.uaa.biz.auth.domain.dto.AuthCallbackDTO;
import com.platform.mesh.uaa.biz.auth.domain.dto.AuthRenderDTO;
import com.platform.mesh.uaa.biz.auth.service.ITokenService;
import com.platform.mesh.uaa.biz.auth.service.manual.TokenServiceManual;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;

/**
 * @description 自定义获取第三方code换信息
 * @author 蝉鸣
 */
@Service
public class TokenServiceImpl implements ITokenService {


	@Autowired
	private TokenServiceManual tokenServiceManual;

	/**
	 * 功能描述:
	 * 〈获取封装方法〉
	 * @return 正常返回:{@link TokenServiceManual}
	 * @author 蝉鸣
	 */
	public TokenServiceManual getServiceManual(){
		return tokenServiceManual;
	};

	/**
	 * 功能描述:
	 * 〈认证页面〉
	 * @param modelAndView modelAndView
	 * @param error error
	 * @return 正常返回:{@link ModelAndView}
	 * @author 蝉鸣
	 */
	@Override
	public ModelAndView loginPage(ModelAndView modelAndView,String error) {
		modelAndView.setViewName(UaaParamsConstant.LOGIN);
		modelAndView.addObject(OAuth2ParameterNames.ERROR, error);
		return modelAndView;
	}

	/**
	 * 功能描述:
	 * 〈确认页面〉
	 * @param modelAndView modelAndView
	 * @param principal principal
	 * @param clientId clientId
	 * @param scope scope
	 * @param state state
	 * @return 正常返回:{@link ModelAndView}
	 * @author 蝉鸣
	 */
	@Override
	public ModelAndView confirmPage(ModelAndView modelAndView, Principal principal, String clientId, String scope, String state) {
		tokenServiceManual.paddingConfirmPage(modelAndView,clientId);
		modelAndView.addObject(OAuth2ParameterNames.CLIENT_ID, clientId);
		modelAndView.addObject(OAuth2ParameterNames.STATE, state);
		modelAndView.addObject(UaaParamsConstant.PRINCIPAL_NAME, principal.getName());
		modelAndView.setViewName(UaaParamsConstant.CONFIRM);
		return modelAndView;
	}

	/**
	 * 功能描述:
	 * 〈获取登录token〉
	 * @param map map
	 * @param authorization authorization
	 * @return 正常返回:{@link JSONObject}
	 * @author 蝉鸣
	 */
	@Override
	public JSONObject getToken(Map<String, Object> map, String authorization) {
		return tokenServiceManual.getSysPasswordTypeToken(map,authorization);
	}

	/**
	 * 功能描述:
	 * 〈删除token〉
	 * @param token token
	 * @return 正常返回:{@link Boolean}
	 * @author 蝉鸣
	 */
	@Override
	public Boolean removeToken(String token) {
		return tokenServiceManual.removeToken(token);
	}

	/**
	 * 功能描述:
	 * 〈第三方登录授权页面渲染地址〉
	 * @param renderDTO renderDTO
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	@Override
	public String renderAuth(AuthRenderDTO renderDTO) {
		return tokenServiceManual.renderAuth(renderDTO);
	}

	/**
	 * 功能描述:
	 * 〈第三方登录账号与系统账户绑定〉
	 * @param callbackDTO callbackDTO
	 * @return 正常返回:{@link SysAccountBO}
	 * @author 蝉鸣
	 */
	@Override
	public SysAccountBO bindAccount(AuthCallbackDTO callbackDTO) {
		return tokenServiceManual.bindAccount(callbackDTO);
	}
}