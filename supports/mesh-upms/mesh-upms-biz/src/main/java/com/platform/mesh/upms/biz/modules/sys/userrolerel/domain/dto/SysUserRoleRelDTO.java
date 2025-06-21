package com.platform.mesh.upms.biz.modules.sys.userrolerel.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description sys_user实体
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户角色关系DTO")
public class SysUserRoleRelDTO extends BaseDTO {

	/**
	 * 用户ID
	 */
	@Schema(description = "用户ID")
	private Long userId;
	/**
	 * 角色ID
	 */
	@Schema(description = "角色ID")
	private List<Long> roleIds;

}