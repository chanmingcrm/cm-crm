package com.platform.mesh.tmp.biz.modules.work.log.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.es.domain.dto.EsDocEGetDTO;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.es.domain.dto.EsDocSGetDTO;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.tmp.biz.modules.work.log.domain.po.WorkLog;
import com.platform.mesh.tmp.biz.modules.work.log.domain.vo.WorkLogVO;
import com.platform.mesh.tmp.biz.modules.work.log.service.IWorkLogService;
import com.platform.mesh.tmp.biz.modules.work.logdata.domain.po.WorkLogData;
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
 * @description 工作日志信息
 * @author 蝉鸣
 */
@Tag(description = "WorkLogController", name = "工作日志")
@RestController
@RequestMapping
public class WorkLogController extends BaseController{

    @Autowired
    private IWorkLogService workLogService;

    /**
	 * 功能描述:
	 * 〈获取工作日志列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取工作日志分页")
	@PostMapping("/oa/work/log/page")
	public Result<PageVO<Object>> selectPage(@RequestBody EsDocPGetDTO pageDTO) {
        PageVO<Object> page = workLogService.selectEsPage(pageDTO);
        return Result.success(page);
	}

    /**
     * 功能描述:
     * 〈获取当前工作日志信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<WorkLogVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前工作日志信息")
    @PostMapping("/oa/work/log/info")
    public Result<WorkLogVO> getWorkLogInfoById(@RequestBody EsDocSGetDTO esDocSGetDTO) {
        WorkLogVO crmWorkLogVO = workLogService.getDataInfoById(esDocSGetDTO,WorkLogVO.class);
        return Result.success(crmWorkLogVO);
    }

    /**
     * 功能描述:
     * 〈新增工作日志〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link Result<WorkLogVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增工作日志")
    @Log(moduleName = "工作日志管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/oa/work/log/add/simp")
    public Result<WorkLogVO> addWorkLogSimp (@Validated @RequestBody DataAddSimpDTO dataAddSimpDTO) {
        WorkLog workLog = workLogService.addDataSimp(dataAddSimpDTO, WorkLog.class, WorkLogData.class);
        return Result.success(BeanUtil.copyProperties(workLog, WorkLogVO.class));
    }
    
    /**
     * 功能描述:
     * 〈新增工作日志〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link Result<WorkLogVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增工作日志")
    @Log(moduleName = "工作日志管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/oa/work/log/add/comp")
    public Result<WorkLogVO> addWorkLogComp (@Validated @RequestBody DataAddCompDTO dataAddCompDTO) {
        WorkLog workLog = workLogService.addDataComp(dataAddCompDTO, WorkLog.class, WorkLogData.class);
        return Result.success(BeanUtil.copyProperties(workLog, WorkLogVO.class));
    }

    /**
     * 功能描述:
     * 〈修改工作日志〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link Result<WorkLogVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改工作日志")
    @Log(moduleName = "工作日志管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/oa/work/log/edit")
    public Result<WorkLogVO> editWorkLog(@Validated @RequestBody DataEditSimpDTO dataEditDTO) {
        WorkLog workLog = workLogService.editData(dataEditDTO, WorkLog.class);
        return Result.success(BeanUtil.copyProperties(workLog, WorkLogVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除工作日志〉
     * @param onBusinessId onBusinessId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除工作日志")
    @Log(moduleName = "工作日志管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/oa/work/log/delete/{onBusinessId}")
    public Result<Boolean> deleteWorkLog(@PathVariable(value = "onBusinessId",required = false)Long onBusinessId) {
        return Result.success(workLogService.deleteData(onBusinessId));
    }

    /**
     * 功能描述:
     * 〈批量删除工作日志〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "批量删除工作日志")
    @Log(moduleName = "工作日志管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/oa/work/log/batch/delete")
    public Result<Boolean> deleteWorkLog(@RequestBody DataDelDTO delDTO) {
        return Result.success(workLogService.deleteData(delDTO));
    }

    /**
     * 功能描述:
     * 〈转移工作日志〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移工作日志")
    @Log(moduleName = "工作日志管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/oa/work/log/trans/scope")
    public Result<Boolean> transWorkLog(@Validated @RequestBody TransScopeDTO transScopeDTO) {
        return Result.success(workLogService.transData(transScopeDTO));
    }
        
    /**
     * 功能描述:
     * 〈导入工作日志模板〉
     * @param headDTOS headDTOS
     * @author 蝉鸣
     */
    @Operation(summary = "导入工作日志模板")
    @Log(moduleName = "工作日志管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/oa/work/log/import/temp")
    public void importWorkLogTemp(@Validated @RequestBody List<HeadDTO> headDTOS, HttpServletResponse response) {
        ExcelUtil.exportTemp(headDTOS,"工作日志导入模板",response);
    }

   /**
     * 功能描述:
     * 〈导入工作日志〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "导入工作日志")
    @Log(moduleName = "工作日志管理", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/oa/work/log/import")
    public Result<Boolean> importWorkLog(@RequestParam("moduleId") Long moduleId,@RequestParam("formId") Long formId,@RequestParam("file") MultipartFile file) {
        return Result.success(workLogService.importData(moduleId,formId,file, WorkLog.class, WorkLogData.class));
    }
    
    /**
     * 功能描述:
     * 〈导出工作日志〉
     * @param exportDTO exportDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出工作日志")
    @Log(moduleName = "工作日志管理", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/oa/work/log/export")
    public void exportWorkLog(@RequestBody EsDocEGetDTO exportDTO, HttpServletResponse response) {
        ExcelUtil.exportData(pageNum->{
                exportDTO.setPageNum(pageNum);
                return workLogService.selectEsPage(exportDTO);
            } ,exportDTO.getHeadDTOS(),exportDTO.getModuleName(),response
        );
    }
}