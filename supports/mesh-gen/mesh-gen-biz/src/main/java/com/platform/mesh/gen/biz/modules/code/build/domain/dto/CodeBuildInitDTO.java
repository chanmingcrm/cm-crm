package com.platform.mesh.gen.biz.modules.code.build.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "生成初始化表")
public class CodeBuildInitDTO extends BaseDTO {

	/**
	 * 数据源ID
	 */
	@Schema(description = "数据源ID")
	private Long dsId;

	/**
	 * 数据库空间
	 */
	@Schema(description = "数据库空间")
	private String tableSchema;

	/**
	 * 项目包名
	 */
	@Schema(description = "项目包名")
	private String packageName;

	/**
	 * 分组ID
	 */
	@Schema(description = "分组ID")
	private Long groupId;
}
