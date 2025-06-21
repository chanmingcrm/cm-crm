package com.platform.mesh.upms.biz.sms.domain.dto;

import com.platform.mesh.core.enums.custom.SmsFlagEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @description
 * @author 蝉鸣
 */
@Data
@Schema(description = "添加账号DTO")
public class SmsSendDTO {


	/**
	 * 手机号码
	 */
	@Schema(description = "手机号码")
	private String phone;

	/**
	 * 短信类型
	 */
	@SchemaEnum(value = SmsFlagEnum.class,description = "短信类型")
	private Integer smsFlag;


}