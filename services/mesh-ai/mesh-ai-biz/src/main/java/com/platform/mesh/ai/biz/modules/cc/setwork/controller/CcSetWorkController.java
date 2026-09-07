package com.platform.mesh.ai.biz.modules.cc.setwork.controller;

import com.platform.mesh.ai.biz.modules.cc.setwork.domain.dto.CcSetWorkDTO;
import com.platform.mesh.ai.biz.modules.cc.setwork.domain.po.CcSetWork;
import com.platform.mesh.ai.biz.modules.cc.setwork.domain.vo.CcSetWorkVO;
import com.platform.mesh.ai.biz.modules.cc.setwork.service.ICcSetWorkService;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 约定当前controller 只引入当前service
 * @description 排班
 * @author 蝉鸣
 */
@Tag(description = "CcSetWorkController", name = "排班")
@RestController
@RequestMapping
public class CcSetWorkController {

    @Autowired
    private ICcSetWorkService ccSetWorkService;

    /**
     * 功能描述:
     * 〈获取排班列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result <PageVO<CcSetWorkVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取排班分页")
    @PostMapping("/cc/set/work/page")
    public Result<PageVO<CcSetWorkVO>> selectPage(@RequestBody PageDTO pageDTO) {
        MPage<CcSetWork> mPage = MPageUtil.pageEntityToMPage(pageDTO, CcSetWork.class);
        MPage<CcSetWork> page = ccSetWorkService.lambdaQuery().page(mPage);
        return Result.success(MPageUtil.convertToVO(page, CcSetWorkVO.class));
    }

    /**
     * 功能描述:
     * 〈获取当前排班信息〉
     * @param workId workId
     * @return 正常返回:{@link Result<CcSetWorkVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前排班信息")
    @GetMapping("/cc/set/work/info/{workId}")
    public Result<CcSetWorkVO> getCcSetWorkInfoById(@PathVariable("workId")Long workId) {
        CcSetWorkVO aiMcpVO = ccSetWorkService.getCcSetWorkById(workId);
        return Result.success(aiMcpVO);
    }

    /**
     * 功能描述:
     * 〈新增排班〉
     * @param aiMcpDTO aiMcpDTO
     * @return 正常返回:{@link Result<CcSetWorkVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增排班")
    @Log(moduleName = "排班管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/cc/set/work/add")
    public Result<CcSetWorkVO> addCcSetWork(@Validated @RequestBody CcSetWorkDTO aiMcpDTO) {
        return Result.success(ccSetWorkService.addCcSetWork(aiMcpDTO));
    }

    /**
     * 功能描述:
     * 〈修改排班〉
     * @param aiMcpDTO aiMcpDTO
     * @return 正常返回:{@link Result<CcSetWorkVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改排班")
    @Log(moduleName = "排班管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/cc/set/work/edit")
    public Result<CcSetWorkVO> editCcSetWork(@Validated @RequestBody CcSetWorkDTO aiMcpDTO) {
        return Result.success(ccSetWorkService.editCcSetWork(aiMcpDTO));
    }

    /**
     * 功能描述:
     * 〈删除排班〉
     * @param workId workId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除排班")
    @Log(moduleName = "排班管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/cc/set/work/delete/{workId}")
    public Result<Boolean> deleteCcSetWork(@PathVariable(value = "workId",required = false)Long workId) {
        return Result.success(ccSetWorkService.deleteCcSetWork(workId));
    }
}
