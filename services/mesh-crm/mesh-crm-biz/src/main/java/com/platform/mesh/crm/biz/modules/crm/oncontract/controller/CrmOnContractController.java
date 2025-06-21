package com.platform.mesh.crm.biz.modules.crm.oncontract.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.crm.oncontract.domain.po.CrmOnContract;
import com.platform.mesh.crm.biz.modules.crm.oncontract.domain.vo.CrmOnContractVO;
import com.platform.mesh.crm.biz.modules.crm.oncontract.service.ICrmOnContractService;
import com.platform.mesh.crm.biz.modules.crm.oncontractdata.domain.po.CrmOnContractData;
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
 * @description 客户关系合同签订信息
 * @author 蝉鸣
 */
@Tag(description = "CrmOnContractController", name = "客户关系合同签订")
@RestController
@RequestMapping
public class CrmOnContractController extends BaseController{
    @Autowired
    private ICrmOnContractService  crmOnContractService;

    /**
	 * 功能描述:
	 * 〈获取客户关系合同签订列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系合同签订分页")
	@PostMapping("/crm/on/contract/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = crmOnContractService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前客户关系合同签订信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<CrmOnContractVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系合同签订信息")
    @PostMapping("/crm/on/contract/info")
    public Result<CrmOnContractVO> getOnContractInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        CrmOnContractVO crmOnContractVO = crmOnContractService.getDataInfoById(esDocSGetDTO,CrmOnContractVO.class);
        return Result.success(crmOnContractVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系合同签订〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<CrmOnContractVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系合同签订")
    @Log(moduleName = "客户关系合同签订管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/on/contract/add/simp")
    public Result<CrmOnContractVO> addOnContractSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        CrmOnContract crmOnContract = crmOnContractService.addDataSimp(dataAddSimpDTO, CrmOnContract.class, CrmOnContractData.class);
        return Result.success(BeanUtil.copyProperties(crmOnContract, CrmOnContractVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增客户关系合同签订〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<CrmOnContractVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系合同签订")
    @Log(moduleName = "客户关系合同签订管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/on/contract/add/comp")
    public Result<CrmOnContractVO> addOnContractComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        CrmOnContract crmOnContract = crmOnContractService.addDataComp(dataAddCompDTO, CrmOnContract.class, CrmOnContractData.class);
        return Result.success(BeanUtil.copyProperties(crmOnContract, CrmOnContractVO.class));
    }

    /**
     * 功能描述:
     * 〈修改客户关系合同签订〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<CrmOnContractVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系合同签订")
    @Log(moduleName = "客户关系合同签订管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/contract/edit")
    public Result<CrmOnContractVO> editOnContract(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        CrmOnContract crmOnContract = crmOnContractService.editData(dataEditDTO, CrmOnContract.class);
        return Result.success(BeanUtil.copyProperties(crmOnContract, CrmOnContractVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系合同签订〉
     * @param onContractId onContractId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系合同签订")
    @Log(moduleName = "客户关系合同签订管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/on/contract/delete/{onContractId}")
    public Result<Boolean> deleteOnContract(@PathVariable(value = "onContractId",required = false)Long onContractId) {
        return Result.success(crmOnContractService.deleteData(onContractId));
    }

    /**
     * 功能描述:
     * 〈批量删除客户关系合同签订〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除客户关系合同签订")
    @Log(moduleName = "客户关系合同签订管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/on/contract/batch/delete")
    public Result<Boolean> deleteOnContract(@RequestBody DataDelDTO delDTO) {
        return Result.success(crmOnContractService.deleteData(delDTO));
    }
    
    /**
     * 功能描述:
     * 〈转移客户关系合同签订〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移客户关系合同签订")
    @Log(moduleName = "客户关系合同签订管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/contract/trans/scope")
    public Result<Boolean> transOnContract(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(crmOnContractService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入客户关系合同签订模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系合同签订模板")
    @Log(moduleName = "客户关系合同签订管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/contract/import/temp")
    public void importOnContractTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"客户关系合同签订导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入客户关系合同签订〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系合同签订")
    @Log(moduleName = "客户关系合同签订管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/crm/on/contract/import")
    public Result<Boolean> importOnContract(@RequestParam("moduleId") Long moduleId,@RequestParam("formId") Long formId,@RequestParam("file") MultipartFile file) {
        return Result.success(crmOnContractService.importData(moduleId,formId,file, CrmOnContract.class, CrmOnContractData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出客户关系合同签订〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出客户关系合同签订")
    @Log(moduleName = "客户关系合同签订管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/crm/on/contract/export")
    public void exportOnContract(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return crmOnContractService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}