package com.platform.mesh.file.oss.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.file.oss.base.common.model.bo.DocFileBO;
import com.platform.mesh.file.oss.base.extend.upload.constant.UploadExtendConst;
import com.platform.mesh.file.oss.base.extend.upload.model.MultiPartBO;
import com.platform.mesh.file.oss.exception.FileExceptionEnum;
import com.platform.mesh.utils.file.FileContentTypeEnum;
import com.platform.mesh.utils.file.FileTypeUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @description 文件工具类
 * @author 蝉鸣
 */
public class OssFileUtil {

    private final static Logger log = LoggerFactory.getLogger(OssFileUtil.class);

    /**
     * 功能描述:
     * 〈拆分上传分片〉
     * @param multipartFile 文件大小
     * @param partSize 分片大小
     * @param tempPath tempPath
     * @return 正常返回:{@link List<MultiPartBO>} 所有分片对象
     * @author 蝉鸣
     */
    public static List<MultiPartBO> splitUploadFile(MultipartFile multipartFile, Long partSize,String tempPath){
        List<MultiPartBO> files = CollUtil.newArrayList();
        //multipartFile 转换 File 临时目录
        String tempFileFolder = tempPath + multipartFile.getOriginalFilename() + IdUtil.fastSimpleUUID();;
        //multipartFile 转换 File
        File tempFile = multipartFileToFile(tempFileFolder, multipartFile);
        // 原文件大小
        long totalSize = multipartFile.getSize();
        // 文件分片块数 最后一块大小可能小于 20MB
        long totalParts = (long) Math.ceil(totalSize * NumberConst.NUM_1.doubleValue() / partSize);
        //缓存文件切分分片
        List<File> fileList = splitFile(tempFile,tempPath);
        //封装BO对象
        for (int i = NumberConst.NUM_0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            MultiPartBO part = new MultiPartBO();
            part.setPartNum((i + NumberConst.NUM_1));
            part.setPartSize(file.length());
            part.setOffsetSize(NumberConst.NUM_0.longValue());
            part.setCurrentPartSize(file.length());
            part.setTotalSize(totalSize);
            part.setTotalParts(totalParts);
            part.setFilename(multipartFile.getOriginalFilename());
            MultipartFile toMultipartFile = fileToMultipartFile(file);
            part.setFile(toMultipartFile);
            files.add(part);
        }
        //清楚本地临时文件
        FileUtil.del(tempFileFolder);
        //返回对象
        return files;
    }


    /**
     * 功能描述:
     * 〈将文件分片多个文件〉
     * @param file file
     * @param partFileFolder partFileFolder
     * @return 正常返回:{@link List<File>}
     * @author 蝉鸣
     */
    public static List<File> splitFile(File file,String partFileFolder){
        List<File> files = CollUtil.newArrayList();

        long contentLength = file.length();
        // 每块大小设置为20MB
        long partSize = UploadExtendConst.DEFAULT_PART_SIZE;
        // 文件分片块数 最后一块大小可能小于 20MB
        long partNum = (long) Math.ceil(contentLength * NumberConst.NUM_1.doubleValue() / partSize);
        try (RandomAccessFile raf_read = new RandomAccessFile(file, UploadExtendConst.READ);){
            // 缓冲区
            byte[] b = new byte[UploadExtendConst.DEFAULT_BUFFER_SIZE];
            for (int i = NumberConst.NUM_1; i <= partNum; i++) {
                // 块文件
                String partFilePath = partFileFolder + SymbolConst.FORWARD_SLASH + i;
                File chunkFile = new File(partFilePath);
                //目录是否存在
                dirNotExistsThenCreate(chunkFile);
                // 创建块文件的写对象
                try (RandomAccessFile raf_write = new RandomAccessFile(chunkFile, UploadExtendConst.READ+UploadExtendConst.WRITE)) {
                    int len;
                    while ((len = raf_read.read(b)) != NumberConst.NUM__1) {
                        raf_write.write(b, NumberConst.NUM_0, len);
                        // 如果块文件的大小达到20M 开始写下一块儿  或者已经到了最后一块
                        if (chunkFile.length() >= partSize) {
                            break;
                        }
                    }
                } finally {
                    files.add(chunkFile);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return files;
    }

    /**
     * 功能描述:
     * 〈目录不存在并新建〉
     * @param chunkFile chunkFile
     * @author 蝉鸣
     */
    public static void dirNotExistsThenCreate(File chunkFile){
        // 检查目录是否存在
        if (!chunkFile.getParentFile().exists()) {
            // 创建目录
            boolean created = chunkFile.getParentFile().mkdirs();
            if (created) {
                log.info("目录已创建");
            } else {
                throw FileExceptionEnum.FILE_MKDIR_ERROR.getBaseException();
            }
        }
    }


    /**
     * 功能描述:
     * 〈MultipartFile转File〉
     * @param outFilePath outFilePath
     * @param multiFile multiFile
     * @return 正常返回:{@link File}
     * @author 蝉鸣
     */
    public static File multipartFileToFile(String outFilePath, MultipartFile multiFile) {
        // 获取文件名
        if (null == multiFile) {
            return null;
        }
        String fileName = multiFile.getOriginalFilename();
        if (null == fileName) {
            return null;
        }

        try {
            File toFile;
            InputStream ins;
            ins = multiFile.getInputStream();
            //指定存储路径
            toFile = new File(outFilePath.concat(File.separator).concat(multiFile.getOriginalFilename()));
            //校验是否存在
            dirNotExistsThenCreate(toFile);
            inputStreamToFile(ins, toFile);
            return toFile;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈流文件转File〉
     * @param ins ins
     * @param file file
     * @author 蝉鸣
     */
    private static void inputStreamToFile(InputStream ins, File file) {
        try (OutputStream os = new FileOutputStream(file)) {
            int bytesRead;
            int bytes = 8192;
            byte[] buffer = new byte[bytes];
            while ((bytesRead = ins.read(buffer, 0, bytes)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            ins.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 功能描述:
     * 〈File转MultipartFile〉
     * @param file file
     * @return 正常返回:{@link MultipartFile}
     * @author 蝉鸣
     */
    public static MultipartFile fileToMultipartFile(File file) {
        MultipartFile result = null;
        if (null != file) {
            try (FileInputStream input = new FileInputStream(file)) {
                result = new MockMultipartFile(file.getName().concat("temp"), file.getName(), "text/plain", input);
            } catch (IOException ignore) {
            }
        }
        return result;
    }

    /**
     * 功能描述:
     * 〈File转MultipartFile〉
     * @param file file
     * @return 正常返回:{@link MultipartFile}
     * @author 蝉鸣
     */
    public static DocFileBO MultipartFileToDocFile(MultipartFile file) {
        DocFileBO docFileBO = new DocFileBO();
        String originalFilename = file.getOriginalFilename();
        if(StrUtil.isNotBlank(originalFilename) && originalFilename.contains(SymbolConst.PERIOD)){
            String fileName = originalFilename.substring(NumberConst.NUM_0,originalFilename.lastIndexOf(SymbolConst.PERIOD));
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(SymbolConst.PERIOD) + NumberConst.NUM_1);
            docFileBO.setFileName(fileName);
            docFileBO.setFileAlias(fileName);
            docFileBO.setFileType(suffix);
        }
        docFileBO.setFileSize(file.getSize());
        return docFileBO;
    }

    /**
     * 预览文件
     * @param response HttpServletResponse
     * @param data 数据
     * @throws IOException IOException
     */
    public static void preview(HttpServletResponse response, byte[] data,String fileName) throws IOException {
        if(ArrayUtil.isEmpty(data)){
            return;
        }
        response.reset();
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, StrUtil.builder()
                .append(HttpConst.FILE_NAME_PREFIX).append(SymbolConst.QUOTE)
                .append(fileName).append(SymbolConst.QUOTE)
                .toString());
        String fileType = FileTypeUtil.getFileType(fileName);
        FileContentTypeEnum enumByDesc = BaseEnum.getEnumByDesc(FileContentTypeEnum.class, fileType, FileContentTypeEnum.JPEG);
        response.setContentType(enumByDesc.getType());
        IOUtils.write(data, response.getOutputStream());
    }

    /**
     * 生成zip文件
     * @param response HttpServletResponse
     * @param data 数据
     * @throws IOException IOException
     */
    public static void download(HttpServletResponse response, byte[] data,String fileName) throws IOException {
        if(ArrayUtil.isEmpty(data)){
            return;
        }
        response.reset();
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, StrUtil.builder()
                .append(HttpConst.FILE_NAME_PREFIX).append(SymbolConst.QUOTE)
                .append(fileName).append(SymbolConst.QUOTE)
                .toString());
        response.setContentType(HttpConst.APPLICATION_STREAM_CHARSET);
        IOUtils.write(data, response.getOutputStream());
    }

    /**
     * 功能描述:
     * 〈获取文件流〉
     * @param bytes bytes
     * @return 正常返回:{@link InputStream}
     * @author 蝉鸣
     */
    public static InputStream byteToInputStream(byte[] bytes) {
        try {
            return new ByteArrayInputStream(bytes);
        }
        catch (Exception e) {
            log.error("字节转换异常", e);
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈输入流转输出byte[]〉
     * @param inputStream inputStream
     * @return 正常返回:{@link MultipartFile}
     * @author 蝉鸣
     */
    public static byte[] inToOutByte(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IoUtil.copy(inputStream, outputStream);
        return outputStream.toByteArray();
    }

    /**
     * 功能描述:
     * 〈单一文件压缩输出流〉
     * @param inputStream inputStream
     * @return 正常返回:{@link MultipartFile}
     * @author 蝉鸣
     */
    public static byte[] inToZipOutByte(String fileName,InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        try {
            // 添加到zip
            zip.putNextEntry(new ZipEntry(fileName));
            byte[] buffer = new byte[NumberConst.NUM_1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != NumberConst.NUM__1) {
                zip.write(buffer, NumberConst.NUM_0, bytesRead);
            }
            zip.flush();
            zip.closeEntry();
        }
        catch (IOException ignore) {

        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 功能描述:
     * 〈批量文件压缩输出流〉
     * @param fileBOS fileBOS
     * @return 正常返回:{@link MultipartFile}
     * @author 蝉鸣
     */
    public static byte[] batchToZipOutByte(List<DocFileBO> fileBOS) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (DocFileBO docFileBO : fileBOS) {
            File file = new File(docFileBO.getFileAddr());
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                inToZipOutByte(docFileBO.getFileName(),fileInputStream,zip);
            }catch (Exception ignore) {
            }
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 功能描述:
     * 〈将文件输入流添加到压缩输出流〉
     * @param inputStream inputStream
     * @author 蝉鸣
     */
    public static void inToZipOutByte(String fileName,InputStream inputStream,ZipOutputStream zip) {
        try {
            // 添加到zip
            zip.putNextEntry(new ZipEntry(fileName));
            IoUtil.write(zip, false,inputStream.readAllBytes());
            zip.flush();
            zip.closeEntry();
        }catch (IOException ignore) {
        }
    }

    /**
     * 功能描述:
     * 〈获取本地文件字节〉
     * @param filePath filePath
     * @return 正常返回:{@link byte} 字节数据
     * @author 蝉鸣
     */
    public static byte[] getLocalFile(String filePath) {
        //本地图片
        InputStream inputStream = null;
        Path path = Paths.get(filePath);
        URI uri = path.toUri();
        try{
            inputStream = uri.toURL().openStream();
            return OssFileUtil.inToOutByte(inputStream);
        }catch (Exception e){
            log.debug(e.getMessage());
            return null;
        }finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * 功能描述:
     * 〈获取远程文件字节〉
     * @param url url
     * @return 正常返回:{@link byte} 字节数据
     * @author 蝉鸣
     */
    public static byte[] getRemoteFile(String url) {
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            // 网络地址
            URL urlObj = URI.create(url).toURL();
            URLConnection urlConnection = urlObj.openConnection();
            urlConnection.setConnectTimeout(NumberConst.NUM_10000);
            urlConnection.setReadTimeout(NumberConst.NUM_10000);
            urlConnection.setDoInput(true);
            inputStream = urlConnection.getInputStream();
            return inToOutByte(inputStream);
        }
        catch (Exception e) {
            return null;
        }
        finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(byteArrayOutputStream);
        }
    }
}
