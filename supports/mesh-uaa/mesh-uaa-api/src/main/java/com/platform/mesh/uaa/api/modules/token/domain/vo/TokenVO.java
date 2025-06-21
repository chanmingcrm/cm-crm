package com.platform.mesh.uaa.api.modules.token.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @description 令牌管理VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "令牌管理DTO")
public class TokenVO extends BaseVO {

	/**
	 * id
	 */
	@Schema(description = "id")
	private String id;

	/**
	 * 用户ID
	 */
	@Schema(description = "用户ID")
	private Long userId;

	/**
	 * 客户端ID
	 */
	@Schema(description = "客户端ID")
	private String clientId;

	/**
	 * 用户名
	 */
	@Schema(description = "用户名")
	private String username;

	/**
	 * accessToken
	 */
	@Schema(description = "accessToken")
	private String accessToken;

	/**
	 * refreshToken
	 */
	@Schema(description = "refreshToken")
	private String refreshToken;

	/**
	 * 授权类型
	 */
	@Schema(description = "授权类型")
	private String tokenType;

	/**
	 * 授权范围
	 */
	@Schema(description = "授权范围")
	private String scope;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private String issuedAt;

	/**
	 * 过期时间
	 */
	@Schema(description = "过期时间")
	private String expiresAt;

}