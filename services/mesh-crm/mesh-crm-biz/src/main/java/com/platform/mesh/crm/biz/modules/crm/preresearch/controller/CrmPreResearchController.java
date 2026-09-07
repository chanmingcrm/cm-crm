package com.platform.mesh.crm.biz.modules.crm.preresearch.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.api.modules.app.domain.vo.ImportVO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.crm.preresearch.domain.po.CrmPreResearch;
import com.platform.mesh.crm.biz.modules.crm.preresearch.domain.vo.CrmPreResearchVO;
import com.platform.mesh.crm.biz.modules.crm.preresearch.service.ICrmPreResearchService;
import com.platform.mesh.crm.biz.modules.crm.preresearchdata.domain.po.CrmPreResearchData;
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
 * @description 客户关系市场调研信息
 * @author 蝉鸣
 */
@Tag(description = "CrmPreResearchController", name = "客户关系市场调研")
@RestController
@RequestMapping
public class CrmPreResearchController extends BaseController{
    @Autowired
    private ICrmPreResearchService  crmPreResearchService;

    /**
	 * 功能描述:
	 * 〈获取客户关系市场调研列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系市场调研分页")
	@PostMapping("/crm/pre/research/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = crmPreResearchService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前客户关系市场调研信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<CrmPreResearchVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系市场调研信息")
    @PostMapping("/crm/pre/research/info")
    public Result<CrmPreResearchVO> getPreResearchInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        CrmPreResearchVO crmPreResearchVO = crmPreResearchService.getDataInfoById(esDocSGetDTO,CrmPreResearchVO.class);
        return Result.success(crmPreResearchVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系市场调研〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<CrmPreResearchVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系市场调研")
    @Log(moduleName = "客户关系市场调研管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/pre/research/add/simp")
    public Result<CrmPreResearchVO> addPreResearchSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        CrmPreResearch crmPreResearch = crmPreResearchService.addDataSimp(dataAddSimpDTO, CrmPreResearch.class, CrmPreResearchData.class);
        return Result.success(BeanUtil.copyProperties(crmPreResearch,CrmPreResearchVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增客户关系市场调研〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<CrmPreResearchVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系市场调研")
    @Log(moduleName = "客户关系市场调研管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/pre/research/add/comp")
    public Result<CrmPreResearchVO> addPreResearchComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        CrmPreResearch crmPreResearch = crmPreResearchService.addDataComp(dataAddCompDTO, CrmPreResearch.class, CrmPreResearchData.class);
        return Result.success(BeanUtil.copyProperties(crmPreResearch,CrmPreResearchVO.class));
    }

    /**
     * 功能描述:
     * 〈修改客户关系市场调研〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<CrmPreResearchVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系市场调研")
    @Log(moduleName = "客户关系市场调研管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/research/edit")
    public Result<CrmPreResearchVO> editPreResearch(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        CrmPreResearch crmPreResearch = crmPreResearchService.editData(dataEditDTO, CrmPreResearch.class, CrmPreResearchData.class);
        return Result.success(BeanUtil.copyProperties(crmPreResearch,CrmPreResearchVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系市场调研〉
     * @param preResearchId preResearchId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系市场调研")
    @Log(moduleName = "客户关系市场调研管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/pre/research/delete/{preResearchId}")
    public Result<Boolean> deletePreResearch(@PathVariable(value = "preResearchId",required = false)Long preResearchId) {
        return Result.success(crmPreResearchService.deleteData(preResearchId));
    }

    /**
     * 功能描述:
     * 〈批量删除客户关系市场调研〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除客户关系市场调研")
    @Log(moduleName = "客户关系市场调研管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/pre/research/batch/delete")
    public Result<Boolean> deletePreResearch(@RequestBody DataDelDTO delDTO) {
        return Result.success(crmPreResearchService.deleteData(delDTO));
    }
    
    /**
     * 功能描述:
     * 〈转移客户关系市场调研〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移客户关系市场调研")
    @Log(moduleName = "客户关系市场调研管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/research/trans/scope")
    public Result<Boolean> transPreResearch(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(crmPreResearchService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入客户关系市场调研模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系市场调研模板")
    @Log(moduleName = "客户关系市场调研管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/research/import/temp")
    public void importPreResearchTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"客户关系市场调研导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入客户关系市场调研〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系市场调研")
    @Log(moduleName = "客户关系市场调研管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/crm/pre/research/import")
    public Result<ImportVO> importPreResearch(@RequestParam("moduleId") Long moduleId, @RequestParam("formId") Long formId, @RequestParam("file") MultipartFile file) {
        DataImportDTO importDTO = new DataImportDTO();
        importDTO.setModuleId(moduleId);
        importDTO.setFormId(formId);
        importDTO.setFile(file);
        return Result.success(crmPreResearchService.importData(importDTO, CrmPreResearch.class, CrmPreResearchData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出客户关系市场调研〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出客户关系市场调研")
    @Log(moduleName = "客户关系市场调研管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/crm/pre/research/export")
    public void exportPreResearch(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return crmPreResearchService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}