package com.platform.mesh.gen.biz.modules.code.ds.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.gen.biz.modules.code.ds.domain.dto.CodeDataSourceDTO;
import com.platform.mesh.gen.biz.modules.code.ds.domain.po.CodeDataSource;
import com.platform.mesh.gen.biz.modules.code.ds.domain.vo.CodeDataSourceVO;
import com.platform.mesh.gen.biz.modules.code.ds.service.ICodeDataSourceService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 约定当前controller 只引入当前service
 * @description 数据源配置
 * @author 蝉鸣
 */
@Tag(description = "CodeDataSourceController", name = "数据源配置")
@RestController
public class CodeDataSourceController {

    @Autowired
    private ICodeDataSourceService codeDataSourceService;


    /**
     * 查询数据源配置分页
     */
    @Operation(summary = "查询数据源配置分页")
    @PostMapping("/code/data/source/page")
    public Result<PageVO<CodeDataSourceVO>> getDataSourcePage(@RequestBody PageDTO pageEntity) {
        MPage<CodeDataSource> levelMPage = MPageUtil.pageEntityToMPage(pageEntity, CodeDataSource.class);
        MPage<CodeDataSource> page = codeDataSourceService.page(levelMPage);
        PageVO<CodeDataSourceVO> voPage = MPageUtil.convertToVO(page, CodeDataSourceVO.class);
        return Result.success(voPage);
    }

    /**
     * 获取数据源配置详细信息
     */
    @Operation(summary = "获取数据源配置详细信息")
    @GetMapping(value = "/code/data/source/{dsId}")
    public Result<CodeDataSource> getDataSourceInfoById(@PathVariable("dsId") Long dsId) {
        return Result.success(codeDataSourceService.getById(dsId));
    }

    /**
     * 新增数据源配置
     */
    @Operation(summary = "新增数据源配置")
    @PostMapping(value = "/code/data/source/add")
    public Result<CodeDataSourceVO> addDataSource(@RequestBody CodeDataSourceDTO addDTO) {
        CodeDataSource codeBuild = BeanUtil.copyProperties(addDTO, CodeDataSource.class);
        codeDataSourceService.save(codeBuild);
        return Result.success(BeanUtil.copyProperties(addDTO, CodeDataSourceVO.class));
    }

    /**
     * 修改数据源配置
     */
    @Operation(summary = "修改数据源配置")
    @PostMapping(value = "/code/data/source/edit")
    public Result<CodeDataSourceVO> editDataSource(@RequestBody CodeDataSourceDTO addDTO) {
        CodeDataSource codeBuild = BeanUtil.copyProperties(addDTO, CodeDataSource.class);
        codeDataSourceService.updateById(codeBuild);
        return Result.success(BeanUtil.copyProperties(addDTO, CodeDataSourceVO.class));
    }

    /**
     * 删除数据源配置
     */
    @Operation(summary = "删除数据源配置")
    @PostMapping("/code/data/source/delete/{dsId}")
    public Result<Boolean> deleteDataSource(@PathVariable(value = "dsId")Long dsId) {
        return Result.success(codeDataSourceService.removeById(dsId));
    }

}
