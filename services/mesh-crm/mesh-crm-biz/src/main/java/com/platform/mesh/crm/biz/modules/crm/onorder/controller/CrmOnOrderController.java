package com.platform.mesh.crm.biz.modules.crm.onorder.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.crm.onorder.domain.po.CrmOnOrder;
import com.platform.mesh.crm.biz.modules.crm.onorder.domain.vo.CrmOnOrderVO;
import com.platform.mesh.crm.biz.modules.crm.onorder.service.ICrmOnOrderService;
import com.platform.mesh.crm.biz.modules.crm.onorderdata.domain.po.CrmOnOrderData;
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
 * @description 客户关系订单信息
 * @author 蝉鸣
 */
@Tag(description = "CrmOnOrderController", name = "客户关系订单")
@RestController
@RequestMapping
public class CrmOnOrderController extends BaseController{
    @Autowired
    private ICrmOnOrderService crmOnOrderService;

    /**
	 * 功能描述:
	 * 〈获取客户关系订单列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系订单分页")
	@PostMapping("/crm/on/order/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = crmOnOrderService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前客户关系订单信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<CrmOnOrderVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系订单信息")
    @PostMapping("/crm/on/order/info")
    public Result<CrmOnOrderVO> getOnOrderInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        CrmOnOrderVO crmOnOrderVO = crmOnOrderService.getDataInfoById(esDocSGetDTO, CrmOnOrderVO.class);
        return Result.success(crmOnOrderVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系订单〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<CrmOnOrderVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系订单")
    @Log(moduleName = "客户关系订单管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/on/order/add/simp")
    public Result<CrmOnOrderVO> addOnOrderSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        CrmOnOrder crmOnOrder = crmOnOrderService.addDataSimp(dataAddSimpDTO, CrmOnOrder.class, CrmOnOrderData.class);
        return Result.success(BeanUtil.copyProperties(crmOnOrder, CrmOnOrderVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增客户关系订单〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<CrmOnOrderVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系订单")
    @Log(moduleName = "客户关系订单管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/on/order/add/comp")
    public Result<CrmOnOrderVO> addOnOrderComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        CrmOnOrder crmOnOrder = crmOnOrderService.addDataComp(dataAddCompDTO, CrmOnOrder.class, CrmOnOrderData.class);
        return Result.success(BeanUtil.copyProperties(crmOnOrder, CrmOnOrderVO.class));
    }

    /**
     * 功能描述:
     * 〈修改客户关系订单〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<CrmOnOrderVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系订单")
    @Log(moduleName = "客户关系订单管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/order/edit")
    public Result<CrmOnOrderVO> editOnOrder(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        CrmOnOrder crmOnOrder = crmOnOrderService.editData(dataEditDTO, CrmOnOrder.class);
        return Result.success(BeanUtil.copyProperties(crmOnOrder, CrmOnOrderVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系订单〉
     * @param onOrderId onOrderId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系订单")
    @Log(moduleName = "客户关系订单管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/on/order/delete/{onOrderId}")
    public Result<Boolean> deleteOnOrder(@PathVariable(value = "onOrderId",required = false)Long onOrderId) {
        return Result.success(crmOnOrderService.deleteData(onOrderId));
    }

    /**
     * 功能描述:
     * 〈批量删除客户关系订单〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除客户关系订单")
    @Log(moduleName = "客户关系订单管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/on/order/batch/delete")
    public Result<Boolean> deleteOnOrder(@RequestBody DataDelDTO delDTO) {
        return Result.success(crmOnOrderService.deleteData(delDTO));
    }
    
    /**
     * 功能描述:
     * 〈转移客户关系订单〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移客户关系订单")
    @Log(moduleName = "客户关系订单管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/order/trans/scope")
    public Result<Boolean> transOnOrder(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(crmOnOrderService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入客户关系订单模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系订单模板")
    @Log(moduleName = "客户关系订单管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/order/import/temp")
    public void importOnOrderTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"客户关系订单导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入客户关系订单〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系订单")
    @Log(moduleName = "客户关系订单管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/crm/on/order/import")
    public Result<Boolean> importOnOrder(@RequestParam("moduleId") Long moduleId,@RequestParam("formId") Long formId,@RequestParam("file") MultipartFile file) {
        return Result.success(crmOnOrderService.importData(moduleId,formId,file, CrmOnOrder.class, CrmOnOrderData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出客户关系订单〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出客户关系订单")
    @Log(moduleName = "客户关系订单管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/crm/on/order/export")
    public void exportOnOrder(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return crmOnOrderService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}