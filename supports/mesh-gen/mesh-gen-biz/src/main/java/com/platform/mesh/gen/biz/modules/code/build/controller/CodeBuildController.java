package com.platform.mesh.gen.biz.modules.code.build.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.gen.biz.modules.code.build.domain.dto.CodeBuildAddDTO;
import com.platform.mesh.gen.biz.modules.code.build.domain.dto.CodeBuildDTO;
import com.platform.mesh.gen.biz.modules.code.build.domain.dto.CodeBuildInitDTO;
import com.platform.mesh.gen.biz.modules.code.build.domain.dto.CodeBuildPreviewDTO;
import com.platform.mesh.gen.biz.modules.code.build.domain.po.CodeBuild;
import com.platform.mesh.gen.biz.modules.code.build.domain.vo.CodeBuildVO;
import com.platform.mesh.gen.biz.modules.code.build.service.ICodeBuildService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 约定当前controller 只引入当前service
 * @description 模板信息
 * @author 蝉鸣
 */
@Tag(description = "CodeBuildController", name = "代码生成构建代码")
@RestController
public class CodeBuildController {

    @Autowired
    private ICodeBuildService codeBuildService;


    /**
     * 查询代码生成分页
     */
    @Operation(summary = "查询代码生成分页")
    @PostMapping("/code/build/page")
    public Result<PageVO<CodeBuildVO>> getBuildPage(@RequestBody PageDTO pageEntity) {
        MPage<CodeBuild> levelMPage = MPageUtil.pageEntityToMPage(pageEntity, CodeBuild.class);
        MPage<CodeBuild> page = codeBuildService.page(levelMPage);
        PageVO<CodeBuildVO> voPage = MPageUtil.convertToVO(page, CodeBuildVO.class);
        return Result.success(voPage);
    }

    /**
     * 获取代码生成详细信息
     */
    @Operation(summary = "获取代码生成详细信息")
    @GetMapping(value = "/code/build/{buildId}")
    public Result<CodeBuildVO> getBuildInfoById(@PathVariable("buildId") Long buildId) {
        return Result.success(BeanUtil.copyProperties(codeBuildService.getById(buildId), CodeBuildVO.class));
    }

    /**
     * 新增代码生成
     */
    @Operation(summary = "新增代码生成")
    @PostMapping(value = "/code/build/add")
    public Result<CodeBuildVO> addBuild(@RequestBody CodeBuildAddDTO addDTO) {
        CodeBuild codeBuild = BeanUtil.copyProperties(addDTO, CodeBuild.class);
        codeBuild.setDelFlag(YesOrNoEnum.YES.getValue());
        codeBuildService.save(codeBuild);
        return Result.success(BeanUtil.copyProperties(codeBuild, CodeBuildVO.class));
    }

    /**
     * 修改代码生成
     */
    @Operation(summary = "修改代码生成")
    @PostMapping(value = "/code/build/edit")
    public Result<CodeBuildVO> editBuild(@RequestBody CodeBuildAddDTO addDTO) {
        CodeBuild codeBuild = BeanUtil.copyProperties(addDTO, CodeBuild.class);
        codeBuildService.updateById(codeBuild);
        return Result.success(BeanUtil.copyProperties(codeBuild, CodeBuildVO.class));
    }

    /**
     * 删除代码生成
     */
    @Operation(summary = "删除代码生成")
    @PostMapping("/code/build/delete/{buildId}")
    public Result<Boolean> deleteBuild(@PathVariable(value = "buildId")Long buildId) {
        return Result.success(codeBuildService.removeById(buildId));
    }

    /**
     * 初始化代码生成
     * @return 正常返回:{@link Result<Boolean>}
     */
    @Operation(summary = "初始化代码生成")
    @PostMapping("/code/build/init")
    public Result<Boolean> buildInit(@RequestBody CodeBuildInitDTO buildInitDTO) {
        return Result.success(codeBuildService.buildInit(buildInitDTO));
    }

    /**
     * 预览代码
     * @param previewDTO previewDTO
     * @return 正常返回:{@link Result<String>}
     */
    @Operation(summary = "预览代码")
    @PostMapping("/code/build/preview")
    public Result<String> previewCode(@RequestBody CodeBuildPreviewDTO previewDTO) {
        return Result.success(Boolean.TRUE.toString(),codeBuildService.previewCode(previewDTO));
    }


    /**
     * 批量生成代码
     * @param response HttpServletResponse
     * @param codeBuildDTO codeBuildDTO
     * @throws IOException IOException
     */
    @Operation(summary = "生成代码")
    @PostMapping("/code/build/gen")
    public void buildCode(HttpServletResponse response, @RequestBody CodeBuildDTO codeBuildDTO) throws IOException {
        byte[] data = codeBuildService.buildCode(codeBuildDTO);
        genCode(response, data);
    }

    /**
     * 生成zip文件
     * @param response HttpServletResponse
     * @param data 数据
     * @throws IOException IOException
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"code.zip\"");
        response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
        response.setContentType(HttpConst.APPLICATION_STREAM_CHARSET);
        try (OutputStream out = response.getOutputStream()) {
            IOUtils.write(data, out);
            // 主动刷新，确保数据完整
            out.flush();
        } catch (IOException ignore) {
            // 处理异常
        }
    }
}
