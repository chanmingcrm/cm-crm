package com.platform.mesh.gen.biz.modules.code.build.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "生成表")
public class CodeBuildDTO extends BaseDTO {

	/**
	 * buildId
	 */
	@Schema(description = "构造ID")
	private Long id;

	/**
	 * 分组ID
	 */
	@Schema(description = "分组ID")
	private List<Long> groupIds;

	/**
	 * 数据表Id
	 */
	@Schema(description = "数据表Id")
	private List<Long> tableIds;
}
