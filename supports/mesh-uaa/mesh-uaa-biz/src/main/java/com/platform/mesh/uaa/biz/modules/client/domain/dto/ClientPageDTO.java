package com.platform.mesh.uaa.biz.modules.client.domain.dto;

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
public class ClientPageDTO extends PageDTO {

	/**
	 * 客户端Id签发时间
	 */
	@Schema(description = "客户端Id签发时间")
	private LocalDateTime clientIdIssuedAt;

	/**
	 * 客户端秘钥过期时间
	 */
	@Schema(description = "客户端秘钥过期时间")
	private LocalDateTime clientSecretExpiresAt;

	/**
	 * 客户端名称
	 */
	@Schema(description = "客户端名称")
	private String clientName;

}