package com.platform.mesh.gen.biz.modules.code.build.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "生成表")
public class CodeBuildVO extends BaseVO {

	/**
	 * id
	 */
	@Schema(description = "构造ID")
	private Long id;

	/**
	 * 构造名称
	 */
	@Schema(description = "构造名称")
	private String buildName;
}
