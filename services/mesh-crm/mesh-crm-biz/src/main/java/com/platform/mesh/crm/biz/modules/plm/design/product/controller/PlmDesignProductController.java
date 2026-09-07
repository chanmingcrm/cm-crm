package com.platform.mesh.crm.biz.modules.plm.design.product.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.api.modules.app.domain.vo.ImportVO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.plm.design.product.domain.po.PlmDesignProduct;
import com.platform.mesh.crm.biz.modules.plm.design.product.domain.vo.PlmDesignProductVO;
import com.platform.mesh.crm.biz.modules.plm.design.product.service.IPlmDesignProductService;
import com.platform.mesh.crm.biz.modules.plm.design.productdata.domain.po.PlmDesignProductData;
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
 * @description 产品设计信息
 * @author 蝉鸣
 */
@Tag(description = "PlmDesignProductController", name = "产品设计")
@RestController
@RequestMapping
public class PlmDesignProductController extends BaseController{

    @Autowired
    private IPlmDesignProductService  plmDesignProductService;

    /**
	 * 功能描述:
	 * 〈获取产品设计列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取产品设计分页")
	@PostMapping("/plm/design/product/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = plmDesignProductService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前产品设计信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<PlmDesignProductVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前产品设计信息")
    @PostMapping("/plm/design/product/info")
    public Result<PlmDesignProductVO> getDesignProductInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        PlmDesignProductVO plmDesignProductVO = plmDesignProductService.getDataInfoById(esDocSGetDTO,PlmDesignProductVO.class);
        return Result.success(plmDesignProductVO);
    }

    /**
     * 功能描述:
     * 〈新增产品设计〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<PlmDesignProductVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增产品设计")
    @Log(moduleName = "产品设计管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/plm/design/product/add/simp")
    public Result<PlmDesignProductVO> addDesignProductSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        PlmDesignProduct plmDesignProduct = plmDesignProductService.addDataSimp(dataAddSimpDTO, PlmDesignProduct.class, PlmDesignProductData.class);
        return Result.success(BeanUtil.copyProperties(plmDesignProduct,PlmDesignProductVO.class));
    }

    /**
     * 功能描述:
     * 〈修改产品设计〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<PlmDesignProductVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改产品设计")
    @Log(moduleName = "产品设计管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/plm/design/product/edit")
    public Result<PlmDesignProductVO> editDesignProduct(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        PlmDesignProduct plmDesignProduct = plmDesignProductService.editData(dataEditDTO, PlmDesignProduct.class, PlmDesignProductData.class);
       return Result.success(BeanUtil.copyProperties(plmDesignProduct,PlmDesignProductVO.class));
    }
    
   /**
     * 功能描述:
     * 〈批量删除产品设计〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除产品设计")
    @Log(moduleName = "产品设计管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/plm/design/product/batch/delete")
    public Result<Boolean> deleteDesignProduct(@RequestBody DataDelDTO delDTO) {
        return Result.success(plmDesignProductService.deleteData(delDTO));
    }
    
    /**
     * 功能描述:
     * 〈转移产品设计〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移产品设计")
    @Log(moduleName = "产品设计管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/plm/design/product/trans/scope")
    public Result<Boolean> transDesignProduct(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(plmDesignProductService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入产品设计模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入产品设计模板")
    @Log(moduleName = "产品设计管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/plm/design/product/import/temp")
    public void importDesignProductTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"产品设计导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入产品设计〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入产品设计")
    @Log(moduleName = "产品设计管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/plm/design/product/import")
    public Result<ImportVO> importDesignProduct(@RequestParam("moduleId") Long moduleId,@RequestParam("formId") Long formId,@RequestParam("file") MultipartFile file) {
        DataImportDTO importDTO = new DataImportDTO();
        importDTO.setModuleId(moduleId);
        importDTO.setFormId(formId);
        importDTO.setFile(file);
        return Result.success(plmDesignProductService.importData(importDTO, PlmDesignProduct.class, PlmDesignProductData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出产品设计〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出产品设计")
    @Log(moduleName = "产品设计管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/plm/design/product/export")
    public void exportDesignProduct(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return plmDesignProductService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}