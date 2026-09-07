package com.platform.mesh.crm.biz.modules.crm.sufopinion.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.api.modules.app.domain.vo.ImportVO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.crm.sufopinion.domain.po.CrmSufOpinion;
import com.platform.mesh.crm.biz.modules.crm.sufopinion.domain.vo.CrmSufOpinionVO;
import com.platform.mesh.crm.biz.modules.crm.sufopinion.service.ICrmSufOpinionService;
import com.platform.mesh.crm.biz.modules.crm.sufopiniondata.domain.po.CrmSufOpinionData;
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
 * @description 客户关系意见评价信息
 * @author 蝉鸣
 */
@Tag(description = "CrmSufOpinionController", name = "客户关系意见评价")
@RestController
@RequestMapping
public class CrmSufOpinionController extends BaseController{
    @Autowired
    private ICrmSufOpinionService  crmSufOpinionService;

    /**
	 * 功能描述:
	 * 〈获取客户关系意见评价列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系意见评价分页")
	@PostMapping("/crm/suf/opinion/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = crmSufOpinionService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前客户关系意见评价信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<CrmSufOpinionVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系意见评价信息")
    @PostMapping("/crm/suf/opinion/info")
    public Result<CrmSufOpinionVO> getSufOpinionInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        CrmSufOpinionVO crmSufOpinionVO = crmSufOpinionService.getDataInfoById(esDocSGetDTO,CrmSufOpinionVO.class);
        return Result.success(crmSufOpinionVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系意见评价〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<CrmSufOpinionVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系意见评价")
    @Log(moduleName = "客户关系意见评价管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/suf/opinion/add/simp")
    public Result<CrmSufOpinionVO> addSufOpinionSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        CrmSufOpinion crmSufOpinion = crmSufOpinionService.addDataSimp(dataAddSimpDTO, CrmSufOpinion.class, CrmSufOpinionData.class);
        return Result.success(BeanUtil.copyProperties(crmSufOpinion,CrmSufOpinionVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增客户关系意见评价〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<CrmSufOpinionVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系意见评价")
    @Log(moduleName = "客户关系意见评价管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/suf/opinion/add/comp")
    public Result<CrmSufOpinionVO> addSufOpinionComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        CrmSufOpinion crmSufOpinion = crmSufOpinionService.addDataComp(dataAddCompDTO, CrmSufOpinion.class, CrmSufOpinionData.class);
        return Result.success(BeanUtil.copyProperties(crmSufOpinion,CrmSufOpinionVO.class));
    }

    /**
     * 功能描述:
     * 〈修改客户关系意见评价〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<CrmSufOpinionVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系意见评价")
    @Log(moduleName = "客户关系意见评价管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/suf/opinion/edit")
    public Result<CrmSufOpinionVO> editSufOpinion(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        CrmSufOpinion crmSufOpinion = crmSufOpinionService.editData(dataEditDTO, CrmSufOpinion.class, CrmSufOpinionData.class);
        return Result.success(BeanUtil.copyProperties(crmSufOpinion,CrmSufOpinionVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系意见评价〉
     * @param sufOpinionId sufOpinionId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系意见评价")
    @Log(moduleName = "客户关系意见评价管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/suf/opinion/delete/{sufOpinionId}")
    public Result<Boolean> deleteSufOpinion(@PathVariable(value = "sufOpinionId",required = false)Long sufOpinionId) {
        return Result.success(crmSufOpinionService.deleteData(sufOpinionId));
    }

    /**
     * 功能描述:
     * 〈批量删除客户关系意见评价〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除客户关系意见评价")
    @Log(moduleName = "客户关系意见评价管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/suf/opinion/batch/delete")
    public Result<Boolean> deleteSufOpinion(@RequestBody DataDelDTO delDTO) {
        return Result.success(crmSufOpinionService.deleteData(delDTO));
    }
    
    /**
     * 功能描述:
     * 〈转移客户关系意见评价〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移客户关系意见评价")
    @Log(moduleName = "客户关系意见评价管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/suf/opinion/trans/scope")
    public Result<Boolean> transSufOpinion(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(crmSufOpinionService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入客户关系意见评价模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系意见评价模板")
    @Log(moduleName = "客户关系意见评价管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/suf/opinion/import/temp")
    public void importSufOpinionTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"客户关系意见评价导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入客户关系意见评价〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系意见评价")
    @Log(moduleName = "客户关系意见评价管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/crm/suf/opinion/import")
    public Result<ImportVO> importSufOpinion(@RequestParam("moduleId") Long moduleId, @RequestParam("formId") Long formId, @RequestParam("file") MultipartFile file) {
        DataImportDTO importDTO = new DataImportDTO();
        importDTO.setModuleId(moduleId);
        importDTO.setFormId(formId);
        importDTO.setFile(file);
        return Result.success(crmSufOpinionService.importData(importDTO, CrmSufOpinion.class, CrmSufOpinionData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出客户关系意见评价〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出客户关系意见评价")
    @Log(moduleName = "客户关系意见评价管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/crm/suf/opinion/export")
    public void exportSufOpinion(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return crmSufOpinionService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}