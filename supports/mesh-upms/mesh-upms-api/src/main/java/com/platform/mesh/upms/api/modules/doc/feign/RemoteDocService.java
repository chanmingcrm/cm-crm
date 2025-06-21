package com.platform.mesh.upms.api.modules.doc.feign;

import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.ServiceNameConst;
import com.platform.mesh.upms.api.modules.doc.domain.vo.DocFileVO;
import com.platform.mesh.upms.api.modules.doc.feign.factory.RemoteDocFallbackFactory;
import com.platform.mesh.utils.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @description 文档信息服务
 * @author 蝉鸣
 */
@FeignClient(contextId = "remoteDocService", value = ServiceNameConst.SYSTEM_SERVICE,
		fallbackFactory = RemoteDocFallbackFactory.class)
public interface RemoteDocService {

	/**
	 * 功能描述:
	 * 〈获取文件消息〉
	 * @param fileIds fileIds
	 * @return 正常返回:{@link Result<List<DocFileVO>>}
	 * @author 蝉鸣
	 */
	@PostMapping(value = "/api/sys/doc/file", headers = HttpConst.HEADER_FROM_IN)
	Result<List<DocFileVO>> getDocFiles(@RequestBody List<Long> fileIds);

}
