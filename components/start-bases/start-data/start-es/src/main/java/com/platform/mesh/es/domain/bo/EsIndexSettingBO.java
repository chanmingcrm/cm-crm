package com.platform.mesh.es.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description ES索引配置BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="ES索引配置BO")
public class EsIndexSettingBO extends BaseBO {

	/**
	 * 索引名称
	 */
	@Schema(description = "索引名称")
	private String indexName;

	/**
	 * 最大结果数
	 */
	@Schema(description = "索引名称")
	private Integer maxResultWindow;

	/**
	 * 分词器
	 */
	@Schema(description = "分词器")
	private String analysis;

	/**
	 * tokenizer
	 */
	@Schema(description = "tokenizer")
	private String tokenizer;

}
