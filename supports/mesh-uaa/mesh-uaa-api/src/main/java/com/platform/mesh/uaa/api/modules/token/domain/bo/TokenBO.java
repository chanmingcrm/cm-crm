package com.platform.mesh.uaa.api.modules.token.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @description 令牌管理DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "令牌管理DTO")
public class TokenBO extends BaseBO {

	/**
	 * 用户名
	 */
	@Schema(description = "用户名")
	private String username;

	/**
	 * 当前页
	 */
	@Schema(description = "当前页")
	private Integer current;

	/**
	 * 页数
	 */
	@Schema(description = "页数")
	private Integer pageSize;

}
