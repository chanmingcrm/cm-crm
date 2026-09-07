package com.platform.mesh.crm.biz.modules.crm.sufdeliver.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.api.modules.app.domain.vo.ImportVO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.crm.sufdeliver.domain.po.CrmSufDeliver;
import com.platform.mesh.crm.biz.modules.crm.sufdeliver.domain.vo.CrmSufDeliverVO;
import com.platform.mesh.crm.biz.modules.crm.sufdeliver.service.ICrmSufDeliverService;
import com.platform.mesh.crm.biz.modules.crm.sufdeliverdata.domain.po.CrmSufDeliverData;
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
 * @description 客户关系标的交付信息
 * @author 蝉鸣
 */
@Tag(description = "CrmSufDeliverController", name = "客户关系标的交付")
@RestController
@RequestMapping
public class CrmSufDeliverController extends BaseController{
    @Autowired
    private ICrmSufDeliverService  crmSufDeliverService;

    /**
	 * 功能描述:
	 * 〈获取客户关系标的交付列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系标的交付分页")
	@PostMapping("/crm/suf/deliver/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = crmSufDeliverService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前客户关系标的交付信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<CrmSufDeliverVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系标的交付信息")
    @PostMapping("/crm/suf/deliver/info")
    public Result<CrmSufDeliverVO> getSufDeliverInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        CrmSufDeliverVO crmSufDeliverVO = crmSufDeliverService.getDataInfoById(esDocSGetDTO,CrmSufDeliverVO.class);
        return Result.success(crmSufDeliverVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系标的交付〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<CrmSufDeliverVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系标的交付")
    @Log(moduleName = "客户关系标的交付管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/suf/deliver/add/simp")
    public Result<CrmSufDeliverVO> addSufDeliverSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        CrmSufDeliver crmSufDeliver = crmSufDeliverService.addDataSimp(dataAddSimpDTO, CrmSufDeliver.class, CrmSufDeliverData.class);
        return Result.success(BeanUtil.copyProperties(crmSufDeliver,CrmSufDeliverVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增客户关系标的交付〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<CrmSufDeliverVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系标的交付")
    @Log(moduleName = "客户关系标的交付管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/suf/deliver/add/comp")
    public Result<CrmSufDeliverVO> addSufDeliverComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        CrmSufDeliver crmSufDeliver = crmSufDeliverService.addDataComp(dataAddCompDTO, CrmSufDeliver.class, CrmSufDeliverData.class);
        return Result.success(BeanUtil.copyProperties(crmSufDeliver,CrmSufDeliverVO.class));
    }

    /**
     * 功能描述:
     * 〈修改客户关系标的交付〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<CrmSufDeliverVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系标的交付")
    @Log(moduleName = "客户关系标的交付管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/suf/deliver/edit")
    public Result<CrmSufDeliverVO> editSufDeliver(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        CrmSufDeliver crmSufDeliver = crmSufDeliverService.editData(dataEditDTO, CrmSufDeliver.class, CrmSufDeliverData.class);
        return Result.success(BeanUtil.copyProperties(crmSufDeliver,CrmSufDeliverVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系标的交付〉
     * @param sufDeliverId sufDeliverId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系标的交付")
    @Log(moduleName = "客户关系标的交付管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/suf/deliver/delete/{sufDeliverId}")
    public Result<Boolean> deleteSufDeliver(@PathVariable(value = "sufDeliverId",required = false)Long sufDeliverId) {
        return Result.success(crmSufDeliverService.deleteData(sufDeliverId));
    }

    /**
     * 功能描述:
     * 〈批量删除客户关系标的交付〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除客户关系标的交付")
    @Log(moduleName = "客户关系标的交付管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/suf/deliver/batch/delete")
    public Result<Boolean> deleteSufDeliver(@RequestBody DataDelDTO delDTO) {
        return Result.success(crmSufDeliverService.deleteData(delDTO));
    }
    
    /**
     * 功能描述:
     * 〈转移客户关系标的交付〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移客户关系标的交付")
    @Log(moduleName = "客户关系标的交付管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/suf/deliver/trans/scope")
    public Result<Boolean> transSufDeliver(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(crmSufDeliverService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入客户关系标的交付模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系标的交付模板")
    @Log(moduleName = "客户关系标的交付管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/suf/deliver/import/temp")
    public void importSufDeliverTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"客户关系标的交付导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入客户关系标的交付〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系标的交付")
    @Log(moduleName = "客户关系标的交付管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/crm/suf/deliver/import")
    public Result<ImportVO> importSufDeliver(@RequestParam("moduleId") Long moduleId, @RequestParam("formId") Long formId, @RequestParam("file") MultipartFile file) {
        DataImportDTO importDTO = new DataImportDTO();
        importDTO.setModuleId(moduleId);
        importDTO.setFormId(formId);
        importDTO.setFile(file);
        return Result.success(crmSufDeliverService.importData(importDTO, CrmSufDeliver.class, CrmSufDeliverData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出客户关系标的交付〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出客户关系标的交付")
    @Log(moduleName = "客户关系标的交付管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/crm/suf/deliver/export")
    public void exportSufDeliver(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return crmSufDeliverService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}