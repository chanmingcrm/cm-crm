package com.platform.mesh.upms.biz.modules.log.operate.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.biz.modules.log.operate.domain.po.LogOperate;
import com.platform.mesh.upms.biz.modules.log.operate.domain.vo.LogOperateVO;
import com.platform.mesh.upms.biz.modules.log.operate.service.ILogOperateService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 操作日志
 * @author 蝉鸣
 */
@Tag(description = "LogOperateController", name = "操作日志")
@RestController
public class LogOperateController extends BaseController{

    @Autowired
    private ILogOperateService logOperateService;

    /**
     * 功能描述:
     * 〈获取操作日志分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<LogOperateVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取操作日志分页")
    @PostMapping("/log/operate/page")
    public Result<PageVO<LogOperateVO>> selectPage(@RequestBody PageDTO pageDTO) {
        MPage<LogOperate> mPage = MPageUtil.pageEntityToMPage(pageDTO, LogOperate.class);
        MPage<LogOperate> pageList = logOperateService.lambdaQuery().orderByDesc(LogOperate::getCreateTime).page(mPage);
        return Result.success(MPageUtil.convertToVO(pageList,LogOperateVO.class));
    }

}
