package com.platform.mesh.crm.biz.modules.crm.precontacts.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.crm.precontacts.domain.po.CrmPreContacts;
import com.platform.mesh.crm.biz.modules.crm.precontacts.domain.vo.CrmPreContactsVO;
import com.platform.mesh.crm.biz.modules.crm.precontacts.service.ICrmPreContactsService;
import com.platform.mesh.crm.biz.modules.crm.precontactsdata.domain.po.CrmPreContactsData;
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
 * @description 客户关系联系人信息
 * @author 蝉鸣
 */
@Tag(description = "CrmPreContactsController", name = "客户关系联系人")
@RestController
@RequestMapping
public class CrmPreContactsController extends BaseController{

    @Autowired
    private ICrmPreContactsService crmPreContactsService;

    /**
     * 功能描述:
     * 〈获取数据分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<Object>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取数据分页")
    @PostMapping("/crm/pre/contacts/page")
    public Result<PageVO<Object>> selectEsPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = crmPreContactsService.selectEsPage(pageDTO);
        return Result.success(page);
    }

    /**
     * 功能描述:
     * 〈获取当前客户关系联系人信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<CrmPreContactsVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系联系人信息")
    @PostMapping("/crm/pre/contacts/info")
    public Result<CrmPreContactsVO> getPreContactsInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        CrmPreContactsVO crmPreContactsVO = crmPreContactsService.getDataInfoById(esDocSGetDTO,CrmPreContactsVO.class);
        return Result.success(crmPreContactsVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系联系人〉
     * @param dataAddSimpDTO dataAddDTO
     * @return 正常返回:{@link Result<CrmPreContactsVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系联系人")
    @Log(moduleName = "客户关系联系人管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/pre/contacts/add/simp")
    public Result<CrmPreContactsVO> addPreContactsSimp(@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        CrmPreContacts crmPreContacts = crmPreContactsService.addDataSimp(dataAddSimpDTO, CrmPreContacts.class, CrmPreContactsData.class);
        return Result.success(BeanUtil.copyProperties(crmPreContacts, CrmPreContactsVO.class));
    }

    /**
     * 功能描述:
     * 〈新增客户关系联系人〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<CrmPreContactsVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系联系人")
    @Log(moduleName = "客户关系联系人管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/pre/contacts/add/comp")
    public Result<CrmPreContactsVO> addPreContactsComp(@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        CrmPreContacts crmPreContacts = crmPreContactsService.addDataComp(dataAddCompDTO, CrmPreContacts.class, CrmPreContactsData.class);
        return Result.success(BeanUtil.copyProperties(crmPreContacts, CrmPreContactsVO.class));
    }

    /**
     * 功能描述:
     * 〈修改客户关系联系人〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<CrmPreContactsVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系联系人")
    @Log(moduleName = "客户关系联系人管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/contacts/edit")
    public Result<CrmPreContactsVO> editPreContacts(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        CrmPreContacts crmPreContacts = crmPreContactsService.editData(dataEditDTO, CrmPreContacts.class);
        return Result.success(BeanUtil.copyProperties(crmPreContacts, CrmPreContactsVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系联系人〉
     * @param preContactsId preContactsId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系联系人")
    @Log(moduleName = "客户关系联系人管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/pre/contacts/delete/{preContactsId}")
    public Result<Boolean> deletePreContacts(@PathVariable(value = "preContactsId",required = false)Long preContactsId) {
        return Result.success(crmPreContactsService.deleteData(preContactsId));
    }

    /**
     * 功能描述:
     * 〈批量删除客户关系联系人〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除客户关系联系人")
    @Log(moduleName = "客户关系联系人管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/pre/contacts/batch/delete")
    public Result<Boolean> deletePreContacts(@RequestBody DataDelDTO delDTO) {
        return Result.success(crmPreContactsService.deleteData(delDTO));
    }

    /**
     * 功能描述:
     * 〈转移客户关系联系人〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移客户关系联系人")
    @Log(moduleName = "客户关系联系人管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/pre/contacts/trans/scope")
    public Result<Boolean> transPreContacts(@RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(crmPreContactsService.transData(transScopeDTO));
    }

    /**
     * 功能描述:
     * 〈导入客户关系联系人模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系联系人模板")
    @PostMapping("/crm/pre/contacts/import/temp")
    public void importPreContactsTemp(@RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        //获取导读模板列头
        ExcelUtil.exportTemp(headDTOS,"联系人导出模板",response);
    }

   /**
     * 功能描述:
     * 〈导入客户关系联系人〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系联系人")
    @Log(moduleName = "客户关系联系人管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/crm/pre/contacts/import")
    public Result<Boolean> importPreContacts(@RequestParam("moduleId") Long moduleId,@RequestParam("formId") Long formId,@RequestParam("file") MultipartFile file) {
        return Result.success(crmPreContactsService.importData(moduleId,formId,file, CrmPreContacts.class, CrmPreContactsData.class));
    }

   /**
     * 功能描述:
     * 〈导出客户关系联系人〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出客户关系联系人")
    @Log(moduleName = "客户关系联系人管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/crm/pre/contacts/export")
    public void exportPreContacts(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return crmPreContactsService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }

}