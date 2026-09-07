package com.platform.mesh.bpm.biz.modules.temp.process.controller;

import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessEditDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessPageDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.vo.BpmTempProcessDesignVO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.vo.BpmTempProcessVO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessDesignDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessAddDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.po.BpmTempProcess;
import com.platform.mesh.bpm.biz.modules.temp.process.service.IBpmTempProcessService;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 约定当前controller 只引入当前service
 * @description 流程过程信息
 * @author 蝉鸣
 */
@Tag(description = "BpmTempProcessController", name = "模板流程过程信息")
@RestController
public class BpmTempProcessController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IBpmTempProcessService bpmTempProcessService;

    /**
     * 功能描述:
     * 〈流程模板分页〉
     * @return 正常返回:{@link Result<BpmTempProcess>}
     * @author 蝉鸣
     */
    @Operation(summary = "流程模板分页")
    @PostMapping("/temp/process/page")
    public Result<PageVO<BpmTempProcessVO>> selectPage(@RequestBody BpmTempProcessPageDTO pageDTO) {
        PageVO<BpmTempProcessVO> tempProcessPage = bpmTempProcessService.selectPage(pageDTO);
        return Result.success(tempProcessPage);
    }

    /**
     * 功能描述:
     * 〈添加流程模板〉
     * @return 正常返回:{@link Result<BpmTempProcess>}
     * @author 蝉鸣
     */
    @Operation(summary = "添加流程模板")
    @PostMapping("/temp/process/add")
    public Result<BpmTempProcess> addProcessTemp(@RequestBody BpmTempProcessAddDTO bpmTempProcessAddDTO) {
        BpmTempProcess tempProcess = bpmTempProcessService.addProcessTemp(bpmTempProcessAddDTO);
        return Result.success(tempProcess);
    }

    /**
     * 功能描述:
     * 〈编辑流程模板〉
     * @return 正常返回:{@link Result<BpmTempProcess>}
     * @author 蝉鸣
     */
    @Operation(summary = "编辑流程模板")
    @PostMapping("/temp/process/edit")
    public Result<BpmTempProcess> editProcessTemp(@RequestBody BpmTempProcessEditDTO bpmTempProcessEditDTO) {
        BpmTempProcess tempProcess = bpmTempProcessService.editProcessTemp(bpmTempProcessEditDTO);
        return Result.success(tempProcess);
    }

    /**
     * 功能描述:
     * 〈设计流程模板〉
     * @return 正常返回:{@link Result<BpmTempProcess>}
     * @author 蝉鸣
     */
    @Operation(summary = "设计流程模板")
    @PostMapping("/temp/process/design")
    public Result<BpmTempProcess> designProcessTemp(@RequestBody BpmTempProcessDesignDTO bpmTempProcessDesignDTO) {
        BpmTempProcess tempProcess = bpmTempProcessService.designProcessTemp(bpmTempProcessDesignDTO);
        return Result.success(tempProcess);
    }

    /**
     * 功能描述:
     * 〈获取流程模板〉
     * @return 正常返回:{@link Result<BpmTempProcess>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取流程模板")
    @GetMapping("/temp/process/get/{tempProcessId}")
    public Result<BpmTempProcessDesignVO> getProcessTemp(@PathVariable("tempProcessId")Long tempProcessId) {
        BpmTempProcessDesignVO tempProcessDesignVO = bpmTempProcessService.getProcessTemp(tempProcessId);
        return Result.success(tempProcessDesignVO);
    }

    /**
     * 功能描述:
     * 〈发布流程模板〉
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "发布流程模板")
    @GetMapping("/temp/process/pub/{tempProcessId}")
    public Result<Boolean> pubProcessTemp(@PathVariable("tempProcessId")Long tempProcessId) {
        return Result.success(bpmTempProcessService.pubProcessTemp(tempProcessId));
    }

    /**
     * 功能描述:
     * 〈作废流程模板〉
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "作废流程模板")
    @GetMapping("/temp/process/cancel/{tempProcessId}")
    public Result<Boolean> cancelProcessTemp(@PathVariable("tempProcessId")Long tempProcessId) {
        return Result.success(bpmTempProcessService.cancelProcessTemp(tempProcessId));
    }

    /**
     * 功能描述:
     * 〈删除流程模板〉
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除流程模板")
    @GetMapping("/temp/process/delete/{tempProcessId}")
    public Result<Boolean> deleteProcessTemp(@PathVariable("tempProcessId")Long tempProcessId) {
        return Result.success(bpmTempProcessService.delProcessTemp(tempProcessId));
    }
  
}
