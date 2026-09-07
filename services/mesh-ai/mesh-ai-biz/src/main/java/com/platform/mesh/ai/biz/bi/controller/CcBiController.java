package com.platform.mesh.ai.biz.bi.controller;

import com.platform.mesh.ai.biz.bi.domain.dto.BiDTO;
import com.platform.mesh.ai.biz.bi.service.ICcBiService;
import com.platform.mesh.core.application.domain.vo.SimpVO;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 约定当前controller 只引入当前service
 * @description 客服会话统计
 * @author 蝉鸣
 */
@Tag(description = "CcBiController", name = "客服会话统计")
@RestController
@RequestMapping
public class CcBiController {

    @Autowired
    private ICcBiService ccBiService;

    /**
     * 功能描述:
     * 〈总接待数量〉
     * @param biDTO biDTO
     * @return 正常返回:{@link Result<SimpVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "总接待数量")
    @PostMapping("/cc/bi/all/total")
    public Result<SimpVO> biAllTotal(@RequestBody BiDTO biDTO) {
        return Result.success(ccBiService.biAllTotal(biDTO));
    }

    /**
     * 功能描述:
     * 〈总接待数量〉
     * @param biDTO biDTO
     * @return 正常返回:{@link Result<SimpVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "总接待数量-个人")
    @PostMapping("/cc/bi/user/total")
    public Result<SimpVO> biUserTotal(@RequestBody BiDTO biDTO) {
        return Result.success(ccBiService.biUserTotal(biDTO));
    }

    /**
     * 功能描述:
     * 〈满意度〉
     * @param biDTO biDTO
     * @return 正常返回:{@link Result<SimpVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "满意度")
    @PostMapping("/cc/bi/star")
    public Result<SimpVO> biStar(@RequestBody BiDTO biDTO) {
        return Result.success(ccBiService.biStar(biDTO));
    }

    /**
     * 功能描述:
     * 〈接待排行榜〉
     * @param biDTO biDTO
     * @return 正常返回:{@link Result<List<SimpVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "接待数排行榜")
    @GetMapping("/cc/bi/num/rank")
    public Result<List<SimpVO>> biNumRank(@RequestBody BiDTO biDTO) {
        return Result.success(ccBiService.biNumRank(biDTO));
    }

    /**
     * 功能描述:
     * 〈满意度排行榜〉
     * @param biDTO biDTO
     * @return 正常返回:{@link Result<List<SimpVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "满意度排行榜")
    @PostMapping("/cc/bi/star/rank")
    public Result<List<SimpVO>> biStarRank(@RequestBody BiDTO biDTO) {
        return Result.success(ccBiService.biStarRank(biDTO));
    }

}
