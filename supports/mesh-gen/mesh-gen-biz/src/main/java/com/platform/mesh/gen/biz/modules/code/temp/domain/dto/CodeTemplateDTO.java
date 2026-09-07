package com.platform.mesh.gen.biz.modules.code.temp.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 代码生成模板数据库对象
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "模板表")
public class CodeTemplateDTO extends BaseDTO {

	@Schema(description = "ID")
	private Long id;

	/**
	 * 模板名称
	 */
	@Schema(description = "模板名称")
	private String templateName;

	/**
	 * 模板描述
	 */
	@Schema(description = "模板描述")
	private String templateDesc;

	/**
	 * 模板代码
	 */
	@Schema(description = "模板代码")
	private String templateCode;

	/**
	 * 模板路径
	 */
	@Schema(description = "模板路径")
	private String buildPath;
}
