package com.platform.mesh.gen.biz.modules.code.table.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 数据库表
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "数据库表")
public class CodeTableDTO extends BaseDTO {

	/**
	 * ID
	 */
	@Schema(description = "ID")
	private Long id;

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
	 * 表名称
	 */
	@Schema(description = "表名称")
	private String tableName;

	/**
	 * 表描述
	 */
	@Schema(description = "表描述")
	private String tableComment;

	/**
	 * 实体类名称
	 */
	@Schema(description = "实体类名称")
	private String className;

	/**
	 * 生成包路径
	 */
	@Schema(description = "生成包路径")
	private String packageName;

	/**
	 * 生成模块名
	 */
	@Schema(description = "生成模块名")
	private String rootModuleName;

	/**
	 * 生成模块名
	 */
	@Schema(description = "生成模块名")
	private String moduleName;

	/**
	 * 生成模块描述
	 */
	@Schema(description = "生成模块描述")
	private String moduleDesc;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@Schema(description = "修改时间")
	private LocalDateTime updateTime;

}
