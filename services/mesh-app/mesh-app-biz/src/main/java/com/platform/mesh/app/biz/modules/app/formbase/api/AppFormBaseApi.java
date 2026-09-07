package com.platform.mesh.app.biz.modules.app.formbase.api;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.AppFormBO;
import com.platform.mesh.app.biz.modules.app.formbase.domain.po.AppFormBase;
import com.platform.mesh.app.biz.modules.app.formbase.service.IAppFormBaseService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 约定当前controller 只引入当前service
 * @description 单信息
 * @author 蝉鸣
 */
@Tag(description = "AppFormBaseController", name = "表单管理")
@RestController
@RequestMapping
public class AppFormBaseApi extends BaseController{

    @Autowired
    private IAppFormBaseService appFormBaseService;

    /**
     * 功能描述:
     * 〈根据moduleId 表单类型快速获取默认字段信息〉
     * @param moduleId moduleId
     * @param formType formType
     * @return 正常返回:{@link Result<AppFormBO>}
     * @author 蝉鸣
     */
    @AuthIgnore
    @Operation(summary = "根据moduleId 业务字段类型快速获取默认字段信息")
    @PostMapping("/api/app/form/fast/form/type")
    public Result<AppFormBO> fastFormByModuleAndFormType(@RequestParam("moduleId")Long moduleId
            , @RequestParam("formType")Integer formType) {
        AppFormBase baseForm = appFormBaseService.getAppFormBaseByFormType(moduleId, formType);
        return Result.success(BeanUtil.copyProperties(baseForm, AppFormBO.class));
    }
}