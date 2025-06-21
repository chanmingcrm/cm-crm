package com.platform.mesh.mybatis.plus.properties;

import com.platform.mesh.mybatis.plus.constant.MybatisPlusConst;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description 数据库常量
 * @author 蝉鸣
 */
@Data
@Component
@ConfigurationProperties(prefix = "mesh.data")
public class MybatisPlusDataProperties {

	/**
	 * 默认数据库类型
	 */
	private String dbType = MybatisPlusConst.DEFAULT_DB_TYPE;

	/**
	 * 是否开启防全表更新删除插件
	 */
	private Boolean enableBlockAttack = true;

	/**
	 * 是否开启分页插件
	 */
	private Boolean enablePage = true;

	/**
	 * 需要忽略数据权限的表名
	 */
	private MybatisPlusScopeProperties scope;

}

