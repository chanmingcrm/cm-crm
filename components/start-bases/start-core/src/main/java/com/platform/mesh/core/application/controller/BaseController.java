package com.platform.mesh.core.application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @description 基础控制器
 * @author 蝉鸣
 */
public class BaseController {

	protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

	/**
	 * 功能描述:
	 * 〈将前台传递过来的日期格式的字符串，自动转化为Date类型〉
	 * @param binder binder
	 * @author 蝉鸣
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
//		// Date 类型转换
//		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
//			@Override
//			public void setAsText(String text) {
//				// setValue(DateUtil.parseDate(text));
//			}
//		});
	}


}
