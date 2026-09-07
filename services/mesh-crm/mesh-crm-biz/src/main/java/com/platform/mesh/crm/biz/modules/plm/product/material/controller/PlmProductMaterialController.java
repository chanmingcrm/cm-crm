package com.platform.mesh.crm.biz.modules.plm.product.material.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.api.modules.app.domain.vo.ImportVO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.plm.product.material.domain.po.PlmProductMaterial;
import com.platform.mesh.crm.biz.modules.plm.product.material.domain.vo.PlmProductMaterialVO;
import com.platform.mesh.crm.biz.modules.plm.product.material.service.IPlmProductMaterialService;
import com.platform.mesh.crm.biz.modules.plm.product.materialdata.domain.po.PlmProductMaterialData;
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
 * @description 产品物料
 * @author 蝉鸣
 */
@Tag(description = "PlmProductMaterialController", name = "产品物料")
@RestController
@RequestMapping
public class PlmProductMaterialController extends BaseController {

    @Autowired
    private IPlmProductMaterialService plmProductmaterialService;

    /**
     * 功能描述:
     * 【获取产品物料列表】
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<Object>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取产品物料分页")
    @PostMapping("/plm/product/material/page")
    public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = plmProductmaterialService.selectEsPage(pageDTO);
        return Result.success(page);
    }

    /**
     * 功能描述:
     * 【获取当前产品物料信息】
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<PlmProductMaterialVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前产品物料信息")
    @PostMapping("/plm/product/material/info")
    public Result<PlmProductMaterialVO> getProductmaterialInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        PlmProductMaterialVO vo = plmProductmaterialService.getDataInfoById(esDocSGetDTO, PlmProductMaterialVO.class);
        return Result.success(vo);
    }

    /**
     * 功能描述:
     * 【新增产品物料】
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<PlmProductMaterialVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增产品物料")
    @Log(moduleName = "产品物料管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/plm/product/material/add/simp")
    public Result<PlmProductMaterialVO> addProductmaterialSimp(@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        PlmProductMaterial data = plmProductmaterialService.addDataSimp(dataAddSimpDTO, PlmProductMaterial.class, PlmProductMaterialData.class);
        return Result.success(BeanUtil.copyProperties(data, PlmProductMaterialVO.class));
    }

    /**
     * 功能描述:
     * 【修改产品物料】
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<PlmProductMaterialVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改产品物料")
    @Log(moduleName = "产品物料管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/plm/product/material/edit")
    public Result<PlmProductMaterialVO> editProductmaterial(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        PlmProductMaterial data = plmProductmaterialService.editData(dataEditDTO, PlmProductMaterial.class, PlmProductMaterialData.class);
        return Result.success(BeanUtil.copyProperties(data, PlmProductMaterialVO.class));
    }

    /**
     * 功能描述:
     * 【批量删除产品物料】
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除产品物料")
    @Log(moduleName = "产品物料管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/plm/product/material/batch/delete")
    public Result<Boolean> deleteProductmaterial(@RequestBody DataDelDTO delDTO) {
        return Result.success(plmProductmaterialService.deleteData(delDTO));
    }

    /**
     * 功能描述:
     * 【转移产品物料】
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移产品物料")
    @Log(moduleName = "产品物料管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/plm/product/material/trans/scope")
    public Result<Boolean> transProductmaterial(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(plmProductmaterialService.transData(transScopeDTO));
    }

    /**
     * 功能描述:
     * 【导入产品物料模板】
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入产品物料模板")
    @Log(moduleName = "产品物料管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/plm/product/material/import/temp")
    public void importProductmaterialTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS, "产品物料导入模板", response);
    }

    /**
     * 功能描述:
     * 【导入产品物料】
     * @param moduleId moduleId
     * @param formId formId
     * @param file file
     * @return 正常返回:{@link Result<ImportVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入产品物料")
    @Log(moduleName = "产品物料管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/plm/product/material/import")
    public Result<ImportVO> importProductmaterial(@RequestParam("moduleId") Long moduleId, @RequestParam("formId") Long formId, @RequestParam("file") MultipartFile file) {
        DataImportDTO importDTO = new DataImportDTO();
        importDTO.setModuleId(moduleId);
        importDTO.setFormId(formId);
        importDTO.setFile(file);
        return Result.success(plmProductmaterialService.importData(importDTO, PlmProductMaterial.class, PlmProductMaterialData.class));
    }

    /**
     * 功能描述:
     * 【导出产品物料】
     * @param exportDTO exportDTO
     * @param response response
     * @author 蝉鸣
     */
    @Operation(summary = "导出产品物料")
    @Log(moduleName = "产品物料管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/plm/product/material/export")
    public void exportProductmaterial(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum -> {
            exportDTO.setPageNum(pageNum);
            return plmProductmaterialService.selectEsPage(exportDTO);
        }, exportDTO.getHeadDTOS(), exportDTO.getModuleName(), response);
    }
}
