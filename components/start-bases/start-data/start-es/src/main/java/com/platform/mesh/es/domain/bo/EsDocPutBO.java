package com.platform.mesh.es.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description ES文档编辑BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="ES文档编辑BO")
public class EsDocPutBO extends BaseBO {

	/**
	 * 索引名称
	 */
	@Schema(description = "索引名称")
	private String indexName;

	/**
	 * 数据ID
	 */
	@Schema(description = "数据ID")
	private List<?> dataIds;

	/**
	 * 文档数据
	 */
	@Schema(description = "文档数据")
	private Map<String, Object> docMap = new HashMap<>();

}
