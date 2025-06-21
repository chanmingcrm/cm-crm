package com.platform.mesh.file.oss.base.extend.upload.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 分片进度
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UploadProcess {
    /**
     * s3 对应的分片上传任务的id
     */
    private String uploadId;
    /**
     * 上传文件名称
     */
    private String filename;
    /**
     * 上传文件临时生成目录:用于文件分割
     */
    private String tempPath;
    /**
     * 分片信息
     */
    private List<UploadPart> uploadPartList = new ArrayList<>();

}
