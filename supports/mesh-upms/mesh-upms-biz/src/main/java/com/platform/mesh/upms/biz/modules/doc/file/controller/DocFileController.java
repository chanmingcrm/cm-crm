package com.platform.mesh.upms.biz.modules.doc.file.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.file.oss.utils.OssFileUtil;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.biz.modules.doc.file.domain.dto.DocFileDTO;
import com.platform.mesh.upms.biz.modules.doc.file.domain.po.DocFile;
import com.platform.mesh.upms.api.modules.doc.domain.vo.DocFileVO;
import com.platform.mesh.upms.biz.modules.doc.file.service.IDocFileService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 约定当前controller 只引入当前service
 * @description 文件信息
 * @author 蝉鸣
 */
@Tag(description = "DocFileController", name = "文件")
@RestController
@RequestMapping
public class DocFileController extends BaseController{
    @Autowired
    private IDocFileService docFileService;

    /**
	 * 功能描述:
	 * 〈获取文件列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<DocFileVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取文件分页")
	@PostMapping("/doc/file/page")
	public Result<PageVO<DocFileVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<DocFile> fileMPage = MPageUtil.pageEntityToMPage(pageDTO, DocFile.class);
        MPage<DocFile> page = docFileService.page(fileMPage);
        PageVO<DocFileVO> voPage = MPageUtil.convertToVO(page, DocFileVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前文件信息〉
     * @param fileId fileId
     * @return 正常返回:{@link Result<DocFileVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前文件信息")
    @GetMapping("/doc/file/info/{fileId}")
    public Result<DocFileVO> getFileInfoById(@PathVariable("fileId")Long fileId) {
        DocFileVO docFileVO = docFileService.getFileInfoById(fileId);
        return Result.success(docFileVO);
    }

    /**
     * 功能描述:
     * 〈新增文件〉
     * @param files files
     * @return 正常返回:{@link Result<DocFileVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "上传文件")
    @Log(moduleName = "文件管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/doc/file/upload")
    public Result<List<DocFileVO>> uploadFile(@RequestParam("files") MultipartFile[] files) {
        return Result.success(docFileService.uploadFile(files));
    }

    /**
     * 功能描述:
     * 〈预览文件〉
     * @param fileId fileId
     * @author 蝉鸣
     */
    @Operation(summary = "预览文件")
    @PostMapping("/doc/file/preview/{fileId}")
    public void previewFile(HttpServletResponse response, @PathVariable("fileId")Long fileId) {
        DocFileVO docFileVO = docFileService.getFileInfoById(fileId);
        byte[] bytes = docFileService.downloadFile(fileId);
        try{
//            byte[] bytes = OssFileUtil.inToZipOutByte(docFileVO.getFileName(),fileInputStream);
            String fileName = docFileVO.getFileName().concat(SymbolConst.PERIOD).concat(docFileVO.getFileType());
            OssFileUtil.preview(response,bytes,fileName);
        }catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * 功能描述:
     * 〈新增文件〉
     * @param fileId fileId
     * @author 蝉鸣
     */
    @Operation(summary = "下载文件")
    @PostMapping("/doc/file/download/{fileId}")
    public void downloadFile(HttpServletResponse response, @PathVariable("fileId")Long fileId) {
        DocFileVO docFileVO = docFileService.getFileInfoById(fileId);
        byte[] bytes = docFileService.downloadFile(fileId);
        try{
//            byte[] bytes = OssFileUtil.inToZipOutByte(docFileVO.getFileName(),fileInputStream);
            String fileName = docFileVO.getFileName().concat(SymbolConst.PERIOD).concat(docFileVO.getFileType());
            OssFileUtil.download(response,bytes,fileName);
        }catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * 功能描述:
     * 〈修改文件〉
     * @param fileDTO fileDTO
     * @return 正常返回:{@link Result<DocFileVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改文件")
    @Log(moduleName = "文件管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/doc/file/edit")
    public Result<DocFileVO> editFile(@Validated @RequestBody DocFileDTO fileDTO) {
        return Result.success(docFileService.addFile(fileDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除文件〉
     * @param fileId fileId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除文件")
    @Log(moduleName = "文件管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/doc/file/delete/{fileId}")
    public Result<Boolean> deleteFile(@PathVariable(value = "fileId",required = false)Long fileId) {
        return Result.success(docFileService.deleteFile(fileId));
    }

}