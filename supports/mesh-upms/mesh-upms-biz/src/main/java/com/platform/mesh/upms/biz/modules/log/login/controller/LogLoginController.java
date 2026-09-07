package com.platform.mesh.upms.biz.modules.log.login.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.biz.modules.log.login.domain.po.LogLogin;
import com.platform.mesh.upms.biz.modules.log.login.domain.vo.LogLoginVO;
import com.platform.mesh.upms.biz.modules.log.login.service.ILogLoginService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 登录日志
 * @author 蝉鸣
 */
@Tag(description = "LogLoginController", name = "登录日志")
@RestController
public class LogLoginController extends BaseController{

    @Autowired
    private ILogLoginService logLoginService;

    /**
     * 功能描述:
     * 〈获取登录日志分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<LogLoginVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取登录日志分页")
    @PostMapping("/log/login/page")
    public Result<PageVO<LogLoginVO>> selectPage(@RequestBody PageDTO pageDTO) {
        MPage<LogLogin> mPage = MPageUtil.pageEntityToMPage(pageDTO, LogLogin.class);
        MPage<LogLogin> pageList = logLoginService.lambdaQuery().orderByDesc(LogLogin::getCreateTime).page(mPage);
        return Result.success(MPageUtil.convertToVO(pageList,LogLoginVO.class));
    }

}
