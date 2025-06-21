package com.platform.mesh.upms.biz.modules.doc.dir.controller;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.upms.biz.modules.doc.dir.domain.dto.DocDirDTO;
import com.platform.mesh.upms.biz.modules.doc.dir.domain.po.DocDir;
import com.platform.mesh.upms.biz.modules.doc.dir.domain.vo.DocDirVO;
import com.platform.mesh.upms.biz.modules.doc.dir.service.IDocDirService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 文件目录信息
 * @author 蝉鸣
 */
@Tag(description = "DocDirController", name = "文件目录")
@RestController
@RequestMapping
public class DocDirController extends BaseController{
    @Autowired
    private IDocDirService docDirService;

    /**
	 * 功能描述:
	 * 〈获取文件目录列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<DocDirVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取文件目录分页")
	@PostMapping("/doc/dir/page")
	public Result<PageVO<DocDirVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<DocDir> dirMPage = MPageUtil.pageEntityToMPage(pageDTO, DocDir.class);
        MPage<DocDir> page = docDirService.page(dirMPage);
        PageVO<DocDirVO> voPage = MPageUtil.convertToVO(page, DocDirVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前文件目录信息〉
     * @param dirId dirId
     * @return 正常返回:{@link Result<DocDirVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前文件目录信息")
    @GetMapping("/doc/dir/info/{dirId}")
    public Result<DocDirVO> getDirInfoById(@PathVariable("dirId")Long dirId) {
        DocDirVO docDirVO = docDirService.getDirInfoById(dirId);
        return Result.success(docDirVO);
    }

    /**
     * 功能描述:
     * 〈新增文件目录〉
     * @param dirDTO dirDTO
     * @return 正常返回:{@link Result<DocDirVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增文件目录")
    @Log(moduleName = "文件目录管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/doc/dir/add")
    public Result<DocDirVO> addDir(@Validated @RequestBody DocDirDTO dirDTO) {
        return Result.success(docDirService.addDir(dirDTO));
    }

    /**
     * 功能描述:
     * 〈修改文件目录〉
     * @param dirDTO dirDTO
     * @return 正常返回:{@link Result<DocDirVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改文件目录")
    @Log(moduleName = "文件目录管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/doc/dir/edit")
    public Result<DocDirVO> editDir(@Validated @RequestBody DocDirDTO dirDTO) {
        return Result.success(docDirService.editDir(dirDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除文件目录〉
     * @param dirId dirId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除文件目录")
    @Log(moduleName = "文件目录管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/doc/dir/delete/{dirId}")
    public Result<Boolean> deleteDir(@PathVariable(value = "dirId",required = false)Long dirId) {
        return Result.success(docDirService.deleteDir(dirId));
    }

}