package com.platform.mesh.gen.biz.modules.code.table.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.gen.biz.modules.code.table.domain.bo.DbTableBO;
import com.platform.mesh.gen.biz.modules.code.table.domain.dto.CodeTableDTO;
import com.platform.mesh.gen.biz.modules.code.table.domain.dto.TableQueryDTO;
import com.platform.mesh.gen.biz.modules.code.table.domain.po.CodeTable;
import com.platform.mesh.gen.biz.modules.code.table.domain.vo.CodeTableVO;
import com.platform.mesh.gen.biz.modules.code.table.service.ICodeTableService;
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
@Tag(description = "CodeTableController", name = "生成表")
@RestController
public class CodeTableController {

    @Autowired
    private ICodeTableService codeTableService;


    /**
     * 查询生成表分页
     */
    @Operation(summary = "查询生成表分页")
    @PostMapping("/code/table/code/page")
    public Result<PageVO<CodeTableVO>> getTablePage(@RequestBody TableQueryDTO pageDTO) {
        MPage<CodeTable> tableMPage = MPageUtil.pageEntityToMPage(pageDTO, CodeTable.class);
        MPage<CodeTable> page = codeTableService.page(tableMPage);
        PageVO<CodeTableVO> voPage = MPageUtil.convertToVO(page, CodeTableVO.class);
        return Result.success(voPage);
    }

    /**
     * 获取生成表详细信息
     */
    @Operation(summary = "获取生成表详细信息")
    @GetMapping(value = "/code/table/{tableId}")
    public Result<CodeTable> getTempInfoById(@PathVariable("tableId") Long tableId) {
        return Result.success(codeTableService.getById(tableId));
    }

    /**
     * 新增生成表
     */
    @Operation(summary = "新增生成表")
    @PostMapping(value = "/code/table/add")
    public Result<CodeTableVO> addTemp(@RequestBody CodeTableDTO addDTO) {
        CodeTable codeBuild = BeanUtil.copyProperties(addDTO, CodeTable.class);
        codeTableService.save(codeBuild);
        return Result.success(BeanUtil.copyProperties(addDTO, CodeTableVO.class));
    }

    /**
     * 修改生成表
     */
    @Operation(summary = "修改生成表")
    @PostMapping(value = "/code/table/edit")
    public Result<CodeTableVO> editTemp(@RequestBody CodeTableDTO addDTO) {
        CodeTable codeBuild = BeanUtil.copyProperties(addDTO, CodeTable.class);
        codeTableService.updateById(codeBuild);
        return Result.success(BeanUtil.copyProperties(addDTO, CodeTableVO.class));
    }

    /**
     * 删除生成表
     */
    @Operation(summary = "删除生成表")
    @PostMapping("/code/table/delete/{tableId}")
    public Result<Boolean> deleteTemp(@PathVariable(value = "tableId")Long tableId) {
        return Result.success(codeTableService.removeById(tableId));
    }

    /**
     * 查询数据库表分页
     */
    @Operation(summary = "查询数据库表分页")
    @PostMapping("/code/table/db/page")
    public Result<PageVO<CodeTableVO>> getDBTablePage(@RequestBody TableQueryDTO pageDTO) {
        MPage<DbTableBO> page = codeTableService.selectDbTablePage(pageDTO);
        PageVO<CodeTableVO> voPage = MPageUtil.convertToVO(page, CodeTableVO.class);
        return Result.success(voPage);
    }


}
