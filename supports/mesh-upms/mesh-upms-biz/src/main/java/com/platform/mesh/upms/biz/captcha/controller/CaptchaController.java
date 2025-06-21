package com.platform.mesh.upms.biz.captcha.controller;

import cloud.tianai.captcha.application.ImageCaptchaApplication;
import cloud.tianai.captcha.application.vo.CaptchaResponse;
import cloud.tianai.captcha.application.vo.ImageCaptchaVO;
import cloud.tianai.captcha.common.response.ApiResponse;
import com.platform.mesh.upms.biz.captcha.domain.dto.CaptchaCheckDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;


/**
 * @description 验证码控制器
 * @author 蝉鸣
 */
@Tag(description = "CaptchaController", name = "验证码控制器")
@RestController
public class CaptchaController {

	@Autowired
	private ImageCaptchaApplication application;

	@Operation(summary = "生成验证码")
	@PostMapping("/captcha/gen")
	public CaptchaResponse<ImageCaptchaVO> genCaptcha(@RequestParam(value = "type", required = false)String type) {
		// 1.生成验证码(该数据返回给前端用于展示验证码数据)
		// 参数1为具体的验证码类型， 默认支持 SLIDER、ROTATE、WORD_IMAGE_CLICK、CONCAT 等验证码类型，详见： `CaptchaTypeConstant`类
        return application.generateCaptcha(type);
	}

	@Operation(summary = "校验验证码")
	@PostMapping("/captcha/check")
	public ApiResponse<?> checkCaptcha(@RequestBody CaptchaCheckDTO captchaCheckDTO) {
		ApiResponse<?> response = application.matching(captchaCheckDTO.getId(), captchaCheckDTO.getData());
		if (response.isSuccess()) {
			// 验证码验证成功，此处应该进行自定义业务处理， 或者返回验证token进行二次验证等。
			ApiResponse<Map<String, String>> validToken = ApiResponse.ofSuccess(Collections.singletonMap("validToken", captchaCheckDTO.getId()));
		}
		return response;
	}


}
