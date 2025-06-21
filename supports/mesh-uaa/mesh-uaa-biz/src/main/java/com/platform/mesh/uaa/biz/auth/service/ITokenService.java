package com.platform.mesh.uaa.biz.auth.service;

import cn.hutool.json.JSONObject;
import com.platform.mesh.uaa.biz.auth.domain.dto.AuthCallbackDTO;
import com.platform.mesh.uaa.biz.auth.domain.dto.AuthRenderDTO;
import com.platform.mesh.uaa.biz.auth.service.manual.TokenServiceManual;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;

public interface ITokenService {

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link TokenServiceManual}
     * @author 蝉鸣
     */
    TokenServiceManual getServiceManual();

    /**
     * 功能描述:
     * 〈认证页面〉
     * @param modelAndView modelAndView
     * @param error error
     * @return 正常返回:{@link ModelAndView}
     * @author 蝉鸣
     */
    ModelAndView loginPage(ModelAndView modelAndView,String error);

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
    ModelAndView confirmPage(ModelAndView modelAndView, Principal principal, String clientId, String scope, String state);

    /**
     * 功能描述:
     * 〈获取登录token〉
     * @param map map
     * @param authorization authorization
     * @return 正常返回:{@link JSONObject}
     * @author 蝉鸣
     */
    JSONObject getToken(Map<String, Object> map, String authorization);

    /**
     * 功能描述:
     * 〈删除token〉
     * @param token token
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean removeToken(String token);

    /**
     * 功能描述:
     * 〈第三方登录授权页面渲染地址〉
     * @param renderDTO renderDTO
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    String renderAuth(AuthRenderDTO renderDTO);

    /**
     * 功能描述:
     * 〈第三方登录账号与系统账户绑定〉
     * @param callbackDTO callbackDTO
     * @return 正常返回:{@link SysAccountBO}
     * @author 蝉鸣
     */
    SysAccountBO bindAccount(AuthCallbackDTO callbackDTO);
}
