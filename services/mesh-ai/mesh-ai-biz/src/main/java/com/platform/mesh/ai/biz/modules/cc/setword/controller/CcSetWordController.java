package com.platform.mesh.ai.biz.modules.cc.setword.controller;

import com.platform.mesh.ai.biz.modules.cc.setword.domain.dto.CcSetWordDTO;
import com.platform.mesh.ai.biz.modules.cc.setword.domain.po.CcSetWord;
import com.platform.mesh.ai.biz.modules.cc.setword.domain.vo.CcSetWordVO;
import com.platform.mesh.ai.biz.modules.cc.setword.service.ICcSetWordService;
import com.platform.mesh.core.application.domain.dto.PageDTO;
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
 * @description 提示语关系
 * @author 蝉鸣
 */
@Tag(description = "CcSetWordController", name = "提示语关系")
@RestController
@RequestMapping
public class CcSetWordController {

    @Autowired
    private ICcSetWordService ccSetWordService;

    /**
     * 功能描述:
     * 〈获取提示语关系列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result <PageVO<CcSetWordVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取提示语关系分页")
    @PostMapping("/cc/set/word/page")
    public Result<PageVO<CcSetWordVO>> selectPage(@RequestBody PageDTO pageDTO) {
        MPage<CcSetWord> mPage = MPageUtil.pageEntityToMPage(pageDTO, CcSetWord.class);
        MPage<CcSetWord> page = ccSetWordService.lambdaQuery().page(mPage);
        return Result.success(MPageUtil.convertToVO(page, CcSetWordVO.class));
    }

    /**
     * 功能描述:
     * 〈获取当前提示语关系信息〉
     * @param wordId wordId
     * @return 正常返回:{@link Result<CcSetWordVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前提示语关系信息")
    @GetMapping("/cc/set/word/info/{wordId}")
    public Result<CcSetWordVO> getCcSetWordInfoById(@PathVariable("wordId")Long wordId) {
        CcSetWordVO aiMcpVO = ccSetWordService.getCcSetWordById(wordId);
        return Result.success(aiMcpVO);
    }

    /**
     * 功能描述:
     * 〈新增提示语关系〉
     * @param setWordDTO setWordDTO
     * @return 正常返回:{@link Result<CcSetWordVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增提示语关系")
    @PostMapping("/cc/set/word/add")
    public Result<CcSetWordVO> addCcSetWord(@Validated @RequestBody CcSetWordDTO setWordDTO) {
        return Result.success(ccSetWordService.addCcSetWord(setWordDTO));
    }

    /**
     * 功能描述:
     * 〈修改提示语关系〉
     * @param setWordDTO setWordDTO
     * @return 正常返回:{@link Result<CcSetWordVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改提示语关系")
    @PostMapping("/cc/set/word/edit")
    public Result<CcSetWordVO> editCcSetWord(@Validated @RequestBody CcSetWordDTO setWordDTO) {
        return Result.success(ccSetWordService.editCcSetWord(setWordDTO));
    }

    /**
     * 功能描述:
     * 〈删除提示语关系〉
     * @param wordId wordId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除提示语关系")
    @PostMapping("/cc/set/word/delete/{wordId}")
    public Result<Boolean> deleteCcSetWord(@PathVariable(value = "wordId",required = false)Long wordId) {
        return Result.success(ccSetWordService.deleteCcSetWord(wordId));
    }
}
