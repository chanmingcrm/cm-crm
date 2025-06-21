package com.platform.mesh.crm.biz.modules.crm.onfollow.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.crm.biz.modules.crm.onfollow.domain.po.CrmOnFollow;
import com.platform.mesh.crm.biz.modules.crm.onfollow.domain.vo.CrmOnFollowVO;
import com.platform.mesh.crm.biz.modules.crm.onfollow.service.ICrmOnFollowService;
import com.platform.mesh.crm.biz.modules.crm.onfollowdata.domain.po.CrmOnFollowData;
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
 * @description 客户关系跟进拜访信息
 * @author 蝉鸣
 */
@Tag(description = "CrmOnFollowController", name = "客户关系跟进拜访")
@RestController
@RequestMapping
public class CrmOnFollowController extends BaseController{
    @Autowired
    private ICrmOnFollowService  crmOnFollowService;

    /**
	 * 功能描述:
	 * 〈获取客户关系跟进拜访列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系跟进拜访分页")
	@PostMapping("/crm/on/follow/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = crmOnFollowService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前客户关系跟进拜访信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<CrmOnFollowVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系跟进拜访信息")
    @PostMapping("/crm/on/follow/info")
    public Result<CrmOnFollowVO> getOnFollowInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        CrmOnFollowVO crmOnFollowVO = crmOnFollowService.getDataInfoById(esDocSGetDTO,CrmOnFollowVO.class);
        return Result.success(crmOnFollowVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系跟进拜访〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<CrmOnFollowVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系跟进拜访")
    @Log(moduleName = "客户关系跟进拜访管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/on/follow/add/simp")
    public Result<CrmOnFollowVO> addOnFollowSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        CrmOnFollow crmOnFollow = crmOnFollowService.addDataSimp(dataAddSimpDTO, CrmOnFollow.class, CrmOnFollowData.class);
        return Result.success(BeanUtil.copyProperties(crmOnFollow,CrmOnFollowVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增客户关系跟进拜访〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<CrmOnFollowVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系跟进拜访")
    @Log(moduleName = "客户关系跟进拜访管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/on/follow/add/comp")
    public Result<CrmOnFollowVO> addOnFollowComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        CrmOnFollow crmOnFollow = crmOnFollowService.addDataComp(dataAddCompDTO, CrmOnFollow.class, CrmOnFollowData.class);
        return Result.success(BeanUtil.copyProperties(crmOnFollow,CrmOnFollowVO.class));
    }

    /**
     * 功能描述:
     * 〈修改客户关系跟进拜访〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<CrmOnFollowVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系跟进拜访")
    @Log(moduleName = "客户关系跟进拜访管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/follow/edit")
    public Result<CrmOnFollowVO> editOnFollow(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        CrmOnFollow crmOnFollow = crmOnFollowService.editData(dataEditDTO, CrmOnFollow.class);
        return Result.success(BeanUtil.copyProperties(crmOnFollow,CrmOnFollowVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系跟进拜访〉
     * @param onFollowId onFollowId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系跟进拜访")
    @Log(moduleName = "客户关系跟进拜访管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/on/follow/delete/{onFollowId}")
    public Result<Boolean> deleteOnFollow(@PathVariable(value = "onFollowId",required = false)Long onFollowId) {
        return Result.success(crmOnFollowService.deleteData(onFollowId));
    }

    /**
     * 功能描述:
     * 〈批量删除客户关系跟进拜访〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除客户关系跟进拜访")
    @Log(moduleName = "客户关系跟进拜访管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/on/follow/batch/delete")
    public Result<Boolean> deleteOnFollow(@RequestBody DataDelDTO delDTO) {
        return Result.success(crmOnFollowService.deleteData(delDTO));
    }
    
    /**
     * 功能描述:
     * 〈转移客户关系跟进拜访〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移客户关系跟进拜访")
    @Log(moduleName = "客户关系跟进拜访管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/follow/trans/scope")
    public Result<Boolean> transOnFollow(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(crmOnFollowService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入客户关系跟进拜访模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系跟进拜访模板")
    @Log(moduleName = "客户关系跟进拜访管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/on/follow/import/temp")
    public void importOnFollowTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"客户关系跟进拜访导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入客户关系跟进拜访〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入客户关系跟进拜访")
    @Log(moduleName = "客户关系跟进拜访管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/crm/on/follow/import")
    public Result<Boolean> importOnFollow(@RequestParam("moduleId") Long moduleId,@RequestParam("formId") Long formId,@RequestParam("file") MultipartFile file) {
        return Result.success(crmOnFollowService.importData(moduleId,formId,file, CrmOnFollow.class, CrmOnFollowData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出客户关系跟进拜访〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出客户关系跟进拜访")
    @Log(moduleName = "客户关系跟进拜访管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/crm/on/follow/export")
    public void exportOnFollow(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return crmOnFollowService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}