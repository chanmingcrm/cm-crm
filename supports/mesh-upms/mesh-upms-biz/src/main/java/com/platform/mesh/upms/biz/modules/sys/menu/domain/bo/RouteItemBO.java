package com.platform.mesh.upms.biz.modules.sys.menu.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * @description RouteItem信息
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "RouteItem信息")
public class RouteItemBO extends BaseBO {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 命名路由
	 */
	@Schema(description = "命名路由")
	private String name;

	/**
	 * 别名
	 */
	@Schema(description = "别名")
	private String alias;

	/**
	 * 命名视图组件
	 */
	@Schema(description = "命名视图组件")
	private String component;

	/**
	 * 路径
	 */
	@Schema(description = "路径")
	private String path;

	/**
	 * 重定向路径
	 */
	@Schema(description = "重定向路径")
	private String redirect;

	/**
	 * 匹配规则是否大小写敏感
	 */
	@Schema(description = "匹配规则是否大小写敏感")
	private Boolean caseSensitive;

	/**
	 * 路由顺序，可用于菜单的排序
	 */
	@Schema(description = "路由顺序")
	private Integer orderNo;

	/**
	 * 路由Mate
	 */
	@Schema(description = "路由Meta")
	private RouteMetaBO routeMeta;



}