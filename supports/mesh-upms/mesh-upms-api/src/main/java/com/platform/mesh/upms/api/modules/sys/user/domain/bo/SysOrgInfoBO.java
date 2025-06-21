package com.platform.mesh.upms.api.modules.sys.user.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @description 组织信息缓存
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "组织信息缓存BO")
public class SysOrgInfoBO extends BaseBO {

	/**
	 * 组织ID
	 */
	@Schema(description = "组织ID")
	private Long levelId;

	/**
	 * 组织名称
	 */
	@Schema(description = "组织名称")
	private String levelName;

	/**
	 * 同级组织Ids
	 */
	@Schema(description = "同级组织Ids")
	private List<Long> sameLevelIds;

	/**
	 * 下级组织Ids
	 */
	@Schema(description = "下级组织Ids")
	private List<Long> subLevelIds;

	/**
	 * 所有子级组织Ids
	 */
	@Schema(description = "所有子级组织Ids")
	private List<Long> allChildrenIds;
}
