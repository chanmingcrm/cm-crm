package com.platform.mesh.crm.biz.modules.crm.oninvoice.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddCompDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataDelDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.app.api.modules.app.domain.dto.TransScopeDTO;
import com.platform.mesh.crm.biz.modules.crm.oninvoice.domain.po.CrmOnInvoice;
import com.platform.mesh.crm.biz.modules.crm.oninvoice.domain.vo.CrmOnInvoiceVO;
import com.platform.mesh.crm.biz.modules.crm.oninvoice.service.ICrmOnInvoiceService;
import com.platform.mesh.crm.biz.modules.crm.oninvoicedata.domain.po.CrmOnInvoiceData;
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
 * @description 客户关系发票回执信息
 * @author 蝉鸣
 */
@Tag(description = "CrmOnInvoiceController", name = "客户关系发票回执")
@RestController
@RequestMapping
public class CrmOnInvoiceController extends BaseController{
    @Autowired
    private ICrmOnInvoiceService  crmOnInvoiceService;

    /**
	 * 功能描述:
	 * 〈获取客户关系发票回执列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系发票回执分页")
	@PostMapping("/crm/on/invoice/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = crmOnInvoiceService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前客户关系发票回执信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<CrmOnInvoiceVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系发票回执信息")
    @PostMapping("/crm/on/invoice/info")
    public Result<CrmOnInvoiceVO> getOnInvoiceInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        CrmOnInvoiceVO crmOnInvoiceVO = crmOnInvoiceService.getDataInfoById(esDocSGetDTO,CrmOnInvoiceVO.class);
        return Result.success(crmOnInvoiceVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系发票回执〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<CrmOnInvoiceVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系发票回执")
    @Log(moduleName = "客户关系发票回执管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/on/invoice/add/simp")
    public Result<CrmOnInvoiceVO> addOnInvoiceSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        CrmOnInvoice crmOnInvoice = crmOnInvoiceService.addDataSimp(dataAddSimpDTO, CrmOnInvoice.class, CrmOnInvoiceData.class);
        return Result.success(BeanUtil.copyProperties(crmOnInvoice,CrmOnInvoiceVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增客户关系发票回执〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<CrmOnInvoiceVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系发票回执")
    @Log(moduleName = "客户关系发票回执管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/on/invoice/add/comp")
    public Result<CrmOnInvoiceVO> addOnInvoiceComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        CrmOnInvoice crmOnInvoice = crmOnInvoiceService.addDataComp(dataAddCompDTO, CrmOnInvoice.class, CrmOnInvoiceData.class);
        return Result.success(BeanUtil.copyProperties(crmOnInvoice,CrmOnInvoiceVO.class));
    }

    /**
     * 功能描述:
     * 〈修改客户关系发票回执〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<CrmOnInvoiceVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系发票回执")
    @Log(moduleName = "客户关系发票回执管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/invoice/edit")
    public Result<CrmOnInvoiceVO> editOnInvoice(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        CrmOnInvoice crmOnInvoice = crmOnInvoiceService.editData(dataEditDTO, CrmOnInvoice.class);
        return Result.success(BeanUtil.copyProperties(crmOnInvoice,CrmOnInvoiceVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系发票回执〉
     * @param onInvoiceId onInvoiceId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系发票回执")
    @Log(moduleName = "客户关系发票回执管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/on/invoice/delete/{onInvoiceId}")
    public Result<Boolean> deleteOnInvoice(@PathVariable(value = "onInvoiceId",required = false)Long onInvoiceId) {
        return Result.success(crmOnInvoiceService.deleteData(onInvoiceId));
    }

    /**
     * 功能描述:
     * 〈批量删除客户关系发票回执〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除客户关系发票回执")
    @Log(moduleName = "客户关系发票回执管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/on/invoice/batch/delete")
    public Result<Boolean> deleteOnInvoice(@RequestBody DataDelDTO delDTO) {
        return Result.success(crmOnInvoiceService.deleteData(delDTO));
    }
    
    /**
     * 功能描述:
     * 〈转移客户关系发票回执〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移客户关系发票回执")
    @Log(moduleName = "客户关系发票回执管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/invoice/trans/scope")
    public Result<Boolean> transOnInvoice(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(crmOnInvoiceService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入客户关系发票回执模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系发票回执模板")
    @Log(moduleName = "客户关系发票回执管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/invoice/import/temp")
    public void importOnInvoiceTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"客户关系发票回执导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入客户关系发票回执〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系发票回执")
    @Log(moduleName = "客户关系发票回执管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/crm/on/invoice/import")
    public Result<Boolean> importOnInvoice(@RequestParam("moduleId") Long moduleId,@RequestParam("formId") Long formId,@RequestParam("file") MultipartFile file) {
        return Result.success(crmOnInvoiceService.importData(moduleId,formId,file, CrmOnInvoice.class, CrmOnInvoiceData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出客户关系发票回执〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出客户关系发票回执")
    @Log(moduleName = "客户关系发票回执管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/crm/on/invoice/export")
    public void exportOnInvoice(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return crmOnInvoiceService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}