package com.platform.mesh.upms.biz.modules.doc.file.api;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.upms.api.modules.doc.domain.vo.DocFileVO;
import com.platform.mesh.upms.biz.modules.doc.file.service.IDocFileService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 约定当前controller 只引入当前service
 * @description 文档文件信息API
 * @author 蝉鸣
 */
@Hidden
@Tag(description = "DocFileApi", name = "文档文件信息API")
@RestController
public class DocFileApi extends BaseController {


    @Autowired
    private IDocFileService docFileService;

    /**
     * 功能描述:
     * 〈获取文件〉
     * @param fileIds fileIds
     * @return 正常返回:{@link Result<List<DocFileVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取文件")
    @PostMapping(value = "/api/sys/doc/file")
    Result<List<DocFileVO>> getDocFiles(@RequestBody List<Long> fileIds){
        return Result.success(docFileService.getFileInfoById(fileIds));
    }

}
