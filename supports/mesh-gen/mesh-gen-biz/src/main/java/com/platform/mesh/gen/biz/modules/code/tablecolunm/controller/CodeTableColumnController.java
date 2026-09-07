package com.platform.mesh.gen.biz.modules.code.tablecolunm.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.gen.biz.modules.code.tablecolunm.domain.dto.CodeTableColumnDTO;
import com.platform.mesh.gen.biz.modules.code.tablecolunm.domain.dto.TableColumnQueryDTO;
import com.platform.mesh.gen.biz.modules.code.tablecolunm.domain.po.CodeTableColumn;
import com.platform.mesh.gen.biz.modules.code.tablecolunm.domain.vo.CodeTableColumnVO;
import com.platform.mesh.gen.biz.modules.code.tablecolunm.service.ICodeTableColumnService;
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
@Tag(description = "CodeTableColumnColumnController", name = "生成表字段")
@RestController
public class CodeTableColumnController {


    @Autowired
    private ICodeTableColumnService codeTableColumnService;


    /**
     * 查询生成表字段分页
     */
    @Operation(summary = "查询生成表字段分页")
    @PostMapping("/code/table/column/code/page")
    public Result<PageVO<CodeTableColumnVO>> getTablePage(@RequestBody TableColumnQueryDTO pageDTO) {
        MPage<CodeTableColumn> tableMPage = MPageUtil.pageEntityToMPage(pageDTO, CodeTableColumn.class);
        MPage<CodeTableColumn> page = codeTableColumnService.page(tableMPage);
        PageVO<CodeTableColumnVO> voPage = MPageUtil.convertToVO(page, CodeTableColumnVO.class);
        return Result.success(voPage);
    }

    /**
     * 获取生成表字段详细信息
     */
    @Operation(summary = "获取生成表字段详细信息")
    @GetMapping(value = "/code/table/column/{columnId}")
    public Result<CodeTableColumn> getTempInfoById(@PathVariable("columnId") Long columnId) {
        return Result.success(codeTableColumnService.getById(columnId));
    }

    /**
     * 新增生成表字段
     */
    @Operation(summary = "新增生成表字段")
    @PostMapping(value = "/code/table/column/add")
    public Result<CodeTableColumnVO> addTemp(@RequestBody CodeTableColumnDTO addDTO) {
        CodeTableColumn codeBuild = BeanUtil.copyProperties(addDTO, CodeTableColumn.class);
        codeTableColumnService.save(codeBuild);
        return Result.success(BeanUtil.copyProperties(addDTO, CodeTableColumnVO.class));
    }

    /**
     * 修改生成表字段
     */
    @Operation(summary = "修改生成表字段")
    @PostMapping(value = "/code/table/column/edit")
    public Result<CodeTableColumnVO> editTemp(@RequestBody CodeTableColumnDTO addDTO) {
        CodeTableColumn codeBuild = BeanUtil.copyProperties(addDTO, CodeTableColumn.class);
        codeTableColumnService.updateById(codeBuild);
        return Result.success(BeanUtil.copyProperties(addDTO, CodeTableColumnVO.class));
    }

    /**
     * 删除生成表字段
     */
    @Operation(summary = "删除生成表字段")
    @PostMapping("/code/table/column/delete/{columnId}")
    public Result<Boolean> deleteTemp(@PathVariable(value = "columnId")Long columnId) {
        return Result.success(codeTableColumnService.removeById(columnId));
    }

//    /**
//     * 查询数据库表分页
//     */
//    @Operation(summary = "查询数据库表分页")
//    @PostMapping("/code/table/column/db/page")
//    public Result<PageVO<DbTableColumnBO>> getDBTablePage(@RequestBody TableColumnQueryDTO pageDTO) {
//        MPage<DbTableColumnBO> page = codeTableColumnService.getDBTableColumnPage(pageDTO.getTableSchema(),pageDTO.getTableNames());
//        PageVO<DbTableColumnBO> voPage = MPageUtil.convertToVO(page, DbTableColumnBO.class);
//        return Result.success(voPage);
//    }
}
