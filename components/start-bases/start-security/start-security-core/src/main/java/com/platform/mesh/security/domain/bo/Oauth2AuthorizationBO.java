package com.platform.mesh.security.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 令牌管理DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "令牌管理DTO")
public class Oauth2AuthorizationBO extends BaseBO {

	/**
	 * 授权ID
	 */
	@Schema(description = "授权ID")
	private String id;
	/**
	 * 注册的客户端 ID
	 */
	@Schema(description = "注册的客户端 ID")
	private String registeredClientId;
	/**
	 * 授权个体名称
	 */
	@Schema(description = "用户主名称")
	private String principalName;
	/**
	 * 授权类型
	 */
	@Schema(description = "授权类型")
	private String authorizationGrantType;
	/**
	 * 授权域
	 */
	@Schema(description = "授权域")
	private String authorizedScopes;
	/**
	 * 参数
	 */
	@Schema(description = "授权属性")
	private String attributes;
	/**
	 * 授权状态
	 */
	@Schema(description = "授权状态")
	private String state;
	/**
	 * 授权码值
	 */
	@Schema(description = "授权码值")
	private String authorizationCodeValue;
	/**
	 * 授权码发放时间
	 */
	@Schema(description = "授权码发放时间")
	private LocalDateTime authorizationCodeIssuedAt;
	/**
	 * 授权码过期时间
	 */
	@Schema(description = "授权码过期时间")
	private LocalDateTime authorizationCodeExpiresAt;
	/**
	 * 授权码元数据
	 */
	@Schema(description = "授权码元数据")
	private String authorizationCodeMetadata;
	/**
	 * 访问令牌值
	 */
	@Schema(description = "访问令牌值")
	private String accessTokenValue;
	/**
	 * 访问令牌发放时间
	 */
	@Schema(description = "访问令牌发放时间")
	private LocalDateTime accessTokenIssuedAt;
	/**
	 * 访问令牌发放时间
	 */
	@Schema(description = "访问令牌发放时间")
	private LocalDateTime accessTokenExpiresAt;
	/**
	 * 访问令牌元数据
	 */
	@Schema(description = "访问令牌元数据")
	private String accessTokenMetadata;
	/**
	 * 访问令牌类型
	 */
	@Schema(description = "访问令牌类型")
	private String accessTokenType;
	/**
	 * 访问令牌范围
	 */
	@Schema(description = "访问令牌范围")
	private String accessTokenScopes;
	/**
	 * OIDC ID 令牌值
	 */
	@Schema(description = "OIDC ID 令牌值")
	private String oidcIdTokenValue;
	/**
	 * OIDC ID 令牌发放时间
	 */
	@Schema(description = "OIDC ID 令牌发放时间")
	private LocalDateTime oidcIdTokenIssuedAt;
	/**
	 * OIDC ID 令牌过期时间
	 */
	@Schema(description = "OIDC ID 令牌过期时间")
	private LocalDateTime oidcIdTokenExpiresAt;
	/**
	 * OIDC ID 令牌元数据
	 */
	@Schema(description = "OIDC ID 令牌元数据")
	private String oidcIdTokenMetadata;
	/**
	 * 刷新令牌值
	 */
	@Schema(description = "刷新令牌值")
	private String refreshTokenValue;
	/**
	 * 刷新令牌发放时间
	 */
	@Schema(description = "刷新令牌发放时间")
	private LocalDateTime refreshTokenIssuedAt;
	/**
	 * 刷新令牌过期时间
	 */
	@Schema(description = "刷新令牌过期时间")
	private LocalDateTime refreshTokenExpiresAt;
	/**
	 * 刷新令牌元数据
	 */
	@Schema(description = "刷新令牌元数据")
	private String refreshTokenMetadata;
	/**
	 * 用户代码值
	 */
	@Schema(description = "用户代码值")
	private String userCodeValue;
	/**
	 * 用户代码发放时间
	 */
	@Schema(description = "用户代码发放时间")
	private LocalDateTime userCodeIssuedAt;
	/**
	 * 用户代码过期时间
	 */
	@Schema(description = "用户代码过期时间")
	private LocalDateTime userCodeExpiresAt;
	/**
	 * 用户代码元数据
	 */
	@Schema(description = "用户代码元数据")
	private String userCodeMetadata;
	/**
	 * 设备代码值
	 */
	@Schema(description = "设备代码值")
	private String deviceCodeValue;
	/**
	 * 设备代码发放时间
	 */
	@Schema(description = "设备代码发放时间")
	private LocalDateTime deviceCodeIssuedAt;
	/**
	 * 设备代码过期时间
	 */
	@Schema(description = "设备代码过期时间")
	private LocalDateTime deviceCodeExpiresAt;
	/**
	 * 设备代码元数据
	 */
	@Schema(description = "设备代码元数据")
	private String deviceCodeMetadata;

}
