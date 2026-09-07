package com.platform.mesh.gen.biz.modules.code.field.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.gen.biz.modules.code.field.domain.dto.CodeFieldMappingDTO;
import com.platform.mesh.gen.biz.modules.code.field.domain.po.CodeFieldMapping;
import com.platform.mesh.gen.biz.modules.code.field.domain.vo.CodeFieldMappingVO;
import com.platform.mesh.gen.biz.modules.code.field.service.ICodeFieldService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 字段类型映射
 * @author 蝉鸣
 */
@Tag(description = "CodeFieldController", name = "字段类型映射")
@RestController
public class CodeFieldController {

    @Autowired
    private ICodeFieldService codeFieldService;


    /**
     * 查询字段映射分页
     */
    @Operation(summary = "查询字段映射分页")
    @PostMapping("/code/field/mapping/page")
    public Result<PageVO<CodeFieldMappingVO>> getFieldMappingPage(@RequestBody PageDTO pageEntity) {
        MPage<CodeFieldMapping> levelMPage = MPageUtil.pageEntityToMPage(pageEntity, CodeFieldMapping.class);
        MPage<CodeFieldMapping> page = codeFieldService.page(levelMPage);
        PageVO<CodeFieldMappingVO> voPage = MPageUtil.convertToVO(page, CodeFieldMappingVO.class);
        return Result.success(voPage);
    }

    /**
     * 新增字段映射
     */
    @Operation(summary = "新增字段映射")
    @PostMapping(value = "/code/field/mapping/add")
    public Result<CodeFieldMappingVO> addFieldMapping(@RequestBody CodeFieldMappingDTO addDTO) {
        CodeFieldMapping codeFieldMapping = BeanUtil.copyProperties(addDTO, CodeFieldMapping.class);
        codeFieldService.save(codeFieldMapping);
        return Result.success(BeanUtil.copyProperties(codeFieldMapping, CodeFieldMappingVO.class));
    }

    /**
     * 修改字段映射
     */
    @Operation(summary = "修改字段映射")
    @PostMapping(value = "/code/field/mapping/edit")
    public Result<CodeFieldMappingVO> editFieldMapping(@RequestBody CodeFieldMappingDTO addDTO) {
        CodeFieldMapping codeFieldMapping = BeanUtil.copyProperties(addDTO, CodeFieldMapping.class);
        codeFieldService.updateById(codeFieldMapping);
        return Result.success(BeanUtil.copyProperties(addDTO, CodeFieldMappingVO.class));
    }

    /**
     * 删除字段映射
     */
    @Operation(summary = "删除字段映射")
    @PostMapping("/code/field/mapping/delete/{fieldId}")
    public Result<Boolean> deleteFieldMapping(@PathVariable(value = "fieldId")Long fieldId) {
        return Result.success(codeFieldService.removeById(fieldId));
    }

}
