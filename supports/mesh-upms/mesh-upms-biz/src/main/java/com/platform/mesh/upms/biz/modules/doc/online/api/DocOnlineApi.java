package com.platform.mesh.upms.biz.modules.doc.online.api;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.upms.api.modules.doc.domain.dto.DocOnlineSaveDTO;
import com.platform.mesh.upms.biz.modules.doc.online.service.IDocOnlineService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 在线文档接口
 * @author 蝉鸣
 */
@Tag(description = "DocOnlineApi", name = "在线文档接口")
@RestController
@RequestMapping
public class DocOnlineApi extends BaseController {

    @Autowired
    private IDocOnlineService docOnlineService;

    /**
     * 功能描述:
     * 【保存在线文档】
     * @param saveDTO saveDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @AuthIgnore
    @Operation(summary = "保存在线文档")
    @PostMapping("/api/doc/online/save")
    public Result<Boolean> saveOnline(@RequestBody DocOnlineSaveDTO saveDTO) {
        docOnlineService.addOnLineByAi(saveDTO);
        return Result.success(Boolean.TRUE);
    }
}
