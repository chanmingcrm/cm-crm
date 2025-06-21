package com.platform.mesh.uaa.biz.modules.client.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
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
@Schema(description = "客户端修改DTO")
public class ClientEditDTO extends BaseDTO {

	/**
	 * 客户端Id
	 */
	@Schema(description = "客户端Id")
	private String clientId;

	/**
	 * 客户端Id签发时间
	 */
	@Schema(description = "客户端Id签发时间")
	private LocalDateTime clientIdIssuedAt;

	/**
	 * 客户端秘钥
	 */
	@Schema(description = "客户端秘钥")
	private String clientSecret;

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

	/**
	 * 客户端头像
	 */
	@Schema(description = "客户端头像")
	private String clientProfile;

	/**
	 * 客户端认证方式
	 */
	@Schema(description = "客户端认证方式")
	private String clientAuthenticationMethods;

	/**
	 * 客户端拥有的授权方式
	 */
	@Schema(description = "客户端拥有的授权方式")
	private String authorizationGrantTypes;

	/**
	 * 回调地址
	 */
	@Schema(description = "回调地址")
	private String redirectUris;

	/**
	 * 授权范围
	 */
	@Schema(description = "授权范围")
	private String scopes;

	/**
	 * 客户端设置
	 */
	@Schema(description = "客户端设置")
	private String clientSettings;

	/**
	 * 通过该客户端授权的范围
	 */
	@Schema(description = "通过该客户端授权的范围")
	private String tokenSettings;

}