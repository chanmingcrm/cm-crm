package com.platform.mesh.file.oss.modules.aws;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.file.oss.base.BaseOssClient;
import com.platform.mesh.file.oss.base.common.model.OssUploadBO;
import com.platform.mesh.file.oss.base.common.model.bo.DocFileBO;
import com.platform.mesh.file.oss.base.extend.download.DownloadExtendOssClient;
import com.platform.mesh.file.oss.base.extend.upload.UploadExtendOssClient;
import com.platform.mesh.file.oss.base.extend.upload.constant.UploadExtendConst;
import com.platform.mesh.file.oss.base.extend.upload.model.MultiPartBO;
import com.platform.mesh.file.oss.base.extend.upload.model.UploadPartTask;
import com.platform.mesh.file.oss.base.extend.upload.model.UploadProcess;
import com.platform.mesh.file.oss.base.extend.upload.pack.UploadExtendManage;
import com.platform.mesh.file.oss.constant.OssTypeConst;
import com.platform.mesh.file.oss.modules.aws.properties.AwsOssProperties;
import com.platform.mesh.file.oss.utils.OssFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @description 自定义客户端实现
 * @author 蝉鸣
 */
@RequiredArgsConstructor
public class AwsOssClient implements BaseOssClient, UploadExtendOssClient, DownloadExtendOssClient {

    private final static Logger log = LoggerFactory.getLogger(AwsOssClient.class);

    private final S3Client s3Client;
    private final AwsOssProperties awsOssProperties;


    /**
     * 功能描述:
     * 〈查询所有的桶列表〉
     * @return 正常返回:{@link List<Bucket>}
     * @author 蝉鸣
     */
    @Override
    @SneakyThrows
    public List<Bucket>  getAllBuckets() {
        return s3Client.listBuckets().buckets();
    }

    /**
     * 功能描述:
     * 〈创建桶〉
     * @param bucketName bucketName
     * @author 蝉鸣
     */
    @Override
    @SneakyThrows
    public void createBucket(String bucketName) {
        S3Waiter s3Waiter = s3Client.waiter();
        CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();

        s3Client.createBucket(bucketRequest);
        HeadBucketRequest bucketRequestWait = HeadBucketRequest.builder()
                .bucket(bucketName)
                .build();
        s3Waiter.waitUntilBucketExists(bucketRequestWait);
    }

    /**
     * 功能描述:
     * 〈获取桶信息〉
     * @param bucketName bucketName
     * @return 正常返回:{@link Optional<Bucket>}
     * @author 蝉鸣
     */
    @Override
    public Optional<Bucket> getBucket(String bucketName) {
        return Optional.empty();
    }

    /**
     * 功能描述:
     * 〈删除桶操作〉
     * @param bucketName bucketName
     * @author 蝉鸣
     */
    @Override
    public void removeBucket(String bucketName) {

    }

    /**
     * 功能描述:
     * 〈上传文件〉
     * @param uploadDTO uploadDTO
     * @return 正常返回:{@link DocFileBO}
     * @author 蝉鸣
     */
    @Override
    public DocFileBO upLoadFile(OssUploadBO uploadDTO) {
        return null;
    }

    /**
     * 功能描述:
     * 〈获取文件信息〉
     * @param bucketName bucketName
     * @param fileName fileName
     * @author 蝉鸣
     */
    @Override
    public void getFileInfo(String bucketName, String fileName) {

    }

    /**
     * 功能描述:
     * 〈获取文件URL〉
     * @param bucketName bucketName
     * @param fileName fileName
     * @param expires expires
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    @Override
    public String getFileURL(String bucketName, String fileName, Duration expires) {
        try {
            URI uri = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(fileName).build()).toURI();
            log.info(uri.toString());
            return uri.toURL().toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈下载文件〉
     * @param bucketName bucketName
     * @param fileName fileName
     * @author 蝉鸣
     */
    @Override
    public byte[] downloadFile(String bucketName, String fileName) {
        fileName = fileName.replaceAll(SymbolConst.PATTERN_FORWARD_SLASH, StrUtil.EMPTY);
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        ResponseBytes<GetObjectResponse> objectAsBytes = s3Client.getObjectAsBytes(objectRequest);
        return objectAsBytes.asByteArray();
    }

    /**
     * 功能描述:
     * 〈删除文件〉
     * @param bucketName bucketName
     * @param fileName fileName
     * @author 蝉鸣
     */
    @Override
    public void removeFile(String bucketName, String fileName) {

    }

    /**
     * 功能描述:
     * 〈分片上传文件-前端分片〉
     * @param multiPartBO multiPartBO
     * @author 蝉鸣
     */
    @Override
    public void uploadFileMultiPart(MultiPartBO multiPartBO) {
        //文件操作对象
        UploadExtendManage extendManage = new UploadExtendManage(this);
        //第一种方式直接上传
        extendManage.uploadFileMultiPart(multiPartBO);
    }


    /**
     * 功能描述:
     * 〈分片上传文件-后端分片,上传完直接合并〉
     * @param file file
     * @author 蝉鸣
     */
    @Override
    public DocFileBO uploadFileMultiPart(MultipartFile file) {
        String tempPath = awsOssProperties.getTempPath() + file.getOriginalFilename() + IdUtil.fastSimpleUUID();
        //切割文件
        List<MultiPartBO> multiPartBOList = OssFileUtil.splitUploadFile(file, awsOssProperties.getSliceConfig().getPartSize(),tempPath);
        //第二种方式多线程并发上传
        //初始化线程池
        ExecutorService executorService = Executors.newFixedThreadPool(awsOssProperties.getSliceConfig().getConnectionsNum());
        //初始化线程回调集合
        List<Future<UploadProcess>> futures = CollUtil.newArrayList();
        //多线程上传
        for (MultiPartBO partBO : multiPartBOList) {
            futures.add(executorService.submit(new UploadPartTask(this, partBO)));
        }
        //关闭线程池
        executorService.shutdown();
        //多线程回调可自定义操作
        for (Future<UploadProcess> future : futures) {
            try {
                UploadProcess partResult = future.get();
                String uploadId = partResult.getUploadId();
            } catch (Exception e) {
                throw new BaseException(e);
            }
        }
        UploadExtendConst.UPLOAD_PROCESS_STORAGE.get(file.getOriginalFilename()).setTempPath(tempPath);
        //可自定义添加验证
        log.info("所有分片上传完毕！");
        //可自定义添加验证

        //如果上传完毕则合并
        completeMultipartUpload(file.getOriginalFilename());
        //设置返回值
        DocFileBO docFileBO = OssFileUtil.MultipartFileToDocFile(file);
        docFileBO.setFileSource(OssTypeConst.AWS);
        docFileBO.setFileEndpoint(awsOssProperties.getEndPoint());
        docFileBO.setFileBucket(awsOssProperties.getBucketName());
        docFileBO.setFileAddr(StrUtil.SLASH
                .concat(docFileBO.getFileName()).concat(StrUtil.DOT).concat(docFileBO.getFileType()));
        return docFileBO;
    }

    /**
     * 功能描述:
     * 〈分片上传第一步:初始化上传信息获取唯一UploadId〉
     * @param filename filename
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    @Override
    public String initiateMultipartUpload(String filename) {
        // 初始化分片上传任务
        CreateMultipartUploadResponse createMultipartUploadResponse = s3Client.createMultipartUpload(builder -> builder
                .bucket(awsOssProperties.getBucketName())
                .key(filename));
        // 获取分片上传uploadId
        return createMultipartUploadResponse.uploadId();
    }

    /**
     * 功能描述:
     * 〈分片上传第二步:上传文件信息获取标识Tag〉
     * @param multiPartBO multiPartBO
     * @param uploadId uploadId
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    @Override
    public String uploadMultipart(MultiPartBO multiPartBO, String uploadId) {
        try (InputStream in = multiPartBO.getFile().getInputStream()) {
            // 上传后会将 InputStream 加密,需要重置为ByteArrayInputStream/FileInputStream 才能继续上传
            ByteArrayInputStream fileInputStream = new ByteArrayInputStream(IOUtils.toByteArray(in));
            // 分片上传请求
            UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
                    .bucket(awsOssProperties.getBucketName())
                    .key(multiPartBO.getFilename())
                    .uploadId(uploadId)
                    .partNumber(multiPartBO.getPartNum())
                    .build();
            UploadPartResponse partResponse = s3Client.uploadPart(
                    uploadPartRequest,
                    RequestBody.fromBytes(fileInputStream.readAllBytes()));
            //返回上传文件标记
            return partResponse.eTag();
        } catch (IOException e) {
            log.error("文件【{}】上传分片【{}】失败", multiPartBO.getFilename(), multiPartBO.getPartNum(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈分片上传第三步:根据标识Tag集合合并分片文件〉
     * @param filename filename
     * @author 蝉鸣
     */
    @Override
    public void completeMultipartUpload(String filename) {
        //获取缓存文件上传进度信息
        UploadProcess uploadProcess = UploadExtendConst.UPLOAD_PROCESS_STORAGE.get(filename);
        //获取分片文件标记信息
        List<CompletedPart > partETagList = uploadProcess.getUploadPartList()
                .stream()
                .map(uploadPart -> CompletedPart.builder()
                        .partNumber(uploadPart.getPartNum())
                        .eTag(uploadPart.getUploadAddr())
                        .build())
                .collect(Collectors.toList());
        //创建合并请求对象
        //调用API合并文件
        s3Client.completeMultipartUpload(b -> b
                .bucket(awsOssProperties.getBucketName())
                .key(uploadProcess.getFilename())
                .uploadId(uploadProcess.getUploadId())
                .multipartUpload(CompletedMultipartUpload.builder().parts(partETagList).build()));

        //清除文件上传信息
        UploadExtendConst.UPLOAD_PROCESS_STORAGE.remove(filename);
        //清楚本地临时文件
        FileUtil.del(uploadProcess.getTempPath());
    }
}
