package com.platform.mesh.file.oss.base.common.model;

import lombok.Data;

import java.io.InputStream;

/**
 * @description 文件上传对象
 * @author 蝉鸣
 */
@Data
public class OssUploadBO {

    /**
     * 桶名称
     */
    private String bucketName;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件流
     */
    private InputStream inputStream;
    /**
     * 文件大小
     */
    private Long size;
    /**
     * 文件context类型
     */
    private String contextType;
    /**
     * 是否覆盖
     */
    private Boolean isOverride;

}
