package com.platform.mesh.gen.biz.modules.code.ds.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("code_datasource")
public class CodeDataSource extends BasePO {

	@TableId( type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 配置类型(0：主机模式，1：JDBC)
	 */
	private Integer confFlag;

	/**
	 * 数据库类型
	 */
	private String dbFlag;

	/**
	 * 数据源名称
	 */
	private String dsName;

	/**
	 * jdbc 别名
	 */
	private String jdbcName;

	/**
	 * jdbc url
	 */
	private String jdbcUrl;

	/**
	 * jdbc 用户名
	 */
	private String jdbcUsername;

	/**
	 * jdbc 密码
	 */
	private String jdbcPassword;

	/**
	 * jdbc 驱动
	 */
	private String jdbcDriver;

	/**
	 * 删除标记
	 */
	private Integer delFlag;

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
