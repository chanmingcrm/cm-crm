package com.platform.mesh.gen.biz.modules.code.build.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "生成表")
public class CodeBuildAddDTO extends BaseDTO {

	/**
	 * buildId
	 */
	@Schema(description = "构造ID")
	private Long id;

	/**
	 * 构造名称
	 */
	@Schema(description = "构造名称")
	private String buildName;
}
