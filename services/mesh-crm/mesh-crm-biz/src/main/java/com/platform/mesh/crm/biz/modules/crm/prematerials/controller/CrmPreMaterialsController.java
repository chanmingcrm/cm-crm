package com.platform.mesh.crm.biz.modules.crm.prematerials.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddCompDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataDelDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.app.api.modules.app.domain.dto.TransScopeDTO;
import com.platform.mesh.crm.biz.modules.crm.prematerials.domain.po.CrmPreMaterials;
import com.platform.mesh.crm.biz.modules.crm.prematerials.domain.vo.CrmPreMaterialsVO;
import com.platform.mesh.crm.biz.modules.crm.prematerials.service.ICrmPreMaterialsService;
import com.platform.mesh.crm.biz.modules.crm.prematerialsdata.domain.po.CrmPreMaterialsData;
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
 * @description 客户关系活动物料信息
 * @author 蝉鸣
 */
@Tag(description = "CrmPreMaterialsController", name = "客户关系活动物料")
@RestController
@RequestMapping
public class CrmPreMaterialsController extends BaseController{
    @Autowired
    private ICrmPreMaterialsService  crmPreMaterialsService;

    /**
	 * 功能描述:
	 * 〈获取客户关系活动物料列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系活动物料分页")
	@PostMapping("/crm/pre/materials/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = crmPreMaterialsService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前客户关系活动物料信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<CrmPreMaterialsVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系活动物料信息")
    @PostMapping("/crm/pre/materials/info")
    public Result<CrmPreMaterialsVO> getPreMaterialsInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        CrmPreMaterialsVO crmPreMaterialsVO = crmPreMaterialsService.getDataInfoById(esDocSGetDTO,CrmPreMaterialsVO.class);
        return Result.success(crmPreMaterialsVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系活动物料〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<CrmPreMaterialsVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系活动物料")
    @Log(moduleName = "客户关系活动物料管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/pre/materials/add/simp")
    public Result<CrmPreMaterialsVO> addPreMaterialsSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        CrmPreMaterials crmPreMaterials = crmPreMaterialsService.addDataSimp(dataAddSimpDTO, CrmPreMaterials.class, CrmPreMaterialsData.class);
        return Result.success(BeanUtil.copyProperties(crmPreMaterials, CrmPreMaterialsVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增客户关系活动物料〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<CrmPreMaterialsVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系活动物料")
    @Log(moduleName = "客户关系活动物料管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/pre/materials/add/comp")
    public Result<CrmPreMaterialsVO> addPreMaterialsComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        CrmPreMaterials crmPreMaterials = crmPreMaterialsService.addDataComp(dataAddCompDTO, CrmPreMaterials.class, CrmPreMaterialsData.class);
        return Result.success(BeanUtil.copyProperties(crmPreMaterials, CrmPreMaterialsVO.class));
    }

    /**
     * 功能描述:
     * 〈修改客户关系活动物料〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<CrmPreMaterialsVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系活动物料")
    @Log(moduleName = "客户关系活动物料管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/materials/edit")
    public Result<CrmPreMaterialsVO> editPreMaterials(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        CrmPreMaterials crmPreMaterials = crmPreMaterialsService.editData(dataEditDTO, CrmPreMaterials.class);
        return Result.success(BeanUtil.copyProperties(crmPreMaterials, CrmPreMaterialsVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系活动物料〉
     * @param preMaterialsId preMaterialsId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系活动物料")
    @Log(moduleName = "客户关系活动物料管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/pre/materials/delete/{preMaterialsId}")
    public Result<Boolean> deletePreMaterials(@PathVariable(value = "preMaterialsId",required = false)Long preMaterialsId) {
        return Result.success(crmPreMaterialsService.deleteData(preMaterialsId));
    }

    /**
     * 功能描述:
     * 〈批量删除客户关系活动物料〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除客户关系活动物料")
    @Log(moduleName = "客户关系活动物料管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/pre/materials/batch/delete")
    public Result<Boolean> deletePreMaterials(@RequestBody DataDelDTO delDTO) {
        return Result.success(crmPreMaterialsService.deleteData(delDTO));
    }
    
    /**
     * 功能描述:
     * 〈转移客户关系活动物料〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移客户关系活动物料")
    @Log(moduleName = "客户关系活动物料管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/materials/trans/scope")
    public Result<Boolean> transPreMaterials(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(crmPreMaterialsService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入客户关系活动物料模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系活动物料模板")
    @Log(moduleName = "客户关系活动物料管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/materials/import/temp")
    public void importPreMaterialsTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"客户关系活动物料导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入客户关系活动物料〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系活动物料")
    @Log(moduleName = "客户关系活动物料管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/crm/pre/materials/import")
    public Result<Boolean> importPreMaterials(@RequestParam("moduleId") Long moduleId,@RequestParam("formId") Long formId,@RequestParam("file") MultipartFile file) {
        return Result.success(crmPreMaterialsService.importData(moduleId,formId,file, CrmPreMaterials.class, CrmPreMaterialsData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出客户关系活动物料〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出客户关系活动物料")
    @Log(moduleName = "客户关系活动物料管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/crm/pre/materials/export")
    public void exportPreMaterials(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return crmPreMaterialsService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}