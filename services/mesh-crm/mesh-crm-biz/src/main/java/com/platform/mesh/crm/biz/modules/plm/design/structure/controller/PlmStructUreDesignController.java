package com.platform.mesh.crm.biz.modules.plm.design.structure.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.api.modules.app.domain.vo.ImportVO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.plm.design.structure.domain.po.PlmStructUreDesign;
import com.platform.mesh.crm.biz.modules.plm.design.structure.domain.vo.PlmStructUreDesignVO;
import com.platform.mesh.crm.biz.modules.plm.design.structure.service.IPlmStructUreDesignService;
import com.platform.mesh.crm.biz.modules.plm.design.structuredata.domain.po.PlmStructUreDesignData;
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
 * @description 结构设计
 * @author 蝉鸣
 */
@Tag(description = "PlmStructUreDesignController", name = "结构设计")
@RestController
@RequestMapping
public class PlmStructUreDesignController extends BaseController {

    @Autowired
    private IPlmStructUreDesignService plmStructuredesignService;

    /**
     * 功能描述:
     * 【获取结构设计列表】
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<Object>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取结构设计分页")
    @PostMapping("/plm/design/structure/page")
    public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = plmStructuredesignService.selectEsPage(pageDTO);
        return Result.success(page);
    }

    /**
     * 功能描述:
     * 【获取当前结构设计信息】
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<PlmStructUreDesignVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前结构设计信息")
    @PostMapping("/plm/design/structure/info")
    public Result<PlmStructUreDesignVO> getStructuredesignInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        PlmStructUreDesignVO vo = plmStructuredesignService.getDataInfoById(esDocSGetDTO, PlmStructUreDesignVO.class);
        return Result.success(vo);
    }

    /**
     * 功能描述:
     * 【新增结构设计】
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<PlmStructUreDesignVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增结构设计")
    @Log(moduleName = "结构设计管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/plm/design/structure/add/simp")
    public Result<PlmStructUreDesignVO> addStructuredesignSimp(@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        PlmStructUreDesign data = plmStructuredesignService.addDataSimp(dataAddSimpDTO, PlmStructUreDesign.class, PlmStructUreDesignData.class);
        return Result.success(BeanUtil.copyProperties(data, PlmStructUreDesignVO.class));
    }

    /**
     * 功能描述:
     * 【修改结构设计】
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<PlmStructUreDesignVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改结构设计")
    @Log(moduleName = "结构设计管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/plm/design/structure/edit")
    public Result<PlmStructUreDesignVO> editStructuredesign(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        PlmStructUreDesign data = plmStructuredesignService.editData(dataEditDTO, PlmStructUreDesign.class, PlmStructUreDesignData.class);
        return Result.success(BeanUtil.copyProperties(data, PlmStructUreDesignVO.class));
    }

    /**
     * 功能描述:
     * 【批量删除结构设计】
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除结构设计")
    @Log(moduleName = "结构设计管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/plm/design/structure/batch/delete")
    public Result<Boolean> deleteStructuredesign(@RequestBody DataDelDTO delDTO) {
        return Result.success(plmStructuredesignService.deleteData(delDTO));
    }

    /**
     * 功能描述:
     * 【转移结构设计】
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移结构设计")
    @Log(moduleName = "结构设计管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/plm/design/structure/trans/scope")
    public Result<Boolean> transStructuredesign(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(plmStructuredesignService.transData(transScopeDTO));
    }

    /**
     * 功能描述:
     * 【导入结构设计模板】
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入结构设计模板")
    @Log(moduleName = "结构设计管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/plm/design/structure/import/temp")
    public void importStructuredesignTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS, "结构设计导入模板", response);
    }

    /**
     * 功能描述:
     * 【导入结构设计】
     * @param moduleId moduleId
     * @param formId formId
     * @param file file
     * @return 正常返回:{@link Result<ImportVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入结构设计")
    @Log(moduleName = "结构设计管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/plm/design/structure/import")
    public Result<ImportVO> importStructuredesign(@RequestParam("moduleId") Long moduleId, @RequestParam("formId") Long formId, @RequestParam("file") MultipartFile file) {
        DataImportDTO importDTO = new DataImportDTO();
        importDTO.setModuleId(moduleId);
        importDTO.setFormId(formId);
        importDTO.setFile(file);
        return Result.success(plmStructuredesignService.importData(importDTO, PlmStructUreDesign.class, PlmStructUreDesignData.class));
    }

    /**
     * 功能描述:
     * 【导出结构设计】
     * @param exportDTO exportDTO
     * @param response response
     * @author 蝉鸣
     */
    @Operation(summary = "导出结构设计")
    @Log(moduleName = "结构设计管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/plm/design/structure/export")
    public void exportStructuredesign(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum -> {
            exportDTO.setPageNum(pageNum);
            return plmStructuredesignService.selectEsPage(exportDTO);
        }, exportDTO.getHeadDTOS(), exportDTO.getModuleName(), response);
    }
}
