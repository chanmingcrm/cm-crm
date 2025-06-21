package com.platform.mesh.upms.biz.modules.org.level.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.biz.modules.org.level.domain.dto.OrgLevelDTO;
import com.platform.mesh.upms.biz.modules.org.level.domain.dto.OrgLevelPageDTO;
import com.platform.mesh.upms.biz.modules.org.level.domain.po.OrgLevel;
import com.platform.mesh.upms.biz.modules.org.level.domain.vo.OrgLevelVO;
import com.platform.mesh.upms.biz.modules.org.level.service.IOrgLevelService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 约定当前controller 只引入当前service
 * @description 组织信息
 * @author 蝉鸣
 */
@Tag(description = "OrgLevelController", name = "组织信息")
@RestController
public class OrgLevelController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IOrgLevelService orgLevelService;

    /**
     * 功能描述:
     * 〈获取组织分页列表〉
     * @param orgLevelPageDTO orgLevelDTO
     * @return 正常返回:{@link Result<OrgLevelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取组织列表")
    @PostMapping("/level/page")
    public Result<PageVO<OrgLevelVO>> getLevelPage(@RequestBody OrgLevelPageDTO orgLevelPageDTO) {
        MPage<OrgLevel> page = orgLevelService.selectPage(orgLevelPageDTO);
        PageVO<OrgLevelVO> voPage = MPageUtil.convertToVO(page, OrgLevelVO.class);
        return Result.success(voPage);
    }

    /**
     * 功能描述:
     * 〈获取当前组织信息〉
     * @param levelId levelId
     * @return 正常返回:{@link Result<OrgLevelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前组织信息")
    @GetMapping("/level/info/{levelId}")
    public Result<OrgLevelVO> getLevelInfoById(@PathVariable("levelId")Long levelId) {
        OrgLevelVO orgLevelVO = orgLevelService.getLevelInfoById(levelId);
        return Result.success(orgLevelVO);
    }

    /**
     * 功能描述:
     * 〈获取组织树结构〉
     * @param levelId levelId
     * @return 正常返回:{@link Result<List<OrgLevelVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取组织树结构")
    @GetMapping("/level/tree")
    public Result<List<OrgLevelVO>> getLevelTree(@RequestParam(value = "levelId",required = false)Long levelId) {
        List<OrgLevelVO> menuTree = orgLevelService.getLevelTree(levelId);
        return Result.success(menuTree);
    }


    /**
     * 功能描述:
     * 〈新增层级〉
     * @param levelDTO levelDTO
     * @return 正常返回:{@link Result<OrgLevelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增层级")
    @Log(moduleName = "层级管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/level/add")
//	@PreAuthorize("@rolePermission.hasPermi('org:level:add')")
    public Result<OrgLevelVO> addLevel(@Validated @RequestBody OrgLevelDTO levelDTO) {
        return Result.success(orgLevelService.addLevel(levelDTO));
    }

    /**
     * 功能描述:
     * 〈修改层级〉
     * @param levelDTO levelDTO
     * @return 正常返回:{@link Result<OrgLevelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改层级")
    @Log(moduleName = "层级管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/level/edit")
//	@PreAuthorize("@rolePermission.hasPermi('org:level:edit')")
    public Result<OrgLevelVO> editLevel(@Validated @RequestBody OrgLevelDTO levelDTO) {
        return Result.success(orgLevelService.editLevel(levelDTO));
    }

    /**
     * 功能描述:
     * 〈删除层级〉
     * @param levelIds levelIds
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除层级")
    @Log(moduleName = "层级管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/level/delete")
//	@PreAuthorize("@rolePermission.hasPermi('org:level:delete')")
    public Result<Boolean> deleteLevel(@RequestBody List<Long> levelIds) {
        return Result.success(orgLevelService.deleteLevel(levelIds));
    }

  
}
