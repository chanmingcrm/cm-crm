package com.platform.mesh.gen.biz.modules.code.ds.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "数据库配置表")
public class CodeDataSourceDTO extends BaseDTO {

	@Schema(description = "ID")
	private Long id;

	/**
	 * 配置类型(0：主机模式，1：JDBC)
	 */
	@Schema(description = "配置类型")
	private Integer confFlag;

	/**
	 * 数据库类型
	 */
	@Schema(description = "数据库类型")
	private String dbFlag;

	/**
	 * 数据源名称
	 */
	@Schema(description = "数据源名称")
	private String dsName;

	/**
	 * jdbc 别名
	 */
	@Schema(description = "jdbc 别名")
	private String jdbcName;

	/**
	 * jdbc url
	 */
	@Schema(description = "jdbc url")
	private String jdbcUrl;

	/**
	 * jdbc 用户名
	 */
	@Schema(description = "jdbc 用户名")
	private String jdbcUsername;

	/**
	 * jdbc 密码
	 */
	@Schema(description = "jdbc 密码")
	private String jdbcPassword;

	/**
	 * jdbc 驱动
	 */
	@Schema(description = "jdbc 驱动")
	private String jdbcDriver;

}
