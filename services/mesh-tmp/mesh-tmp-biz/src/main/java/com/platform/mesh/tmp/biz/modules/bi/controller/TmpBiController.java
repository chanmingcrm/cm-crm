package com.platform.mesh.tmp.biz.modules.bi.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.tmp.biz.modules.bi.domain.dto.BiDTO;
import com.platform.mesh.tmp.biz.modules.bi.domain.vo.BiSimpVO;
import com.platform.mesh.tmp.biz.modules.bi.service.ITmpBiService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 约定当前controller 只引入当前service
 * @description 客户关系BI统计信息
 * @author 蝉鸣
 */
@Tag(description = "TmpBiController", name = "客户关系BI统计")
@RestController
@RequestMapping
public class TmpBiController extends BaseController{

    @Autowired
    private ITmpBiService tmpBiService;

    /**
	 * 功能描述:
	 * 〈今日事项〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<BiSimpVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "今日事项")
	@PostMapping("/tmp/bi/todo/today/num/panel")
	public Result<BiSimpVO> todoNumTodayPanel(@RequestBody BiDTO biDTO) {
        return Result.success(tmpBiService.todoNumTodayPanel(biDTO));
	}

	/**
	 * 功能描述:
	 * 〈逾期事项〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<BiSimpVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "逾期事项")
	@PostMapping("/tmp/bi/overdue/num/panel")
	public Result<BiSimpVO> overdueNumPanel(@RequestBody BiDTO biDTO) {
		return Result.success(tmpBiService.overdueNumPanel(biDTO));
	}

	/**
	 * 功能描述:
	 * 〈预警事项〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<BiSimpVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "预警事项")
	@PostMapping("/tmp/bi/warn/num/panel")
	public Result<BiSimpVO> warnNumPanel(@RequestBody BiDTO biDTO) {
		return Result.success(tmpBiService.warnNumPanel(biDTO));
	}

	/**
	 * 功能描述:
	 * 〈完成率〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<BiSimpVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "完成率")
	@PostMapping("/tmp/bi/complete/rate/panel")
	public Result<BiSimpVO> completeRatePanel(@RequestBody BiDTO biDTO) {
		return Result.success(tmpBiService.completeRatePanel(biDTO));
	}

	/**
	 * 功能描述:
	 * 〈逾期率〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<BiSimpVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "逾期事项")
	@PostMapping("/tmp/bi/overdue/rate/panel")
	public Result<BiSimpVO> overdueRatePanel(@RequestBody BiDTO biDTO) {
		return Result.success(tmpBiService.overdueRatePanel(biDTO));
	}

	/**
	 * 功能描述:
	 * 〈平均任务处理时长〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<List<BiSimpVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "平均任务处理时长")
	@PostMapping("/tmp/bi/handle/time/chart")
	public Result<List<BiSimpVO>> handleTimeChart(@RequestBody BiDTO biDTO) {
		return Result.success(tmpBiService.handleTimeChart(biDTO));
	}

	/**
	 * 功能描述:
	 * 〈任务完成率〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<List<BiSimpVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "任务完成率")
	@PostMapping("/tmp/bi/complete/rate/chart")
	public Result<List<BiSimpVO>> completeRateChart(@RequestBody BiDTO biDTO) {
		return Result.success(tmpBiService.completeRateChart(biDTO));
	}

	/**
	 * 功能描述:
	 * 〈逾期任务数〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<List<BiSimpVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "逾期任务数")
	@PostMapping("/tmp/bi/overdue/num/chart")
	public Result<List<BiSimpVO>> overdueNumChart(@RequestBody BiDTO biDTO) {
		return Result.success(tmpBiService.overdueNumChart(biDTO));
	}

	/**
	 * 功能描述:
	 * 〈风险任务预警〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<List<BiSimpVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "风险任务预警")
	@PostMapping("/tmp/bi/warn/num/chart")
	public Result<List<BiSimpVO>> warnNumChart(@RequestBody BiDTO biDTO) {
		return Result.success(tmpBiService.warnNumChart(biDTO));
	}

}