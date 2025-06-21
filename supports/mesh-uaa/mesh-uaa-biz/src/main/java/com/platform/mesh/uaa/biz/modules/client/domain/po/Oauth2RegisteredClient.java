package com.platform.mesh.uaa.biz.modules.client.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.po.BasePO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description 终端配置表 sys_oauth_client_details
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "oauth2_registered_client", autoResultMap = true)
public class Oauth2RegisteredClient extends BasePO {

	/**
	 * 主键Id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 客户端Id
	 */
	private String clientId;

	/**
	 * 客户端Id签发时间
	 */
	private LocalDateTime clientIdIssuedAt;

	/**
	 * 客户端秘钥
	 */
	private String clientSecret;

	/**
	 * 客户端秘钥过期时间
	 */
	private LocalDateTime clientSecretExpiresAt;

	/**
	 * 客户端名称
	 */
	private String clientName;

	/**
	 * 客户端头像
	 */
	private String clientProfile;

	/**
	 * 客户端认证方式
	 */
	private String clientAuthenticationMethods;

	/**
	 * 客户端拥有的授权方式
	 */
	private String authorizationGrantTypes;

	/**
	 * 回调地址
	 */
	private String redirectUris;

	/**
	 * 授权范围
	 */
	private String scopes;

	/**
	 * 客户端设置
	 */
	private String clientSettings;

	/**
	 * 通过该客户端授权的范围
	 */
	private String tokenSettings;

	/**
	 * 是否删除
	 */
	private Integer deleted;
}