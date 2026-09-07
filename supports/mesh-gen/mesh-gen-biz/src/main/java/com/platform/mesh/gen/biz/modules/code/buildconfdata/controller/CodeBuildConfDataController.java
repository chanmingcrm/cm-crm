package com.platform.mesh.gen.biz.modules.code.buildconfdata.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.gen.biz.modules.code.buildconfdata.domain.dto.CodeBuildConfDataDTO;
import com.platform.mesh.gen.biz.modules.code.buildconfdata.domain.dto.CodeBuildConfDataPageDTO;
import com.platform.mesh.gen.biz.modules.code.buildconfdata.domain.po.CodeBuildConfData;
import com.platform.mesh.gen.biz.modules.code.buildconfdata.domain.vo.CodeBuildConfDataVO;
import com.platform.mesh.gen.biz.modules.code.buildconfdata.service.ICodeBuildConfDataService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 约定当前controller 只引入当前service
 * @description 代码生成参数配置
 * @author 蝉鸣
 */
@Tag(description = "CodeBuildConfDataController", name = "代码生成参数配置值")
@RestController
public class CodeBuildConfDataController {

    @Autowired
    private ICodeBuildConfDataService codeBuildConfDataService;

    /**
     * 功能描述:
     * 〈获取需要配置的信息〉
     * @author 蝉鸣
     */
    @Operation(summary = "获取需要配置的信息")
    @PostMapping("/code/build/conf/data")
    public Result<MPage<CodeBuildConfData>> buildConfDataPage(@RequestBody CodeBuildConfDataPageDTO pageEntity) {
        return Result.success(codeBuildConfDataService.buildConfDataPage(pageEntity));
    }


    /**
     * 新增配置值
     */
    @Operation(summary = "新增配置值")
    @PostMapping(value = "/code/build/conf/data/add")
    public Result<CodeBuildConfDataVO> addConfData(@RequestBody CodeBuildConfDataDTO addDTO) {
        CodeBuildConfData codeBuildConfData = BeanUtil.copyProperties(addDTO, CodeBuildConfData.class);
        codeBuildConfDataService.save(codeBuildConfData);
        return Result.success(BeanUtil.copyProperties(codeBuildConfData, CodeBuildConfDataVO.class));
    }

    /**
     * 修改配置值
     */
    @Operation(summary = "修改配置值")
    @PostMapping(value = "/code/build/conf/data/edit")
    public Result<CodeBuildConfDataVO> editConfData(@RequestBody CodeBuildConfDataDTO addDTO) {
        CodeBuildConfData codeBuildConfData = BeanUtil.copyProperties(addDTO, CodeBuildConfData.class);
        codeBuildConfDataService.updateById(codeBuildConfData);
        return Result.success(BeanUtil.copyProperties(codeBuildConfData, CodeBuildConfDataVO.class));
    }

    /**
     * 删除配置值
     */
    @Operation(summary = "删除配置值")
    @PostMapping("/code/build/conf/data/delete/{dataId}")
    public Result<Boolean> deleteConfData(@PathVariable(value = "dataId")Long dataId) {
        return Result.success(codeBuildConfDataService.removeById(dataId));
    }

}
