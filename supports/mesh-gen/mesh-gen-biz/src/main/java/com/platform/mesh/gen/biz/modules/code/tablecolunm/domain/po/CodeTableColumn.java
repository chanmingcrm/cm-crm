package com.platform.mesh.gen.biz.modules.code.tablecolunm.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description 代码生成模板数据库对象
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("code_table_column")
public class CodeTableColumn extends BasePO {

	@TableId( type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 表ID
	 */
	private Long tableId;

	/**
	 * 列名称
	 */
	private String columnName;

	/**
	 * 列描述
	 */
	private String columnComment;

	/**
	 * 列类型
	 */
	private String columnFlag;

	/**
	 * 字段类型
	 */
	private String fieldFlag;

	/**
	 * 字段名称
	 */
	private String fieldName;

	/**
	 * 主键标记
	 */
	private Integer pkFlag;

	/**
	 * 自增标记
	 */
	private Integer incrFlag;

	/**
	 * 空值标记
	 */
	private Integer nullFlag;

	/**
	 * po标记
	 */
	private Integer poFlag;

	/**
	 * bo标记
	 */
	private Integer boFlag;

	/**
	 * vo标记
	 */
	private Integer voFlag;

	/**
	 * 排序标记
	 */
	private Integer sortFlag;

	/**
	 * 创建人ID
	 */
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 修改人ID
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Long updateUserId;

	/**
	 * 修改时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

	/**
	 * 用户ID
	 */
	@TableField(fill = FieldFill.INSERT)
	private Long scopeUserId;

	/**
	 * 组织ID
	 */
	@TableField(fill = FieldFill.INSERT)
	private Long scopeOrgId;

}
