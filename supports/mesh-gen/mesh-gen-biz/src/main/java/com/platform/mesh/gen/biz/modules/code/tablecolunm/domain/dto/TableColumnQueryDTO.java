package com.platform.mesh.gen.biz.modules.code.tablecolunm.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 代码生成模板数据库对象
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "表字段DTO")
public class TableColumnQueryDTO extends PageDTO {

	/**
	 * 数据源ID
	 */
	@Schema(description = "数据源ID")
	private Long dsId;

	/**
	 * 表名称
	 */
	@Schema(description = "表名称")
	private String tableSchema;

	/**
	 * 表名称
	 */
	@Schema(description = "表名称")
	private List<String> tableNames;

}
