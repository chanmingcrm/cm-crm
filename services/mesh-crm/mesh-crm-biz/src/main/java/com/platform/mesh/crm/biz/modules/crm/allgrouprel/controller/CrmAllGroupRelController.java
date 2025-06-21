package com.platform.mesh.crm.biz.modules.crm.allgrouprel.controller;

import com.platform.mesh.crm.biz.modules.crm.allgrouprel.domain.dto.CrmAllGroupRelDTO;
import com.platform.mesh.crm.biz.modules.crm.allgrouprel.domain.po.CrmAllGroupRel;
import com.platform.mesh.crm.biz.modules.crm.allgrouprel.domain.vo.CrmAllGroupRelVO;
import com.platform.mesh.crm.biz.modules.crm.allgrouprel.service.ICrmAllGroupRelService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 客户关系分组关联信息
 * @author 蝉鸣
 */
@Tag(description = "CrmAllGroupRelController", name = "客户关系分组关联")
@RestController
@RequestMapping
public class CrmAllGroupRelController extends BaseController{
    @Autowired
    private ICrmAllGroupRelService  crmAllGroupRelService;

    /**
	 * 功能描述:
	 * 〈获取客户关系分组关联列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<CrmAllGroupRelVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系分组关联分页")
	@PostMapping("/crm/all/group/rel/page")
	public Result<PageVO<CrmAllGroupRelVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<CrmAllGroupRel> allGroupRelMPage = MPageUtil.pageEntityToMPage(pageDTO, CrmAllGroupRel.class);
        MPage<CrmAllGroupRel> page = crmAllGroupRelService.page(allGroupRelMPage);
        PageVO<CrmAllGroupRelVO> voPage = MPageUtil.convertToVO(page, CrmAllGroupRelVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前客户关系分组关联信息〉
     * @param allGroupRelId allGroupRelId
     * @return 正常返回:{@link Result<CrmAllGroupRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系分组关联信息")
    @GetMapping("/crm/all/group/rel/info/{allGroupRelId}")
    public Result<CrmAllGroupRelVO> getAllGroupRelInfoById(@PathVariable("allGroupRelId")Long allGroupRelId) {
        CrmAllGroupRelVO crmAllGroupRelVO = crmAllGroupRelService.getAllGroupRelInfoById(allGroupRelId);
        return Result.success(crmAllGroupRelVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系分组关联〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link Result<CrmAllGroupRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系分组关联")
    @Log(moduleName = "客户关系分组关联管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/all/group/rel/add")
    public Result<CrmAllGroupRelVO> addAllGroupRel(@Validated @RequestBody CrmAllGroupRelDTO allGroupRelDTO) {
        return Result.success(crmAllGroupRelService.addAllGroupRel(allGroupRelDTO));
    }

    /**
     * 功能描述:
     * 〈修改客户关系分组关联〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link Result<CrmAllGroupRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系分组关联")
    @Log(moduleName = "客户关系分组关联管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/all/group/rel/edit")
    public Result<CrmAllGroupRelVO> editAllGroupRel(@Validated @RequestBody CrmAllGroupRelDTO allGroupRelDTO) {
        return Result.success(crmAllGroupRelService.editAllGroupRel(allGroupRelDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系分组关联〉
     * @param allGroupRelId allGroupRelId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系分组关联")
    @Log(moduleName = "客户关系分组关联管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/all/group/rel/delete/{allGroupRelId}")
    public Result<Boolean> deleteAllGroupRel(@PathVariable(value = "allGroupRelId",required = false)Long allGroupRelId) {
        return Result.success(crmAllGroupRelService.deleteAllGroupRel(allGroupRelId));
    }

}