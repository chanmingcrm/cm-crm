package com.platform.mesh.es.domain.dto;

import com.platform.mesh.utils.excel.dto.HeadDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description ES分页查询DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="ES导出DTO")
public class EsDocEGetDTO extends EsDocPGetDTO {

	/**
	 * 模块名称
	 */
	@Schema(description = "模块名称")
	private String moduleName;
	/**
	 * 导出列头
	 */
	@Schema(description = "导出列头")
	private List<HeadDTO> headDTOS;


}
