package com.platform.mesh.upms.biz.modules.doc.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.api.modules.doc.domain.vo.DocFileVO;
import com.platform.mesh.upms.biz.modules.doc.file.domain.dto.DocFileDTO;
import com.platform.mesh.upms.biz.modules.doc.file.domain.dto.DocPageDTO;
import com.platform.mesh.upms.biz.modules.doc.file.domain.po.DocFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;




/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 文件信息
 * @author 蝉鸣
 */
public interface IDocFileService extends IService<DocFile> {


    /**
     * 功能描述:
     * 〈分页查询文档〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<DocFile>}
     * @author 蝉鸣
     */
    MPage<DocFile> selectPage(DocPageDTO pageDTO);

    /**
     * 功能描述:
     * 〈获取当前文件信息〉
     * @param fileId fileId
     * @return 正常返回:{@link DocFileVO}
     * @author 蝉鸣
     */
    DocFileVO getFileInfoById(Long fileId);

    /**
     * 功能描述:
     * 〈获取当前文件信息〉
     * @param fileIds fileIds
     * @return 正常返回:{@link List<DocFileVO>}
     * @author 蝉鸣
     */
    List<DocFileVO> getFileInfoById(List<Long> fileIds);

    /**
     * 功能描述:
     * 〈主要用于获取验证码等未登录状态下的文件，需要限制数量〉
     * @param fileFlag fileFlag
     * @return 正常返回:{@link List<DocFileVO>}
     * @author 蝉鸣
     */
    List<DocFileVO> getByFileFlagNoAuth(Integer fileFlag);

    /**
     * 功能描述:
     * 〈新增文件〉
     * @param moduleId moduleId
     * @param dataId dataId
     * @param files files
     * @return 正常返回:{@link DocFileVO}
     * @author 蝉鸣
     */
    List<DocFileVO> uploadFile(Long moduleId,Long dataId,MultipartFile[] files);

    /**
     * 功能描述:
     * 〈流式下载文件〉
     * @param fileId fileId
     * @return 正常返回:{@link InputStream}
     */
    InputStream downloadFileStream(Long fileId);

    /**
     * 功能描述:
     * 〈修改文件〉
     * @param fileDTO fileDTO
     * @return 正常返回:{@link DocFileVO}
     * @author 蝉鸣
     */
    DocFileVO addFile(DocFileDTO fileDTO);

    /**
     * 功能描述:
     * 〈删除文件〉
     * @param fileId fileId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteFile(Long fileId);

    /**
     * 功能描述:
     * 〈分页查询文档〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<DocFileVO>}
     * @author 蝉鸣
     */
    PageVO<DocFileVO> selectOpenPage(DocPageDTO pageDTO);

    /**
     * 功能描述:
     * 〈新增文件〉
     * @param dirId dirId
     * @param files files
     * @author 蝉鸣
     */
    List<DocFileVO> uploadFileByDir(Long dirId,MultipartFile[] files);
}
