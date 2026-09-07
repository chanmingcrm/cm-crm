package com.platform.mesh.upms.api.modules.sys.user.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 角色BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "角色BO")
public class SysRoleBO extends BaseBO {

	/**
	 * 角色自增ID
	 */
	@Schema(description = "角色自增ID")
	private Long id;

	/**
	 * 角色名
	 */
	@Schema(description = "角色名")
	private String roleName;

	/**
	 * (激活状态)
	 */
	@SchemaEnum(value=YesOrNoEnum.class, description = "激活状态")
	private Integer actFlag;

	/**
	 * (初始状态)
	 */
	@SchemaEnum(value=YesOrNoEnum.class, description = "初始状态")
	private Integer initFlag;

}
