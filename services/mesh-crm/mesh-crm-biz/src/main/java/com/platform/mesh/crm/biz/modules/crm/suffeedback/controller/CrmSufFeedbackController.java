package com.platform.mesh.crm.biz.modules.crm.suffeedback.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.api.modules.app.domain.vo.ImportVO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.crm.suffeedback.domain.po.CrmSufFeedback;
import com.platform.mesh.crm.biz.modules.crm.suffeedback.domain.vo.CrmSufFeedbackVO;
import com.platform.mesh.crm.biz.modules.crm.suffeedback.service.ICrmSufFeedbackService;
import com.platform.mesh.crm.biz.modules.crm.suffeedbackdata.domain.po.CrmSufFeedbackData;
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
 * @description 客户关系市场反馈信息
 * @author 蝉鸣
 */
@Tag(description = "CrmSufFeedbackController", name = "客户关系市场反馈")
@RestController
@RequestMapping
public class CrmSufFeedbackController extends BaseController{
    @Autowired
    private ICrmSufFeedbackService  crmSufFeedbackService;

    /**
	 * 功能描述:
	 * 〈获取客户关系市场反馈列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系市场反馈分页")
	@PostMapping("/crm/suf/feedback/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = crmSufFeedbackService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前客户关系市场反馈信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<CrmSufFeedbackVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系市场反馈信息")
    @PostMapping("/crm/suf/feedback/info")
    public Result<CrmSufFeedbackVO> getSufFeedbackInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        CrmSufFeedbackVO crmSufFeedbackVO = crmSufFeedbackService.getDataInfoById(esDocSGetDTO,CrmSufFeedbackVO.class);
        return Result.success(crmSufFeedbackVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系市场反馈〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<CrmSufFeedbackVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系市场反馈")
    @Log(moduleName = "客户关系市场反馈管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/suf/feedback/add/simp")
    public Result<CrmSufFeedbackVO> addSufFeedbackSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        CrmSufFeedback crmSufFeedback = crmSufFeedbackService.addDataSimp(dataAddSimpDTO, CrmSufFeedback.class, CrmSufFeedbackData.class);
        return Result.success(BeanUtil.copyProperties(crmSufFeedback,CrmSufFeedbackVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增客户关系市场反馈〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<CrmSufFeedbackVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系市场反馈")
    @Log(moduleName = "客户关系市场反馈管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/suf/feedback/add/comp")
    public Result<CrmSufFeedbackVO> addSufFeedbackComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        CrmSufFeedback crmSufFeedback = crmSufFeedbackService.addDataComp(dataAddCompDTO, CrmSufFeedback.class, CrmSufFeedbackData.class);
        return Result.success(BeanUtil.copyProperties(crmSufFeedback,CrmSufFeedbackVO.class));
    }

    /**
     * 功能描述:
     * 〈修改客户关系市场反馈〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<CrmSufFeedbackVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系市场反馈")
    @Log(moduleName = "客户关系市场反馈管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/suf/feedback/edit")
    public Result<CrmSufFeedbackVO> editSufFeedback(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        CrmSufFeedback crmSufFeedback = crmSufFeedbackService.editData(dataEditDTO, CrmSufFeedback.class, CrmSufFeedbackData.class);
        return Result.success(BeanUtil.copyProperties(crmSufFeedback,CrmSufFeedbackVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系市场反馈〉
     * @param sufFeedbackId sufFeedbackId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系市场反馈")
    @Log(moduleName = "客户关系市场反馈管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/suf/feedback/delete/{sufFeedbackId}")
    public Result<Boolean> deleteSufFeedback(@PathVariable(value = "sufFeedbackId",required = false)Long sufFeedbackId) {
        return Result.success(crmSufFeedbackService.deleteData(sufFeedbackId));
    }

    /**
     * 功能描述:
     * 〈批量删除客户关系市场反馈〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除客户关系市场反馈")
    @Log(moduleName = "客户关系市场反馈管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/suf/feedback/batch/delete")
    public Result<Boolean> deleteSufFeedback(@RequestBody DataDelDTO delDTO) {
        return Result.success(crmSufFeedbackService.deleteData(delDTO));
    }

    /**
     * 功能描述:
     * 〈转移客户关系市场反馈〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移客户关系市场反馈")
    @Log(moduleName = "客户关系市场反馈管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/suf/feedback/trans/scope")
    public Result<Boolean> transSufFeedback(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(crmSufFeedbackService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入客户关系市场反馈模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系市场反馈模板")
    @Log(moduleName = "客户关系市场反馈管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/suf/feedback/import/temp")
    public void importSufFeedbackTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"客户关系市场反馈导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入客户关系市场反馈〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系市场反馈")
    @Log(moduleName = "客户关系市场反馈管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/crm/suf/feedback/import")
    public Result<ImportVO> importSufFeedback(@RequestParam("moduleId") Long moduleId, @RequestParam("formId") Long formId, @RequestParam("file") MultipartFile file) {
        DataImportDTO importDTO = new DataImportDTO();
        importDTO.setModuleId(moduleId);
        importDTO.setFormId(formId);
        importDTO.setFile(file);
        return Result.success(crmSufFeedbackService.importData(importDTO, CrmSufFeedback.class, CrmSufFeedbackData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出客户关系市场反馈〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出客户关系市场反馈")
    @Log(moduleName = "客户关系市场反馈管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/crm/suf/feedback/export")
    public void exportSufFeedback(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return crmSufFeedbackService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}