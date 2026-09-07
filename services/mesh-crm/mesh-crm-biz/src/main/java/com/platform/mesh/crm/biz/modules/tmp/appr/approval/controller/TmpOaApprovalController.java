package com.platform.mesh.crm.biz.modules.tmp.appr.approval.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.api.modules.app.domain.vo.ImportVO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.tmp.appr.approval.domain.po.TmpOaApproval;
import com.platform.mesh.crm.biz.modules.tmp.appr.approval.domain.vo.TmpOaApprovalVO;
import com.platform.mesh.crm.biz.modules.tmp.appr.approval.service.ITmpOaApprovalService;
import com.platform.mesh.crm.biz.modules.tmp.appr.approvaldata.domain.po.TmpOaApprovalData;
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
 * @description OA办公审批信息
 * @author 蝉鸣
 */
@Tag(description = "TmpOaApprovalController", name = "OA办公审批")
@RestController
@RequestMapping
public class TmpOaApprovalController extends BaseController{


    @Autowired
    private ITmpOaApprovalService oaApprovalService;

    /**
	 * 功能描述:
	 * 〈获取OA办公审批列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取OA办公审批分页")
	@PostMapping("/tmp/oa/approval/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = oaApprovalService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前OA办公审批信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<TmpOaApprovalVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前OA办公审批信息")
    @PostMapping("/tmp/oa/approval/info")
    public Result<TmpOaApprovalVO> getOaApprovalInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        TmpOaApprovalVO tmpOaApprovalVO = oaApprovalService.getDataInfoById(esDocSGetDTO,TmpOaApprovalVO.class);
        return Result.success(tmpOaApprovalVO);
    }

    /**
     * 功能描述:
     * 〈新增OA办公审批〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<TmpOaApprovalVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增OA办公审批")
    @Log(moduleName = "OA办公审批管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/tmp/oa/approval/add/simp")
    public Result<TmpOaApprovalVO> addOaApprovalSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        TmpOaApproval oaApproval = oaApprovalService.addDataSimp(dataAddSimpDTO, TmpOaApproval.class, TmpOaApprovalData.class);
        return Result.success(BeanUtil.copyProperties(oaApproval, TmpOaApprovalVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增OA办公审批〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<TmpOaApprovalVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增OA办公审批")
    @Log(moduleName = "OA办公审批管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/tmp/oa/approval/add/comp")
    public Result<TmpOaApprovalVO> addOaApprovalComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        TmpOaApproval oaApproval = oaApprovalService.addDataComp(dataAddCompDTO, TmpOaApproval.class, TmpOaApprovalData.class);
        return Result.success(BeanUtil.copyProperties(oaApproval, TmpOaApprovalVO.class));
    }

    /**
     * 功能描述:
     * 〈修改OA办公审批〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<TmpOaApprovalVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改OA办公审批")
    @Log(moduleName = "OA办公审批管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/tmp/oa/approval/edit")
    public Result<TmpOaApprovalVO> editOaApproval(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        TmpOaApproval oaApproval = oaApprovalService.editData(dataEditDTO, TmpOaApproval.class, TmpOaApprovalData.class);
        return Result.success(BeanUtil.copyProperties(oaApproval, TmpOaApprovalVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除OA办公审批〉
     * @param onBusinessId onBusinessId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除OA办公审批")
    @Log(moduleName = "OA办公审批管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/tmp/oa/approval/delete/{onBusinessId}")
    public Result<Boolean> deleteOaApproval(@PathVariable(value = "onBusinessId",required = false)Long onBusinessId) {
        return Result.success(oaApprovalService.deleteData(onBusinessId));
    }

    /**
     * 功能描述:
     * 〈批量删除OA办公审批〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除OA办公审批")
    @Log(moduleName = "OA办公审批管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/tmp/oa/approval/batch/delete")
    public Result<Boolean> deleteOaApproval(@RequestBody DataDelDTO delDTO) {
        return Result.success(oaApprovalService.deleteData(delDTO));
    }

    /**
     * 功能描述:
     * 〈转移OA办公审批〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移OA办公审批")
    @Log(moduleName = "OA办公审批管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/tmp/oa/approval/trans/scope")
    public Result<Boolean> transOaApproval(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(oaApprovalService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入OA办公审批模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入OA办公审批模板")
    @Log(moduleName = "OA办公审批管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/tmp/oa/approval/import/temp")
    public void importOaApprovalTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"OA办公审批导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入OA办公审批〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入OA办公审批")
    @Log(moduleName = "OA办公审批管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/tmp/oa/approval/import")
    public Result<ImportVO> importOaApproval(@RequestParam("moduleId") Long moduleId, @RequestParam("formId") Long formId, @RequestParam("file") MultipartFile file) {
        DataImportDTO importDTO = new DataImportDTO();
        importDTO.setModuleId(moduleId);
        importDTO.setFormId(formId);
        importDTO.setFile(file);
        return Result.success(oaApprovalService.importData(importDTO, TmpOaApproval.class, TmpOaApprovalData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出OA办公审批〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出OA办公审批")
    @Log(moduleName = "OA办公审批管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/tmp/oa/approval/export")
    public void exportOaApproval(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return oaApprovalService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}