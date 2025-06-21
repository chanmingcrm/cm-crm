package com.platform.mesh.es.domain.dto;

import com.platform.mesh.core.application.domain.dto.QueryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @description ES分页查询DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="ES分页查询DTO")
public class EsDocPGetDTO extends QueryDTO {

	/**
	 * 索引名称
	 */
	@Schema(description = "索引名称")
	private String indexName;

	/**
	 * ES时间快照
	 */
	@Schema(description = "ES时间快照")
	private String pit;

	/**
	 * ES搜索
	 */
	@Schema(description = "ES搜索")
	private List<Map<String,Object>> searchAfter;

}
