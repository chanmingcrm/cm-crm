package com.platform.mesh.bpm.api.modules.inst.feign;

import com.platform.mesh.bpm.api.modules.inst.domain.dto.BpmPDTO;
import com.platform.mesh.bpm.api.modules.inst.feign.factory.RemoteBpmFallbackFactory;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.ServiceNameConst;
import com.platform.mesh.utils.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @description Oauth2服务
 * @author 蝉鸣
 */
@FeignClient(contextId = "RemoteBpmService", value = ServiceNameConst.BPM_SERVICE,
		fallbackFactory = RemoteBpmFallbackFactory.class)
public interface RemoteBpmService {

    /**
     * 功能描述:
     * 〈根据模块获取运行中待审批数据〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<Long>>}
     * @author 蝉鸣
     */
    @PostMapping(value = "/api/inst/process/run/audit/data", headers = HttpConst.HEADER_FROM_IN)
    Result<PageVO<Long>> getRunDataIdsByModuleSchema(@RequestBody BpmPDTO pageDTO);

    /**
     * 功能描述:
     * 〈根据模块获取运行中待审批数据〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<Map>}
     * @author 蝉鸣
     */
    @PostMapping(value = "/api/inst/process/run/audit/num", headers = HttpConst.HEADER_FROM_IN)
    Result<Map<String,Long>> getRunDataNumByModuleSchema(@RequestBody BpmPDTO pageDTO);

}
