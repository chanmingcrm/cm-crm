package com.platform.mesh.app.biz.modules.data.common.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.biz.modules.data.common.domain.po.DataCommon;
import com.platform.mesh.app.biz.modules.data.common.domain.vo.DataCommonVO;
import com.platform.mesh.app.biz.modules.data.common.service.IDataCommonService;
import com.platform.mesh.app.biz.modules.data.commondata.domain.po.DataCommonData;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
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
 * @description 通用数据信息
 * @author 蝉鸣
 */
@Tag(description = "DataCommonController", name = "通用数据")
@RestController
@RequestMapping
public class DataCommonController extends BaseController{
    @Autowired
    private IDataCommonService dataCommonService;


    /**
     * 功能描述:
     * 〈获取通用列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<Object>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取通用分页")
    @PostMapping("/app/data/common/page")
    public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = dataCommonService.selectEsPage(pageDTO);
        return Result.success(page);
    }

    /**
     * 功能描述:
     * 〈获取当前通用信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<DataCommonVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前通用信息")
    @PostMapping("/app/data/common/info")
    public Result<DataCommonVO> getDataInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        DataCommonVO dataCommonVO = dataCommonService.getDataInfoById(esDocSGetDTO,DataCommonVO.class);
        return Result.success(dataCommonVO);
    }

    /**
     * 功能描述:
     * 〈新增通用〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<DataCommonVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增通用")
    @Log(moduleName = "通用管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/app/data/common/add/simp")
    public Result<DataCommonVO> addDataSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        DataCommon crmData = dataCommonService.addDataSimp(dataAddSimpDTO, DataCommon.class, DataCommonData.class);
        return Result.success(BeanUtil.copyProperties(crmData,DataCommonVO.class));
    }

    /**
     * 功能描述:
     * 〈新增通用〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<DataCommonVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增通用")
    @Log(moduleName = "通用管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/app/data/common/add/comp")
    public Result<DataCommonVO> addDataComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        DataCommon crmData = dataCommonService.addDataComp(dataAddCompDTO, DataCommon.class, DataCommonData.class);
        return Result.success(BeanUtil.copyProperties(crmData,DataCommonVO.class));
    }

    /**
     * 功能描述:
     * 〈修改通用〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<DataCommonVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改通用")
    @Log(moduleName = "通用管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/app/data/common/edit")
    public Result<DataCommonVO> editData(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        DataCommon crmData = dataCommonService.editData(dataEditDTO, DataCommon.class);
        return Result.success(BeanUtil.copyProperties(crmData,DataCommonVO.class));
    }

    /**
     * 功能描述:
     * 〈删除通用〉
     * @param dataId dataId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除通用")
    @Log(moduleName = "通用管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/app/data/common/delete/{dataId}")
    public Result<Boolean> deleteData(@PathVariable(value = "dataId",required = false)Long dataId) {
        return Result.success(dataCommonService.deleteData(dataId));
    }

    /**
     * 功能描述:
     * 〈批量删除通用〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除通用")
    @Log(moduleName = "通用管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/app/data/common/batch/delete")
    public Result<Boolean> deleteData(@RequestBody DataDelDTO delDTO) {
        return Result.success(dataCommonService.deleteData(delDTO));
    }

    /**
     * 功能描述:
     * 〈转移通用〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移通用")
    @Log(moduleName = "通用管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/app/data/common/trans/scope")
    public Result<Boolean> transData(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(dataCommonService.transData(transScopeDTO));
    }

    /**
     * 功能描述:
     * 〈导入通用模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入通用模板")
    @Log(moduleName = "通用管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/app/data/common/import/temp")
    public void importDataTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"通用导入模板",response);
    }

    /**
     * 功能描述:
     * 〈导入通用〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入通用")
    @Log(moduleName = "通用管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/app/data/common/import")
    public Result<Boolean> importData(@RequestParam("moduleId") Long moduleId,@RequestParam("formId") Long formId,@RequestParam("file") MultipartFile file) {
        return Result.success(dataCommonService.importData(moduleId,formId,file,DataCommon.class,DataCommonData.class));
    }

    /**
     * 功能描述:
     * 〈导出通用〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出通用")
    @Log(moduleName = "通用管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/app/data/common/export")
    public void exportData(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                    exportDTO.setPageNum(pageNum);
                    return dataCommonService.selectEsPage(exportDTO);
                } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}