package com.platform.mesh.upms.biz.modules.conf.sysset.api;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.upms.api.modules.conf.domian.bo.ConfSysSetBO;
import com.platform.mesh.upms.biz.modules.conf.sysset.domain.po.ConfSysSet;
import com.platform.mesh.upms.biz.modules.conf.sysset.service.IConfSysSetService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 约定当前controller 只引入当前service
 * @description 配置系统信息
 * @author 蝉鸣
 */
@Tag(description = "ConfSysSetController", name = "配置系统")
@RestController
@RequestMapping
public class ConfSysSetApi extends BaseController{
    @Autowired
    private IConfSysSetService  confSysSetService;

    /**
	 * 功能描述:
	 * 〈获取配置系统列表〉
	 * @param confSource confSource
	 * @return 正常返回:{@link Result<List<ConfSysSetBO>>}
	 * @author 蝉鸣
	 */
	@AuthIgnore
	@Operation(summary = "获取配置系统分页")
	@GetMapping("/api/conf/sys/set/list")
	public Result<List<ConfSysSetBO>> selectList(@RequestParam("confSource") Integer confSource) {
        List<ConfSysSet> list = confSysSetService.lambdaQuery()
				.eq(ConfSysSet::getConfSource,confSource)
				.list();
        return Result.success(BeanUtil.copyToList(list,ConfSysSetBO.class));
	}

}
