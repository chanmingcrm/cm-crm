package com.platform.mesh.crm.biz.modules.crm.allgoal.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.crm.biz.modules.crm.allgoal.domain.dto.CrmAllGoalDTO;
import com.platform.mesh.crm.biz.modules.crm.allgoal.domain.po.CrmAllGoal;
import com.platform.mesh.crm.biz.modules.crm.allgoal.domain.vo.CrmAllGoalVO;
import com.platform.mesh.crm.biz.modules.crm.allgoal.service.ICrmAllGoalService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Month;


/**
 * 约定当前controller 只引入当前service
 * @description 客户关系目标信息
 * @author 蝉鸣
 */
@Tag(description = "CrmAllGoalController", name = "客户关系目标")
@RestController
@RequestMapping
public class CrmAllGoalController extends BaseController{


    @Autowired
    private ICrmAllGoalService crmAllGoalService;


    /**
	 * 功能描述:
	 * 〈获取客户关系目标列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<CrmAllGoalVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系目标分页")
	@PostMapping("/crm/all/goal/page")
	public Result<PageVO<CrmAllGoalVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<CrmAllGoal> allGoalMPage = MPageUtil.pageEntityToMPage(pageDTO, CrmAllGoal.class);
        MPage<CrmAllGoal> page = crmAllGoalService.page(allGoalMPage);
        PageVO<CrmAllGoalVO> voPage = MPageUtil.convertToVO(page, CrmAllGoalVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈新增客户关系目标〉
     * @param allGoalDTO allGoalDTO
     * @return 正常返回:{@link Result<CrmAllGoalVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系目标")
    @Log(moduleName = "客户关系目标管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/all/goal/add")
    public Result<CrmAllGoalVO> addAllGoal(@Validated @RequestBody CrmAllGoalDTO allGoalDTO) {
        return Result.success(crmAllGoalService.addGoal(allGoalDTO));
    }

    /**
     * 功能描述:
     * 〈修改客户关系目标〉
     * @param allGoalDTO allGoalDTO
     * @return 正常返回:{@link Result<CrmAllGoalVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系目标")
    @Log(moduleName = "客户关系目标管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/all/goal/edit")
    public Result<CrmAllGoalVO> editAllGoal(@Validated @RequestBody CrmAllGoalDTO allGoalDTO) {
        return Result.success(crmAllGoalService.editGoal(allGoalDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系目标〉
     * @param allGoalId allGoalId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系目标")
    @Log(moduleName = "客户关系目标管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/all/goal/delete/{allGoalId}")
    public Result<Boolean> deleteAllGoal(@PathVariable(value = "allGoalId",required = false)Long allGoalId) {
        return Result.success(crmAllGoalService.deleteGoal(allGoalId));
    }

   /**
     * 功能描述:
     * 〈测试〉
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "测试")
    @PostMapping("/crm/all/goal/day")
    public Result<Boolean> test() {
        CrmAllGoal byId = crmAllGoalService.getById(1);
        Month month = Month.of(5);
        crmAllGoalService.getDayGoal(byId,month);
        return Result.success(Boolean.TRUE);
    }

}