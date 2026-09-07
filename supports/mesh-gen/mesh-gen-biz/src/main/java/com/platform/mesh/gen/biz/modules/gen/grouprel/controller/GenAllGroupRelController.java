package com.platform.mesh.gen.biz.modules.gen.grouprel.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.gen.biz.modules.gen.grouprel.domain.dto.GenGroupRelDTO;
import com.platform.mesh.gen.biz.modules.gen.grouprel.domain.dto.GenGroupRelPageDTO;
import com.platform.mesh.gen.biz.modules.gen.grouprel.domain.po.GenGroupRel;
import com.platform.mesh.gen.biz.modules.gen.grouprel.domain.vo.GenGroupRelVO;
import com.platform.mesh.gen.biz.modules.gen.grouprel.service.IGenGroupRelService;
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
 * @description 模板分组关系
 * @author 蝉鸣
 */
@Tag(description = "GenAllGroupRelController", name = "模板分组关系")
@RestController
public class GenAllGroupRelController {

    @Autowired
    private IGenGroupRelService genGroupRelService;

    /**
     * 功能描述:
     * 〈获取模块分组关联列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result < MPage <GenGroupRelVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取模块分组关联分页")
    @PostMapping("/gen/all/group/rel/page")
    public Result<PageVO<GenGroupRelVO>> selectPage(@RequestBody GenGroupRelPageDTO pageDTO) {
        MPage<GenGroupRelVO> page = genGroupRelService.selectPage(pageDTO);
        PageVO<GenGroupRelVO> voPage = MPageUtil.convertToVO(page, GenGroupRelVO.class);
        return Result.success(voPage);
    }

    /**
     * 功能描述:
     * 〈获取当前模块分组关联信息〉
     * @param genGroupRelId genGroupRelId
     * @return 正常返回:{@link Result<GenGroupRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前模块分组关联信息")
    @GetMapping("/gen/all/group/rel/info/{genGroupRelId}")
    public Result<GenGroupRelVO> getAllGroupRelInfoById(@PathVariable("genGroupRelId")Long genGroupRelId) {
        GenGroupRel allGroupRel = genGroupRelService.getById(genGroupRelId);
        return Result.success(BeanUtil.copyProperties(allGroupRel, GenGroupRelVO.class));
    }

    /**
     * 功能描述:
     * 〈新增模块分组关联〉
     * @param genGroupRelDTO genGroupRelDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增模块分组关联")
    @Log(moduleName = "模块分组关联管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/gen/all/group/rel/add")
    public Result<Boolean> addAllGroupRel(@Validated @RequestBody GenGroupRelDTO genGroupRelDTO) {
        return Result.success(genGroupRelService.addAllGroupRel(genGroupRelDTO));
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
    @PostMapping("/gen/all/group/rel/delete/{allGroupRelId}")
    public Result<Boolean> deleteAllGroupRel(@PathVariable(value = "allGroupRelId",required = false)Long allGroupRelId) {
        return Result.success(genGroupRelService.deleteAllGroupRel(allGroupRelId));
    }
}
