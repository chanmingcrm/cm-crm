package com.platform.mesh.upms.api.modules.doc.feign.factory;

import com.platform.mesh.upms.api.modules.doc.domain.dto.DocOnlineSaveDTO;
import com.platform.mesh.upms.api.modules.doc.domain.vo.DocFileVO;
import com.platform.mesh.upms.api.modules.doc.feign.RemoteDocService;
import com.platform.mesh.upms.api.modules.msg.feign.RemoteMsgService;
import com.platform.mesh.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description 文档服务降级处理
 * @author 蝉鸣
 */
@Component
public class RemoteDocFallbackFactory implements FallbackFactory<RemoteDocService> {

	private static final Logger log = LoggerFactory.getLogger(RemoteDocFallbackFactory.class);

	/**
	 * 功能描述:
	 * 〈创建实例〉
	 * @param throwable throwable
	 * @return 正常返回:{@link RemoteMsgService}
	 * @author 蝉鸣
	 */
	@Override
	public RemoteDocService create(Throwable throwable) {
		log.error("消息服务调用失败:{}", throwable.getMessage());
		return new RemoteDocService() {
			@Override
			public Result<List<DocFileVO>> getDocFiles(List<Long> fileIds) {
				return Result.error();
			}

			@Override
			public Result<Void> saveOnline(DocOnlineSaveDTO saveDTO) {
				return Result.error();
			}
		};
	}

}
