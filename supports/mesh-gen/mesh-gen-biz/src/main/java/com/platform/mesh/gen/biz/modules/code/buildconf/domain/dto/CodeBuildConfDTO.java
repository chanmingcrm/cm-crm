package com.platform.mesh.gen.biz.modules.code.buildconf.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "生成配置")
public class CodeBuildConfDTO extends BaseDTO {

	@Schema(description = "ID")
	private Long id;

	/**
	 * 配置名称
	 */
	@Schema(description = "配置名称")
	private String confName;

	/**
	 * 配置编码
	 */
	@Schema(description = "配置编码")
	private String confCode;

	/**
	 * 配置描述
	 */
	@Schema(description = "配置描述")
	private String confDesc;
}
