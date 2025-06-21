package com.platform.mesh.crm.biz.modules.crm.sufreview.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddCompDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataDelDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.app.api.modules.app.domain.dto.TransScopeDTO;
import com.platform.mesh.crm.biz.modules.crm.sufreview.domain.po.CrmSufReview;
import com.platform.mesh.crm.biz.modules.crm.sufreview.domain.vo.CrmSufReviewVO;
import com.platform.mesh.crm.biz.modules.crm.sufreview.service.ICrmSufReviewService;
import com.platform.mesh.crm.biz.modules.crm.sufreviewdata.domain.po.CrmSufReviewData;
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
 * @description 客户关系复盘总结信息
 * @author 蝉鸣
 */
@Tag(description = "CrmSufReviewController", name = "客户关系复盘总结")
@RestController
@RequestMapping
public class CrmSufReviewController extends BaseController{
    @Autowired
    private ICrmSufReviewService  crmSufReviewService;

    /**
	 * 功能描述:
	 * 〈获取客户关系复盘总结列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系复盘总结分页")
	@PostMapping("/crm/suf/review/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = crmSufReviewService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前客户关系复盘总结信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<CrmSufReviewVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系复盘总结信息")
    @PostMapping("/crm/suf/review/info")
    public Result<CrmSufReviewVO> getSufReviewInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        CrmSufReviewVO crmSufReviewVO = crmSufReviewService.getDataInfoById(esDocSGetDTO,CrmSufReviewVO.class);
        return Result.success(crmSufReviewVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系复盘总结〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<CrmSufReviewVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系复盘总结")
    @Log(moduleName = "客户关系复盘总结管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/suf/review/add/simp")
    public Result<CrmSufReviewVO> addSufReviewSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        CrmSufReview crmSufReview = crmSufReviewService.addDataSimp(dataAddSimpDTO, CrmSufReview.class, CrmSufReviewData.class);
        return Result.success(BeanUtil.copyProperties(crmSufReview,CrmSufReviewVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增客户关系复盘总结〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<CrmSufReviewVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系复盘总结")
    @Log(moduleName = "客户关系复盘总结管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/suf/review/add/comp")
    public Result<CrmSufReviewVO> addSufReviewComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        CrmSufReview crmSufReview = crmSufReviewService.addDataComp(dataAddCompDTO, CrmSufReview.class, CrmSufReviewData.class);
        return Result.success(BeanUtil.copyProperties(crmSufReview,CrmSufReviewVO.class));
    }

    /**
     * 功能描述:
     * 〈修改客户关系复盘总结〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<CrmSufReviewVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系复盘总结")
    @Log(moduleName = "客户关系复盘总结管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/suf/review/edit")
    public Result<CrmSufReviewVO> editSufReview(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        CrmSufReview crmSufReview = crmSufReviewService.editData(dataEditDTO, CrmSufReview.class);
        return Result.success(BeanUtil.copyProperties(crmSufReview,CrmSufReviewVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系复盘总结〉
     * @param sufReviewId sufReviewId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系复盘总结")
    @Log(moduleName = "客户关系复盘总结管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/suf/review/delete/{sufReviewId}")
    public Result<Boolean> deleteSufReview(@PathVariable(value = "sufReviewId",required = false)Long sufReviewId) {
        return Result.success(crmSufReviewService.deleteData(sufReviewId));
    }

    /**
     * 功能描述:
     * 〈批量删除客户关系复盘总结〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除客户关系复盘总结")
    @Log(moduleName = "客户关系复盘总结管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/suf/review/batch/delete")
    public Result<Boolean> deleteSufReview(@RequestBody DataDelDTO delDTO) {
        return Result.success(crmSufReviewService.deleteData(delDTO));
    }

    /**
     * 功能描述:
     * 〈转移客户关系复盘总结〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移客户关系复盘总结")
    @Log(moduleName = "客户关系复盘总结管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/suf/review/trans/scope")
    public Result<Boolean> transSufReview(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(crmSufReviewService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入客户关系复盘总结模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系复盘总结模板")
    @Log(moduleName = "客户关系复盘总结管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/suf/review/import/temp")
    public void importSufReviewTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"客户关系复盘总结导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入客户关系复盘总结〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系复盘总结")
    @Log(moduleName = "客户关系复盘总结管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/crm/suf/review/import")
    public Result<Boolean> importSufReview(@RequestParam("moduleId") Long moduleId,@RequestParam("formId") Long formId,@RequestParam("file") MultipartFile file) {
        return Result.success(crmSufReviewService.importData(moduleId,formId,file, CrmSufReview.class, CrmSufReviewData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出客户关系复盘总结〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出客户关系复盘总结")
    @Log(moduleName = "客户关系复盘总结管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/crm/suf/review/export")
    public void exportSufReview(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return crmSufReviewService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}