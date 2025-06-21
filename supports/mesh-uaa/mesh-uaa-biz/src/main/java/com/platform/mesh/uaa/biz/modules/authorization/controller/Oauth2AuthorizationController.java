package com.platform.mesh.uaa.biz.modules.authorization.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.security.domain.bo.Oauth2AuthorizationBO;
import com.platform.mesh.uaa.biz.modules.authorization.domain.dto.AuthorizationPageDTO;
import com.platform.mesh.uaa.biz.modules.authorization.domain.po.Oauth2Authorization;
import com.platform.mesh.uaa.biz.modules.authorization.service.IOauth2AuthorizationService;
import com.platform.mesh.uaa.biz.modules.client.domain.vo.ClientVO;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description 终端配置 信息操作处理
 * @author 蝉鸣
 */
@Hidden
@RestController
public class Oauth2AuthorizationController extends BaseController {

	@Autowired
	private IOauth2AuthorizationService oauth2AuthorizationService;

	/**
	 * 功能描述:
	 * 〈获取终端配置详细信息〉
	 * @param authorizationPageDTO authorizationPageDTO
	 * @return 正常返回:{@link Result<MPage<Oauth2Authorization>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "查询终端配置分页")
	@GetMapping(value = "/authorization/page")
	public Result<MPage<Oauth2Authorization>> selectPage(@RequestBody AuthorizationPageDTO authorizationPageDTO) {
		return Result.success(oauth2AuthorizationService.selectPage(authorizationPageDTO));
	}

	/**
	 * 功能描述:
	 * 〈获取终端配置详细信息〉
	 * @param authorizationId 授权ID
	 * @return 正常返回:{@link Result<ClientVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "查询终端配置信息")
	@GetMapping(value = "/authorization/get/{authorizationId}")
	public Result<ClientVO> getClientById(@PathVariable("authorizationId") String authorizationId) {
		ClientVO clientVO = BeanUtil.copyProperties(oauth2AuthorizationService.getById(authorizationId), ClientVO.class);
		return Result.success(clientVO);
	}

	/**
	 * 功能描述:
	 * 〈修改终端配置〉
	 * @param oauth2AuthorizationBO oauth2AuthorizationBO
	 * @return 正常返回:{@link Result<ClientVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "修改终端配置")
	@PostMapping(value = "/authorization/edit")
	public Result<ClientVO> editClient(@RequestBody Oauth2AuthorizationBO oauth2AuthorizationBO) {
		ClientVO clientVO = BeanUtil.copyProperties(oauth2AuthorizationService.editAuthorization(oauth2AuthorizationBO), ClientVO.class);
		return Result.success(clientVO);
	}
}
