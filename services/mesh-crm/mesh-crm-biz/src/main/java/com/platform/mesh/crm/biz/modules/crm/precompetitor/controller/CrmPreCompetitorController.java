package com.platform.mesh.crm.biz.modules.crm.precompetitor.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.crm.precompetitor.domain.po.CrmPreCompetitor;
import com.platform.mesh.crm.biz.modules.crm.precompetitor.domain.vo.CrmPreCompetitorVO;
import com.platform.mesh.crm.biz.modules.crm.precompetitor.service.ICrmPreCompetitorService;
import com.platform.mesh.crm.biz.modules.crm.precompetitordata.domain.po.CrmPreCompetitorData;
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
 * @description 客户关系竞品分析信息
 * @author 蝉鸣
 */
@Tag(description = "CrmPreCompetitorController", name = "客户关系竞品分析")
@RestController
@RequestMapping
public class CrmPreCompetitorController extends BaseController{
    @Autowired
    private ICrmPreCompetitorService  crmPreCompetitorService;

    /**
	 * 功能描述:
	 * 〈获取客户关系竞品分析列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系竞品分析分页")
	@PostMapping("/crm/pre/competitor/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = crmPreCompetitorService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前客户关系竞品分析信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<CrmPreCompetitorVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系竞品分析信息")
    @PostMapping("/crm/pre/competitor/info")
    public Result<CrmPreCompetitorVO> getPreCompetitorInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        CrmPreCompetitorVO crmPreCompetitorVO = crmPreCompetitorService.getDataInfoById(esDocSGetDTO,CrmPreCompetitorVO.class);
        return Result.success(crmPreCompetitorVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系竞品分析〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<CrmPreCompetitorVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系竞品分析")
    @Log(moduleName = "客户关系竞品分析管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/pre/competitor/add/simp")
    public Result<CrmPreCompetitorVO> addPreCompetitorSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        CrmPreCompetitor crmPreCompetitor = crmPreCompetitorService.addDataSimp(dataAddSimpDTO, CrmPreCompetitor.class, CrmPreCompetitorData.class);
        return Result.success(BeanUtil.copyProperties(crmPreCompetitor,CrmPreCompetitorVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增客户关系竞品分析〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<CrmPreCompetitorVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系竞品分析")
    @Log(moduleName = "客户关系竞品分析管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/pre/competitor/add/comp")
    public Result<CrmPreCompetitorVO> addPreCompetitorComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        CrmPreCompetitor crmPreCompetitor = crmPreCompetitorService.addDataComp(dataAddCompDTO, CrmPreCompetitor.class, CrmPreCompetitorData.class);
        return Result.success(BeanUtil.copyProperties(crmPreCompetitor,CrmPreCompetitorVO.class));
    }

    /**
     * 功能描述:
     * 〈修改客户关系竞品分析〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<CrmPreCompetitorVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系竞品分析")
    @Log(moduleName = "客户关系竞品分析管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/competitor/edit")
    public Result<CrmPreCompetitorVO> editPreCompetitor(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        CrmPreCompetitor crmPreCompetitor = crmPreCompetitorService.editData(dataEditDTO, CrmPreCompetitor.class);
        return Result.success(BeanUtil.copyProperties(crmPreCompetitor,CrmPreCompetitorVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系竞品分析〉
     * @param preCompetitorId preCompetitorId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系竞品分析")
    @Log(moduleName = "客户关系竞品分析管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/pre/competitor/delete/{preCompetitorId}")
    public Result<Boolean> deletePreCompetitor(@PathVariable(value = "preCompetitorId",required = false)Long preCompetitorId) {
        return Result.success(crmPreCompetitorService.deleteData(preCompetitorId));
    }

    /**
     * 功能描述:
     * 〈批量删除客户关系竞品分析〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除客户关系竞品分析")
    @Log(moduleName = "客户关系竞品分析管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/pre/competitor/batch/delete")
    public Result<Boolean> deletePreCompetitor(@RequestBody DataDelDTO delDTO) {
        return Result.success(crmPreCompetitorService.deleteData(delDTO));
    }
    
    /**
     * 功能描述:
     * 〈转移客户关系竞品分析〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移客户关系竞品分析")
    @Log(moduleName = "客户关系竞品分析管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/competitor/trans/scope")
    public Result<Boolean> transPreCompetitor(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(crmPreCompetitorService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入客户关系竞品分析模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系竞品分析模板")
    @Log(moduleName = "客户关系竞品分析管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/competitor/import/temp")
    public void importPreCompetitorTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"客户关系竞品分析导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入客户关系竞品分析〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系竞品分析")
    @Log(moduleName = "客户关系竞品分析管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/crm/pre/competitor/import")
    public Result<Boolean> importPreCompetitor(@RequestParam("moduleId") Long moduleId,@RequestParam("formId") Long formId,@RequestParam("file") MultipartFile file) {
        return Result.success(crmPreCompetitorService.importData(moduleId,formId,file, CrmPreCompetitor.class, CrmPreCompetitorData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出客户关系竞品分析〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出客户关系竞品分析")
    @Log(moduleName = "客户关系竞品分析管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/crm/pre/competitor/export")
    public void exportPreCompetitor(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return crmPreCompetitorService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}