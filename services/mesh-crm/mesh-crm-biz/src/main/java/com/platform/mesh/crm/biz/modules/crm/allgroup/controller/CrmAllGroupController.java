package com.platform.mesh.crm.biz.modules.crm.allgroup.controller;

import com.platform.mesh.crm.biz.modules.crm.allgroup.domain.dto.CrmAllGroupDTO;
import com.platform.mesh.crm.biz.modules.crm.allgroup.domain.po.CrmAllGroup;
import com.platform.mesh.crm.biz.modules.crm.allgroup.domain.vo.CrmAllGroupVO;
import com.platform.mesh.crm.biz.modules.crm.allgroup.service.ICrmAllGroupService;
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
 * @description 客户关系分组信息
 * @author 蝉鸣
 */
@Tag(description = "CrmAllGroupController", name = "客户关系分组")
@RestController
@RequestMapping
public class CrmAllGroupController extends BaseController{
    @Autowired
    private ICrmAllGroupService  crmAllGroupService;

    /**
	 * 功能描述:
	 * 〈获取客户关系分组列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<CrmAllGroupVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取客户关系分组分页")
	@PostMapping("/crm/all/group/page")
	public Result<PageVO<CrmAllGroupVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<CrmAllGroup> allGroupMPage = MPageUtil.pageEntityToMPage(pageDTO, CrmAllGroup.class);
        MPage<CrmAllGroup> page = crmAllGroupService.page(allGroupMPage);
        PageVO<CrmAllGroupVO> voPage = MPageUtil.convertToVO(page, CrmAllGroupVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前客户关系分组信息〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link Result<CrmAllGroupVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客户关系分组信息")
    @GetMapping("/crm/all/group/info/{allGroupId}")
    public Result<CrmAllGroupVO> getAllGroupInfoById(@PathVariable("allGroupId")Long allGroupId) {
        CrmAllGroupVO crmAllGroupVO = crmAllGroupService.getAllGroupInfoById(allGroupId);
        return Result.success(crmAllGroupVO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link Result<CrmAllGroupVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客户关系分组")
    @Log(moduleName = "客户关系分组管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/crm/all/group/add")
    public Result<CrmAllGroupVO> addAllGroup(@Validated @RequestBody CrmAllGroupDTO allGroupDTO) {
        return Result.success(crmAllGroupService.addAllGroup(allGroupDTO));
    }

    /**
     * 功能描述:
     * 〈修改客户关系分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link Result<CrmAllGroupVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客户关系分组")
    @Log(moduleName = "客户关系分组管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/crm/all/group/edit")
    public Result<CrmAllGroupVO> editAllGroup(@Validated @RequestBody CrmAllGroupDTO allGroupDTO) {
        return Result.success(crmAllGroupService.editAllGroup(allGroupDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除客户关系分组〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客户关系分组")
    @Log(moduleName = "客户关系分组管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/crm/all/group/delete/{allGroupId}")
    public Result<Boolean> deleteAllGroup(@PathVariable(value = "allGroupId",required = false)Long allGroupId) {
        return Result.success(crmAllGroupService.deleteAllGroup(allGroupId));
    }

}