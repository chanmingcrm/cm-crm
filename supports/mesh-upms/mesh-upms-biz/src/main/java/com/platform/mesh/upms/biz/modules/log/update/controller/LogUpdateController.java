package com.platform.mesh.upms.biz.modules.log.update.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.LoginTypeEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.biz.modules.label.base.domain.vo.LabelBaseVO;
import com.platform.mesh.upms.biz.modules.log.update.domain.dto.LogUpdateDTO;
import com.platform.mesh.upms.biz.modules.log.update.domain.dto.LogUpdatePageDTO;
import com.platform.mesh.upms.biz.modules.log.update.domain.po.LogUpdate;
import com.platform.mesh.upms.biz.modules.log.update.domain.vo.LogUpdateVO;
import com.platform.mesh.upms.biz.modules.log.update.service.ILogUpdateService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 约定当前controller 只引入当前service
 * @description 更新日志
 * @author 蝉鸣
 */
@Tag(description = "LogUpdateController", name = "更新日志")
@RestController
public class LogUpdateController extends BaseController{

    @Autowired
    private ILogUpdateService logUpdateService;

    /**
     * 功能描述:
     * 〈获取更新日志列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result < MPage < LabelBaseVO >>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取更新日志列表")
    @PostMapping("/log/update/page")
    public Result<PageVO<LogUpdateVO>> selectPage(@RequestBody LogUpdatePageDTO pageDTO) {
        if(ObjectUtil.isEmpty(pageDTO.getLogFlag())){
            pageDTO.setLogFlag(LoginTypeEnum.APP.getValue());
        }
        MPage<LogUpdate> baseMPage = MPageUtil.pageEntityToMPage(pageDTO, LogUpdate.class);
        MPage<LogUpdate> page = logUpdateService.lambdaQuery()
                .eq(LogUpdate::getLogFlag,pageDTO.getLogFlag())
                .orderByDesc(LogUpdate::getCreateTime)
                .page(baseMPage);
        PageVO<LogUpdateVO> voPage = MPageUtil.convertToVO(page, LogUpdateVO.class);
        return Result.success(voPage);
    }

    /**
     * 功能描述:
     * 〈获取最新更新日志〉
     * @return 正常返回:{@link Result<LogUpdateVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取最新更新日志")
    @GetMapping("/log/update/last")
    public Result<LogUpdateVO> getLastOne() {
        LogUpdate logUpdate = logUpdateService.getLastOne(LoginTypeEnum.APP.getValue());
        return Result.success(BeanUtil.copyProperties(logUpdate, LogUpdateVO.class));
    }

    /**
     * 功能描述:
     * 〈获取最新更新日志〉
     * @return 正常返回:{@link Result<LogUpdateVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取最新更新日志")
    @PostMapping("/log/update/last")
    public Result<LogUpdateVO> getLastOne(@RequestParam("logFlag") Integer logFlag) {
        LogUpdate logUpdate = logUpdateService.getLastOne(logFlag);
        return Result.success(BeanUtil.copyProperties(logUpdate, LogUpdateVO.class));
    }

    /**
     * 功能描述:
     * 〈新增更新日志〉
     * @param logDTO logDTO
     * @return 正常返回:{@link Result<LabelBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增更新日志")
    @PostMapping("/log/update/add")
    public Result<Boolean> addLog(@Validated @RequestBody LogUpdateDTO logDTO) {
        logUpdateService.addLog(logDTO);
        return Result.success(Boolean.TRUE);
    }

    /**
     * 功能描述:
     * 〈修改更新日志〉
     * @param logDTO logDTO
     * @return 正常返回:{@link Result<LabelBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改更新日志")
    @PostMapping("/log/update/edit")
    public Result<Boolean> editLog(@Validated @RequestBody LogUpdateDTO logDTO) {
        logUpdateService.editLog(logDTO);
        return Result.success(Boolean.TRUE);
    }

    /**
     * 功能描述:
     * 〈删除更新日志〉
     * @param logId logId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除更新日志")
    @PostMapping("/log/update/delete/{logId}")
    public Result<Boolean> deleteBase(@PathVariable(value = "logId",required = false)Long logId) {
        return Result.success(logUpdateService.removeById(logId));
    }

}
