package com.platform.mesh.upms.biz.sms.controller;

import com.platform.mesh.upms.biz.sms.domain.dto.SmsSendDTO;
import com.platform.mesh.upms.biz.sms.service.ISmsService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 约定当前controller 只引入当前service
 * @description 短信服务
 * @author 蝉鸣
 */
@Tag(description = "SmsController", name = "短信服务")
@RestController
public class SmsController {

	@Autowired
	private ISmsService smsService;

	/**
	 * 功能描述:
	 * 〈发送短信〉
	 * @return 正常返回:{@link Result<Boolean>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "发送短信")
	@PostMapping("/sms/send")
	public Result<Boolean> sendSmsCheckCode(@RequestBody SmsSendDTO sendDTO) {
		smsService.sendSmsCheckCode(sendDTO);
		return Result.success(Boolean.TRUE);
	}
}
