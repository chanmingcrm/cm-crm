package com.platform.mesh.uaa.biz.modules.client.api;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.uaa.biz.modules.client.domain.po.Oauth2RegisteredClient;
import com.platform.mesh.uaa.biz.modules.client.service.IOauth2RegisteredClientService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description 终端配置 信息操作处理
 * @author 蝉鸣
 */
@Hidden
@RestController
public class Oauth2RegisteredClientApi extends BaseController {

	@Autowired
	private IOauth2RegisteredClientService oauth2RegisteredClientService;

	/**
	 * 功能描述:
	 * 〈获取终端配置详细信息〉
	 * @param clientId 终端ID
	 * @return 正常返回:{@link Result<Oauth2RegisteredClient>}
	 * @author 蝉鸣
	 */
	@AuthIgnore
	@GetMapping(value = "/api/client/{clientId}")
	public Result<Oauth2RegisteredClient> getClientDetailsById(@PathVariable("clientId") String clientId) {
		return Result.success(oauth2RegisteredClientService.selectSysClientDetailsById(clientId));
	}

}
