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
	 * 每页显示记录数
	 */
	@Schema(description = "每页显示记录数")
	private List<Long> conditions;

	/**
	 * 条件
	 */
	@Schema(description = "条件")
	private List<CondDTO> condDTO;

}
