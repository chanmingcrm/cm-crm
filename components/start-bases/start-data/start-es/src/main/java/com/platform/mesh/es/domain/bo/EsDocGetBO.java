package com.platform.mesh.es.domain.bo;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.es.enums.EsBoolEnum;
import com.platform.mesh.es.util.EsUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description ES文档获取BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="ES文档获取BO")
public class EsDocGetBO extends PageDTO {

	/**
	 * 索引名称
	 */
	@Schema(description = "索引名称")
	private String indexName;

	/**
	 * Bool查询条件
	 */
	@Schema(description = "Bool查询条件")
	private BoolQuery.Builder boolBuilder = EsUtil.getSearchBuilderBool();

	/**
	 * 扩展查询条件
	 */
	@Schema(description = "扩展查询条件")
	private Map<EsBoolEnum, List<Query>> queryMap = new HashMap<>();

	/**
	 * 分析条件
	 */
	@Schema(description = "分析条件")
	private Map<String, Aggregation> aggrMap = new HashMap<>();

	/**
	 * 排序列
	 */
	@Schema(description = "排序列")
	private List<SortOptions> sortOptions = new ArrayList<>();

	/**
	 * ES时间快照
	 */
	@Schema(description = "ES时间快照")
	private String pit;

	/**
	 * ES搜索
	 */
	@Schema(description = "ES搜索")
	private List<FieldValue> searchAfter;

}
