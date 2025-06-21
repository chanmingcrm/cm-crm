package com.platform.mesh.file.oss.base.extend.upload.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description 分片文件信息
 * @author 蝉鸣
 */
@Data
public class MultiPartBO {
    /**
     * 当前文件块，从1开始
     */
    private Integer partNum;

    /**
     * 分块大小
     */
    private Long partSize;

    /**
     * 偏移量
     */
    private Long OffsetSize;

    /**
     * 当前分块大小
     */
    private Long currentPartSize;

    /**
     * 总大小
     */
    private Long totalSize;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 总块数
     */
    private Long totalParts;

    /**
     * 分块文件内容
     */
    private MultipartFile file;
}
