package com.platform.mesh.upms.biz.modules.sys.menu.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.mybatis.plus.annotation.TableParentId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description sys_menu实体
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "sys_menu", autoResultMap = true)
public class SysMenu extends BasePO {

	/**
	 * 菜单自增ID
	 */
	@TableId(value = "id",type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 顶层ID
	 */
	private Long rootId;

	/**
	 * 父菜单ID
	 */
	@TableParentId(value = "parent_id")
	private Long parentId;

	/**
	 * 模块id(非必填， 有的手动创建的不需要传入模块id)
	 */
	private Long moduleId;

	/**
	 * 路由参数类型(Item,Mate)
	 */
	private Integer argType;

	/**
	 * 菜单类型(应用，菜单，页面，按钮)
	 */
	private Integer menuType;

	/**
	 * 命名路由
	 */
	private String name;

	/**
	 * 别名
	 */
	private String alias;

	/**
	 * 编码标识
	 */
	private String mac;

	/**
	 * 命名视图组件
	 */
	private String component;

	/**
	 * 路径
	 */
	private String path;

	/**
	 * 重定向路径
	 */
	private String redirect;

	/**
	 * 匹配规则是否大小写敏感
	 */
	private Integer caseSensitive;

	/**
	 * 路由顺序，可用于菜单的排序
	 */
	private Integer orderNo;

	/**
	 * 路由标题
	 */
	private String title;

	/**
	 * 外链链接）
	 */
	private String href;

	/**
	 * 菜单和面包屑对应的图标
	 */
	private String icon;

	/**
	 * 菜单和面包屑对应的中图标
	 */
	private String iconM;

	/**
	 * 菜单和面包屑对应的大图标
	 */
	private String iconL;

	/**
	 * 作为单级路由的父级路由布局组件
	 */
	private String singleLayout;

	/**
	 * 路由的动态路径
	 */
	private String dynamicPath;

	/**
	 * 当前路由需要选中的菜单项(用于跳转至不在左侧菜单显示的路由且需要高亮某个菜单的情况)
	 */
	private String activeMenu;

	/**
	 * 哪些类型的用户有权限才能访问的路由
	 */
	private String permissions;

	/**
	 * 需要登录权限
	 */
	private Integer requiresAuth;

	/**
	 * 是否固定在tab卡不可关闭
	 */
	private Integer affix;

	/**
	 * 表示是否是多级路由的中间级路由(用于转换路由数据时筛选多级路由的标识，定义路由时不用填写)
	 */
	private Integer multi;

	/**
	 * 是否支持多个tab页签(默认一个，即相同name的路由会被替换)
	 */
	private Integer multiTab;

	/**
	 * 缓存页面
	 */
	private Integer keepAlive;

	/**
	 * 是否在菜单中隐藏(一些列表、表格的详情页面需要通过参数跳转，所以不能显示在菜单中)
	 */
	private Integer hideMenu;

	/**
	 * 是否隐藏菜单子项
	 */
	private Integer hideMenuChildren;

	/**
	 * 路由参数
	 */
	private String params;

	/**
	 * 是否总是显示
	 */
	private Integer alwaysShow;

	/**
	 * 删除标识
	 */
	private Integer delFlag;

	/**
	 * 创建人ID
	 */
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 修改人ID
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Long updateUserId;

	/**
	 * 修改时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

}