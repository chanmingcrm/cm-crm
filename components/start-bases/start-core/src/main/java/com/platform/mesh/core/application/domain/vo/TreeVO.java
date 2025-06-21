package com.platform.mesh.core.application.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 树结构类
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="树结构类VO")
public class TreeVO<T> extends BaseVO{

	/**
	 * 自增ID
	 */
	@Schema(description = "自增ID")
	private Long id;

	/**
	 * 父菜单ID
	 */
	@Schema(description = "父菜单ID")
	private Long parentId;

	/**
	 * 子路由
	 */
	@Schema(description = "子路由")
	private List<T> children;

}
