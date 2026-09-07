package com.platform.mesh.crm.biz.modules.plm.product.category.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.api.modules.app.domain.vo.ImportVO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.plm.product.category.domain.po.PlmProductCategory;
import com.platform.mesh.crm.biz.modules.plm.product.category.domain.vo.PlmProductCategoryVO;
import com.platform.mesh.crm.biz.modules.plm.product.category.service.IPlmProductCategoryService;
import com.platform.mesh.crm.biz.modules.plm.product.categorydata.domain.po.PlmProductCategoryData;
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
 * @description 产品大类
 * @author 蝉鸣
 */
@Tag(description = "PlmProductBigCategoryController", name = "产品大类")
@RestController
@RequestMapping
public class PlmProductCategoryController extends BaseController {

    @Autowired
    private IPlmProductCategoryService plmProductbigcategoryService;

    /**
     * 功能描述:
     * 【获取产品大类列表】
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<Object>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取产品大类分页")
    @PostMapping("/plm/product/category/page")
    public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = plmProductbigcategoryService.selectEsPage(pageDTO);
        return Result.success(page);
    }

    /**
     * 功能描述:
     * 【获取当前产品大类信息】
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result< PlmProductCategoryVO >}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前产品大类信息")
    @PostMapping("/plm/product/category/info")
    public Result<PlmProductCategoryVO> getProductbigcategoryInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        PlmProductCategoryVO vo = plmProductbigcategoryService.getDataInfoById(esDocSGetDTO, PlmProductCategoryVO.class);
        return Result.success(vo);
    }

    /**
     * 功能描述:
     * 【新增产品大类】
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result< PlmProductCategoryVO >}
     * @author 蝉鸣
     */
    @Operation(summary = "新增产品大类")
    @Log(moduleName = "产品大类管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/plm/product/category/add/simp")
    public Result<PlmProductCategoryVO> addProductbigcategorySimp(@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        PlmProductCategory data = plmProductbigcategoryService.addDataSimp(dataAddSimpDTO, PlmProductCategory.class, PlmProductCategoryData.class);
        return Result.success(BeanUtil.copyProperties(data, PlmProductCategoryVO.class));
    }

    /**
     * 功能描述:
     * 【修改产品大类】
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result< PlmProductCategoryVO >}
     * @author 蝉鸣
     */
    @Operation(summary = "修改产品大类")
    @Log(moduleName = "产品大类管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/plm/product/category/edit")
    public Result<PlmProductCategoryVO> editProductbigcategory(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        PlmProductCategory data = plmProductbigcategoryService.editData(dataEditDTO, PlmProductCategory.class, PlmProductCategoryData.class);
        return Result.success(BeanUtil.copyProperties(data, PlmProductCategoryVO.class));
    }

    /**
     * 功能描述:
     * 【批量删除产品大类】
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除产品大类")
    @Log(moduleName = "产品大类管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/plm/product/category/batch/delete")
    public Result<Boolean> deleteProductbigcategory(@RequestBody DataDelDTO delDTO) {
        return Result.success(plmProductbigcategoryService.deleteData(delDTO));
    }

    /**
     * 功能描述:
     * 【转移产品大类】
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移产品大类")
    @Log(moduleName = "产品大类管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/plm/product/category/trans/scope")
    public Result<Boolean> transProductbigcategory(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(plmProductbigcategoryService.transData(transScopeDTO));
    }

    /**
     * 功能描述:
     * 【导入产品大类模板】
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入产品大类模板")
    @Log(moduleName = "产品大类管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/plm/product/category/import/temp")
    public void importProductbigcategoryTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS, "产品大类导入模板", response);
    }

    /**
     * 功能描述:
     * 【导入产品大类】
     * @param moduleId moduleId
     * @param formId formId
     * @param file file
     * @return 正常返回:{@link Result<ImportVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入产品大类")
    @Log(moduleName = "产品大类管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/plm/product/category/import")
    public Result<ImportVO> importProductbigcategory(@RequestParam("moduleId") Long moduleId, @RequestParam("formId") Long formId, @RequestParam("file") MultipartFile file) {
        DataImportDTO importDTO = new DataImportDTO();
        importDTO.setModuleId(moduleId);
        importDTO.setFormId(formId);
        importDTO.setFile(file);
        return Result.success(plmProductbigcategoryService.importData(importDTO, PlmProductCategory.class, PlmProductCategoryData.class));
    }

    /**
     * 功能描述:
     * 【导出产品大类】
     * @param exportDTO exportDTO
     * @param response response
     * @author 蝉鸣
     */
    @Operation(summary = "导出产品大类")
    @Log(moduleName = "产品大类管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/plm/product/category/export")
    public void exportProductbigcategory(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum -> {
            exportDTO.setPageNum(pageNum);
            return plmProductbigcategoryService.selectEsPage(exportDTO);
        }, exportDTO.getHeadDTOS(), exportDTO.getModuleName(), response);
    }
}
