package com.platform.mesh.upms.biz.modules.sys.userrolerel.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 人员角色关系VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户角色关系VO")
public class SysUserRoleRelVO extends BaseVO {

	/**
	 * 用户ID
	 */
	@Schema(description = "用户ID")
	private Long userId;

	/**
	 * 角色ID
	 */
	@Schema(description = "角色ID")
	private Long roleId;

	/**
	 * 角色名称
	 */
	@Schema(description = "角色名称")
	private String roleName;

}