package com.platform.mesh.file.oss.base.extend.upload.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 分片文件上传回调信息
 * @author 蝉鸣
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadPart {

    /**
     * 分片上传编号
     */
    private Integer partNum;
    /**
     * 分片上传结果地址
     * 使用一个类来表示每个分片保存的信息
     * 对于本地文件系统 保存每块分片保存的路径
     * 对于AWS S3记录每个分片的eTag信息
     */
    private String uploadAddr;
}
