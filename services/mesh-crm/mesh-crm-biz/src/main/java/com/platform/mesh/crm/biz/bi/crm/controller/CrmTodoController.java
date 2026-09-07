package com.platform.mesh.crm.biz.bi.crm.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.crm.biz.bi.crm.domain.dto.TodoPDTO;
import com.platform.mesh.crm.biz.bi.crm.service.ICrmTodoService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 约定当前controller 只引入当前service
 * @description 客户关系待办
 * @author 蝉鸣
 */
@Tag(description = "CrmTodoController", name = "客户关系待办")
@RestController
@RequestMapping
public class CrmTodoController extends BaseController{

    @Autowired
    private ICrmTodoService crmTodoService;

    /**
	 * 功能描述:
	 * 〈获取待办数量角标〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<Object>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取待办数量角标")
	@PostMapping("/crm/todo/num")
	public Result<Object> todoNum(@RequestBody TodoPDTO pageDTO) {
        return Result.success(crmTodoService.todoNum(pageDTO));
	}

    /**
	 * 功能描述:
	 * 〈今日需联系客户〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "今日需联系客户")
	@PostMapping("/crm/todo/rel/customer/today")
	public Result<PageVO<Object>> todoRelCustomerToday(@RequestBody TodoPDTO pageDTO) {
        return Result.success(crmTodoService.todoRelCustomerToday(pageDTO));
	}

    /**
	 * 功能描述:
	 * 〈今日需联系客户〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "今日需联系商机")
	@PostMapping("/crm/todo/rel/business/today")
	public Result<PageVO<Object>> todoRelBusinessToday(@RequestBody TodoPDTO pageDTO) {
        return Result.success(crmTodoService.todoRelBusinessToday(pageDTO));
	}

    /**
	 * 功能描述:
	 * 〈待审核客户〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "待审核客户")
	@PostMapping("/crm/todo/audit/customer")
	public Result<PageVO<Object>> todoAuditCustomer(@RequestBody TodoPDTO pageDTO) {
        return Result.success(crmTodoService.todoAuditCustomer(pageDTO));
	}

    /**
	 * 功能描述:
	 * 〈待审核商机〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "待审核商机")
	@PostMapping("/crm/todo/audit/business")
	public Result<PageVO<Object>> todoAuditBusiness(@RequestBody TodoPDTO pageDTO) {
        return Result.success(crmTodoService.todoAuditBusiness(pageDTO));
	}

    /**
	 * 功能描述:
	 * 〈待审核合同〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "待审核合同")
	@PostMapping("/crm/todo/audit/contract")
	public Result<PageVO<Object>> todoAuditContract(@RequestBody TodoPDTO pageDTO) {
        return Result.success(crmTodoService.todoAuditContract(pageDTO));
	}

    /**
	 * 功能描述:
	 * 〈待审核报价单〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<Object>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "待审核报价单")
	@PostMapping("/crm/todo/audit/proposal")
	public Result<PageVO<Object>> todoAuditProposal(@RequestBody TodoPDTO pageDTO) {
        return Result.success(crmTodoService.todoAuditProposal(pageDTO));
	}



}
