package com.platform.mesh.gen.biz.modules.code.field.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "数据库字段映射")
public class CodeFieldMappingDTO extends BaseDTO {


	/**
	 * ID
	 */
	@Schema(description = "ID")
	private Long id;

	/**
	 * 属性包名
	 */
	@Schema(description = "属性包名")
	private String packageName;

	/**
	 * 字段类型
	 */
	@Schema(description = "字段类型")
	private String columnFlag;

	/**
	 * 属性类型
	 */
	@Schema(description = "属性类型")
	private String fieldFlag;
}
