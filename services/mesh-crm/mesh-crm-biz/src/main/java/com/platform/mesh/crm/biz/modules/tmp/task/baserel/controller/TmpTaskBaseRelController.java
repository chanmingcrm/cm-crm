package com.platform.mesh.crm.biz.modules.tmp.task.baserel.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.domain.dto.TmpTaskBaseRelDTO;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.domain.po.TmpTaskBaseRel;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.domain.vo.TmpTaskBaseRelVO;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.service.ITmpTaskBaseRelService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 任务数据关联信息
 * @author 蝉鸣
 */
@Tag(description = "TaskDataRelController", name = "任务数据关联")
@RestController
@RequestMapping
public class TmpTaskBaseRelController extends BaseController{
    @Autowired
    private ITmpTaskBaseRelService taskDataRelService;

    /**
	 * 功能描述:
	 * 〈获取任务数据关联列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<  TmpTaskBaseRelVO  >>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取任务数据关联分页")
	@PostMapping("/tmp/task/data/rel/page")
	public Result<PageVO<TmpTaskBaseRelVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<TmpTaskBaseRel> dataRelMPage = MPageUtil.pageEntityToMPage(pageDTO, TmpTaskBaseRel.class);
        MPage<TmpTaskBaseRel> page = taskDataRelService.page(dataRelMPage);
        PageVO<TmpTaskBaseRelVO> voPage = MPageUtil.convertToVO(page, TmpTaskBaseRelVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前任务数据关联信息〉
     * @param dataRelId dataRelId
     * @return 正常返回:{@link Result<  TmpTaskBaseRelVO  >}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前任务数据关联信息")
    @GetMapping("/tmp/task/data/rel/info/{dataRelId}")
    public Result<TmpTaskBaseRelVO> getDataRelInfoById(@PathVariable("dataRelId")Long dataRelId) {
        TmpTaskBaseRelVO tmpTaskBaseRelVO = taskDataRelService.getDataRelInfoById(dataRelId);
        return Result.success(tmpTaskBaseRelVO);
    }

    /**
     * 功能描述:
     * 〈新增任务数据关联〉
     * @param dataRelDTO dataRelDTO
     * @return 正常返回:{@link Result<  TmpTaskBaseRelVO  >}
     * @author 蝉鸣
     */
    @Operation(summary = "新增任务数据关联")
    @Log(moduleName = "任务数据关联管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/tmp/task/data/rel/add")
    public Result<TmpTaskBaseRelVO> addDataRel(@Validated @RequestBody TmpTaskBaseRelDTO dataRelDTO) {
        return Result.success(taskDataRelService.addDataRel(dataRelDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除任务数据关联〉
     * @param dataRelId dataRelId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除任务数据关联")
    @Log(moduleName = "任务数据关联管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/tmp/task/data/rel/delete/{dataRelId}")
    public Result<Boolean> deleteDataRel(@PathVariable(value = "dataRelId",required = false)Long dataRelId) {
        return Result.success(taskDataRelService.deleteDataRel(dataRelId));
    }

}