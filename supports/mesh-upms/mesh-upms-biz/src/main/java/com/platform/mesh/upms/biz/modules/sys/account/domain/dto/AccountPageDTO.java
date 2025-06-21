package com.platform.mesh.upms.biz.modules.sys.account.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @description
 * @author 蝉鸣
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountPageDTO extends PageDTO {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * delFlag
	 */
	@Schema(description = "delFlag")
	private Integer delFlag;

	/**
	 * userId
	 */
	@Schema(description = "userId")
	private Long userId;

	/**
	 * userName
	 */
	@Schema(description = "userName")
	private String userName;

	/**
	 * nickName
	 */
	@Schema(description = "nickName")
	private String nickName;

}