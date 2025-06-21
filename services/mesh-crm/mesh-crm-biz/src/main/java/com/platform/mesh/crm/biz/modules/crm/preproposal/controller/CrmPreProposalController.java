package com.platform.mesh.crm.biz.modules.crm.preproposal.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddCompDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataDelDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.app.api.modules.app.domain.dto.TransScopeDTO;
import com.platform.mesh.crm.biz.modules.crm.preproposal.domain.po.CrmPreProposal;
import com.platform.mesh.crm.biz.modules.crm.preproposal.domain.vo.CrmPreProposalVO;
import com.platform.mesh.crm.biz.modules.crm.preproposal.service.ICrmPreProposalService;
import com.platform.mesh.crm.biz.modules.crm.preproposaldata.domain.po.CrmPreProposalData;
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
 * @description 客户关系提案报价信息
 * @author 蝉鸣
 */
@Tag(description = "CrmPreProposalController", name = "客户关系提案报价")
@RestController
@RequestMapping
public class CrmPreProposalController extends BaseController{
    @Autowired
    private ICrmPreProposalService  crmPreProposalService;

    /**
	 * 功能描述:
	 * 〈获取客户关系提案报价列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系提案报价分页")
	@PostMapping("/crm/pre/proposal/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = crmPreProposalService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前客户关系提案报价信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<CrmPreProposalVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系提案报价信息")
    @PostMapping("/crm/pre/proposal/info")
    public Result<CrmPreProposalVO> getPreProposalInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        CrmPreProposalVO crmPreProposalVO = crmPreProposalService.getDataInfoById(esDocSGetDTO,CrmPreProposalVO.class);
        return Result.success(crmPreProposalVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系提案报价〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<CrmPreProposalVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系提案报价")
    @Log(moduleName = "客户关系提案报价管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/pre/proposal/add/simp")
    public Result<CrmPreProposalVO> addPreProposalSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        CrmPreProposal crmPreProposal = crmPreProposalService.addDataSimp(dataAddSimpDTO, CrmPreProposal.class, CrmPreProposalData.class);
        return Result.success(BeanUtil.copyProperties(crmPreProposal, CrmPreProposalVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增客户关系提案报价〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<CrmPreProposalVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系提案报价")
    @Log(moduleName = "客户关系提案报价管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/pre/proposal/add/comp")
    public Result<CrmPreProposalVO> addPreProposalComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        CrmPreProposal crmPreProposal = crmPreProposalService.addDataComp(dataAddCompDTO, CrmPreProposal.class, CrmPreProposalData.class);
        return Result.success(BeanUtil.copyProperties(crmPreProposal, CrmPreProposalVO.class));
    }

    /**
     * 功能描述:
     * 〈修改客户关系提案报价〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<CrmPreProposalVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系提案报价")
    @Log(moduleName = "客户关系提案报价管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/proposal/edit")
    public Result<CrmPreProposalVO> editPreProposal(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        CrmPreProposal crmPreProposal = crmPreProposalService.editData(dataEditDTO, CrmPreProposal.class);
        return Result.success(BeanUtil.copyProperties(crmPreProposal, CrmPreProposalVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系提案报价〉
     * @param preProposalId preProposalId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系提案报价")
    @Log(moduleName = "客户关系提案报价管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/pre/proposal/delete/{preProposalId}")
    public Result<Boolean> deletePreProposal(@PathVariable(value = "preProposalId",required = false)Long preProposalId) {
        return Result.success(crmPreProposalService.deleteData(preProposalId));
    }

    /**
     * 功能描述:
     * 〈批量删除客户关系提案报价〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除客户关系提案报价")
    @Log(moduleName = "客户关系提案报价管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/pre/proposal/batch/delete")
    public Result<Boolean> deletePreProposal(@RequestBody DataDelDTO delDTO) {
        return Result.success(crmPreProposalService.deleteData(delDTO));
    }

    /**
     * 功能描述:
     * 〈转移客户关系提案报价〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移客户关系提案报价")
    @Log(moduleName = "客户关系提案报价管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/proposal/trans/scope")
    public Result<Boolean> transPreProposal(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(crmPreProposalService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入客户关系提案报价模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系提案报价模板")
    @Log(moduleName = "客户关系提案报价管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/proposal/import/temp")
    public void importPreProposalTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"客户关系提案报价导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入客户关系提案报价〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系提案报价")
    @Log(moduleName = "客户关系提案报价管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/crm/pre/proposal/import")
    public Result<Boolean> importPreProposal(@RequestParam("moduleId") Long moduleId,@RequestParam("formId") Long formId,@RequestParam("file") MultipartFile file) {
        return Result.success(crmPreProposalService.importData(moduleId,formId,file, CrmPreProposal.class, CrmPreProposalData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出客户关系提案报价〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出客户关系提案报价")
    @Log(moduleName = "客户关系提案报价管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/crm/pre/proposal/export")
    public void exportPreProposal(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return crmPreProposalService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}