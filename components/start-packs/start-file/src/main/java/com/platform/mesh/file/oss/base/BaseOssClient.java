package com.platform.mesh.file.oss.base;

import com.platform.mesh.file.oss.base.common.model.OssUploadBO;
import com.platform.mesh.file.oss.base.common.model.bo.DocFileBO;
import com.platform.mesh.file.oss.base.extend.upload.UploadExtendOssClient;
import software.amazon.awssdk.services.s3.model.Bucket;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * @description OSS功能
 * @author 蝉鸣
 */
public interface BaseOssClient extends UploadExtendOssClient {


    //-- --------------------------------------------------------
    //        -- bucket操作接口
    //-- --------------------------------------------------------
    /**
     * 功能描述:
     * 〈获取所有的桶信息〉
     * @return 正常返回:{@link List<Object>}
     * @author 蝉鸣
     */
    List<Bucket> getAllBuckets();

    /**
     * 功能描述:
     * 〈创建bucket〉
     * @param bucketName bucketName
     * @author 蝉鸣
     */
    void createBucket(String bucketName);

    /**
     * 功能描述:
     * 〈获取单个桶信息〉
     * @param bucketName bucketName
     * @return 正常返回:{@link Optional<Bucket>}
     * @author 蝉鸣
     */
    Optional<Bucket> getBucket(String bucketName);

    /**
     * 功能描述:
     * 〈移除桶信息〉
     * @param bucketName bucketName
     * @author 蝉鸣
     */
    void removeBucket(String bucketName);

    //-- --------------------------------------------------------
    //        -- 文件上传接口
    //-- --------------------------------------------------------
    /**
     * 功能描述:
     * 〈简易上传〉
     * @param uploadO uploadO
     * @return 正常返回:{@link DocFileBO}
     * @author 蝉鸣
     */
    DocFileBO upLoadFile(OssUploadBO uploadO);

    //-- --------------------------------------------------------
    //        -- 文件详情接口
    //-- --------------------------------------------------------
    /**
     * 功能描述:
     * 〈文件详情〉
     * @param bucketName bucketName
     * @param fileName fileName
     * @author 蝉鸣
     */
    void getFileInfo(String bucketName, String fileName);
    /**
     * 功能描述:
     * 〈文件URL〉
     * @param bucketName bucketName
     * @param fileName fileName
     * @param expires expires
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    String getFileURL(String bucketName, String fileName, Duration expires);

    //-- --------------------------------------------------------
    //        -- 文件下载接口
    //-- --------------------------------------------------------
    /**
     * 功能描述:
     * 〈简易下载〉
     * @param bucketName bucketName
     * @param fileName fileName
     * @author 蝉鸣
     */
    byte[] downloadFile(String bucketName, String fileName);

    //-- --------------------------------------------------------
    //        -- 文件删除接口
    //-- --------------------------------------------------------
    /**
     * 功能描述:
     * 〈删除文件〉
     * @param bucketName bucketName
     * @param fileName fileName
     * @author 蝉鸣
     */
    void removeFile(String bucketName, String fileName);

}
