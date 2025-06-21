package com.platform.mesh.file.oss.base.extend.upload.model;

import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.file.oss.base.extend.upload.UploadExtendOssClient;
import com.platform.mesh.file.oss.base.extend.upload.pack.UploadExtendManage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * @description 多线程上传任务
 * @author 蝉鸣
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadPartTask implements Callable<UploadProcess> {

    private final static Logger log = LoggerFactory.getLogger(UploadPartTask.class);

    /**
     * OSS客户端
     */
    private UploadExtendOssClient ossClient;
    /**
     * 断点续传对象
     */
    private MultiPartBO multipartBO;

    @Override
    public UploadProcess call() {
        //获取分片上传返回信息
        UploadExtendManage extendManage = new UploadExtendManage(ossClient);
        //获取分片上传返回信息
        UploadProcess uploadProcess = extendManage.uploadFileMultiPart(multipartBO);
        if (ObjectUtil.isNotEmpty(uploadProcess)) {
            //其他自定义操作
            log.info(uploadProcess.toString());
        }
        return uploadProcess;
    }
}
