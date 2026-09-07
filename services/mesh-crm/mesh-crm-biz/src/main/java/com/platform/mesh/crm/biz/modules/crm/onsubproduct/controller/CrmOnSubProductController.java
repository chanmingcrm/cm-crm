package com.platform.mesh.crm.biz.modules.crm.onsubproduct.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.api.modules.app.domain.vo.ImportVO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.crm.onsubproduct.domain.po.CrmOnSubProduct;
import com.platform.mesh.crm.biz.modules.crm.onsubproduct.domain.vo.CrmOnSubProductVO;
import com.platform.mesh.crm.biz.modules.crm.onsubproduct.service.ICrmOnSubProductService;
import com.platform.mesh.crm.biz.modules.crm.onsubproductdata.domain.po.CrmOnSubProductData;
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
 * @description 客户关系关联子产品信息
 * @author 蝉鸣
 */
@Tag(description = "CrmOnSubProductController", name = "客户关系关联子产品")
@RestController
@RequestMapping
public class CrmOnSubProductController extends BaseController{
    @Autowired
    private ICrmOnSubProductService crmOnSubProductService;

    /**
	 * 功能描述:
	 * 〈获取客户关系关联子产品列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系关联子产品分页")
	@PostMapping("/crm/on/sub/product/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = crmOnSubProductService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前客户关系关联子产品信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<CrmOnSubProductVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系关联子产品信息")
    @PostMapping("/crm/on/sub/product/info")
    public Result<CrmOnSubProductVO> getOnSubProductInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        CrmOnSubProductVO crmOnSubProductVO = crmOnSubProductService.getDataInfoById(esDocSGetDTO, CrmOnSubProductVO.class);
        return Result.success(crmOnSubProductVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系关联子产品〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<CrmOnSubProductVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系关联子产品")
    @Log(moduleName = "客户关系关联子产品管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/on/sub/product/add/simp")
    public Result<CrmOnSubProductVO> addOnSubProductSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        CrmOnSubProduct crmOnSubProduct = crmOnSubProductService.addDataSimp(dataAddSimpDTO, CrmOnSubProduct.class, CrmOnSubProductData.class);
        return Result.success(BeanUtil.copyProperties(crmOnSubProduct, CrmOnSubProductVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增客户关系关联子产品〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<CrmOnSubProductVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系关联子产品")
    @Log(moduleName = "客户关系关联子产品管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/on/sub/product/add/comp")
    public Result<CrmOnSubProductVO> addOnSubProductComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        CrmOnSubProduct crmOnSubProduct = crmOnSubProductService.addDataComp(dataAddCompDTO, CrmOnSubProduct.class, CrmOnSubProductData.class);
        return Result.success(BeanUtil.copyProperties(crmOnSubProduct, CrmOnSubProductVO.class));
    }

    /**
     * 功能描述:
     * 〈修改客户关系关联子产品〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<CrmOnSubProductVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系关联子产品")
    @Log(moduleName = "客户关系关联子产品管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/sub/product/edit")
    public Result<CrmOnSubProductVO> editOnSubProduct(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        CrmOnSubProduct crmOnSubProduct = crmOnSubProductService.editData(dataEditDTO, CrmOnSubProduct.class, CrmOnSubProductData.class);
        return Result.success(BeanUtil.copyProperties(crmOnSubProduct, CrmOnSubProductVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系关联子产品〉
     * @param OnSubProductId OnSubProductId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系关联子产品")
    @Log(moduleName = "客户关系关联子产品管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/on/sub/product/delete/{OnSubProductId}")
    public Result<Boolean> deleteOnSubProduct(@PathVariable(value = "OnSubProductId",required = false)Long OnSubProductId) {
        return Result.success(crmOnSubProductService.deleteData(OnSubProductId));
    }

    /**
     * 功能描述:
     * 〈批量删除客户关系关联子产品〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除客户关系关联子产品")
    @Log(moduleName = "客户关系关联子产品管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/on/sub/product/batch/delete")
    public Result<Boolean> deleteOnSubProduct(@RequestBody DataDelDTO delDTO) {
        return Result.success(crmOnSubProductService.deleteData(delDTO));
    }

    /**
     * 功能描述:
     * 〈转移客户关系关联子产品〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移客户关系关联子产品")
    @Log(moduleName = "客户关系关联子产品管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/sub/product/trans/scope")
    public Result<Boolean> transOnSubProduct(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(crmOnSubProductService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入客户关系关联子产品模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系关联子产品模板")
    @Log(moduleName = "客户关系关联子产品管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/sub/product/import/temp")
    public void importOnSubProductTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"客户关系关联子产品导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入客户关系关联子产品〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系关联子产品")
    @Log(moduleName = "客户关系关联子产品管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/crm/on/sub/product/import")
    public Result<ImportVO> importOnSubProduct(@RequestParam("moduleId") Long moduleId, @RequestParam("formId") Long formId, @RequestParam("file") MultipartFile file) {
        DataImportDTO importDTO = new DataImportDTO();
        importDTO.setModuleId(moduleId);
        importDTO.setFormId(formId);
        importDTO.setFile(file);
        return Result.success(crmOnSubProductService.importData(importDTO, CrmOnSubProduct.class, CrmOnSubProductData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出客户关系关联子产品〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出客户关系关联子产品")
    @Log(moduleName = "客户关系关联子产品管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/crm/on/sub/product/export")
    public void exportOnSubProduct(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return crmOnSubProductService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}