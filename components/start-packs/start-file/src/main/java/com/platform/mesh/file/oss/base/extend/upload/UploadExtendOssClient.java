package com.platform.mesh.file.oss.base.extend.upload;

import com.platform.mesh.file.oss.base.common.model.bo.DocFileBO;
import com.platform.mesh.file.oss.base.extend.upload.model.MultiPartBO;
import com.platform.mesh.file.oss.exception.FileExceptionEnum;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description 上传扩展功能
 * @author 蝉鸣
 */
public interface UploadExtendOssClient {

    //-- --------------------------------------------------------
    //        -- 文件分片上传入口
    //-- --------------------------------------------------------

    /**
     * 功能描述:
     * 〈分块上传文件〉
     * @param multiPartBO 文件块信息
     * @author 蝉鸣
     */
    void uploadFileMultiPart(MultiPartBO multiPartBO);

    /**
     * 功能描述:
     * 〈分块上传文件〉
     * @param file 文件块信息
     * @author 蝉鸣
     */
    DocFileBO uploadFileMultiPart(MultipartFile file);


    //-- --------------------------------------------------------
    //        -- 文件分片上传三步走
    //-- --------------------------------------------------------

    /**
     * 功能描述:
     * 〈初始化断点续传对象〉
     * @param filename filename
     * @return 正常返回:{@link String} uploadId
     * @author 蝉鸣
     */
    default String initiateMultipartUpload(String filename) {
        throw FileExceptionEnum.FILE_INIT_INVALID.getBaseException();
    }

    /**
     * 功能描述:
     * 〈上传文件块〉
     * @param multipartBO 文件块
     * @param uploadId 任务ID
     * @return 正常返回:{@link String} 对于S3返回eTag地址  对于本地文件返回文件块地址
     * @author 蝉鸣
     */
    default String uploadMultipart(MultiPartBO multipartBO, String uploadId) {
        throw FileExceptionEnum.FILE_UPLOAD_INVALID.getBaseException();
    }

    /**
     * 功能描述:
     * 〈文件合并〉
     * @param filename 文件名
     * @author 蝉鸣
     */
    default void completeMultipartUpload(String filename){
        throw FileExceptionEnum.FILE_MULTI_INVALID.getBaseException();
    };

}
