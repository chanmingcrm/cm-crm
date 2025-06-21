package com.platform.mesh.es.domain.bo;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @description ES索引映射BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="ES索引映射BO")
public class EsIndexMappingBO extends BaseBO {

	/**
	 * 索引名称
	 */
	@Schema(description = "索引名称")
	private String indexName;

	/**
	 * 文档属性
	 */
	@Schema(description = "文档属性")
	private Map<String, Property> properties;

}
