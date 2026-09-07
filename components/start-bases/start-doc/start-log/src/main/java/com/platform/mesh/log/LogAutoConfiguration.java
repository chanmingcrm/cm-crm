package com.platform.mesh.log;

import com.platform.mesh.log.aspect.SysLogAspect;
import com.platform.mesh.log.event.listener.SysLoginLogListener;
import com.platform.mesh.log.event.listener.SysOperateLogListener;
import com.platform.mesh.upms.api.modules.sys.log.feign.RemoteLogService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description 日志自动配置
 * @author 蝉鸣
 */
//@EnableAsync
@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
public class LogAutoConfiguration {

	/**
	 * 功能描述:
	 * 〈登录日志监听〉
	 * @param remoteLogService remoteLogService
	 * @return 正常返回:{@link SysLoginLogListener}
	 * @author 蝉鸣
	 */
	@Bean
	public SysLoginLogListener sysLoginLogListener(RemoteLogService remoteLogService) {
		return new SysLoginLogListener(remoteLogService);
	}

	/**
	 * 功能描述:
	 * 〈操作日志监听〉
	 * @param remoteLogService remoteLogService
	 * @return 正常返回:{@link SysOperateLogListener}
	 * @author 蝉鸣
	 */
	@Bean
	public SysOperateLogListener sysOperateLogEventLogListener(RemoteLogService remoteLogService) {
		return new SysOperateLogListener(remoteLogService);
	}

	/**
	 * 功能描述:
	 * 〈系统操作日志〉
	 * @return 正常返回:{@link SysLogAspect}
	 * @author 蝉鸣
	 */
	@Bean
	public SysLogAspect sysLogAspect() {
		return new SysLogAspect();
	}

}