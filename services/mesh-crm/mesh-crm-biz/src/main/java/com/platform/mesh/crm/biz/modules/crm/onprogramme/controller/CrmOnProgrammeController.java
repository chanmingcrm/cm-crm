package com.platform.mesh.crm.biz.modules.crm.onprogramme.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.api.modules.app.domain.vo.ImportVO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.crm.onprogramme.domain.po.CrmOnProgramme;
import com.platform.mesh.crm.biz.modules.crm.onprogramme.domain.vo.CrmOnProgrammeVO;
import com.platform.mesh.crm.biz.modules.crm.onprogramme.service.ICrmOnProgrammeService;
import com.platform.mesh.crm.biz.modules.crm.onprogrammedata.domain.po.CrmOnProgrammeData;
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
 * @description 客户关系方案输出信息
 * @author 蝉鸣
 */
@Tag(description = "CrmOnProgrammeController", name = "客户关系方案输出")
@RestController
@RequestMapping
public class CrmOnProgrammeController extends BaseController{
    @Autowired
    private ICrmOnProgrammeService  crmOnProgrammeService;

    /**
	 * 功能描述:
	 * 〈获取客户关系方案输出列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系方案输出分页")
	@PostMapping("/crm/on/programme/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = crmOnProgrammeService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前客户关系方案输出信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<CrmOnProgrammeVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系方案输出信息")
    @PostMapping("/crm/on/programme/info")
    public Result<CrmOnProgrammeVO> getOnProgrammeInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        CrmOnProgrammeVO crmOnProgrammeVO = crmOnProgrammeService.getDataInfoById(esDocSGetDTO,CrmOnProgrammeVO.class);
        return Result.success(crmOnProgrammeVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系方案输出〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<CrmOnProgrammeVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系方案输出")
    @Log(moduleName = "客户关系方案输出管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/on/programme/add/simp")
    public Result<CrmOnProgrammeVO> addOnProgrammeSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        CrmOnProgramme crmOnProgramme = crmOnProgrammeService.addDataSimp(dataAddSimpDTO, CrmOnProgramme.class, CrmOnProgrammeData.class);
        return Result.success(BeanUtil.copyProperties(crmOnProgramme,CrmOnProgrammeVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增客户关系方案输出〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<CrmOnProgrammeVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系方案输出")
    @Log(moduleName = "客户关系方案输出管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/on/programme/add/comp")
    public Result<CrmOnProgrammeVO> addOnProgrammeComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        CrmOnProgramme crmOnProgramme = crmOnProgrammeService.addDataComp(dataAddCompDTO, CrmOnProgramme.class, CrmOnProgrammeData.class);
        return Result.success(BeanUtil.copyProperties(crmOnProgramme,CrmOnProgrammeVO.class));
    }

    /**
     * 功能描述:
     * 〈修改客户关系方案输出〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<CrmOnProgrammeVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系方案输出")
    @Log(moduleName = "客户关系方案输出管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/programme/edit")
    public Result<CrmOnProgrammeVO> editOnProgramme(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        CrmOnProgramme crmOnProgramme = crmOnProgrammeService.editData(dataEditDTO, CrmOnProgramme.class, CrmOnProgrammeData.class);
        return Result.success(BeanUtil.copyProperties(crmOnProgramme,CrmOnProgrammeVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系方案输出〉
     * @param onProgrammeId onProgrammeId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系方案输出")
    @Log(moduleName = "客户关系方案输出管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/on/programme/delete/{onProgrammeId}")
    public Result<Boolean> deleteOnProgramme(@PathVariable(value = "onProgrammeId",required = false)Long onProgrammeId) {
        return Result.success(crmOnProgrammeService.deleteData(onProgrammeId));
    }

    /**
     * 功能描述:
     * 〈批量删除客户关系方案输出〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除客户关系方案输出")
    @Log(moduleName = "客户关系方案输出管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/on/programme/batch/delete")
    public Result<Boolean> deleteOnProgramme(@RequestBody DataDelDTO delDTO) {
        return Result.success(crmOnProgrammeService.deleteData(delDTO));
    }
    
    /**
     * 功能描述:
     * 〈转移客户关系方案输出〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移客户关系方案输出")
    @Log(moduleName = "客户关系方案输出管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/programme/trans/scope")
    public Result<Boolean> transOnProgramme(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(crmOnProgrammeService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入客户关系方案输出模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系方案输出模板")
    @Log(moduleName = "客户关系方案输出管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/programme/import/temp")
    public void importOnProgrammeTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"客户关系方案输出导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入客户关系方案输出〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系方案输出")
    @Log(moduleName = "客户关系方案输出管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/crm/on/programme/import")
    public Result<ImportVO> importOnProgramme(@RequestParam("moduleId") Long moduleId, @RequestParam("formId") Long formId, @RequestParam("file") MultipartFile file) {
        DataImportDTO importDTO = new DataImportDTO();
        importDTO.setModuleId(moduleId);
        importDTO.setFormId(formId);
        importDTO.setFile(file);
        return Result.success(crmOnProgrammeService.importData(importDTO, CrmOnProgramme.class, CrmOnProgrammeData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出客户关系方案输出〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出客户关系方案输出")
    @Log(moduleName = "客户关系方案输出管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/crm/on/programme/export")
    public void exportOnProgramme(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return crmOnProgrammeService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}