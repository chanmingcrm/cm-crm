package com.platform.mesh.bpm.biz.modules.data.inst.controller;

import com.platform.mesh.bpm.biz.modules.data.inst.domain.vo.BpmDataInstRelVO;
import com.platform.mesh.bpm.biz.modules.data.inst.service.IBpmDataInstRelService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 约定当前controller 只引入当前service
 * @description 业务数据实例流程数据信息
 * @author 蝉鸣
 */
@Tag(description = "BpmDataInstController", name = "业务数据实例流程数据信息")
@RestController
public class BpmDataInstRelController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IBpmDataInstRelService bpmDataInstRelService;

    /**
     * 功能描述:
     * 〈根据表单Id获取流程与业务表单绑定关系〉
     * @return 正常返回:{@link Result <List<BpmDataInstRelVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "根据数据Id获取流程与业务数据绑定关系")
    @PostMapping("/bpm/data/inst/rel/by/data/{dataId}")
    public Result<List<BpmDataInstRelVO>> getDataInstRelByDataId(@PathVariable("dataId")Long dataId) {
        List<BpmDataInstRelVO> dataInstRelVOS = bpmDataInstRelService.getDataInstRelByDataId(dataId);
        return Result.success(dataInstRelVOS);
    }
}
