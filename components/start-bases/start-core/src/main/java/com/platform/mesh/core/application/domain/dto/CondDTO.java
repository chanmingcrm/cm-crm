package com.platform.mesh.core.application.domain.dto;

import com.platform.mesh.core.constants.SearchColumnConst;
import com.platform.mesh.core.enums.logic.ref.LogicRefEnum;
import com.platform.mesh.core.enums.logic.type.LogicTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 条件DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="条件DTO")
public class CondDTO extends BaseDTO {

	/**
	 * 条件字段标识
	 */
	@Schema(description = "条件字段标识")
	private String columnMac;

	/**
	 * 条件组件类型
	 */
	@Schema(description = "条件组件类型")
	private String compMac;

	/**
	 * 逻辑类型
	 */
	@Schema(description = "逻辑类型")
	private LogicTypeEnum condType = LogicTypeEnum.AND;

	/**
	 * 逻辑关系
	 */
	@Schema(description = "逻辑关系")
	private LogicRefEnum condRef = LogicRefEnum.EQ;

	/**
	 * 是否忽略无效值
	 */
	@Schema(description = "是否忽略无效值")
	private Boolean ignoreCase = true;

	/**
	 * data表字段标识
	 */
	@Schema(description = "data表字段标识")
	private String dataColumnMac = SearchColumnConst.DATA_COLUMN_MAC;

	/**
	 * data表字段名称
	 */
	@Schema(description = "data表字段名称")
	private String dataColumnValue = SearchColumnConst.DATA_COLUMN_VALUE;

	/**
	 * 搜索值
	 */
	@Schema(description = "搜索值")
	private List<String> searchValues = new ArrayList<>();

}
