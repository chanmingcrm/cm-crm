package com.platform.mesh.tmp.biz.modules.task.base.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddCompDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataDelDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.app.api.modules.app.domain.dto.TransScopeDTO;
import com.platform.mesh.es.domain.dto.EsDocEGetDTO;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.es.domain.dto.EsDocSGetDTO;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.tmp.biz.modules.task.base.domain.po.TaskBase;
import com.platform.mesh.tmp.biz.modules.task.base.domain.vo.TaskBaseVO;
import com.platform.mesh.tmp.biz.modules.task.base.service.ITaskBaseService;
import com.platform.mesh.tmp.biz.modules.task.basedata.domain.po.TaskBaseData;
import com.platform.mesh.utils.excel.ExcelUtil;
import com.platform.mesh.utils.excel.dto.HeadDTO;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 约定当前controller 只引入当前service
 * @description 任务信息
 * @author 蝉鸣
 */
@Tag(description = "TaskBaseController", name = "任务")
@RestController
@RequestMapping
public class TaskBaseController extends BaseController{
    @Autowired
    private ITaskBaseService taskBaseService;

    /**
     * 功能描述:
     * 〈获取任务列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<Object>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取任务分页")
    @PostMapping("/task/base/page")
    public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = taskBaseService.selectEsPage(pageDTO);
        return Result.success(page);
    }

    /**
     * 功能描述:
     * 〈获取当前任务信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<TaskBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前任务信息")
    @PostMapping("/task/base/info")
    public Result<TaskBaseVO> getTaskBaseInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        TaskBaseVO crmTaskBaseVO = taskBaseService.getDataInfoById(esDocSGetDTO,TaskBaseVO.class);
        return Result.success(crmTaskBaseVO);
    }

    /**
     * 功能描述:
     * 〈新增任务〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<TaskBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增任务")
    @Log(moduleName = "任务管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/task/base/add/simp")
    public Result<TaskBaseVO> addTaskBaseSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        TaskBase taskBase = taskBaseService.addDataSimp(dataAddSimpDTO, TaskBase.class, TaskBaseData.class);
        return Result.success(BeanUtil.copyProperties(taskBase,TaskBaseVO.class));
    }

    /**
     * 功能描述:
     * 〈新增任务〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<TaskBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增任务")
    @Log(moduleName = "任务管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/task/base/add/comp")
    public Result<TaskBaseVO> addTaskBaseComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        TaskBase taskBase = taskBaseService.addDataComp(dataAddCompDTO, TaskBase.class, TaskBaseData.class);
        return Result.success(BeanUtil.copyProperties(taskBase,TaskBaseVO.class));
    }

    /**
     * 功能描述:
     * 〈修改任务〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<TaskBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改任务")
    @Log(moduleName = "任务管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/task/base/edit")
    public Result<TaskBaseVO> editTaskBase(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        TaskBase taskBase = taskBaseService.editData(dataEditDTO, TaskBase.class);
        return Result.success(BeanUtil.copyProperties(taskBase,TaskBaseVO.class));
    }

    /**
     * 功能描述:
     * 〈删除任务〉
     * @param dataId dataId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除任务")
    @Log(moduleName = "任务管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/task/base/delete/{dataId}")
    public Result<Boolean> deleteTaskBase(@PathVariable(value = "dataId",required = false)Long dataId) {
        return Result.success(taskBaseService.deleteData(dataId));
    }

    /**
     * 功能描述:
     * 〈批量删除任务〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除任务")
    @Log(moduleName = "任务管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/task/base/batch/delete")
    public Result<Boolean> deleteTaskBase(@RequestBody DataDelDTO delDTO) {
        return Result.success(taskBaseService.deleteData(delDTO));
    }

    /**
     * 功能描述:
     * 〈转移任务〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移任务")
    @Log(moduleName = "任务管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/task/base/trans/scope")
    public Result<Boolean> transTaskBase(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(taskBaseService.transData(transScopeDTO));
    }

    /**
     * 功能描述:
     * 〈导入任务模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入任务模板")
    @Log(moduleName = "任务管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/task/base/import/temp")
    public void importTaskBaseTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"任务导入模板",response);
    }

    /**
     * 功能描述:
     * 〈导入任务〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入任务")
    @Log(moduleName = "任务管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/task/base/import")
    public Result<Boolean> importTaskBase(@RequestParam("moduleId") Long moduleId,@RequestParam("formId") Long formId,@RequestParam("file") MultipartFile file) {
        return Result.success(taskBaseService.importData(moduleId,formId,file, TaskBase.class, TaskBaseData.class));
    }

    /**
     * 功能描述:
     * 〈导出任务〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出任务")
    @Log(moduleName = "任务管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/task/base/export")
    public void exportTaskBase(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                    exportDTO.setPageNum(pageNum);
                    return taskBaseService.selectEsPage(exportDTO);
                } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}