package com.platform.mesh.mybatis.plus.properties;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.datascope.constant.DataScopeConst;
import lombok.Data;

import java.util.Set;

/**
 * @description 数据库常量
 * @author 蝉鸣
 */
@Data
public class MybatisPlusScopeProperties {

	/**
	 * 是否开启数据权限插件
	 */
	private Boolean enable = true;

	/**
	 * 是否开启数据权限注解:@IgnoreDataScope
	 */
	private Boolean enableAnno = false;
	/**
	 * 用户权限字段名称
	 */
	private String userColumn = DataScopeConst.DEFAULT_SCOPE_USER_ID;
	/**
	 * 组织权限字段名称
	 */
	private String orgColumn = DataScopeConst.DEFAULT_SCOPE_ORG_ID;

	/**
	 * 需要忽略多租户的表名
	 */
	private Set<String> ignoreTables = CollUtil.newHashSet();

	/**
	 * 需要忽略数据权限的方法名
	 */
	private Set<String> ignoreSegments = CollUtil.newHashSet();

}

