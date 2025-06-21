package com.platform.mesh.upms.api.modules.msg.feign.factory;

import com.platform.mesh.core.application.domain.bo.MsgNoticeBO;
import com.platform.mesh.upms.api.modules.msg.domain.bo.MsgBaseBO;
import com.platform.mesh.upms.api.modules.msg.feign.RemoteMsgService;
import com.platform.mesh.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @description 消息信息服务降级处理
 * @author 蝉鸣
 */
@Component
public class RemoteMsgFallbackFactory implements FallbackFactory<RemoteMsgService> {

	private static final Logger log = LoggerFactory.getLogger(RemoteMsgFallbackFactory.class);

	/**
	 * 功能描述:
	 * 〈创建实例〉
	 * @param throwable throwable
	 * @return 正常返回:{@link RemoteMsgService}
	 * @author 蝉鸣
	 */
	@Override
	public RemoteMsgService create(Throwable throwable) {
		log.error("消息服务调用失败:{}", throwable.getMessage());
		return new RemoteMsgService() {
			@Override
			public Result<Boolean> sendMsg(MsgBaseBO msgBaseBO) {
				return Result.error();
			}

			@Override
			public void addMsgNotice(MsgNoticeBO msgNoticeBO) {

			}

			@Override
			public void handleMsgNotice() {

			}

			@Override
			public void clearMsgNotice() {

			}
		};
	}

}
