package com.platform.mesh.upms.biz.modules.log.modify.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.upms.biz.modules.log.modify.domain.dto.LogModifyPageDTO;
import com.platform.mesh.upms.biz.modules.log.modify.domain.vo.LogModifyVO;
import com.platform.mesh.upms.biz.modules.log.modify.service.ILogModifyService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 修改日志
 * @author 蝉鸣
 */
@Tag(description = "LogLoginController", name = "修改日志")
@RestController
public class LogModifyController extends BaseController{

    /**
     * 服务对象
     */
    @Autowired
    private ILogModifyService logModifyService;

    /**
     * 功能描述:
     * 〈获取变更日志分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<LogModifyVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取变更日志分页")
    @PostMapping("/log/modify/page")
    public Result<PageVO<LogModifyVO>> selectPage(@RequestBody LogModifyPageDTO pageDTO) {
        PageVO<LogModifyVO> voPage = logModifyService.selectPage(pageDTO);
        return Result.success(voPage);
    }

}
