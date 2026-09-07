package com.platform.mesh.ai.biz.modules.cc.group.controller;

import com.platform.mesh.ai.biz.modules.cc.group.domain.dto.CcGroupDTO;
import com.platform.mesh.ai.biz.modules.cc.group.domain.dto.CcGroupInitDTO;
import com.platform.mesh.ai.biz.modules.cc.group.domain.dto.CcGroupPageDTO;
import com.platform.mesh.ai.biz.modules.cc.group.domain.po.CcGroup;
import com.platform.mesh.ai.biz.modules.cc.group.domain.vo.CcGroupInitVO;
import com.platform.mesh.ai.biz.modules.cc.group.domain.vo.CcGroupVO;
import com.platform.mesh.ai.biz.modules.cc.group.service.ICcGroupService;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
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
 * @description 会话群
 * @author 蝉鸣
 */
@Tag(description = "CcGroupController", name = "会话群")
@RestController
@RequestMapping
public class CcGroupController {

    @Autowired
    private ICcGroupService ccGroupService;

    /**
     * 功能描述:
     * 〈获取会话群信息列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result < PageVO < CcGroupVO >>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取会话群信息分页")
    @PostMapping("/cc/group/page")
    public Result<PageVO<CcGroupVO>> selectPage(@RequestBody CcGroupPageDTO pageDTO) {
        PageVO<CcGroupVO> pageVO = ccGroupService.selectPage(pageDTO);
        return Result.success(pageVO);
    }

    /**
     * 功能描述:
     * 〈获取当前会话群信息信息〉
     * @param groupId groupId
     * @return 正常返回:{@link Result<CcGroupVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前会话群信息信息")
    @GetMapping("/cc/group/info/{groupId}")
    public Result<CcGroupVO> getCcGroupInfoById(@PathVariable("groupId")Long groupId) {
        CcGroupVO aiMcpVO = ccGroupService.getCcGroupById(groupId);
        return Result.success(aiMcpVO);
    }

    /**
     * 功能描述:
     * 〈新增会话群信息〉
     * @param aiMcpDTO aiMcpDTO
     * @return 正常返回:{@link Result<CcGroupVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增会话群信息")
    @Log(moduleName = "会话群信息管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/cc/group/add")
    public Result<CcGroupVO> addCcGroup(@Validated @RequestBody CcGroupDTO aiMcpDTO) {
        return Result.success(ccGroupService.addCcGroup(aiMcpDTO));
    }

    /**
     * 功能描述:
     * 〈修改会话群信息〉
     * @param aiMcpDTO aiMcpDTO
     * @return 正常返回:{@link Result<CcGroupVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改会话群信息")
    @Log(moduleName = "会话群信息管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/cc/group/edit")
    public Result<CcGroupVO> editCcGroup(@Validated @RequestBody CcGroupDTO aiMcpDTO) {
        return Result.success(ccGroupService.editCcGroup(aiMcpDTO));
    }

    /**
     * 功能描述:
     * 〈删除会话群信息〉
     * @param groupId groupId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除会话群信息")
    @Log(moduleName = "会话群信息管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/cc/group/delete/{groupId}")
    public Result<Boolean> deleteCcGroup(@PathVariable(value = "groupId",required = false)Long groupId) {
        return Result.success(ccGroupService.deleteCcGroup(groupId));
    }

    /**
     * 功能描述:
     * 〈初始化会话群〉
     * @param initDTO initDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "初始化会话群")
    @PostMapping("/cc/group/init")
    public Result<CcGroupInitVO> initCcGroup(@RequestBody CcGroupInitDTO initDTO) {
        return Result.success(ccGroupService.initCcGroup(initDTO));
    }
}
