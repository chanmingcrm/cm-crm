package com.platform.mesh.upms.biz.modules.sys.menu.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.bo.RouteItemBO;
import com.platform.mesh.upms.api.modules.sys.account.enums.MenuTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description sys_user实体
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "菜单VO")
public class SysMenuVO extends BaseVO {

	/**
	 * 菜单自增ID
	 */
	@Schema(description = "菜单自增ID")
	private Long id;

	/**
	 * 顶层ID
	 */
	@Schema(description = "顶层ID")
	private Long rootId;

	/**
	 * 父菜单ID
	 */
	@Schema(description = "父菜单ID")
	private Long parentId;

	/**
	 * 路由参数类型(Item,Mate)
	 */
	@Schema(description = "路由参数类型")
	private Integer argType;

	/**
	 * 菜单类型(应用，菜单，页面，按钮)
	 */
	@SchemaEnum(value = MenuTypeEnum.class,description = "菜单类型")
	private Integer menuType = MenuTypeEnum.MENU.getValue();

	/**
	 * 路由信息
	 */
	@Schema(description = "路由信息")
	private RouteItemBO routeItem;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private Long createUserId;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人")
	private Long updateUserId;

	/**
	 * 修改时间
	 */
	@Schema(description = "修改时间")
	private LocalDateTime updateTime;

	@Schema(description = "路由标题")
	private String title;

	@Schema(description = "命名路由")
	private String name;
	
	@Schema(description = "编码标识")
	private String mac;

	@Schema(description = "路径")
	private String path;

	@Schema(description = "命名视图组件")
	private String component;

	@Schema(description = "哪些类型的用户有权限才能访问的路由")
	private String permissions;

	@Schema(description = "是否在菜单中隐藏(一些列表、表格的详情页面需要通过参数跳转，所以不能显示在菜单中)")
	private Integer hideMenu;

	@Schema(description = "路由顺序，可用于菜单的排序")
	private String orderNo;
	/**
	 * 子路由
	 */
	@Schema(description = "子路由")
	private List<SysMenuVO> children;

	@Schema(description = "路由参数")
	private List<String> params;

	@Schema(description = "是否总是显示")
	private Integer alwaysShow;
}