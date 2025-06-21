package com.platform.mesh.crm.biz.modules.crm.precustomer.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.po.CrmPreCustomer;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.vo.CrmPreCustomerVO;
import com.platform.mesh.crm.biz.modules.crm.precustomer.service.ICrmPreCustomerService;
import com.platform.mesh.crm.biz.modules.crm.precustomerdata.domain.po.CrmPreCustomerData;
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
 * @description 客户关系客户对象信息
 * @author 蝉鸣
 */
@Tag(description = "CrmPreCustomerController", name = "客户关系客户对象")
@RestController
@RequestMapping
public class CrmPreCustomerController extends BaseController{
    @Autowired
    private ICrmPreCustomerService  crmPreCustomerService;

    /**
     * 功能描述:
     * 〈获取数据分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<Object>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取数据分页")
    @PostMapping("/crm/pre/customer/page")
    public Result<PageVO<Object>> selectEsPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = crmPreCustomerService.selectEsPage(pageDTO);
        return Result.success(page);
    }

    /**
     * 功能描述:
     * 〈获取当前客户关系客户对象信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<CrmPreCustomerVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系客户对象信息")
    @PostMapping("/crm/pre/customer/info")
    public Result<CrmPreCustomerVO> getPreCustomerInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        CrmPreCustomerVO crmPreCustomerVO = crmPreCustomerService.getDataInfoById(esDocSGetDTO,CrmPreCustomerVO.class);
        return Result.success(crmPreCustomerVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系客户对象〉
     * @param dataAddSimpDTO dataAddDTO
     * @return 正常返回:{@link Result<CrmPreCustomerVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系客户对象")
    @Log(moduleName = "客户关系客户对象管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/pre/customer/add/simp")
    public Result<CrmPreCustomerVO> addPreCustomerSimp(@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        CrmPreCustomer crmPreCustomer = crmPreCustomerService.addDataSimp(dataAddSimpDTO, CrmPreCustomer.class, CrmPreCustomerData.class);
        return Result.success(BeanUtil.copyProperties(crmPreCustomer,CrmPreCustomerVO.class));
    }

    /**
     * 功能描述:
     * 〈新增客户关系客户对象〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<CrmPreCustomerVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系客户对象")
    @Log(moduleName = "客户关系客户对象管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/pre/customer/add/comp")
    public Result<CrmPreCustomerVO> addPreCustomerComp(@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        CrmPreCustomer crmPreCustomer = crmPreCustomerService.addDataComp(dataAddCompDTO, CrmPreCustomer.class, CrmPreCustomerData.class);
        return Result.success(BeanUtil.copyProperties(crmPreCustomer,CrmPreCustomerVO.class));
    }

    /**
     * 功能描述:
     * 〈修改客户关系客户对象〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<CrmPreCustomerVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系客户对象")
    @Log(moduleName = "客户关系客户对象管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/customer/edit")
    public Result<CrmPreCustomerVO> editPreCustomer(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        CrmPreCustomer crmPreCustomer = crmPreCustomerService.editData(dataEditDTO, CrmPreCustomer.class);
        return Result.success(BeanUtil.copyProperties(crmPreCustomer,CrmPreCustomerVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系客户对象〉
     * @param preCustomerId preCustomerId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系客户对象")
    @Log(moduleName = "客户关系客户对象管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/pre/customer/delete/{preCustomerId}")
    public Result<Boolean> deletePreCustomer(@PathVariable(value = "preCustomerId",required = false)Long preCustomerId) {
        return Result.success(crmPreCustomerService.deleteData(preCustomerId));
    }

    /**
     * 功能描述:
     * 〈批量删除客户关系客户对象〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除客户关系客户对象")
    @Log(moduleName = "客户关系客户对象管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/pre/customer/batch/delete")
    public Result<Boolean> deletePreCustomer(@RequestBody DataDelDTO delDTO) {
        return Result.success(crmPreCustomerService.deleteData(delDTO));
    }

    /**
     * 功能描述:
     * 〈转移客户关系客户对象〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移客户关系客户对象")
    @Log(moduleName = "客户关系客户对象管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/customer/trans/scope")
    public Result<Boolean> transPreCustomer(@RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(crmPreCustomerService.transData(transScopeDTO));
    }

    /**
     * 功能描述:
     * 〈导入客户关系客户对象模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系客户对象模板")
    @PostMapping("/crm/pre/customer/import/temp")
    public void importPreCustomerTemp(@RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        //获取导读模板列头
        ExcelUtil.exportTemp(headDTOS,"客户对象导出模板",response);
    }

   /**
     * 功能描述:
     * 〈导入客户关系客户对象〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系客户对象")
    @Log(moduleName = "客户关系客户对象管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/crm/pre/customer/import")
    public Result<Boolean> importPreCustomer(@RequestParam("moduleId") Long moduleId,@RequestParam("formId") Long formId,@RequestParam("file") MultipartFile file) {
        return Result.success(crmPreCustomerService.importData(moduleId,formId,file, CrmPreCustomer.class, CrmPreCustomerData.class));
    }

   /**
     * 功能描述:
     * 〈导出客户关系客户对象〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出客户关系客户对象")
    @Log(moduleName = "客户关系客户对象管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/crm/pre/customer/export")
    public void exportPreCustomer(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return crmPreCustomerService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }

}