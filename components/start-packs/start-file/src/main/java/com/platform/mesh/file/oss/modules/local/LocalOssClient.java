package com.platform.mesh.file.oss.modules.local;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.file.oss.base.BaseOssClient;
import com.platform.mesh.file.oss.base.common.model.OssUploadBO;
import com.platform.mesh.file.oss.base.common.model.bo.DocFileBO;
import com.platform.mesh.file.oss.base.extend.upload.constant.UploadExtendConst;
import com.platform.mesh.file.oss.base.extend.upload.model.MultiPartBO;
import com.platform.mesh.file.oss.base.extend.upload.model.UploadProcess;
import com.platform.mesh.file.oss.base.extend.upload.pack.UploadExtendManage;
import com.platform.mesh.file.oss.constant.OssBaseConst;
import com.platform.mesh.file.oss.constant.OssTypeConst;
import com.platform.mesh.file.oss.exception.FileExceptionEnum;
import com.platform.mesh.file.oss.modules.local.properties.LocalOssProperties;
import com.platform.mesh.file.oss.utils.OssFileUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.Bucket;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @description 自定义客户端实现
 * @author 蝉鸣
 */
@RequiredArgsConstructor
public class LocalOssClient implements BaseOssClient {

    private final static Logger log = LoggerFactory.getLogger(LocalOssClient.class);

    private final LocalOssProperties localProperties;

    /**
     * 功能描述: 
     * 〈查询所有的桶列表〉
     * @return 正常返回:{@link List<Bucket>}
     * @author 蝉鸣
     */
    @Override
    public List<Bucket>  getAllBuckets() {
        String basePath = localProperties.getBasePath();
        log.info("查询所有的文件列表");
        return CollUtil.newArrayList();
    }

    /**
     * 功能描述:
     * 〈创建桶〉
     * @param bucketName bucketName
     * @author 蝉鸣
     */
    @Override
    public void createBucket(String bucketName) {
        log.info("创建一个文件夹");
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
        //StaticFileSourceConfiguration 通过重写资源处理,配置本地映射可以访问目标资源文件
        //前端访问格式:ip:port/module/prefix/image.png
        //本项目例如：http://192.168.0.8:8080/upms/images/preview/heng.png
        //路径只返回了/prefix/image.png,ip:port/module由前端根据具体拼接
        return OssBaseConst.PREVIEW_IMAGES_PATH.concat(fileName);
    }

    /**
     * 功能描述:
     * 〈下载文件〉
     * @param bucketName bucketName
     * @param fileName fileName
     * @author 蝉鸣
     */
    @Override
    public InputStream downloadFileStream(String bucketName, String fileName) {
        Path filePath = resolveSafePath(bucketName, fileName);
        try {
            return Files.newInputStream(filePath);
        } catch (IOException e) {
            throw new BaseException(e);
        }
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
        String uniqueFileName = OssFileUtil.uniqueFileName(file.getOriginalFilename());
        //初始化上传信息
        String uploadId = initiateMultipartUpload(uniqueFileName);
        //切割文件：文件已经上传本地，其实后边操作都是多余的，只是为了相同步骤而进行
        //手动生成上传进度：其实文件已经上传，也已经分片操作
        UploadProcess uploadProcess = new UploadProcess().setFilename(uniqueFileName).setUploadId(uploadId).setTempPath(uploadId);
        UploadExtendConst.UPLOAD_PROCESS_STORAGE.put(uniqueFileName, uploadProcess);
        //可自定义添加验证
        completeMultipartUpload(uniqueFileName);
        //设置返回值
        DocFileBO docFileBO = OssFileUtil.multipartFileToDocFile(file);
        docFileBO.setFileSource(OssTypeConst.LOCAL);
        docFileBO.setFileEndpoint(localProperties.getBasePath());
        docFileBO.setFileBucket(localProperties.getBasePath());
        docFileBO.setFileAddr(StrPool.SLASH
                .concat(uniqueFileName));
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
        Path tempPath = resolveSafePath(localProperties.getTempPath(), filename + IdUtil.fastSimpleUUID());
        FileUtil.mkdir(tempPath.toString());
        return tempPath.toString();
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
        String chunkFilePath = uploadId + SymbolConst.FORWARD_SLASH + multiPartBO.getPartNum();
        File file = new File(chunkFilePath);
        // 检查目录是否存在
        if (!file.getParentFile().exists()) {
            // 创建目录
            boolean created = file.getParentFile().mkdirs();
            if (created) {
                log.info("目录已创建");
            } else {
                throw FileExceptionEnum.FILE_MKDIR_ERROR.getBaseException();
            }
        }
        try (InputStream in = multiPartBO.getFile().getInputStream();
             OutputStream out = Files.newOutputStream(Paths.get(chunkFilePath))) {
            FileCopyUtils.copy(in, out);
        } catch (IOException e) {
            throw new BaseException(e);
        }
        return chunkFilePath;
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
        if (ObjectUtil.isEmpty(uploadProcess) || ObjectUtil.isEmpty(uploadProcess.getUploadId())){
            return;
        }
        // 需要合并的文件所在的文件夹
        File chunkFolder = new File(uploadProcess.getUploadId());
        // 合并后的文件
        Path mergePath = resolveSafePath(localProperties.getBasePath(), filename);
        //合并操作
        try (BufferedOutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(mergePath))) {

            byte[] bytes = new byte[UploadExtendConst.DEFAULT_BUFFER_SIZE];
            //查询所有的文件
            File[] fileArray = Optional.ofNullable(chunkFolder.listFiles())
                    .orElseThrow(() -> new IllegalArgumentException("Folder is empty"));
            List<File> fileList = Arrays.stream(fileArray).sorted(Comparator.comparing(File::getName)).toList();
            fileList.forEach(file -> {
                try (BufferedInputStream inputStream = new BufferedInputStream(Files.newInputStream(file.toPath()))) {
                    int len;
                    while ((len = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, len);
                    }
                } catch (IOException e) {
                    throw new BaseException(e);
                }
            });
        } catch (IOException e) {
            throw new BaseException(e);
        } finally {
            //删除临时文件
            FileUtil.del(uploadProcess.getTempPath());
            //清除文件上传信息
            UploadExtendConst.UPLOAD_PROCESS_STORAGE.remove(filename);
        }
    }

    /**
     * 功能描述:
     * 〈获取文件路径〉
     * @param basePath basePath
     * @param fileName fileName
     * @author 蝉鸣
     */
    private Path resolveSafePath(String basePath, String fileName) {
        Path base = Paths.get(basePath).toAbsolutePath().normalize();
        String sanitizeName = CharSequenceUtil.removePrefix(fileName, SymbolConst.FORWARD_SLASH);
        Path target = base.resolve(sanitizeName).normalize();
        if (!target.startsWith(base)) {
            throw new IllegalArgumentException("invalid file path");
        }
        return target;
    }
}


