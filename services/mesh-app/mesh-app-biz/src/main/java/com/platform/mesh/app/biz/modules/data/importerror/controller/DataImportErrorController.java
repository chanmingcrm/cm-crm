package com.platform.mesh.app.biz.modules.data.importerror.controller;

import com.platform.mesh.app.biz.modules.data.importerror.domain.dto.ErrorEDTO;
import com.platform.mesh.app.biz.modules.data.importerror.domain.dto.ErrorPDTO;
import com.platform.mesh.app.biz.modules.data.importerror.domain.vo.ErrorCVO;
import com.platform.mesh.app.biz.modules.data.importerror.domain.vo.ErrorSVO;
import com.platform.mesh.app.biz.modules.data.importerror.service.IDataImportErrorService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.utils.excel.ExcelUtil;
import com.platform.mesh.utils.excel.dto.HeadDTO;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 约定当前controller 只引入当前service
 * @description 导入错误数据信息
 * @author 蝉鸣
 */
@Tag(description = "DataImportErrorController", name = "导入错误数据")
@RestController
@RequestMapping
public class DataImportErrorController extends BaseController{

    @Autowired
    private IDataImportErrorService dataImportErrorService;


    /**
     * 功能描述:
     * 〈查询简易导入错误信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<ErrorSVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "查询简易导入错误信息")
    @PostMapping("/data/import/error/batch/page")
    public Result<PageVO<ErrorSVO>> selectImportErrorBatchPage(@RequestBody ErrorPDTO pageDTO) {
        return Result.success(dataImportErrorService.selectImportErrorBatchPage(pageDTO));
    }

    /**
     * 功能描述:
     * 〈查询详细导入错误信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<ErrorCVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "查询详细导入错误信息")
    @PostMapping("/data/import/error/info/page")
    public Result<PageVO<ErrorCVO>> selectImportErrorInfoPage(@RequestBody ErrorPDTO pageDTO) {
        return Result.success(dataImportErrorService.selectImportErrorInfoPage(pageDTO));
    }

    /**
     * 功能描述:
     * 〈导出导入错误信息〉
     * @param errorEDTO errorEDTO
     * @author 蝉鸣
     */
    @Operation(summary = "导出导入错误信息")
    @PostMapping("/data/import/error/export")
    public void exportImportError(@RequestBody ErrorEDTO errorEDTO, HttpServletResponse response) {
        HeadDTO headDTO = new HeadDTO();
        headDTO.setHeadMac(NumberConst.NUM_0.toString());
        headDTO.setHeadName("错误信息");
        //添加错误信息列
        errorEDTO.getHeadDTOS().addFirst(headDTO);
        ExcelUtil.exportData(pageNum->{
                    errorEDTO.setPageNum(pageNum);
                    return dataImportErrorService.selectImportErrorExportPage(errorEDTO);
                } ,errorEDTO.getHeadDTOS(),errorEDTO.getModuleName(),response
        );
    }

}