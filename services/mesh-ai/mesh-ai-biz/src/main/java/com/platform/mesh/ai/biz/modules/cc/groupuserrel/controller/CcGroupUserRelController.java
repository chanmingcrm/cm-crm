package com.platform.mesh.ai.biz.modules.cc.groupuserrel.controller;

import com.platform.mesh.ai.biz.modules.cc.groupuserrel.domain.dto.CcGroupUserRelDTO;
import com.platform.mesh.ai.biz.modules.cc.groupuserrel.domain.dto.CcGroupUserRelPageDTO;
import com.platform.mesh.ai.biz.modules.cc.groupuserrel.domain.po.CcGroupUserRel;
import com.platform.mesh.ai.biz.modules.cc.groupuserrel.domain.vo.CcGroupUserRelVO;
import com.platform.mesh.ai.biz.modules.cc.groupuserrel.service.ICcGroupUserRelService;
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
 * @description 会话群人员关系
 * @author 蝉鸣
 */
@Tag(description = "CcGroupUserRelController", name = "会话群人员关系")
@RestController
@RequestMapping
public class CcGroupUserRelController {

    @Autowired
    private ICcGroupUserRelService ccGroupUserRelService;

    /**
     * 功能描述:
     * 〈获取会话群人员关系列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<CcGroupUserRelVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取会话群人员关系分页")
    @PostMapping("/cc/group/user/rel/page")
    public Result<PageVO<CcGroupUserRelVO>> selectPage(@RequestBody CcGroupUserRelPageDTO pageDTO) {
        MPage<CcGroupUserRel> mpage = ccGroupUserRelService.selectPage(pageDTO);
        return Result.success(MPageUtil.convertToVO(mpage,CcGroupUserRelVO.class));
    }

    /**
     * 功能描述:
     * 〈新增会话群人员关系〉
     * @param aiMcpDTO aiMcpDTO
     * @return 正常返回:{@link Result<CcGroupUserRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增会话群人员关系")
    @Log(moduleName = "会话群人员关系管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/cc/group/user/rel/add")
    public Result<CcGroupUserRelVO> addCcGroupUserRel(@Validated @RequestBody CcGroupUserRelDTO aiMcpDTO) {
        return Result.success(ccGroupUserRelService.addCcGroupUserRel(aiMcpDTO));
    }

    /**
     * 功能描述:
     * 〈删除会话群人员关系〉
     * @param relId relId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除会话群人员关系")
    @Log(moduleName = "会话群人员关系管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/cc/group/user/rel/delete/{relId}")
    public Result<Boolean> deleteCcGroupUserRel(@PathVariable(value = "relId",required = false)Long relId) {
        return Result.success(ccGroupUserRelService.deleteCcGroupUserRel(relId));
    }
}
