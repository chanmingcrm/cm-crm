package com.platform.mesh.gen.biz.modules.code.temp.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.gen.biz.modules.code.temp.domain.dto.CodeTemplateDTO;
import com.platform.mesh.gen.biz.modules.code.temp.domain.po.CodeTemplate;
import com.platform.mesh.gen.biz.modules.code.temp.domain.vo.CodeTemplateVO;
import com.platform.mesh.gen.biz.modules.code.temp.service.ICodeTemplateService;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
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
@Tag(description = "CodeTemplateController", name = "代码生成业务模板")
@RestController
public class CodeTemplateController {

    @Autowired
    private ICodeTemplateService codeTemplateService;

    /**
     * 查询代码生成业务模板分页
     */
    @Operation(summary = "查询代码生成业务模板分页")
    @PostMapping("/code/temp/page")
    public Result<PageVO<CodeTemplate>> getTempPage(@RequestBody PageDTO pageEntity) {
        MPage<CodeTemplate> levelMPage = MPageUtil.pageEntityToMPage(pageEntity, CodeTemplate.class);
        MPage<CodeTemplate> page = codeTemplateService.page(levelMPage);
        PageVO<CodeTemplate> voPage = MPageUtil.convertToVO(page, CodeTemplate.class);
        return Result.success(voPage);
    }

    /**
     * 获取代码生成业务模板详细信息
     */
    @Operation(summary = "获取代码生成业务模板详细信息")
    @GetMapping(value = "/code/temp/{tempId}")
    public Result<CodeTemplateVO> getTempInfoById(@PathVariable("tempId") Long tempId) {
        return Result.success(BeanUtil.copyProperties(codeTemplateService.getById(tempId),CodeTemplateVO.class));
    }

    /**
     * 新增代码生成业务模板
     */
    @Operation(summary = "新增代码生成业务模板")
    @PostMapping(value = "/code/temp/add")
    public Result<CodeTemplateVO> addTemp(@RequestBody CodeTemplateDTO addDTO) {
        CodeTemplate codeTemplate = BeanUtil.copyProperties(addDTO, CodeTemplate.class);
        codeTemplate.setDelFlag(YesOrNoEnum.YES.getValue());
        codeTemplateService.save(codeTemplate);
        return Result.success(BeanUtil.copyProperties(codeTemplate, CodeTemplateVO.class));
    }

    /**
     * 修改代码生成业务模板
     */
    @Operation(summary = "修改代码生成业务模板")
    @PostMapping(value = "/code/temp/edit")
    public Result<CodeTemplateVO> editTemp(@RequestBody CodeTemplateDTO addDTO) {
        CodeTemplate codeTemplate = BeanUtil.copyProperties(addDTO, CodeTemplate.class);
        codeTemplateService.updateById(codeTemplate);
        return Result.success(BeanUtil.copyProperties(codeTemplate, CodeTemplateVO.class));
    }

    /**
     * 删除代码生成业务模板
     */
    @Operation(summary = "删除代码生成业务模板")
    @PostMapping("/code/temp/delete/{tempId}")
    public Result<Boolean> deleteTemp(@PathVariable(value = "tempId")Long tempId) {
        return Result.success(codeTemplateService.removeById(tempId));
    }

}
