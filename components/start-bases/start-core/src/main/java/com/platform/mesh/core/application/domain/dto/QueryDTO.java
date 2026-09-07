package com.platform.mesh.core.application.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 查询DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="查询DTO")
public class QueryDTO extends PageDTO {

	/**
	 * 是否忽略权限
	 */
	@Schema(description = "是否忽略权限", hidden = true)
	private Boolean ignoreScope = Boolean.FALSE;

	/**
	 * 数据权限
	 */
	@Schema(description = "数据权限")
	private Integer dataScope;

	/**
	 * 数据标识
	 */
	@Schema(description = "数据标识")
	private Integer dataFlag;

	/**
	 * 数据ID
	 */
	@Schema( description = "数据ID")
	private List<Long> dataIds;

	/**
	 * 条件
	 */
	@Schema(description = "条件")
	private List<CondDTO> condDTO;

}
