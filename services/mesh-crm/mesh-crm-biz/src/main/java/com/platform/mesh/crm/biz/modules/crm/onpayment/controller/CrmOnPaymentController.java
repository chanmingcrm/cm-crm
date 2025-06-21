package com.platform.mesh.crm.biz.modules.crm.onpayment.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddCompDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataDelDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.app.api.modules.app.domain.dto.TransScopeDTO;
import com.platform.mesh.crm.biz.modules.crm.onpayment.domain.po.CrmOnPayment;
import com.platform.mesh.crm.biz.modules.crm.onpayment.domain.vo.CrmOnPaymentVO;
import com.platform.mesh.crm.biz.modules.crm.onpayment.service.ICrmOnPaymentService;
import com.platform.mesh.crm.biz.modules.crm.onpaymentdata.domain.po.CrmOnPaymentData;
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
 * @description 客户关系款项记录信息
 * @author 蝉鸣
 */
@Tag(description = "CrmOnPaymentController", name = "客户关系款项记录")
@RestController
@RequestMapping
public class CrmOnPaymentController extends BaseController{
    @Autowired
    private ICrmOnPaymentService  crmOnPaymentService;

    /**
	 * 功能描述:
	 * 〈获取客户关系款项记录列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系款项记录分页")
	@PostMapping("/crm/on/payment/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = crmOnPaymentService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前客户关系款项记录信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<CrmOnPaymentVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系款项记录信息")
    @PostMapping("/crm/on/payment/info")
    public Result<CrmOnPaymentVO> getOnPaymentInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        CrmOnPaymentVO crmOnPaymentVO = crmOnPaymentService.getDataInfoById(esDocSGetDTO,CrmOnPaymentVO.class);
        return Result.success(crmOnPaymentVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系款项记录〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<CrmOnPaymentVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系款项记录")
    @Log(moduleName = "客户关系款项记录管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/on/payment/add/simp")
    public Result<CrmOnPaymentVO> addOnPaymentSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        CrmOnPayment crmOnPayment = crmOnPaymentService.addDataSimp(dataAddSimpDTO, CrmOnPayment.class, CrmOnPaymentData.class);
        return Result.success(BeanUtil.copyProperties(crmOnPayment,CrmOnPaymentVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增客户关系款项记录〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<CrmOnPaymentVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系款项记录")
    @Log(moduleName = "客户关系款项记录管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/on/payment/add/comp")
    public Result<CrmOnPaymentVO> addOnPaymentComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        CrmOnPayment crmOnPayment = crmOnPaymentService.addDataComp(dataAddCompDTO, CrmOnPayment.class, CrmOnPaymentData.class);
        return Result.success(BeanUtil.copyProperties(crmOnPayment,CrmOnPaymentVO.class));
    }

    /**
     * 功能描述:
     * 〈修改客户关系款项记录〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<CrmOnPaymentVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系款项记录")
    @Log(moduleName = "客户关系款项记录管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/payment/edit")
    public Result<CrmOnPaymentVO> editOnPayment(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        CrmOnPayment crmOnPayment = crmOnPaymentService.editData(dataEditDTO, CrmOnPayment.class);
        return Result.success(BeanUtil.copyProperties(crmOnPayment,CrmOnPaymentVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系款项记录〉
     * @param onPaymentId onPaymentId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系款项记录")
    @Log(moduleName = "客户关系款项记录管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/on/payment/delete/{onPaymentId}")
    public Result<Boolean> deleteOnPayment(@PathVariable(value = "onPaymentId",required = false)Long onPaymentId) {
        return Result.success(crmOnPaymentService.deleteData(onPaymentId));
    }

    /**
     * 功能描述:
     * 〈批量删除客户关系款项记录〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除客户关系款项记录")
    @Log(moduleName = "客户关系款项记录管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/on/payment/batch/delete")
    public Result<Boolean> deleteOnPayment(@RequestBody DataDelDTO delDTO) {
        return Result.success(crmOnPaymentService.deleteData(delDTO));
    }
    
    /**
     * 功能描述:
     * 〈转移客户关系款项记录〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移客户关系款项记录")
    @Log(moduleName = "客户关系款项记录管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/payment/trans/scope")
    public Result<Boolean> transOnPayment(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(crmOnPaymentService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入客户关系款项记录模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系款项记录模板")
    @Log(moduleName = "客户关系款项记录管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/payment/import/temp")
    public void importOnPaymentTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"客户关系款项记录导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入客户关系款项记录〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系款项记录")
    @Log(moduleName = "客户关系款项记录管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/crm/on/payment/import")
    public Result<Boolean> importOnPayment(@RequestParam("moduleId") Long moduleId,@RequestParam("formId") Long formId,@RequestParam("file") MultipartFile file) {
        return Result.success(crmOnPaymentService.importData(moduleId,formId,file, CrmOnPayment.class, CrmOnPaymentData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出客户关系款项记录〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出客户关系款项记录")
    @Log(moduleName = "客户关系款项记录管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/crm/on/payment/export")
    public void exportOnPayment(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return crmOnPaymentService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}