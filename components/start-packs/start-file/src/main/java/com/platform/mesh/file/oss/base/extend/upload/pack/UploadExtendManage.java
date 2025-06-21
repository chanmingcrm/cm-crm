package com.platform.mesh.file.oss.base.extend.upload.pack;

import com.platform.mesh.file.oss.base.extend.upload.UploadExtendOssClient;
import com.platform.mesh.file.oss.base.extend.upload.constant.UploadExtendConst;
import com.platform.mesh.file.oss.base.extend.upload.model.MultiPartBO;
import com.platform.mesh.file.oss.base.extend.upload.model.UploadPart;
import com.platform.mesh.file.oss.base.extend.upload.model.UploadProcess;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @description 上传操作
 * @author 蝉鸣
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class UploadExtendManage {

    /**
     * OSS客户端
     */
    private UploadExtendOssClient ossClient;

    /**
     * 功能描述:
     * 〈上传文件获取上传进度〉
     * @param multiPartBO multiPartBO
     * @return 正常返回:{@link UploadProcess}
     * @author 蝉鸣
     */
    public UploadProcess uploadFileMultiPart(MultiPartBO multiPartBO) {
        //文件名称
        String filename = multiPartBO.getFilename();
        //文件进度
        UploadProcess uploadProcess;
        //上传uploadId
        String uploadId;
        try {
            //采用信号量加锁
            UploadExtendConst.uploadSemaphore.acquire();
            //如果有缓存文件信息直接获取
            if (UploadExtendConst.UPLOAD_PROCESS_STORAGE.containsKey(filename)) {
                uploadProcess = UploadExtendConst.UPLOAD_PROCESS_STORAGE.get(filename);
                uploadId = uploadProcess.getUploadId();
                //是否上传完毕
                AtomicBoolean isUploaded = new AtomicBoolean(false);
                //校验文件分片与上传分片编号是否一致
                Optional.ofNullable(uploadProcess.getUploadPartList())
                        .ifPresent(uploadPartList ->
                            isUploaded.set(uploadPartList.stream()
                                    .anyMatch(uploadPart ->
                                            Objects.equals(uploadPart.getPartNum(), multiPartBO.getPartNum())
                                    )
                            )
                        );
                //跳过已上传分片
                if (isUploaded.get()) {
                    log.info("文件【{}】分块【{}】已经上传，跳过", multiPartBO.getFilename(), multiPartBO.getPartNum());
                    return uploadProcess;
                }
            } else {
                //如果没有缓存文件信息初始化上传信息
                uploadId = ossClient.initiateMultipartUpload(filename);
                uploadProcess = new UploadProcess().setFilename(filename).setUploadId(uploadId).setTempPath(uploadId);
                UploadExtendConst.UPLOAD_PROCESS_STORAGE.put(filename, uploadProcess);
            }

            //获取已上传信息
            List<UploadPart> uploadPartList = uploadProcess.getUploadPartList();
            //获取上传地址
            String uploadAddr = ossClient.uploadMultipart(multiPartBO, uploadId);
            //添加最新上传信息
            uploadPartList.add(new UploadPart( multiPartBO.getPartNum(),uploadAddr));
            //更新缓存上传信息
            UploadExtendConst.UPLOAD_PROCESS_STORAGE.put(filename, uploadProcess.setUploadPartList(uploadPartList));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            //释放锁
            UploadExtendConst.uploadSemaphore.release();
        }
        //返回上传进度信息
        return uploadProcess;
    }


}
