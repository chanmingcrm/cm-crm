package com.platform.mesh.uaa.biz.modules.client.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.uaa.biz.modules.client.domain.dto.ClientAddDTO;
import com.platform.mesh.uaa.biz.modules.client.domain.dto.ClientEditDTO;
import com.platform.mesh.uaa.biz.modules.client.domain.dto.ClientPageDTO;
import com.platform.mesh.uaa.biz.modules.client.domain.po.Oauth2RegisteredClient;
import com.platform.mesh.uaa.biz.modules.client.domain.vo.ClientVO;
import com.platform.mesh.uaa.biz.modules.client.service.IOauth2RegisteredClientService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description Oauth2注册授权客户端基础 信息操作处理
 * @author 蝉鸣
 */
//@Hidden
@RestController
@Tag(description = "Oauth2RegisteredClientController", name = "Oauth2注册授权客户端基础")
public class Oauth2RegisteredClientController extends BaseController {

	@Autowired
	private IOauth2RegisteredClientService oauth2RegisteredClientService;

	/**
	 * 功能描述:
	 * 〈获取终端配置详细信息〉
	 * @param clientPageDTO clientPageDTO
	 * @return 正常返回:{@link Result<MPage<Oauth2RegisteredClient>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "查询终端配置分页")
	@PostMapping(value = "/client/page")
	public Result<MPage<Oauth2RegisteredClient>> selectPage(@RequestBody ClientPageDTO clientPageDTO) {
		return Result.success(oauth2RegisteredClientService.selectPage(clientPageDTO));
	}

	/**
	 * 功能描述:
	 * 〈获取终端配置详细信息〉
	 * @param clientId 终端ID
	 * @return 正常返回:{@link Result<ClientVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "查询终端配置信息")
	@GetMapping(value = "/client/get/{clientId}")
	public Result<ClientVO> getClientById(@PathVariable("clientId") String clientId) {
		ClientVO clientVO = BeanUtil.copyProperties(oauth2RegisteredClientService.selectSysClientDetailsById(clientId), ClientVO.class);
		return Result.success(clientVO);
	}

	/**
	 * 功能描述:
	 * 〈新增终端配置〉
	 * @param clientAddDTO clientAddDTO
	 * @return 正常返回:{@link Result<ClientVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "新增终端配置")
	@PostMapping(value = "/client/add")
	public Result<ClientVO> addClient(@RequestBody ClientAddDTO clientAddDTO) {
		ClientVO clientVO = BeanUtil.copyProperties(oauth2RegisteredClientService.addClient(clientAddDTO), ClientVO.class);
		return Result.success(clientVO);
	}


	/**
	 * 功能描述:
	 * 〈修改终端配置〉
	 * @param clientEditDTO clientEditDTO
	 * @return 正常返回:{@link Result<ClientVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "修改终端配置")
	@PostMapping(value = "/client/edit")
	public Result<ClientVO> editClient(@RequestBody ClientEditDTO clientEditDTO) {
		ClientVO clientVO = BeanUtil.copyProperties(oauth2RegisteredClientService.editClient(clientEditDTO), ClientVO.class);
		return Result.success(clientVO);
	}

	/**
	 * 功能描述:
	 * 〈删除终端配置〉
	 * @param clientId clientId
	 * @return 正常返回:{@link Result<Boolean>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "删除终端配置")
	@DeleteMapping(value = "/client/delete/{clientId}")
	public Result<Boolean> deleteClient(@PathVariable("clientId") String clientId) {
		return Result.success(oauth2RegisteredClientService.deleteClient(clientId));
	}
}
