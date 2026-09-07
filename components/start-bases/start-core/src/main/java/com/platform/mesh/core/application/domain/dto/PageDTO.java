package com.platform.mesh.core.application.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 分页DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="分页DTO")
public class PageDTO extends BaseDTO {

	/**
	 * 当前记录起始索引
	 */
	@Schema(description = "当前记录起始索引")
	private Integer pageNum = 1;

	/**
	 * 每页显示记录数: 若为 -1 不分页，查询所有数据
	 */
	@Schema(description = "每页显示记录数")
	private Integer pageSize = 20;

    /**
     * 是否正序:true 正序
     */
    @Schema(description = "是否正序")
    private Boolean isAsc = Boolean.TRUE;

	/**
	 * 排序列
	 */
	@Schema(description = "排序列")
	private List<SortDTO> sorts = new ArrayList<>();

}
