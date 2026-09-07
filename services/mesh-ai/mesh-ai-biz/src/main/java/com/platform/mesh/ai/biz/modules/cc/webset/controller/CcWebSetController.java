package com.platform.mesh.ai.biz.modules.cc.webset.controller;

import com.platform.mesh.ai.biz.modules.cc.webset.domain.dto.CcWebSetDTO;
import com.platform.mesh.ai.biz.modules.cc.webset.domain.dto.CcConsultationGuideDTO;
import com.platform.mesh.ai.biz.modules.cc.webset.domain.dto.CcConsultationLeadDTO;
import com.platform.mesh.ai.biz.modules.cc.webset.domain.po.CcWebSet;
import com.platform.mesh.ai.biz.modules.cc.webset.domain.vo.CcWebSetVO;
import com.platform.mesh.ai.biz.modules.cc.webset.service.ICcWebSetService;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 约定当前controller 只引入当前service
 * @description 聊天WebSet
 * @author 蝉鸣
 */
@Tag(description = "CcWebSetController", name = "聊天WebSet")
@RestController
@RequestMapping
public class CcWebSetController {

    @Autowired
    private ICcWebSetService ccWebSetService;

    /**
     * 功能描述:
     * 〈获取页面配置列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result <PageVO<CcWebSetVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取页面配置分页")
    @PostMapping("/cc/web/set/page")
    public Result<PageVO<CcWebSetVO>> selectPage(@RequestBody PageDTO pageDTO) {
        MPage<CcWebSet> mPage = MPageUtil.pageEntityToMPage(pageDTO, CcWebSet.class);
        MPage<CcWebSet> page = ccWebSetService.page(mPage);
        return Result.success(MPageUtil.convertToVO(page, CcWebSetVO.class));
    }

    /**
     * 功能描述:
     * 〈获取当前页面配置信息〉
     * @param webSetId webSetId
     * @return 正常返回:{@link Result<CcWebSetVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前页面配置信息")
    @GetMapping("/cc/web/set/info/{webSetId}")
    public Result<CcWebSetVO> getCcWebSetInfoById(@PathVariable("webSetId")Long webSetId) {
        CcWebSetVO aiMcpVO = ccWebSetService.getCcWebSetById(webSetId);
        return Result.success(aiMcpVO);
    }

    /**
     * 访客端获取咨询引导配置，不返回脚本、租户等内部字段。
     */
    @AuthIgnore
    @Operation(summary = "获取官网咨询引导配置")
    @GetMapping("/cc/web/set/guide/{webSetId}")
    public Result<CcConsultationGuideDTO> getConsultationGuideById(
            @PathVariable("webSetId") Long webSetId) {
        return Result.success(ccWebSetService.getConsultationGuideById(webSetId));
    }

    /** 官网访客提交咨询手机号，租户由 webSetId 服务端解析。 */
    @AuthIgnore
    @Operation(summary = "提交官网咨询手机号")
    @PostMapping("/cc/web/set/lead")
    public Result<Void> saveConsultationLead(
            @Validated @RequestBody CcConsultationLeadDTO leadDTO) {
        ccWebSetService.saveConsultationLead(leadDTO);
        return Result.success();
    }

    /**
     * 功能描述:
     * 〈新增页面配置〉
     * @param aiMcpDTO aiMcpDTO
     * @return 正常返回:{@link Result<CcWebSetVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增页面配置")
    @Log(moduleName = "页面配置管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/cc/web/set/add")
    public Result<CcWebSetVO> addCcWebSet(@Validated @RequestBody CcWebSetDTO aiMcpDTO) {
        return Result.success(ccWebSetService.addCcWebSet(aiMcpDTO));
    }

    /**
     * 功能描述:
     * 〈修改页面配置〉
     * @param aiMcpDTO aiMcpDTO
     * @return 正常返回:{@link Result<CcWebSetVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改页面配置")
    @Log(moduleName = "页面配置管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/cc/web/set/edit")
    public Result<CcWebSetVO> editCcWebSet(@Validated @RequestBody CcWebSetDTO aiMcpDTO) {
        return Result.success(ccWebSetService.editCcWebSet(aiMcpDTO));
    }

    /**
     * 功能描述:
     * 〈删除页面配置〉
     * @param webSetId webSetId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除页面配置")
    @Log(moduleName = "页面配置管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/cc/web/set/delete/{webSetId}")
    public Result<Boolean> deleteCcWebSet(@PathVariable(value = "webSetId",required = false)Long webSetId) {
        return Result.success(ccWebSetService.deleteCcWebSet(webSetId));
    }

}
