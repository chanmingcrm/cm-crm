package com.platform.mesh.gen.biz.modules.code.buildconfdata.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 参数配置DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="参数配置DTO")
public class CodeBuildConfDataPageDTO extends PageDTO {

	/**
	 * 构造ID
	 */
	@Schema(description="构造ID")
	private Long buildId;

}
