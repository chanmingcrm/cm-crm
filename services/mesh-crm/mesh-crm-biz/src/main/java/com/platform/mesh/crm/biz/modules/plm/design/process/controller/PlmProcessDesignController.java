package com.platform.mesh.crm.biz.modules.plm.design.process.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.api.modules.app.domain.vo.ImportVO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.plm.design.process.domain.po.PlmProcessDesign;
import com.platform.mesh.crm.biz.modules.plm.design.process.domain.vo.PlmProcessDesignVO;
import com.platform.mesh.crm.biz.modules.plm.design.process.service.IPlmProcessDesignService;
import com.platform.mesh.crm.biz.modules.plm.design.processdata.domain.po.PlmProcessDesignData;
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
 * @description 工序设计
 * @author 蝉鸣
 */
@Tag(description = "PlmProcessDesignController", name = "工序设计")
@RestController
@RequestMapping
public class PlmProcessDesignController extends BaseController {

    @Autowired
    private IPlmProcessDesignService plmProcessdesignService;

    /**
     * 功能描述:
     * 【获取工序设计列表】
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<Object>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取工序设计分页")
    @PostMapping("/plm/design/process/page")
    public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = plmProcessdesignService.selectEsPage(pageDTO);
        return Result.success(page);
    }

    /**
     * 功能描述:
     * 【获取当前工序设计信息】
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<PlmProcessDesignVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前工序设计信息")
    @PostMapping("/plm/design/process/info")
    public Result<PlmProcessDesignVO> getProcessdesignInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        PlmProcessDesignVO vo = plmProcessdesignService.getDataInfoById(esDocSGetDTO, PlmProcessDesignVO.class);
        return Result.success(vo);
    }

    /**
     * 功能描述:
     * 【新增工序设计】
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<PlmProcessDesignVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增工序设计")
    @Log(moduleName = "工序设计管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/plm/design/process/add/simp")
    public Result<PlmProcessDesignVO> addProcessdesignSimp(@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        PlmProcessDesign data = plmProcessdesignService.addDataSimp(dataAddSimpDTO, PlmProcessDesign.class, PlmProcessDesignData.class);
        return Result.success(BeanUtil.copyProperties(data, PlmProcessDesignVO.class));
    }

    /**
     * 功能描述:
     * 【修改工序设计】
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<PlmProcessDesignVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改工序设计")
    @Log(moduleName = "工序设计管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/plm/design/process/edit")
    public Result<PlmProcessDesignVO> editProcessdesign(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        PlmProcessDesign data = plmProcessdesignService.editData(dataEditDTO, PlmProcessDesign.class, PlmProcessDesignData.class);
        return Result.success(BeanUtil.copyProperties(data, PlmProcessDesignVO.class));
    }

    /**
     * 功能描述:
     * 【批量删除工序设计】
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除工序设计")
    @Log(moduleName = "工序设计管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/plm/design/process/batch/delete")
    public Result<Boolean> deleteProcessdesign(@RequestBody DataDelDTO delDTO) {
        return Result.success(plmProcessdesignService.deleteData(delDTO));
    }

    /**
     * 功能描述:
     * 【转移工序设计】
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移工序设计")
    @Log(moduleName = "工序设计管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/plm/design/process/trans/scope")
    public Result<Boolean> transProcessdesign(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(plmProcessdesignService.transData(transScopeDTO));
    }

    /**
     * 功能描述:
     * 【导入工序设计模板】
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入工序设计模板")
    @Log(moduleName = "工序设计管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/plm/design/process/import/temp")
    public void importProcessdesignTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS, "工序设计导入模板", response);
    }

    /**
     * 功能描述:
     * 【导入工序设计】
     * @param moduleId moduleId
     * @param formId formId
     * @param file file
     * @return 正常返回:{@link Result<ImportVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入工序设计")
    @Log(moduleName = "工序设计管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/plm/design/process/import")
    public Result<ImportVO> importProcessdesign(@RequestParam("moduleId") Long moduleId, @RequestParam("formId") Long formId, @RequestParam("file") MultipartFile file) {
        DataImportDTO importDTO = new DataImportDTO();
        importDTO.setModuleId(moduleId);
        importDTO.setFormId(formId);
        importDTO.setFile(file);
        return Result.success(plmProcessdesignService.importData(importDTO, PlmProcessDesign.class, PlmProcessDesignData.class));
    }

    /**
     * 功能描述:
     * 【导出工序设计】
     * @param exportDTO exportDTO
     * @param response response
     * @author 蝉鸣
     */
    @Operation(summary = "导出工序设计")
    @Log(moduleName = "工序设计管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/plm/design/process/export")
    public void exportProcessdesign(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum -> {
            exportDTO.setPageNum(pageNum);
            return plmProcessdesignService.selectEsPage(exportDTO);
        }, exportDTO.getHeadDTOS(), exportDTO.getModuleName(), response);
    }
}
