package com.platform.mesh.crm.biz.modules.crm.predrainage.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.api.modules.app.domain.vo.ImportVO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.dto.CheckDTO;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.vo.CheckVO;
import com.platform.mesh.crm.biz.modules.crm.predrainage.domain.dto.CrmPreDrainageGetDTO;
import com.platform.mesh.crm.biz.modules.crm.predrainage.domain.po.CrmPreDrainage;
import com.platform.mesh.crm.biz.modules.crm.predrainage.domain.vo.CrmPreDrainageVO;
import com.platform.mesh.crm.biz.modules.crm.predrainage.service.ICrmPreDrainageService;
import com.platform.mesh.crm.biz.modules.crm.predrainagedata.domain.po.CrmPreDrainageData;
import com.platform.mesh.es.domain.dto.EsDocEGetDTO;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.es.domain.dto.EsDocSGetDTO;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.upms.api.modules.conf.domian.bo.ConfSysSetBO;
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
 * @description 客户关系活动引流信息
 * @author 蝉鸣
 */
@Tag(description = "CrmPreDrainageController", name = "客户关系活动引流")
@RestController
@RequestMapping
public class CrmPreDrainageController extends BaseController{
    @Autowired
    private ICrmPreDrainageService  crmPreDrainageService;

    /**
	 * 功能描述:
	 * 〈获取客户关系活动引流列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系活动引流分页")
	@PostMapping("/crm/pre/drainage/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = crmPreDrainageService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前客户关系活动引流信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<CrmPreDrainageVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系活动引流信息")
    @PostMapping("/crm/pre/drainage/info")
    public Result<CrmPreDrainageVO> getPreDrainageInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        CrmPreDrainageVO crmPreDrainageVO = crmPreDrainageService.getDataInfoById(esDocSGetDTO,CrmPreDrainageVO.class);
        return Result.success(crmPreDrainageVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系活动引流〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<CrmPreDrainageVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系活动引流")
    @Log(moduleName = "客户关系活动引流管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/pre/drainage/add/simp")
    public Result<CrmPreDrainageVO> addPreDrainageSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        CrmPreDrainage crmPreDrainage = crmPreDrainageService.addDataSimp(dataAddSimpDTO, CrmPreDrainage.class, CrmPreDrainageData.class);
        return Result.success(BeanUtil.copyProperties(crmPreDrainage, CrmPreDrainageVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增客户关系活动引流〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<CrmPreDrainageVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系活动引流")
    @Log(moduleName = "客户关系活动引流管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/pre/drainage/add/comp")
    public Result<CrmPreDrainageVO> addPreDrainageComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        CrmPreDrainage crmPreDrainage = crmPreDrainageService.addDataComp(dataAddCompDTO, CrmPreDrainage.class, CrmPreDrainageData.class);
        return Result.success(BeanUtil.copyProperties(crmPreDrainage, CrmPreDrainageVO.class));
    }

    /**
     * 功能描述:
     * 〈修改客户关系活动引流〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<CrmPreDrainageVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系活动引流")
    @Log(moduleName = "客户关系活动引流管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/drainage/edit")
    public Result<CrmPreDrainageVO> editPreDrainage(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        CrmPreDrainage crmPreDrainage = crmPreDrainageService.editData(dataEditDTO, CrmPreDrainage.class, CrmPreDrainageData.class);
        return Result.success(BeanUtil.copyProperties(crmPreDrainage, CrmPreDrainageVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系活动引流〉
     * @param preDrainageId preDrainageId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系活动引流")
    @Log(moduleName = "客户关系活动引流管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/pre/drainage/delete/{preDrainageId}")
    public Result<Boolean> deletePreDrainage(@PathVariable(value = "preDrainageId",required = false)Long preDrainageId) {
        return Result.success(crmPreDrainageService.deleteData(preDrainageId));
    }

    /**
     * 功能描述:
     * 〈批量删除客户关系活动引流〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除客户关系活动引流")
    @Log(moduleName = "客户关系展示产品管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/pre/drainage/batch/delete")
    public Result<Boolean> deletePreDrainage(@RequestBody DataDelDTO delDTO) {
        return Result.success(crmPreDrainageService.deleteData(delDTO));
    }

    /**
     * 功能描述:
     * 〈转移客户关系活动引流〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移客户关系活动引流")
    @Log(moduleName = "客户关系活动引流管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/drainage/trans/scope")
    public Result<Boolean> transPreDrainage(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(crmPreDrainageService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入客户关系活动引流模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系活动引流模板")
    @Log(moduleName = "客户关系活动引流管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/drainage/import/temp")
    public void importPreDrainageTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"客户关系活动引流导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入客户关系活动引流〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系活动引流")
    @Log(moduleName = "客户关系活动引流管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/crm/pre/drainage/import")
    public Result<ImportVO> importPreDrainage(@RequestParam("moduleId") Long moduleId, @RequestParam("formId") Long formId, @RequestParam("file") MultipartFile file) {
        DataImportDTO importDTO = new DataImportDTO();
        importDTO.setModuleId(moduleId);
        importDTO.setFormId(formId);
        importDTO.setFile(file);
        return Result.success(crmPreDrainageService.importData(importDTO, CrmPreDrainage.class, CrmPreDrainageData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出客户关系活动引流〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出客户关系活动引流")
    @Log(moduleName = "客户关系活动引流管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/crm/pre/drainage/export")
    public void exportPreDrainage(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return crmPreDrainageService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }

    /**
     * 功能描述:
     * 〈查重客户关系活动引流〉
     * @param checkDTO checkDTO
     * @author 蝉鸣
     */
    @Operation(summary = "查重客户关系活动引流")
    @PostMapping("/crm/pre/drainage/check")
    public Result<List<CheckVO>> checkPreDrainage(@RequestBody CheckDTO checkDTO) {
        List<CheckVO> page = crmPreDrainageService.checkPreDrainage(checkDTO);
        return Result.success(page);
    }

    /**
     * 功能描述:
     * 〈同步抖音线索〉
     * @author 蝉鸣
     */
    @Operation(summary = "同步抖音线索")
    @PostMapping("/crm/pre/drainage/sync/douyin")
    public Result<Void> syncDouYinClue(@RequestBody ConfSysSetBO sysSetBO) {
        crmPreDrainageService.syncDouYinClue(sysSetBO);
        return Result.success();
    }

    /**
     * 功能描述:
     * 〈同步企微线索〉
     * @author 蝉鸣
     */
    @Operation(summary = "同步企微线索")
    @PostMapping("/crm/pre/drainage/sync/wxwork")
    public Result<Void> syncWxWorkContact(@RequestBody ConfSysSetBO sysSetBO) {
        crmPreDrainageService.syncWxWorkContact(sysSetBO);
        return Result.success();
    }

    /**
     * 功能描述:
     * 〈根据第三方ID查询线索/客户信息〉
     * @author 蝉鸣
     */
    @Operation(summary = "根据第三方ID查询线索/客户信息")
    @PostMapping("/crm/pre/drainage/get/by/third")
    public Result<Object> getByThirdId(@RequestBody CrmPreDrainageGetDTO getDTO) {
        return Result.success(crmPreDrainageService.getByThirdId(getDTO));
    }

}