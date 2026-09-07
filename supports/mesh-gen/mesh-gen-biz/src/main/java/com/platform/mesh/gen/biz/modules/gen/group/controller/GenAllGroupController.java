package com.platform.mesh.gen.biz.modules.gen.group.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.gen.biz.modules.gen.group.domain.dto.GenAllGroupDTO;
import com.platform.mesh.gen.biz.modules.gen.group.domain.po.GenGroup;
import com.platform.mesh.gen.biz.modules.gen.group.domain.vo.GenAllGroupVO;
import com.platform.mesh.gen.biz.modules.gen.group.service.IGenGroupService;
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
 * @description 模块分组信息
 * @author 蝉鸣
 */
@Tag(description = "GenGroupController", name = "模块分组")
@RestController
public class GenAllGroupController extends BaseController{
    @Autowired
    private IGenGroupService genGroupService;

    /**
	 * 功能描述:
	 * 〈获取模块分组列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<GenAllGroupVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取模块分组分页")
	@PostMapping("/gen/all/group/page")
	public Result<PageVO<GenAllGroupVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<GenGroup> mPage = MPageUtil.pageEntityToMPage(pageDTO, GenGroup.class);
        MPage<GenGroup> page = genGroupService.page(mPage);
        PageVO<GenAllGroupVO> voPage = MPageUtil.convertToVO(page, GenAllGroupVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前模块分组信息〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link Result<GenAllGroupVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前模块分组信息")
    @GetMapping("/gen/all/group/info/{allGroupId}")
    public Result<GenAllGroupVO> getAllGroupInfoById(@PathVariable("allGroupId")Long allGroupId) {
        GenGroup genAllGroup = genGroupService.getById(allGroupId);
        return Result.success(BeanUtil.copyProperties(genAllGroup, GenAllGroupVO.class));
    }

    /**
     * 功能描述:
     * 〈新增模块分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link Result<GenAllGroupVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增模块分组")
    @Log(moduleName = "模块分组管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/gen/all/group/add")
    public Result<GenAllGroupVO> addAllGroup(@Validated @RequestBody GenAllGroupDTO allGroupDTO) {
        return Result.success(genGroupService.addAllGroup(allGroupDTO));
    }

    /**
     * 功能描述:
     * 〈修改模块分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link Result<GenAllGroupVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改模块分组")
    @Log(moduleName = "模块分组管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/gen/all/group/edit")
    public Result<GenAllGroupVO> editAllGroup(@Validated @RequestBody GenAllGroupDTO allGroupDTO) {
        return Result.success(genGroupService.editAllGroup(allGroupDTO));
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
    @PostMapping("/gen/all/group/delete/{allGroupId}")
    public Result<Boolean> deleteAllGroup(@PathVariable(value = "allGroupId",required = false)Long allGroupId) {
        return Result.success(genGroupService.deleteAllGroup(allGroupId));
    }

}