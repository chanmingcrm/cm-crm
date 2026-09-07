package com.platform.mesh.gen.biz.modules.code.tablecolunm.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 代码生成模板数据库对象
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "表字段VO")
public class CodeTableColumnVO extends BaseVO {

	@Schema(description = "ID")
	private Long id;

	/**
	 * 表ID
	 */
	@Schema(description = "表ID")
	private Long tableId;

	/**
	 * 列名称
	 */
	@Schema(description = "列名称")
	private String columnName;

	/**
	 * 列描述
	 */
	@Schema(description = "列描述")
	private String columnComment;

	/**
	 * 列类型
	 */
	@Schema(description = "列类型")
	private String columnFlag;

	/**
	 * 字段类型
	 */
	@Schema(description = "字段类型")
	private String fieldFlag;

	/**
	 * 字段名称
	 */
	@Schema(description = "字段名称")
	private String fieldName;

	/**
	 * 主键标记
	 */
	@Schema(description = "主键标记")
	private Integer pkFlag;

	/**
	 * 自增标记
	 */
	@Schema(description = "自增标记")
	private Integer incrFlag;

	/**
	 * 空值标记
	 */
	@Schema(description = "空值标记")
	private Integer nullFlag;

	/**
	 * po标记
	 */
	@Schema(description = "po标记")
	private Integer poFlag;

	/**
	 * bo标记
	 */
	@Schema(description = "bo标记")
	private Integer boFlag;

	/**
	 * vo标记
	 */
	@Schema(description = "vo标记")
	private Integer voFlag;

	/**
	 * 排序标记
	 */
	@Schema(description = "排序标记")
	private Integer sortFlag;

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
