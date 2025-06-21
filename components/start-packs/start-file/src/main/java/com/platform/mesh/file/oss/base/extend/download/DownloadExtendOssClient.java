package com.platform.mesh.file.oss.base.extend.download;

import com.platform.mesh.file.oss.exception.FileExceptionEnum;

import java.io.InputStream;

/**
 * @description 文件下载扩展
 * @author 蝉鸣
 */
public interface DownloadExtendOssClient {

    //-- --------------------------------------------------------
    //        -- 文件断点续传下载
    //-- --------------------------------------------------------


    //-- --------------------------------------------------------
    //        -- 文件分片下载
    //-- --------------------------------------------------------


    /**
     * 功能描述:
     * 〈下载分片〉
     * @param bucket 文件桶
     * @param key 目标文件
     * @param start 文件开始字节
     * @param end 文件结束字节
     * @return 正常返回:{@link InputStream} 此范围的文件流
     * @author 蝉鸣
     */
    default byte[] downloadPart(String bucket, String key, Long start, Long end){
        throw FileExceptionEnum.FILE_DOWN_INVALID.getBaseException();
    }
}
