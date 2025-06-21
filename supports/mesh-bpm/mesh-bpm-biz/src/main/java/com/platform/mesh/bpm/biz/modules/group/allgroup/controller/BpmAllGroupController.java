package com.platform.mesh.bpm.biz.modules.group.allgroup.controller;

import com.platform.mesh.bpm.biz.modules.group.allgroup.domain.dto.BpmAllGroupDTO;
import com.platform.mesh.bpm.biz.modules.group.allgroup.domain.po.BpmAllGroup;
import com.platform.mesh.bpm.biz.modules.group.allgroup.domain.vo.BpmAllGroupVO;
import com.platform.mesh.bpm.biz.modules.group.allgroup.service.IBpmAllGroupService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.application.domain.dto.PageDTO;
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
 * @description 模块分组信息
 * @author 蝉鸣
 */
@Tag(description = "BpmAllGroupController", name = "模块分组")
@RestController
public class BpmAllGroupController extends BaseController{


    @Autowired
    private IBpmAllGroupService appAllGroupService;

    /**
	 * 功能描述:
	 * 〈获取模块分组列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<BpmAllGroupVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取模块分组分页")
	@PostMapping("/bpm/all/group/page")
	public Result<PageVO<BpmAllGroupVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<BpmAllGroup> allGroupMPage = MPageUtil.pageEntityToMPage(pageDTO, BpmAllGroup.class);
        MPage<BpmAllGroup> page = appAllGroupService.page(allGroupMPage);
        PageVO<BpmAllGroupVO> voPage = MPageUtil.convertToVO(page, BpmAllGroupVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前模块分组信息〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link Result<BpmAllGroupVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前模块分组信息")
    @GetMapping("/bpm/all/group/info/{allGroupId}")
    public Result<BpmAllGroupVO> getAllGroupInfoById(@PathVariable("allGroupId")Long allGroupId) {
        BpmAllGroupVO appAllGroupVO = appAllGroupService.getAllGroupInfoById(allGroupId);
        return Result.success(appAllGroupVO);
    }

    /**
     * 功能描述:
     * 〈新增模块分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link Result<BpmAllGroupVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增模块分组")
    @Log(moduleName = "模块分组管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/bpm/all/group/add")
    public Result<BpmAllGroupVO> addAllGroup(@Validated @RequestBody BpmAllGroupDTO allGroupDTO) {
        return Result.success(appAllGroupService.addAllGroup(allGroupDTO));
    }

    /**
     * 功能描述:
     * 〈修改模块分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link Result<BpmAllGroupVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改模块分组")
    @Log(moduleName = "模块分组管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/bpm/all/group/edit")
    public Result<BpmAllGroupVO> editAllGroup(@Validated @RequestBody BpmAllGroupDTO allGroupDTO) {
        return Result.success(appAllGroupService.editAllGroup(allGroupDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除模块分组〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除模块分组")
    @Log(moduleName = "模块分组管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/bpm/all/group/delete/{allGroupId}")
    public Result<Boolean> deleteAllGroup(@PathVariable(value = "allGroupId",required = false)Long allGroupId) {
        return Result.success(appAllGroupService.deleteAllGroup(allGroupId));
    }

}