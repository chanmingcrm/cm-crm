package com.platform.mesh.gen.biz.modules.code.field.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "数据库字段映射")
public class CodeFieldMappingVO extends BaseVO {

	/**
	 * ID
	 */
	@Schema(description = "ID")
	private Long id;

	/**
	 * 属性包名
	 */
	@Schema(description = "属性包名")
	private String packageName;

	/**
	 * 字段类型
	 */
	@Schema(description = "字段类型")
	private String columnFlag;

	/**
	 * 属性类型
	 */
	@Schema(description = "属性类型")
	private String fieldFlag;

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
