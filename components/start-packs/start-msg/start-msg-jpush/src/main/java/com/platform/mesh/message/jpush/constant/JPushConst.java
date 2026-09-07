package com.platform.mesh.message.jpush.constant;

import cn.hutool.core.collection.CollUtil;

import java.util.List;

/**
 * @description 数据库常量
 * @author 蝉鸣
 */
public interface JPushConst {


	/**
	 * 配置前缀
	 */
	String CONFIG_PREFIX = "jpush.api";

	/**
	 * 标题
	 */
	String ALERT_TITLE = "title";

	/**
	 * 消息体
	 */
	String ALERT_CONTENT = "content";

	/**
	 * 消息体
	 */
	String ALERT_CATEGORY = "WORK";

	/**
	 * 安卓URL
	 */
	String ANDROID_URL = "intent:#Intent;action=android.intent.action.MAIN;end";

	/**
	 * 鸿蒙URL
	 */
	String HMOS_URL = "action.system.home";

	/**
	 * 系统消息通知
	 */
	String MESH_MSG_URL = "/pages-message/message/index";

	/**
	 * 系统待办通知
	 */
	String MESH_TODO_URL = " /pages-message/todo/index";

	/**
	 * 敏感信息
	 */
	List<String> IGNORE_FIELD = CollUtil.newArrayList(
			"token",
			"password"
	);
}

