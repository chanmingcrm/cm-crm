package com.platform.mesh.uaa.api.modules.client.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * @description 终端配置表 sys_oauth_client_details
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "终端配置表")
public class Oauth2RegisteredClientBO extends BaseBO {

	/**
	 * 终端编号
	 */
	@Schema(description = "终端编号")
	private String clientId;

	/**
	 * 资源ID标识
	 */
	@Schema(description = "资源ID标识")
	private String resourceIds;

	/**
	 * 终端安全码
	 */
	@Schema(description = "终端安全码")
	private String clientSecret;

	/**
	 * 终端授权范围
	 */
	@Schema(description = "终端授权范围")
	private String scope;

	/**
	 * 终端授权类型
	 */
	@Schema(description = "终端授权类型")
	private String authorizedGrantTypes;

	/**
	 * 服务器回调地址
	 */
	@Schema(description = "服务器回调地址")
	private String webServerRedirectUri;

	/**
	 * 访问资源所需权限
	 */
	@Schema(description = "访问资源所需权限")
	private String authorities;

	/**
	 * 设定终端的access_token的有效时间值（秒）
	 */
	@Schema(description = "设定终端的access_token的有效时间值（秒）")
	private Integer accessTokenValidity;

	/**
	 * 设定终端的refresh_token的有效时间值（秒）
	 */
	@Schema(description = "设定终端的refresh_token的有效时间值（秒）")
	private Integer refreshTokenValidity;

	/**
	 * 附加信息
	 */
	@Schema(description = "附加信息")
	private String additionalInformation;

	/**
	 * 是否登录时跳过授权
	 */
	@Schema(description = "是否登录时跳过授权")
	private String autoapprove;

}