package com.platform.mesh.app.biz.modules.third.thirdformcolumnmapping.api;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.ThirdFormColumnMappingBO;
import com.platform.mesh.app.biz.modules.third.thirdformcolumnmapping.domain.po.ThirdFormColumnMapping;
import com.platform.mesh.app.biz.modules.third.thirdformcolumnmapping.service.IThirdFormColumnMappingService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 约定当前controller 只引入当前service
 * @description 第三方字段映射设置信息
 * @author 蝉鸣
 */
@Tag(description = "ThirdFormColumnMappingApi", name = "第三方字段映射设置")
@RestController
@RequestMapping
public class ThirdFormColumnMappingApi extends BaseController{

    
    @Autowired
    private IThirdFormColumnMappingService thirdFormColumnMappingService;


    /**
     * 功能描述:
     * 〈获取当前第三方字段映射信息〉
     * @param sourceFlag sourceFlag
     * @return 正常返回:{@link Result<List<ThirdFormColumnMappingBO>>}
     * @author 蝉鸣
     */
    @AuthIgnore
    @GetMapping("/api/third/form/column/mapping/list")
    public Result<List<ThirdFormColumnMappingBO>> getThirdFormColumnMapping(@RequestParam("sourceFlag")Integer sourceFlag) {
        List<ThirdFormColumnMapping> thirdFormColumnMappings = thirdFormColumnMappingService.getThirdFormColumnMapping(sourceFlag);
        return Result.success(BeanUtil.copyToList(thirdFormColumnMappings, ThirdFormColumnMappingBO.class));
    }

}
