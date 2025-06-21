package com.platform.mesh.core.application.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 排序DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="排序DTO")
public class SortDTO extends BaseDTO {

	/**
	 * ES 使用排序类型
	 */
	@Schema(description = "ES 使用排序类型")
	private String kind;

	/**
	 * 排序列
	 */
	@Schema(description = "排序列")
	private String sortColumn;

	/**
	 * 排序的方向 "desc" 或者 "asc".
	 */
	@Schema(description = "排序的方向 desc 或者 asc .")
	private Boolean isAsc = true;

}
