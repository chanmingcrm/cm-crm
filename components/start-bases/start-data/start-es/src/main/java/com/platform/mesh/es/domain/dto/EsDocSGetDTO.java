package com.platform.mesh.es.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @description ES单体查询DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="ES单体查询DTO")
public class EsDocSGetDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 索引名称
	 */
	@Schema(description = "索引名称")
	private String indexName;

	/**
	 * 模块ID
	 */
	@Schema(description = "模块ID")
	private Long moduleId;

	/**
	 * 数据ID
	 */
	@Schema(description = "数据ID")
	private Long dataId;

}
