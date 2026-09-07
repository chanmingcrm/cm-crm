package com.platform.mesh.crm.biz.modules.crm.onbusiness.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.api.modules.app.domain.vo.ImportVO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.crm.onbusiness.domain.po.CrmOnBusiness;
import com.platform.mesh.crm.biz.modules.crm.onbusiness.domain.vo.CrmOnBusinessVO;
import com.platform.mesh.crm.biz.modules.crm.onbusiness.service.ICrmOnBusinessService;
import com.platform.mesh.crm.biz.modules.crm.onbusinessdata.domain.po.CrmOnBusinessData;
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
 * @description 客户关系商机跟进信息
 * @author 蝉鸣
 */
@Tag(description = "CrmOnBusinessController", name = "客户关系商机跟进")
@RestController
@RequestMapping
public class CrmOnBusinessController extends BaseController{
    @Autowired
    private ICrmOnBusinessService  crmOnBusinessService;

    /**
	 * 功能描述:
	 * 〈获取客户关系商机跟进列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系商机跟进分页")
	@PostMapping("/crm/on/business/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = crmOnBusinessService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前客户关系商机跟进信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<CrmOnBusinessVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系商机跟进信息")
    @PostMapping("/crm/on/business/info")
    public Result<CrmOnBusinessVO> getOnBusinessInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        CrmOnBusinessVO crmOnBusinessVO = crmOnBusinessService.getDataInfoById(esDocSGetDTO,CrmOnBusinessVO.class);
        return Result.success(crmOnBusinessVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系商机跟进〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<CrmOnBusinessVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系商机跟进")
    @Log(moduleName = "客户关系商机跟进管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/on/business/add/simp")
    public Result<CrmOnBusinessVO> addOnBusinessSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        CrmOnBusiness crmOnBusiness = crmOnBusinessService.addDataSimp(dataAddSimpDTO, CrmOnBusiness.class, CrmOnBusinessData.class);
        return Result.success(BeanUtil.copyProperties(crmOnBusiness,CrmOnBusinessVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增客户关系商机跟进〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<CrmOnBusinessVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系商机跟进")
    @Log(moduleName = "客户关系商机跟进管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/on/business/add/comp")
    public Result<CrmOnBusinessVO> addOnBusinessComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        CrmOnBusiness crmOnBusiness = crmOnBusinessService.addDataComp(dataAddCompDTO, CrmOnBusiness.class, CrmOnBusinessData.class);
        return Result.success(BeanUtil.copyProperties(crmOnBusiness,CrmOnBusinessVO.class));
    }

    /**
     * 功能描述:
     * 〈修改客户关系商机跟进〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<CrmOnBusinessVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系商机跟进")
    @Log(moduleName = "客户关系商机跟进管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/business/edit")
    public Result<CrmOnBusinessVO> editOnBusiness(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        CrmOnBusiness crmOnBusiness = crmOnBusinessService.editData(dataEditDTO, CrmOnBusiness.class, CrmOnBusinessData.class);
        return Result.success(BeanUtil.copyProperties(crmOnBusiness,CrmOnBusinessVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系商机跟进〉
     * @param onBusinessId onBusinessId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系商机跟进")
    @Log(moduleName = "客户关系商机跟进管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/on/business/delete/{onBusinessId}")
    public Result<Boolean> deleteOnBusiness(@PathVariable(value = "onBusinessId",required = false)Long onBusinessId) {
        return Result.success(crmOnBusinessService.deleteData(onBusinessId));
    }

    /**
     * 功能描述:
     * 〈批量删除客户关系商机跟进〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除客户关系商机跟进")
    @Log(moduleName = "客户关系商机跟进管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/on/business/batch/delete")
    public Result<Boolean> deleteOnBusiness(@RequestBody DataDelDTO delDTO) {
        return Result.success(crmOnBusinessService.deleteData(delDTO));
    }

    /**
     * 功能描述:
     * 〈转移客户关系商机跟进〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移客户关系商机跟进")
    @Log(moduleName = "客户关系商机跟进管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/business/trans/scope")
    public Result<Boolean> transOnBusiness(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(crmOnBusinessService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入客户关系商机跟进模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系商机跟进模板")
    @Log(moduleName = "客户关系商机跟进管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/business/import/temp")
    public void importOnBusinessTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"客户关系商机跟进导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入客户关系商机跟进〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系商机跟进")
    @Log(moduleName = "客户关系商机跟进管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/crm/on/business/import")
    public Result<ImportVO> importOnBusiness(@RequestParam("moduleId") Long moduleId, @RequestParam("formId") Long formId, @RequestParam("file") MultipartFile file) {
        DataImportDTO importDTO = new DataImportDTO();
        importDTO.setModuleId(moduleId);
        importDTO.setFormId(formId);
        importDTO.setFile(file);
        return Result.success(crmOnBusinessService.importData(importDTO,CrmOnBusiness.class,CrmOnBusinessData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出客户关系商机跟进〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出客户关系商机跟进")
    @Log(moduleName = "客户关系商机跟进管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/crm/on/business/export")
    public void exportOnBusiness(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return crmOnBusinessService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}