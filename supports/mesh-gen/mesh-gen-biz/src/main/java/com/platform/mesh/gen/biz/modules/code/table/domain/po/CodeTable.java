package com.platform.mesh.gen.biz.modules.code.table.domain.po;

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
@TableName("code_table")
public class CodeTable extends BasePO {

	@TableId( type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 数据源ID
	 */
	private Long dsId;

	/**
	 * 数据源别名
	 */
	private String tableSchema;

	/**
	 * 表名称
	 */
	private String tableName;

	/**
	 * 表描述
	 */
	private String tableComment;

	/**
	 * 实体类名称
	 */
	private String className;

	/**
	 * 生成包路径
	 */
	private String packageName;

	/**
	 * 生成模块名
	 */
	private String rootModuleName;

	/**
	 * 生成模块名
	 */
	private String moduleName;

	/**
	 * 生成模块描述
	 */
	private String moduleDesc;

	/**
	 * 备注
	 */
	private String remark;

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
