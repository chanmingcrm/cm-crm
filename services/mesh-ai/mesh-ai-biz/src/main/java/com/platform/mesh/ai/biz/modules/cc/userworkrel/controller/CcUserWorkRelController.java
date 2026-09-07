package com.platform.mesh.ai.biz.modules.cc.userworkrel.controller;

import com.platform.mesh.ai.biz.modules.cc.userworkrel.domain.dto.CcUserWorkRelDTO;
import com.platform.mesh.ai.biz.modules.cc.userworkrel.domain.dto.CcUserWorkRelPageDTO;
import com.platform.mesh.ai.biz.modules.cc.userworkrel.domain.po.CcUserWorkRel;
import com.platform.mesh.ai.biz.modules.cc.userworkrel.domain.vo.CcUserWorkRelVO;
import com.platform.mesh.ai.biz.modules.cc.userworkrel.service.ICcUserWorkRelService;
import com.platform.mesh.core.application.domain.vo.PageVO;
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
 * @description 人员排班
 * @author 蝉鸣
 */
@Tag(description = "CcUserWorkRelController", name = "人员排班")
@RestController
@RequestMapping
public class CcUserWorkRelController {

    @Autowired
    private ICcUserWorkRelService ccUserWorkRelService;

    /**
     * 功能描述:
     * 〈获取人员排班列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<CcUserWorkRelVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取人员排班分页")
    @PostMapping("/cc/user/work/rel/page")
    public Result<PageVO<CcUserWorkRelVO>> selectPage(@RequestBody CcUserWorkRelPageDTO pageDTO) {
        MPage<CcUserWorkRel> page = ccUserWorkRelService.selectPage(pageDTO);
        return Result.success(MPageUtil.convertToVO(page, CcUserWorkRelVO.class));
    }

    /**
     * 功能描述:
     * 〈新增人员排班〉
     * @param aiMcpDTO aiMcpDTO
     * @return 正常返回:{@link Result<CcUserWorkRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增人员排班")
    @PostMapping("/cc/user/work/rel/add")
    public Result<CcUserWorkRelVO> addCcUserWorkRel(@Validated @RequestBody CcUserWorkRelDTO aiMcpDTO) {
        return Result.success(ccUserWorkRelService.addCcUserWorkRel(aiMcpDTO));
    }

    /**
     * 功能描述:
     * 〈删除人员排班〉
     * @param relId relId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除人员排班")
    @PostMapping("/cc/user/work/rel/delete/{relId}")
    public Result<Boolean> deleteCcUserWorkRel(@PathVariable(value = "relId",required = false)Long relId) {
        return Result.success(ccUserWorkRelService.deleteCcUserWorkRel(relId));
    }
}
