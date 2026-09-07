package com.platform.mesh.bpm.biz.modules.inst.nodeaudit.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.dto.BpmInstNodeAuditAddDTO;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.dto.BpmInstNodeAuditDelDTO;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.po.BpmInstNodeAudit;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.vo.BpmInstNodeAuditVO;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.service.IBpmInstNodeAuditService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 约定当前controller 只引入当前service
 * @description 实例流程节点审批信息
 * @author 蝉鸣
 */
@Tag(description = "BpmInstNodeAuditController", name = "实例流程节点审批信息")
@RestController
public class BpmInstNodeAuditController extends BaseController {

    /**
     * 服务对象
     */
    @Autowired
    private IBpmInstNodeAuditService bpmInstNodeAuditService;

    /**
     * 功能描述:
     * 〈查询当前节点下所有的审批人员〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link Result<List<BpmInstNodeAuditVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "查询当前节点下所有的审批人员")
    @GetMapping("/inst/node/audit/get/{instNodeId}")
    public Result<List<BpmInstNodeAuditVO>> getInstNodeAuditList(@PathVariable("instNodeId")Long instNodeId) {
        List<BpmInstNodeAudit> bpmInstNodeAudits = bpmInstNodeAuditService.selectNodeAuditsByNodeId(instNodeId);
        return Result.success(BeanUtil.copyToList(bpmInstNodeAudits, BpmInstNodeAuditVO.class));
    }

    /**
     * 功能描述:
     * 〈添加当前节点审批信息〉
     * @param addDTO addDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "添加当前节点审批信息")
    @PostMapping("/inst/node/audit/add")
    public Result<Boolean> addInstNodeAudit(@RequestBody BpmInstNodeAuditAddDTO addDTO) {
        return Result.success(bpmInstNodeAuditService.addInstNodeAudit(addDTO));
    }

    /**
     * 功能描述:
     * 〈删除当前节点审批信息〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除当前节点审批信息")
    @PostMapping("/inst/node/audit/delete")
    public Result<Boolean> delInstNodeAudit(@RequestBody BpmInstNodeAuditDelDTO delDTO) {
        return Result.success(bpmInstNodeAuditService.delInstNodeAudit(delDTO));
    }
  
}
