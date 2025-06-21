package com.platform.mesh.bpm.biz.modules.group.allgrouprel.controller;

import com.platform.mesh.bpm.biz.modules.group.allgrouprel.domain.dto.BpmAllGroupRelDTO;
import com.platform.mesh.bpm.biz.modules.group.allgrouprel.domain.po.BpmAllGroupRel;
import com.platform.mesh.bpm.biz.modules.group.allgrouprel.domain.vo.BpmAllGroupRelVO;
import com.platform.mesh.bpm.biz.modules.group.allgrouprel.service.IBpmAllGroupRelService;
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
 * @description 模块分组关联信息
 * @author 蝉鸣
 */
@Tag(description = "BpmAllGroupRelController", name = "模块分组关联")
@RestController
public class BpmAllGroupRelController extends BaseController{

    @Autowired
    private IBpmAllGroupRelService appAllGroupRelService;

    /**
	 * 功能描述:
	 * 〈获取模块分组关联列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<BpmAllGroupRelVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取模块分组关联分页")
	@PostMapping("/bpm/all/group/rel/page")
	public Result<PageVO<BpmAllGroupRelVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<BpmAllGroupRel> allGroupRelMPage = MPageUtil.pageEntityToMPage(pageDTO, BpmAllGroupRel.class);
        MPage<BpmAllGroupRel> page = appAllGroupRelService.page(allGroupRelMPage);
        PageVO<BpmAllGroupRelVO> voPage = MPageUtil.convertToVO(page, BpmAllGroupRelVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前模块分组关联信息〉
     * @param allGroupRelId allGroupRelId
     * @return 正常返回:{@link Result<BpmAllGroupRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前模块分组关联信息")
    @GetMapping("/bpm/all/group/rel/info/{allGroupRelId}")
    public Result<BpmAllGroupRelVO> getAllGroupRelInfoById(@PathVariable("allGroupRelId")Long allGroupRelId) {
        BpmAllGroupRelVO appAllGroupRelVO = appAllGroupRelService.getAllGroupRelInfoById(allGroupRelId);
        return Result.success(appAllGroupRelVO);
    }

    /**
     * 功能描述:
     * 〈新增模块分组关联〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link Result<BpmAllGroupRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增模块分组关联")
    @Log(moduleName = "模块分组关联管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/bpm/all/group/rel/add")
    public Result<BpmAllGroupRelVO> addAllGroupRel(@Validated @RequestBody BpmAllGroupRelDTO allGroupRelDTO) {
        return Result.success(appAllGroupRelService.addAllGroupRel(allGroupRelDTO));
    }

    /**
     * 功能描述:
     * 〈修改模块分组关联〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link Result<BpmAllGroupRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改模块分组关联")
    @Log(moduleName = "模块分组关联管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/bpm/all/group/rel/edit")
    public Result<BpmAllGroupRelVO> editAllGroupRel(@Validated @RequestBody BpmAllGroupRelDTO allGroupRelDTO) {
        return Result.success(appAllGroupRelService.editAllGroupRel(allGroupRelDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除模块分组关联〉
     * @param allGroupRelId allGroupRelId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除模块分组关联")
    @Log(moduleName = "模块分组关联管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/bpm/all/group/rel/delete/{allGroupRelId}")
    public Result<Boolean> deleteAllGroupRel(@PathVariable(value = "allGroupRelId",required = false)Long allGroupRelId) {
        return Result.success(appAllGroupRelService.deleteAllGroupRel(allGroupRelId));
    }

}