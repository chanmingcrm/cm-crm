package com.platform.mesh.crm.biz.modules.tmp.task.base.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.api.modules.app.domain.vo.ImportVO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.tmp.task.base.domain.dto.TmpTaskBaseDTO;
import com.platform.mesh.crm.biz.modules.tmp.task.base.domain.po.TmpTaskBase;
import com.platform.mesh.crm.biz.modules.tmp.task.base.domain.vo.TmpTaskBaseVO;
import com.platform.mesh.crm.biz.modules.tmp.task.base.service.ITmpTaskBaseService;
import com.platform.mesh.crm.biz.modules.tmp.task.basedata.domain.po.TmpTaskBaseData;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.domain.dto.TmpTaskBaseRelPageDTO;
import com.platform.mesh.es.domain.dto.EsDocEGetDTO;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.es.domain.dto.EsDocSGetDTO;
import com.platform.mesh.log.annotation.Log;
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
public class TmpTaskBaseController extends BaseController{
    @Autowired
    private ITmpTaskBaseService taskBaseService;

    /**
     * 功能描述:
     * 〈获取任务列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<Object>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取任务分页")
    @PostMapping("/tmp/task/base/page")
    public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = taskBaseService.selectEsPage(pageDTO);
        return Result.success(page);
    }

    /**
     * 功能描述:
     * 〈获取当前任务信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result< TmpTaskBaseVO >}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前任务信息")
    @PostMapping("/tmp/task/base/info")
    public Result<TmpTaskBaseVO> getTaskBaseInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        TmpTaskBaseVO tmpTaskBaseVO = taskBaseService.getDataInfoById(esDocSGetDTO, TmpTaskBaseVO.class);
        return Result.success(tmpTaskBaseVO);
    }

    /**
     * 功能描述:
     * 〈新增任务〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result< TmpTaskBaseVO >}
     * @author 蝉鸣
     */
    @Operation(summary = "新增任务")
    @Log(moduleName = "任务管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/tmp/task/base/add/simp")
    public Result<TmpTaskBaseVO> addTaskBaseSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        TmpTaskBase tmpTaskBase = taskBaseService.addDataSimp(dataAddSimpDTO, TmpTaskBase.class, TmpTaskBaseData.class);
        return Result.success(BeanUtil.copyProperties(tmpTaskBase, TmpTaskBaseVO.class));
    }

    /**
     * 功能描述:
     * 〈新增任务〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result< TmpTaskBaseVO >}
     * @author 蝉鸣
     */
    @Operation(summary = "新增任务")
    @Log(moduleName = "任务管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/tmp/task/base/add/comp")
    public Result<TmpTaskBaseVO> addTaskBaseComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        TmpTaskBase tmpTaskBase = taskBaseService.addDataComp(dataAddCompDTO, TmpTaskBase.class, TmpTaskBaseData.class);
        return Result.success(BeanUtil.copyProperties(tmpTaskBase, TmpTaskBaseVO.class));
    }

    /**
     * 功能描述:
     * 〈修改任务〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result< TmpTaskBaseVO >}
     * @author 蝉鸣
     */
    @Operation(summary = "修改任务")
    @Log(moduleName = "任务管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/tmp/task/base/edit")
    public Result<TmpTaskBaseVO> editTaskBase(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        TmpTaskBase tmpTaskBase = taskBaseService.editData(dataEditDTO, TmpTaskBase.class, TmpTaskBaseData.class);
        return Result.success(BeanUtil.copyProperties(tmpTaskBase, TmpTaskBaseVO.class));
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
    @PostMapping("/tmp/task/base/delete/{dataId}")
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
    @PostMapping("/tmp/task/base/batch/delete")
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
    @PostMapping("/tmp/task/base/trans/scope")
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
    @PostMapping("/tmp/task/base/import/temp")
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
    @PostMapping("/tmp/task/base/import")
    public Result<ImportVO> importTaskBase(@RequestParam("moduleId") Long moduleId, @RequestParam("formId") Long formId, @RequestParam("file") MultipartFile file) {
        DataImportDTO importDTO = new DataImportDTO();
        importDTO.setModuleId(moduleId);
        importDTO.setFormId(formId);
        importDTO.setFile(file);
        return Result.success(taskBaseService.importData(importDTO, TmpTaskBase.class, TmpTaskBaseData.class));
    }

    /**
     * 功能描述:
     * 〈导出任务〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出任务")
    @Log(moduleName = "任务管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/tmp/task/base/export")
    public void exportTaskBase(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                    exportDTO.setPageNum(pageNum);
                    return taskBaseService.selectEsPage(exportDTO);
                } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }

    /**
     * 功能描述:
     * 〈关联任务分页查询〉
     * @param tmpTaskBaseDTO taskBaseDTO
     * @return 正常返回:{@link Result< TmpTaskBaseVO >}
     * @author 蝉鸣
     */
    @Operation(summary = "新增关联任务")
    @PostMapping("/tmp/task/base/add/and/rel")
    public Result<TmpTaskBaseVO> addTaskBaseAndRel (@RequestBody TmpTaskBaseDTO tmpTaskBaseDTO) {
        TmpTaskBase tmpTaskBase = taskBaseService.addTaskBaseAndRel(tmpTaskBaseDTO);
        return Result.success(BeanUtil.copyProperties(tmpTaskBase, TmpTaskBaseVO.class));
    }

    /**
     * 功能描述:
     * 〈新增关联任务〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result< TmpTaskBaseVO >}
     * @author 蝉鸣
     */
    @Operation(summary = "关联任务分页查询")
    @PostMapping("/tmp/task/base/page/and/rel")
    public Result<PageVO<TmpTaskBaseVO>> taskBaseAndRelPage (@RequestBody TmpTaskBaseRelPageDTO pageDTO) {
        PageVO<TmpTaskBaseVO> page = taskBaseService.taskBaseAndRelPage(pageDTO);
        return Result.success(page);
    }

}