package com.platform.mesh.gen.biz.modules.code.build.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "生成预览对象")
public class CodeBuildPreviewDTO extends BaseDTO {


	/**
	 * 构建ID
	 */
	@Schema(description = "构建ID")
	private Long buildId;

	/**
	 * 模板ID
	 */
	@Schema(description = "模板ID")
	private Long tempId;

	/**
	 * 表单ID
	 */
	@Schema(description = "表单ID")
	private Long tableId;
}
