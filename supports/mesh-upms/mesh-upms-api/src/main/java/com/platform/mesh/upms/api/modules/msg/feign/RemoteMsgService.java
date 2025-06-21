package com.platform.mesh.upms.api.modules.msg.feign;

import com.platform.mesh.core.application.domain.bo.MsgNoticeBO;
import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.ServiceNameConst;
import com.platform.mesh.upms.api.modules.msg.domain.bo.MsgBaseBO;
import com.platform.mesh.upms.api.modules.msg.feign.factory.RemoteMsgFallbackFactory;
import com.platform.mesh.utils.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @description 消息信息服务
 * @author 蝉鸣
 */
@FeignClient(contextId = "remoteMsgService", value = ServiceNameConst.SYSTEM_SERVICE,
		fallbackFactory = RemoteMsgFallbackFactory.class)
public interface RemoteMsgService {

	/**
	 * 功能描述:
	 * 〈发送站内消息〉
	 * @param msgBaseBO msgBaseBO
	 * @return 正常返回:{@link Result<Boolean>}
	 * @author 蝉鸣
	 */
	@PostMapping(value = "/api/sys/send/msg", headers = HttpConst.HEADER_FROM_IN)
	Result<Boolean> sendMsg(@RequestBody MsgBaseBO msgBaseBO);

	/**
	 * 功能描述:
	 * 〈新增消息提醒〉
	 * @author 蝉鸣
	 */
	@PostMapping(value = "/api/sys/msg/notice/add", headers = HttpConst.HEADER_FROM_IN)
	void addMsgNotice(@RequestBody MsgNoticeBO msgNoticeBO);

	/**
	 * 功能描述:
	 * 〈定时执行消息提醒〉
	 * @author 蝉鸣
	 */
	@PostMapping(value = "/api/sys/msg/notice/handle", headers = HttpConst.HEADER_FROM_IN)
	void handleMsgNotice();

	/**
	 * 功能描述:
	 * 〈定时清理消息提醒〉
	 * @author 蝉鸣
	 */
	@PostMapping(value = "/api/sys/msg/notice/clear", headers = HttpConst.HEADER_FROM_IN)
	void clearMsgNotice();


}
