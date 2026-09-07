package com.platform.mesh.gen.biz.modules.code.table.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class TableQueryDTO extends PageDTO {

	/**
	 * 数据源ID
	 */
	@Schema(description = "数据源ID")
	private Long dsId;

	/**
	 * 数据库表空间
	 */
	@Schema(description = "数据库表空间")
	@NotBlank(message = "数据库表空间称不能为空")
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
	 * 开始时间
	 */
	@Schema(description = "开始时间")
	private LocalDateTime beginTime;

	/**
	 * 结束时间
	 */
	@Schema(description = "结束时间")
	private LocalDateTime endTime;

}
