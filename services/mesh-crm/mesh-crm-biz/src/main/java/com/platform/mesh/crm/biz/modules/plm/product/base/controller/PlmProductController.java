package com.platform.mesh.crm.biz.modules.plm.product.base.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.api.modules.app.domain.vo.ImportVO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.plm.product.base.domain.po.PlmProduct;
import com.platform.mesh.crm.biz.modules.plm.product.base.domain.vo.PlmProductVO;
import com.platform.mesh.crm.biz.modules.plm.product.base.service.IPlmProductService;
import com.platform.mesh.crm.biz.modules.plm.product.basedata.domain.po.PlmProductData;
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
 * @description 供应链产品表信息
 * @author 蝉鸣
 */
@Tag(description = "PlmProductController", name = "供应链产品表")
@RestController
@RequestMapping
public class PlmProductController extends BaseController{

    @Autowired
    private IPlmProductService plmProductService;

    /**
	 * 功能描述:
	 * 〈获取供应链产品列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取供应链产品分页")
	@PostMapping("/plm/product/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = plmProductService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前供应链产品信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result< PlmProductVO >}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前供应链产品信息")
    @PostMapping("/plm/product/info")
    public Result<PlmProductVO> getOnOrderInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        PlmProductVO plmProductVO = plmProductService.getDataInfoById(esDocSGetDTO, PlmProductVO.class);
        return Result.success(plmProductVO);
    }

    /**
     * 功能描述:
     * 〈新增供应链产品〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result< PlmProductVO >}
     * @author 蝉鸣
     */
    @Operation(summary = "新增供应链产品")
    @Log(moduleName = "供应链产品管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/plm/product/add/simp")
    public Result<PlmProductVO> addOnOrderSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        PlmProduct plmProduct = plmProductService.addDataSimp(dataAddSimpDTO, PlmProduct.class, PlmProductData.class);
        return Result.success(BeanUtil.copyProperties(plmProduct, PlmProductVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增供应链产品〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result< PlmProductVO >}
     * @author 蝉鸣
     */
    @Operation(summary = "新增供应链产品")
    @Log(moduleName = "供应链产品管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/plm/product/add/comp")
    public Result<PlmProductVO> addOnOrderComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        PlmProduct plmProduct = plmProductService.addDataComp(dataAddCompDTO, PlmProduct.class, PlmProductData.class);
        return Result.success(BeanUtil.copyProperties(plmProduct, PlmProductVO.class));
    }

    /**
     * 功能描述:
     * 〈修改供应链产品〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result< PlmProductVO >}
     * @author 蝉鸣
     */
    @Operation(summary = "修改供应链产品")
    @Log(moduleName = "供应链产品管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/plm/product/edit")
    public Result<PlmProductVO> editOnOrder(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        PlmProduct plmProduct = plmProductService.editData(dataEditDTO, PlmProduct.class, PlmProductData.class);
        return Result.success(BeanUtil.copyProperties(plmProduct, PlmProductVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除供应链产品〉
     * @param onOrderId onOrderId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除供应链产品")
    @Log(moduleName = "供应链产品管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/plm/product/delete/{onOrderId}")
    public Result<Boolean> deleteOnOrder(@PathVariable(value = "onOrderId",required = false)Long onOrderId) {
        return Result.success(plmProductService.deleteData(onOrderId));
    }

    /**
     * 功能描述:
     * 〈批量删除供应链产品〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除供应链产品")
    @Log(moduleName = "供应链产品管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/plm/product/batch/delete")
    public Result<Boolean> deleteOnOrder(@RequestBody DataDelDTO delDTO) {
        return Result.success(plmProductService.deleteData(delDTO));
    }
    
    /**
     * 功能描述:
     * 〈转移供应链产品〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移供应链产品")
    @Log(moduleName = "供应链产品管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/plm/product/trans/scope")
    public Result<Boolean> transOnOrder(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(plmProductService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入供应链产品模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入供应链产品模板")
    @Log(moduleName = "供应链产品管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/plm/product/import/temp")
    public void importOnOrderTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"供应链产品导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入供应链产品〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入供应链产品")
    @Log(moduleName = "供应链产品管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/plm/product/import")
    public Result<ImportVO> importOnOrder(@RequestParam("moduleId") Long moduleId, @RequestParam("formId") Long formId, @RequestParam("file") MultipartFile file) {
        DataImportDTO importDTO = new DataImportDTO();
        importDTO.setModuleId(moduleId);
        importDTO.setFormId(formId);
        importDTO.setFile(file);
        return Result.success(plmProductService.importData(importDTO, PlmProduct.class, PlmProductData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出供应链产品〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出供应链产品")
    @Log(moduleName = "供应链产品管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/plm/product/export")
    public void exportOnOrder(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return plmProductService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}