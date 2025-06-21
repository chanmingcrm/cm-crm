package com.platform.mesh.crm.biz.modules.bi.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.crm.biz.modules.bi.domain.dto.BiDTO;
import com.platform.mesh.crm.biz.modules.bi.domain.dto.ModuleBiDTO;
import com.platform.mesh.crm.biz.modules.bi.domain.vo.BiIdSimpVO;
import com.platform.mesh.crm.biz.modules.bi.domain.vo.BiSimpVO;
import com.platform.mesh.crm.biz.modules.bi.service.ICrmBiService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 约定当前controller 只引入当前service
 * @description 客户关系BI统计信息
 * @author 蝉鸣
 */
@Tag(description = "CrmBiController", name = "客户关系BI统计")
@RestController
@RequestMapping
public class CrmBiController extends BaseController{

    @Autowired
    private ICrmBiService crmBiService;

    /**
	 * 功能描述:
	 * 〈获取新增客户统计〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<BiSimpVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取新增客户统计")
	@PostMapping("/crm/bi/customer/new")
	public Result<BiSimpVO> biCustomerNew(@RequestBody BiDTO biDTO) {
        return Result.success(crmBiService.biCustomerNew(biDTO));
	}

    /**
	 * 功能描述:
	 * 〈获取新增联系人统计〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<BiSimpVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取新增联系人统计")
	@PostMapping("/crm/bi/contacts/new")
	public Result<BiSimpVO> biContactsNew(@RequestBody BiDTO biDTO) {
        return Result.success(crmBiService.biContactsNew(biDTO));
	}

    /**
	 * 功能描述:
	 * 〈获取新增商机统计〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<BiSimpVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取新增商机统计")
	@PostMapping("/crm/bi/business/new")
	public Result<BiSimpVO> biBusinessNew(@RequestBody BiDTO biDTO) {
        return Result.success(crmBiService.biBusinessNew(biDTO));
	}

    /**
	 * 功能描述:
	 * 〈获取新增合同统计〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<BiSimpVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取新增合同统计")
	@PostMapping("/crm/bi/contract/new")
	public Result<BiSimpVO> biContractNew(@RequestBody BiDTO biDTO) {
        return Result.success(crmBiService.biContractNew(biDTO));
	}

    /**
	 * 功能描述:
	 * 〈获取新增跟进统计〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<BiSimpVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取新增跟进统计")
	@PostMapping("/crm/bi/follow/new")
	public Result<BiSimpVO> biFollowNew(@RequestBody BiDTO biDTO) {
        return Result.success(crmBiService.biFollowNew(biDTO));
	}

    /**
	 * 功能描述:
	 * 〈获取合同金额统计〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<BiSimpVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取合同金额统计")
	@PostMapping("/crm/bi/contract/money")
	public Result<BiSimpVO> biContractMoney(@RequestBody BiDTO biDTO) {
        return Result.success(crmBiService.biContractMoney(biDTO));
	}

    /**
	 * 功能描述:
	 * 〈获取商机金额统计〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<BiSimpVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取商机金额统计")
	@PostMapping("/crm/bi/business/money")
	public Result<BiSimpVO> biBusinessMoney(@RequestBody BiDTO biDTO) {
        return Result.success(crmBiService.biBusinessMoney(biDTO));
	}

    /**
	 * 功能描述:
	 * 〈获取回款金额统计〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<BiSimpVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取回款金额统计")
	@PostMapping("/crm/bi/payment/money")
	public Result<BiSimpVO> biPaymentMoney(@RequestBody BiDTO biDTO) {
        return Result.success(crmBiService.biPaymentMoney(biDTO));
	}

    /**
	 * 功能描述:
	 * 〈合同目标及完成情况〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<List<BiSimpVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "合同目标及完成情况")
	@PostMapping("/crm/bi/contract/money/chart")
	public Result<List<BiSimpVO>> biContractEstAndAct(@RequestBody BiDTO biDTO) {
        return Result.success(crmBiService.biContractEstAndAct(biDTO));
	}

    /**
	 * 功能描述:
	 * 〈业绩指标完成率〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<BiSimpVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "业绩指标完成率")
	@PostMapping("/crm/bi/achieve/money/panel")
	public Result<BiSimpVO> biAchieveEstAndAct(@RequestBody ModuleBiDTO biDTO) {
        return Result.success(crmBiService.biAchieveEstAndAct(biDTO));
	}

    /**
	 * 功能描述:
	 * 〈排行榜〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<BiSimpVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "排行榜")
	@PostMapping("/crm/bi/rank")
	public Result<List<BiIdSimpVO>> biRankEstAndAct(@RequestBody ModuleBiDTO biDTO) {
        return Result.success(crmBiService.biRankEstAndAct(biDTO));
	}

    /**
	 * 功能描述:
	 * 〈遗忘提醒〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<Map<String,Integer>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "遗忘提醒")
	@PostMapping("/crm/bi/notice")
	public Result<Map<String,Integer>> biNotice(@RequestBody BiDTO biDTO) {
        return Result.success(crmBiService.biNotice(biDTO));
	}

    /**
	 * 功能描述:
	 * 〈数据汇总〉
	 * @param biDTO biDTO
	 * @return 正常返回:{@link Result<Map<String,Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "数据汇总")
	@PostMapping("/crm/bi/data/count")
	public Result<Map<String,Object>> biDataCount(@RequestBody BiDTO biDTO) {
        return Result.success(crmBiService.biDataCount(biDTO));
	}

}