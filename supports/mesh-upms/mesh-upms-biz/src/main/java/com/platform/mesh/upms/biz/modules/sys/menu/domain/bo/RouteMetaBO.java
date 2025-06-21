package com.platform.mesh.upms.biz.modules.sys.menu.domain.bo;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description RouteMate信息
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "RouteMate信息")
public class RouteMetaBO extends BaseBO {

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
	 * 使用本地svg作为的菜单和面包屑对应的图标(assets/svg-icon文件夹的的svg文件名)
	 */
	@Schema(description = "使用本地svg作为的菜单和面包屑对应的图标")
	private String localIcon;

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
	@Schema(description = "访问权限")
	private String permissions;

	/**
	 * 需要登录权限
	 */
	@Schema(description = "需要登录权限")
	private Boolean requiresAuth;

	/**
	 * 是否固定在tab卡不可关闭
	 */
	@Schema(description = "是否固定在tab卡不可关闭")
	private Boolean affix;

	/**
	 * 表示是否是多级路由的中间级路由(用于转换路由数据时筛选多级路由的标识，定义路由时不用填写)
	 */
	@Schema(description = "表示是否是多级路由的中间级路由")
	private Boolean multi;

	/**
	 * 是否支持多个tab页签(默认一个，即相同name的路由会被替换)
	 */
	@Schema(description = "是否支持多个tab页签")
	private Boolean multiTab;

	/**
	 * 缓存页面
	 */
	@Schema(description = "缓存页面")
	private Boolean keepAlive;

	/**
	 * 是否隐藏
	 */
	@Schema(description = "是否隐藏")
	private Integer hidden;

	/**
	 * 是否在菜单中隐藏(一些列表、表格的详情页面需要通过参数跳转，所以不能显示在菜单中)
	 */
	@Schema(description = "是否在菜单中隐藏")
	private Boolean hideMenu;

	/**
	 * 是否隐藏菜单子项
	 */
	@Schema(description = "是否隐藏菜单子项")
	private Boolean hideMenuChildren;

	/**
	 * 是否总是显示
	 */
	@Schema(description = "是否总是显示")
	private Integer alwaysShow;

	/**
	 * 额外参数
	 */
	@Schema(description = "额外参数")
	private JSONArray params;


}