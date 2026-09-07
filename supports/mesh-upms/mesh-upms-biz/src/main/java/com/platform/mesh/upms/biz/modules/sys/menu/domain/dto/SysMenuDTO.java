package com.platform.mesh.upms.biz.modules.sys.menu.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.api.modules.sys.account.enums.MenuTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description sys_menu实体
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "SysMenu信息")
public class SysMenuDTO extends BaseDTO {

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


	@Schema(description = "APP业务层-应用模块ID")
	private Long moduleId;
	/**
	 * 路由参数类型(Item,Mate)
	 */
	@Schema(description = "路由参数类型")
	private Integer argType;

	/**
	 * 菜单类型(应用，菜单，页面，按钮)
	 */
	@SchemaEnum(value = MenuTypeEnum.class,description = "菜单类型")
	private Integer menuType;

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
	 * 编码标识
	 */
	@Schema(description = "编码标识")
	private String mac;

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
	private Integer caseSensitive;

	/**
	 * 路由顺序，可用于菜单的排序
	 */
	@Schema(description = "路由顺序")
	private Integer orderNo;

	/**
	 * 路由标题
	 */
	@Schema(description = "路由标题")
	private String title;

	/**
	 * 外链链接
	 */
	@Schema(description = "外链链接")
	private String href;

	/**
	 * 菜单和面包屑对应的图标
	 */
	@Schema(description = "菜单和面包屑对应的图标")
	private String icon;

    /**
     * 菜单和面包屑对应的中图标
     */
    @Schema(description = "菜单和面包屑对应的中图标")
    private String iconM;

    /**
     * 菜单和面包屑对应的大图标
     */
    @Schema(description = "菜单和面包屑对应的大图标")
    private String iconL;

	/**
	 * 作为单级路由的父级路由布局组件
	 */
	@Schema(description = "作为单级路由的父级路由布局组件")
	private String singleLayout;

	/**
	 * 路由的动态路径
	 */
	@Schema(description = "路由的动态路径")
	private String dynamicPath;

	/**
	 * 当前路由需要选中的菜单项(用于跳转至不在左侧菜单显示的路由且需要高亮某个菜单的情况)
	 */
	@Schema(description = "当前路由需要选中的菜单项")
	private String activeMenu;

	/**
	 * 哪些类型的用户有权限才能访问的路由
	 */
	@Schema(description = "哪些类型的用户有权限才能访问的路由")
	private String permissions;

	/**
	 * 需要登录权限
	 */
	@Schema(description = "需要登录权限")
	private Integer requiresAuth;

	/**
	 * 是否固定在tab卡不可关闭
	 */
	@Schema(description = "是否固定在tab卡不可关闭")
	private Integer affix;

	/**
	 * 表示是否是多级路由的中间级路由(用于转换路由数据时筛选多级路由的标识，定义路由时不用填写)
	 */
	@Schema(description = "表示是否是多级路由的中间级路由")
	private Integer multi;

	/**
	 * 是否支持多个tab页签(默认一个，即相同name的路由会被替换)
	 */
	@Schema(description = "是否支持多个tab页签")
	private Integer multiTab;

	/**
	 * 缓存页面
	 */
	@Schema(description = "缓存页面")
	private Integer keepAlive;

	/**
	 * 是否在菜单中隐藏(一些列表、表格的详情页面需要通过参数跳转，所以不能显示在菜单中)
	 */
	@Schema(description = "是否在菜单中隐藏")
	private Integer hideMenu;

	/**
	 * 是否隐藏菜单子项
	 */
	@Schema(description = "是否隐藏菜单子项")
	private Integer hideMenuChildren;

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


	@Schema(description = "路由参数")
	private List<RouteParamsDTO> params;


	@Schema(description = "是否总是显示")
	private Integer alwaysShow;
}