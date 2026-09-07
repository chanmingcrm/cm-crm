package com.platform.mesh.app.biz.modules.data.importerror.api;

import com.platform.mesh.app.api.modules.app.domain.bo.ImportErrorBO;
import com.platform.mesh.app.biz.modules.data.importerror.service.IDataImportErrorService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(description = "DataImportErrorApi", name = "导入错误数据")
@RestController
@RequestMapping
public class DataImportErrorApi extends BaseController{

    @Autowired
    private IDataImportErrorService dataImportErrorService;


    /**
     * 功能描述:
     * 〈保存导入错误信息〉
     * @param errorBO errorBO
     * @author 蝉鸣
     */
    @AuthIgnore
    @PostMapping("/api/data/import/error/add")
    public Result<Void> saveImportError(@RequestBody ImportErrorBO errorBO) {
        dataImportErrorService.saveImportError(errorBO);
        return Result.success();
    }

}