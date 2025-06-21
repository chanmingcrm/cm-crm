package com.platform.mesh.crm.biz.modules.crm.preproduct.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddCompDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataDelDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.app.api.modules.app.domain.dto.TransScopeDTO;
import com.platform.mesh.crm.biz.modules.crm.preproduct.domain.po.CrmPreProduct;
import com.platform.mesh.crm.biz.modules.crm.preproduct.domain.vo.CrmPreProductVO;
import com.platform.mesh.crm.biz.modules.crm.preproduct.service.ICrmPreProductService;
import com.platform.mesh.crm.biz.modules.crm.preproductdata.domain.po.CrmPreProductData;
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
 * @description 客户关系展示产品信息
 * @author 蝉鸣
 */
@Tag(description = "CrmPreProductController", name = "客户关系展示产品")
@RestController
@RequestMapping
public class CrmPreProductController extends BaseController{
    @Autowired
    private ICrmPreProductService  crmPreProductService;

    /**
	 * 功能描述:
	 * 〈获取客户关系展示产品列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系展示产品分页")
	@PostMapping("/crm/pre/product/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = crmPreProductService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前客户关系展示产品信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<CrmPreProductVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系展示产品信息")
    @PostMapping("/crm/pre/product/info")
    public Result<CrmPreProductVO> getPreProductInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        CrmPreProductVO crmPreProductVO = crmPreProductService.getDataInfoById(esDocSGetDTO,CrmPreProductVO.class);
        return Result.success(crmPreProductVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系展示产品〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<CrmPreProductVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系展示产品")
    @Log(moduleName = "客户关系展示产品管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/pre/product/add/simp")
    public Result<CrmPreProductVO> addPreProductSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        CrmPreProduct crmPreProduct = crmPreProductService.addDataSimp(dataAddSimpDTO, CrmPreProduct.class, CrmPreProductData.class);
        return Result.success(BeanUtil.copyProperties(crmPreProduct,CrmPreProductVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增客户关系展示产品〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<CrmPreProductVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系展示产品")
    @Log(moduleName = "客户关系展示产品管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/pre/product/add/comp")
    public Result<CrmPreProductVO> addPreProductComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        CrmPreProduct crmPreProduct = crmPreProductService.addDataComp(dataAddCompDTO, CrmPreProduct.class, CrmPreProductData.class);
        return Result.success(BeanUtil.copyProperties(crmPreProduct,CrmPreProductVO.class));
    }

    /**
     * 功能描述:
     * 〈修改客户关系展示产品〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<CrmPreProductVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系展示产品")
    @Log(moduleName = "客户关系展示产品管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/product/edit")
    public Result<CrmPreProductVO> editPreProduct(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        CrmPreProduct crmPreProduct = crmPreProductService.editData(dataEditDTO, CrmPreProduct.class);
        return Result.success(BeanUtil.copyProperties(crmPreProduct,CrmPreProductVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系展示产品〉
     * @param preProductId preProductId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系展示产品")
    @Log(moduleName = "客户关系展示产品管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/pre/product/delete/{preProductId}")
    public Result<Boolean> deletePreProduct(@PathVariable(value = "preProductId",required = false)Long preProductId) {
        return Result.success(crmPreProductService.deleteData(preProductId));
    }

   /**
     * 功能描述:
     * 〈批量删除客户关系展示产品〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除客户关系展示产品")
    @Log(moduleName = "客户关系展示产品管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/pre/product/batch/delete")
    public Result<Boolean> deletePreProduct(@RequestBody DataDelDTO delDTO) {
        return Result.success(crmPreProductService.deleteData(delDTO));
    }
    
    /**
     * 功能描述:
     * 〈转移客户关系展示产品〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移客户关系展示产品")
    @Log(moduleName = "客户关系展示产品管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/product/trans/scope")
    public Result<Boolean> transPreProduct(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(crmPreProductService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入客户关系展示产品模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系展示产品模板")
    @Log(moduleName = "客户关系展示产品管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/product/import/temp")
    public void importPreProductTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"客户关系展示产品导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入客户关系展示产品〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系展示产品")
    @Log(moduleName = "客户关系展示产品管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/crm/pre/product/import")
    public Result<Boolean> importPreProduct(@RequestParam("moduleId") Long moduleId,@RequestParam("formId") Long formId,@RequestParam("file") MultipartFile file) {
        return Result.success(crmPreProductService.importData(moduleId,formId,file, CrmPreProduct.class, CrmPreProductData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出客户关系展示产品〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出客户关系展示产品")
    @Log(moduleName = "客户关系展示产品管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/crm/pre/product/export")
    public void exportPreProduct(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return crmPreProductService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}