package com.platform.mesh.gen.biz.modules.code.buildconf.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.gen.biz.modules.code.buildconf.domain.dto.CodeBuildConfDTO;
import com.platform.mesh.gen.biz.modules.code.buildconf.domain.po.CodeBuildConf;
import com.platform.mesh.gen.biz.modules.code.buildconf.domain.vo.CodeBuildConfVO;
import com.platform.mesh.gen.biz.modules.code.buildconf.service.ICodeBuildConfService;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 约定当前controller 只引入当前service
 * @description 模板信息
 * @author 蝉鸣
 */
@Tag(description = "CodeBuildController", name = "代码生成参数配置")
@RestController
public class CodeBuildConfController {

    @Autowired
    private ICodeBuildConfService codeBuildConfService;

    /**
     * 功能描述:
     * 〈获取需要配置的信息〉
     * @author 蝉鸣
     */
    @Operation(summary = "获取需要配置的信息")
    @PostMapping("/code/build/conf")
    public Result<MPage<CodeBuildConf>> buildConfPage(@RequestBody PageDTO pageEntity) {
        return Result.success(codeBuildConfService.buildConfPage(pageEntity));
    }


    /**
     * 新增生成配置
     */
    @Operation(summary = "新增生成配置")
    @PostMapping(value = "/code/build/conf/add")
    public Result<CodeBuildConfVO> addBuildConf(@RequestBody CodeBuildConfDTO addDTO) {
        CodeBuildConf codeBuildConf = BeanUtil.copyProperties(addDTO, CodeBuildConf.class);
        codeBuildConfService.save(codeBuildConf);
        return Result.success(BeanUtil.copyProperties(codeBuildConf, CodeBuildConfVO.class));
    }

    /**
     * 修改生成配置
     */
    @Operation(summary = "修改生成配置")
    @PostMapping(value = "/code/build/conf/edit")
    public Result<CodeBuildConfVO> editBuildConf(@RequestBody CodeBuildConfDTO addDTO) {
        CodeBuildConf codeBuildConf = BeanUtil.copyProperties(addDTO, CodeBuildConf.class);
        codeBuildConfService.updateById(codeBuildConf);
        return Result.success(BeanUtil.copyProperties(addDTO, CodeBuildConfVO.class));
    }

    /**
     * 删除生成配置
     */
    @Operation(summary = "删除生成配置")
    @PostMapping("/code/build/conf/delete/{confId}")
    public Result<Boolean> deleteBuildConf(@PathVariable(value = "confId")Long confId) {
        return Result.success(codeBuildConfService.removeById(confId));
    }

}
