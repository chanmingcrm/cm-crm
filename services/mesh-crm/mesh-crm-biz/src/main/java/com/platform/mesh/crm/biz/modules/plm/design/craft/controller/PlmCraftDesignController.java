package com.platform.mesh.crm.biz.modules.plm.design.craft.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.api.modules.app.domain.vo.ImportVO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.plm.design.craft.domain.po.PlmCraftDesign;
import com.platform.mesh.crm.biz.modules.plm.design.craft.domain.vo.PlmCraftDesignVO;
import com.platform.mesh.crm.biz.modules.plm.design.craft.service.IPlmCraftDesignService;
import com.platform.mesh.crm.biz.modules.plm.design.craftdata.domain.po.PlmCraftDesignData;
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
 * @description 工艺设计
 * @author 蝉鸣
 */
@Tag(description = "PlmCraftDesignController", name = "工艺设计")
@RestController
@RequestMapping
public class PlmCraftDesignController extends BaseController {

    @Autowired
    private IPlmCraftDesignService plmCraftdesignService;

    /**
     * 功能描述:
     * 【获取工艺设计列表】
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<Object>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取工艺设计分页")
    @PostMapping("/plm/design/craft/page")
    public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = plmCraftdesignService.selectEsPage(pageDTO);
        return Result.success(page);
    }

    /**
     * 功能描述:
     * 【获取当前工艺设计信息】
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<PlmCraftDesignVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前工艺设计信息")
    @PostMapping("/plm/design/craft/info")
    public Result<PlmCraftDesignVO> getCraftdesignInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        PlmCraftDesignVO vo = plmCraftdesignService.getDataInfoById(esDocSGetDTO, PlmCraftDesignVO.class);
        return Result.success(vo);
    }

    /**
     * 功能描述:
     * 【新增工艺设计】
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<PlmCraftDesignVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增工艺设计")
    @Log(moduleName = "工艺设计管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/plm/design/craft/add/simp")
    public Result<PlmCraftDesignVO> addCraftdesignSimp(@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        PlmCraftDesign data = plmCraftdesignService.addDataSimp(dataAddSimpDTO, PlmCraftDesign.class, PlmCraftDesignData.class);
        return Result.success(BeanUtil.copyProperties(data, PlmCraftDesignVO.class));
    }

    /**
     * 功能描述:
     * 【修改工艺设计】
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<PlmCraftDesignVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改工艺设计")
    @Log(moduleName = "工艺设计管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/plm/design/craft/edit")
    public Result<PlmCraftDesignVO> editCraftdesign(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        PlmCraftDesign data = plmCraftdesignService.editData(dataEditDTO, PlmCraftDesign.class, PlmCraftDesignData.class);
        return Result.success(BeanUtil.copyProperties(data, PlmCraftDesignVO.class));
    }

    /**
     * 功能描述:
     * 【批量删除工艺设计】
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除工艺设计")
    @Log(moduleName = "工艺设计管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/plm/design/craft/batch/delete")
    public Result<Boolean> deleteCraftdesign(@RequestBody DataDelDTO delDTO) {
        return Result.success(plmCraftdesignService.deleteData(delDTO));
    }

    /**
     * 功能描述:
     * 【转移工艺设计】
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移工艺设计")
    @Log(moduleName = "工艺设计管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/plm/design/craft/trans/scope")
    public Result<Boolean> transCraftdesign(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(plmCraftdesignService.transData(transScopeDTO));
    }

    /**
     * 功能描述:
     * 【导入工艺设计模板】
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入工艺设计模板")
    @Log(moduleName = "工艺设计管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/plm/design/craft/import/temp")
    public void importCraftdesignTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS, "工艺设计导入模板", response);
    }

    /**
     * 功能描述:
     * 【导入工艺设计】
     * @param moduleId moduleId
     * @param formId formId
     * @param file file
     * @return 正常返回:{@link Result<ImportVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入工艺设计")
    @Log(moduleName = "工艺设计管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/plm/design/craft/import")
    public Result<ImportVO> importCraftdesign(@RequestParam("moduleId") Long moduleId, @RequestParam("formId") Long formId, @RequestParam("file") MultipartFile file) {
        DataImportDTO importDTO = new DataImportDTO();
        importDTO.setModuleId(moduleId);
        importDTO.setFormId(formId);
        importDTO.setFile(file);
        return Result.success(plmCraftdesignService.importData(importDTO, PlmCraftDesign.class, PlmCraftDesignData.class));
    }

    /**
     * 功能描述:
     * 【导出工艺设计】
     * @param exportDTO exportDTO
     * @param response response
     * @author 蝉鸣
     */
    @Operation(summary = "导出工艺设计")
    @Log(moduleName = "工艺设计管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/plm/design/craft/export")
    public void exportCraftdesign(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum -> {
            exportDTO.setPageNum(pageNum);
            return plmCraftdesignService.selectEsPage(exportDTO);
        }, exportDTO.getHeadDTOS(), exportDTO.getModuleName(), response);
    }
}
