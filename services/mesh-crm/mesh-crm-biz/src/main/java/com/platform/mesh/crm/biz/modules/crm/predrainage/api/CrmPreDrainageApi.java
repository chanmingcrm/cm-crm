package com.platform.mesh.crm.biz.modules.crm.predrainage.api;

import cn.hutool.json.JSONUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.crm.biz.modules.crm.predrainage.domain.po.CrmPreDrainage;
import com.platform.mesh.crm.biz.modules.crm.predrainage.service.ICrmPreDrainageService;
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
 * @description 客户关系活动引流信息
 * @author 蝉鸣
 */
@Tag(description = "CrmPreDrainageController", name = "客户关系活动引流")
@RestController
@RequestMapping
public class CrmPreDrainageApi extends BaseController{

    @Autowired
    private ICrmPreDrainageService  crmPreDrainageService;

    /**
     * 功能描述:
     * 〈新增客户关系活动引流〉
     * @param object object
     * @return 正常返回:{@link Result<Void>}
     * @author 蝉鸣
     */
    @AuthIgnore
    @PostMapping("/api/crm/pre/drainage/add/simp")
    public Result<Void> addPreDrainageSimp (@RequestBody String object) {
        //获取module
        CrmPreDrainage appPO = JSONUtil.toBean(JSONUtil.parseObj(object),CrmPreDrainage.class);
        crmPreDrainageService.addDataNoScope(appPO);
        return Result.success();
    }
}