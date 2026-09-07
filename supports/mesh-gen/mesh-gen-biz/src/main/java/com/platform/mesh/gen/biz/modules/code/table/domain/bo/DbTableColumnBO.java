package com.platform.mesh.gen.biz.modules.code.table.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 代码生成业务字段表
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "代码生成业务字段表")
public class DbTableColumnBO extends BaseBO {

	/**
	 * 数据库表空间
	 */
	@Schema(description = "数据库表空间")
	private String tableSchema;

	/**
	 * 数据库表名称
	 */
	@Schema(description = "数据库表名称")
	private String tableName;

	/**
	 * 列名称
	 */
	@Schema(description = "列名称")
	private String columnName;

	/**
	 * 列类型(包括字段长度)
	 */
	@Schema(description = "列类型(包括字段长度)")
	private String columnType;

	/**
	 * 列类型(不包括字段长度)
	 */
	@Schema(description = "列类型(不包括字段长度)")
	private String dataType;

	/**
	 * 可为空标识
	 */
	@Schema(description = "可为空标识 1 必填")
	private Integer nullFlag;

	/**
	 * 主键标识
	 */
	@Schema(description = "主键标识 1 必填")
	private Integer pkFlag;

	/**
	 * 自增标识
	 */
	@Schema(description = "自增标识 1 必填")
	private Integer incrFlag;

	/**
	 * 字段排序
	 */
	@Schema(description = "字段排序")
	private Integer sortFlag;

	/**
	 * 列描述
	 */
	@Schema(description = "列描述")
	private String columnComment;







}
