package com.platform.mesh.uaa.biz.modules.authorization.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "客户端分页DTO")
public class AuthorizationPageDTO extends PageDTO {

	/**
	 * 授权签发时间
	 */
	@Schema(description = "授权签发时间")
	private LocalDateTime authorizationIssuedAt;

	/**
	 * 授权秘钥过期时间
	 */
	@Schema(description = "授权秘钥过期时间")
	private LocalDateTime authorizationExpiresAt;

	/**
	 * 用户主名称
	 */
	@Schema(description = "用户主名称")
	private String principalName;

}