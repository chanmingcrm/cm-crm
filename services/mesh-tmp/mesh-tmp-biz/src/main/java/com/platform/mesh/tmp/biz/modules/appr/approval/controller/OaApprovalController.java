package com.platform.mesh.tmp.biz.modules.appr.approval.controller;

import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.es.domain.dto.EsDocEGetDTO;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.es.domain.dto.EsDocSGetDTO;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.tmp.biz.modules.appr.approval.domain.vo.OaApprovalVO;
import com.platform.mesh.tmp.biz.modules.appr.approval.service.IOaApprovalService;
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
@Tag(description = "CrmOaApprovalController", name = "OA办公审批")
@RestController
@RequestMapping
public class OaApprovalController extends BaseController{
    @Autowired
    private IOaApprovalService  oaApprovalService;

    /**
	 * 功能描述:
	 * 〈获取OA办公审批列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取OA办公审批分页")
	@PostMapping("/oa/approval/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = oaApprovalService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前OA办公审批信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<OaApprovalVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前OA办公审批信息")
    @PostMapping("/oa/approval/info")
    public Result<OaApprovalVO> getOaApprovalInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        OaApprovalVO crmOaApprovalVO = oaApprovalService.getOaApprovalInfoById(esDocSGetDTO);
        return Result.success(crmOaApprovalVO);
    }

    /**
     * 功能描述:
     * 〈新增OA办公审批〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<OaApprovalVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增OA办公审批")
    @Log(moduleName = "OA办公审批管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/oa/approval/add/simp")
    public Result<OaApprovalVO> addOaApprovalSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        return Result.success(oaApprovalService.addOaApprovalSimp(dataAddSimpDTO));
    }
    
    /**
     * 功能描述:
     * 〈新增OA办公审批〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<OaApprovalVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增OA办公审批")
    @Log(moduleName = "OA办公审批管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/oa/approval/add/comp")
    public Result<OaApprovalVO> addOaApprovalComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        return Result.success(oaApprovalService.addOaApprovalComp(dataAddCompDTO));
    }

    /**
     * 功能描述:
     * 〈修改OA办公审批〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<OaApprovalVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改OA办公审批")
    @Log(moduleName = "OA办公审批管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/oa/approval/edit")
    public Result<OaApprovalVO> editOaApproval(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        return Result.success(oaApprovalService.editOaApproval(dataEditDTO));
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
    @PostMapping("/oa/approval/delete/{onBusinessId}")
    public Result<Boolean> deleteOaApproval(@PathVariable(value = "onBusinessId",required = false)Long onBusinessId) {
        return Result.success(oaApprovalService.deleteOaApproval(onBusinessId));
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
    @PostMapping("/oa/approval/batch/delete")
    public Result<Boolean> deleteOaApproval(@RequestBody DataDelDTO delDTO) {
        return Result.success(oaApprovalService.deleteOaApproval(delDTO));
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
    @PostMapping("/oa/approval/trans/scope")
    public Result<Boolean> transOaApproval(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(oaApprovalService.transOaApproval(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入OA办公审批模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入OA办公审批模板")
    @Log(moduleName = "OA办公审批管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/oa/approval/import/temp")
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
    @PostMapping("/oa/approval/import")
    public Result<Boolean> importOaApproval(@RequestParam("moduleId") Long moduleId,@RequestParam("formId") Long formId,@RequestParam("file") MultipartFile file) {
        return Result.success(oaApprovalService.importOaApproval(moduleId,formId,file));
    }
    
    /**
     * 功能描述:
     * 〈导出OA办公审批〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出OA办公审批")
    @Log(moduleName = "OA办公审批管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/oa/approval/export")
    public void exportOaApproval(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return oaApprovalService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}