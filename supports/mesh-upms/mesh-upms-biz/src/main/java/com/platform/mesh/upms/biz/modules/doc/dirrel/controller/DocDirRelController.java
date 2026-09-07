package com.platform.mesh.upms.biz.modules.doc.dirrel.controller;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.upms.biz.modules.doc.dirrel.domain.dto.DocDirRelDTO;
import com.platform.mesh.upms.biz.modules.doc.dirrel.domain.po.DocDirRel;
import com.platform.mesh.upms.biz.modules.doc.dirrel.domain.vo.DocDirRelVO;
import com.platform.mesh.upms.biz.modules.doc.dirrel.service.IDocDirRelService;
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
 * @description 文件信息
 * @author 蝉鸣
 */
@Tag(description = "DocDirRelController", name = "目录文件关系")
@RestController
@RequestMapping
public class DocDirRelController extends BaseController{
    @Autowired
    private IDocDirRelService docDirRelService;

    /**
	 * 功能描述:
	 * 〈获取文件列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<DocDirRelVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取文件分页")
	@PostMapping("/doc/dir/rel/page")
	public Result<PageVO<DocDirRelVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<DocDirRel> dirRelMPage = MPageUtil.pageEntityToMPage(pageDTO, DocDirRel.class);
        MPage<DocDirRel> page = docDirRelService.page(dirRelMPage);
        PageVO<DocDirRelVO> voPage = MPageUtil.convertToVO(page, DocDirRelVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前文件信息〉
     * @param dirRelId dirRelId
     * @return 正常返回:{@link Result<DocDirRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前文件信息")
    @GetMapping("/doc/dir/rel/info/{dirRelId}")
    public Result<DocDirRelVO> getDirRelInfoById(@PathVariable("dirRelId")Long dirRelId) {
        DocDirRelVO docDirRelVO = docDirRelService.getDirRelInfoById(dirRelId);
        return Result.success(docDirRelVO);
    }

    /**
     * 功能描述:
     * 〈新增文件〉
     * @param dirRelDTO dirRelDTO
     * @return 正常返回:{@link Result<DocDirRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增文件")
    @Log(moduleName = "文件管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/doc/dir/rel/add")
    public Result<DocDirRelVO> addDirRel(@Validated @RequestBody DocDirRelDTO dirRelDTO) {
        return Result.success(docDirRelService.addDirRel(dirRelDTO));
    }

    /**
     * 功能描述:
     * 〈修改文件〉
     * @param dirRelDTO dirRelDTO
     * @return 正常返回:{@link Result<DocDirRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改文件")
    @Log(moduleName = "文件管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/doc/dir/rel/edit")
    public Result<DocDirRelVO> editDirRel(@Validated @RequestBody DocDirRelDTO dirRelDTO) {
        return Result.success(docDirRelService.editDirRel(dirRelDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除文件〉
     * @param dirRelId dirRelId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除文件")
    @Log(moduleName = "文件管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/doc/dir/rel/delete/{dirRelId}")
    public Result<Boolean> deleteDirRel(@PathVariable(value = "dirRelId",required = false)Long dirRelId) {
        return Result.success(docDirRelService.deleteDirRel(dirRelId));
    }

}