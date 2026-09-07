package com.platform.mesh.bpm.biz.modules.data.inst.api;

import com.platform.mesh.bpm.api.modules.inst.domain.dto.BpmPDTO;
import com.platform.mesh.bpm.biz.modules.data.inst.service.IBpmDataInstRelService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 约定当前controller 只引入当前service
 * @description 流程过程信息
 * @author 蝉鸣
 */
@Hidden
@RestController
public class BpmDataInstRelApi extends BaseController {

    /**
     * 服务对象
     */
    @Autowired
    private IBpmDataInstRelService bpmDataInstRelService;


    /**
     * 功能描述:
     * 〈根据模块获取运行中待审批数据〉
     * @return 正常返回:{@link Result<PageVO<Long>>}
     * @author 蝉鸣
     */
    @AuthIgnore
    @PostMapping("/api/inst/process/run/audit/data")
    public Result<PageVO<Long>> getRunDataIdsByModuleSchema(@RequestBody BpmPDTO pageDTO) {
        try {
            DataScopeHandler.setEnableDataScope(Boolean.FALSE);
            MPage<Long> mPage = bpmDataInstRelService.getRunDataIdsByModuleSchema(pageDTO);
            return Result.success(MPageUtil.convertToVO(mPage,Long.class));
        } finally {
            DataScopeHandler.unEnableDataScope();
        }
    }


    /**
     * 功能描述:
     * 〈获取模块待审批数量总计〉
     * @return 正常返回:{@link Result<Map>}
     * @author 蝉鸣
     */
    @AuthIgnore
    @PostMapping("/api/inst/process/run/audit/num")
    public Result<Map<String,Long>> getRunDataNumByModuleSchema(@RequestBody BpmPDTO pageDTO) {
        try {
            DataScopeHandler.setEnableDataScope(Boolean.FALSE);
            Map<String,Long> numMap = bpmDataInstRelService.getRunDataNumByModuleSchema(pageDTO);
            return Result.success(numMap);
        } finally {
            DataScopeHandler.unEnableDataScope();
        }
    }

}
