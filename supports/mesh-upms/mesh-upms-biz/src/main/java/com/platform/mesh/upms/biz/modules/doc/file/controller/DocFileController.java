package com.platform.mesh.upms.biz.modules.doc.file.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.file.oss.utils.OssFileUtil;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.api.modules.doc.domain.vo.DocFileVO;
import com.platform.mesh.upms.biz.modules.doc.file.domain.dto.DocFileDTO;
import com.platform.mesh.upms.biz.modules.doc.file.domain.dto.DocPageDTO;
import com.platform.mesh.upms.biz.modules.doc.file.domain.po.DocFile;
import com.platform.mesh.upms.biz.modules.doc.file.exception.DocFileExceptionEnum;
import com.platform.mesh.upms.biz.modules.doc.file.service.IDocFileService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
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
	public Result<PageVO<DocFileVO>> selectPage(@RequestBody DocPageDTO pageDTO) {
        MPage<DocFile> page = docFileService.selectPage(pageDTO);
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
    public Result<List<DocFileVO>> uploadFile(
            @RequestParam(value = "moduleId",required = false) Long moduleId,
            @RequestParam(value ="dataId",required = false) Long dataId,
            @RequestParam("files") MultipartFile[] files) {
        return Result.success(docFileService.uploadFile(moduleId,dataId,files));
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
        try(InputStream inputStream = docFileService.downloadFileStream(fileId)){
            String fileName;
            if(ObjectUtil.isEmpty(docFileVO.getFileName()) || ObjectUtil.isEmpty(docFileVO.getFileType())) {
                fileName = StrUtil.EMPTY;
            }else{
                fileName = docFileVO.getFileName().concat(SymbolConst.PERIOD).concat(docFileVO.getFileType());
            }
            OssFileUtil.preview(response,inputStream,fileName);
        }catch (Exception exception) {
            throw DocFileExceptionEnum.ADD_NO_EXIST.getBaseException();
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
        try(InputStream inputStream = docFileService.downloadFileStream(fileId)){
            String fileName = docFileVO.getFileName().concat(SymbolConst.PERIOD).concat(docFileVO.getFileType());
            OssFileUtil.download(response,inputStream,fileName);
        }catch (Exception exception) {
            throw new BaseException(exception);
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


    /**
     * 功能描述:
     * 〈获取开放的文件分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<MPage<DocFileVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取开放的文件分页")
    @PostMapping("/doc/file/open/page")
    public Result<PageVO<DocFileVO>> selectPageOpen(@RequestBody DocPageDTO pageDTO) {
        PageVO<DocFileVO> voPage = docFileService.selectOpenPage(pageDTO);
        return Result.success(voPage);
    }

    /**
     * 功能描述:
     * 〈在文件夹下上传文件〉
     * @param files files
     * @return 正常返回:{@link Result<MPage<DocFileVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "在文件夹下上传文件")
    @PostMapping("/doc/fil/upload/by/dir")
    public Result<List<DocFileVO>> uploadByDir(
            @RequestParam(value ="dirId",required = false) Long dirId,
            @RequestParam("files") MultipartFile[] files) {
        return Result.success(docFileService.uploadFileByDir(dirId,files));
    }

}