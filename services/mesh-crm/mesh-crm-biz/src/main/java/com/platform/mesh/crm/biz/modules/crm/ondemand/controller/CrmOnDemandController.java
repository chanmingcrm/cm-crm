package com.platform.mesh.crm.biz.modules.crm.ondemand.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.api.modules.app.domain.vo.ImportVO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.crm.ondemand.domain.po.CrmOnDemand;
import com.platform.mesh.crm.biz.modules.crm.ondemand.domain.vo.CrmOnDemandVO;
import com.platform.mesh.crm.biz.modules.crm.ondemand.service.ICrmOnDemandService;
import com.platform.mesh.crm.biz.modules.crm.ondemanddata.domain.po.CrmOnDemandData;
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
 * @description 客户关系需求整理信息
 * @author 蝉鸣
 */
@Tag(description = "CrmOnDemandController", name = "客户关系需求整理")
@RestController
@RequestMapping
public class CrmOnDemandController extends BaseController{
    @Autowired
    private ICrmOnDemandService  crmOnDemandService;

    /**
	 * 功能描述:
	 * 〈获取客户关系需求整理列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系需求整理分页")
	@PostMapping("/crm/on/demand/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = crmOnDemandService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前客户关系需求整理信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<CrmOnDemandVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系需求整理信息")
    @PostMapping("/crm/on/demand/info")
    public Result<CrmOnDemandVO> getOnDemandInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        CrmOnDemandVO crmOnDemandVO = crmOnDemandService.getDataInfoById(esDocSGetDTO,CrmOnDemandVO.class);
        return Result.success(crmOnDemandVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系需求整理〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<CrmOnDemandVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系需求整理")
    @Log(moduleName = "客户关系需求整理管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/on/demand/add/simp")
    public Result<CrmOnDemandVO> addOnDemandSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        CrmOnDemand crmOnDemand = crmOnDemandService.addDataSimp(dataAddSimpDTO, CrmOnDemand.class, CrmOnDemandData.class);
        return Result.success(BeanUtil.copyProperties(crmOnDemand, CrmOnDemandVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增客户关系需求整理〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<CrmOnDemandVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系需求整理")
    @Log(moduleName = "客户关系需求整理管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/on/demand/add/comp")
    public Result<CrmOnDemandVO> addOnDemandComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        CrmOnDemand crmOnDemand = crmOnDemandService.addDataComp(dataAddCompDTO, CrmOnDemand.class, CrmOnDemandData.class);
        return Result.success(BeanUtil.copyProperties(crmOnDemand, CrmOnDemandVO.class));
    }

    /**
     * 功能描述:
     * 〈修改客户关系需求整理〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<CrmOnDemandVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系需求整理")
    @Log(moduleName = "客户关系需求整理管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/demand/edit")
    public Result<CrmOnDemandVO> editOnDemand(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        CrmOnDemand crmOnDemand = crmOnDemandService.editData(dataEditDTO, CrmOnDemand.class, CrmOnDemandData.class);
        return Result.success(BeanUtil.copyProperties(crmOnDemand, CrmOnDemandVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系需求整理〉
     * @param onDemandId onDemandId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系需求整理")
    @Log(moduleName = "客户关系需求整理管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/on/demand/delete/{onDemandId}")
    public Result<Boolean> deleteOnDemand(@PathVariable(value = "onDemandId",required = false)Long onDemandId) {
        return Result.success(crmOnDemandService.deleteData(onDemandId));
    }

    /**
     * 功能描述:
     * 〈批量删除客户关系需求整理〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除客户关系需求整理")
    @Log(moduleName = "客户关系需求整理管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/on/demand/batch/delete")
    public Result<Boolean> deleteOnDemand(@RequestBody DataDelDTO delDTO) {
        return Result.success(crmOnDemandService.deleteData(delDTO));
    }
    
    /**
     * 功能描述:
     * 〈转移客户关系需求整理〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移客户关系需求整理")
    @Log(moduleName = "客户关系需求整理管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/demand/trans/scope")
    public Result<Boolean> transOnDemand(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(crmOnDemandService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入客户关系需求整理模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系需求整理模板")
    @Log(moduleName = "客户关系需求整理管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/demand/import/temp")
    public void importOnDemandTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"客户关系需求整理导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入客户关系需求整理〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系需求整理")
    @Log(moduleName = "客户关系需求整理管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/crm/on/demand/import")
    public Result<ImportVO> importOnDemand(@RequestParam("moduleId") Long moduleId, @RequestParam("formId") Long formId, @RequestParam("file") MultipartFile file) {
        DataImportDTO importDTO = new DataImportDTO();
        importDTO.setModuleId(moduleId);
        importDTO.setFormId(formId);
        importDTO.setFile(file);
        return Result.success(crmOnDemandService.importData(importDTO, CrmOnDemand.class, CrmOnDemandData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出客户关系需求整理〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出客户关系需求整理")
    @Log(moduleName = "客户关系需求整理管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/crm/on/demand/export")
    public void exportOnDemand(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return crmOnDemandService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}