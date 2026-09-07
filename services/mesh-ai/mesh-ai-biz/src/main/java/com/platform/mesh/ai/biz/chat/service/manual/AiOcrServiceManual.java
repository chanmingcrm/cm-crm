package com.platform.mesh.ai.biz.chat.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONNull;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.ai.dashscope.chat.MessageFormat;
import com.alibaba.cloud.ai.dashscope.common.DashScopeApiConstants;
import com.platform.mesh.ai.biz.chat.constant.AiChatConst;
import com.platform.mesh.ai.biz.chat.domain.dto.AiFieldDTO;
import com.platform.mesh.ai.biz.chat.exception.AiChatExceptionEnum;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.doc.domain.vo.DocFileVO;
import com.platform.mesh.upms.api.modules.doc.feign.RemoteDocService;
import com.platform.mesh.utils.file.MimeTypeConst;
import org.apache.tika.Tika;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.content.Media;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description OCR文件解析与消息构建
 * @author 蝉鸣
 */
@Service
public class AiOcrServiceManual {

    private static final Set<String> OCR_FILE_EXTENSIONS = Set.of(
            MimeTypeConst.EXTENSION_JPG, MimeTypeConst.EXTENSION_JPEG, MimeTypeConst.EXTENSION_PNG,
            MimeTypeConst.EXTENSION_BMP, MimeTypeConst.EXTENSION_TIF, MimeTypeConst.EXTENSION_TIFF,
            MimeTypeConst.EXTENSION_WEBP,
            MimeTypeConst.EXTENSION_PDF, MimeTypeConst.EXTENSION_DOC, MimeTypeConst.EXTENSION_DOCX,
            MimeTypeConst.EXTENSION_TXT, MimeTypeConst.EXTENSION_MD, MimeTypeConst.EXTENSION_RTF,
            MimeTypeConst.EXTENSION_XLS, MimeTypeConst.EXTENSION_XLSX, MimeTypeConst.EXTENSION_CSV,
            MimeTypeConst.EXTENSION_PPT, MimeTypeConst.EXTENSION_PPTX
    );
    private static final Set<String> OCR_IMAGE_EXTENSIONS = Set.of(
            MimeTypeConst.EXTENSION_JPG, MimeTypeConst.EXTENSION_JPEG, MimeTypeConst.EXTENSION_PNG,
            MimeTypeConst.EXTENSION_BMP, MimeTypeConst.EXTENSION_TIF, MimeTypeConst.EXTENSION_TIFF,
            MimeTypeConst.EXTENSION_WEBP
    );
    private static final Set<String> OCR_WORD_MIME_TYPES = Set.of(
            MimeTypeConst.APPLICATION_MSWORD, MimeTypeConst.APPLICATION_DOCX
    );

    private final Tika tika = new Tika();

    @Autowired
    private RemoteDocService remoteDocService;

    /**
     * 功能描述:
     * 〈获取OCR消息体〉
     * @param fieldDTO OCR请求参数
     * @return 正常返回:{@link List<Message>}
     * @author 蝉鸣
     */
    public List<Message> genOcrMessagesWithField(AiFieldDTO fieldDTO) {
        // 获取并解析当前用户可访问的OCR文件
        List<OcrFileContent> fileContents = getOcrFileContents(fieldDTO.getFileIds());
        // 获取图片文件对应的多模态媒体信息
        List<Media> mediaList = fileContents.stream()
                .map(OcrFileContent::media)
                .filter(Objects::nonNull)
                .toList();
        // 拼接数据库提示词与字段映射
        String ocrPrompt = buildOcrPrompt(fieldDTO.getPrompt(), fieldDTO.getColumnMap());
        // 拼接数据库提示词与字段映射
        StringBuilder prompt = new StringBuilder(ocrPrompt);
        // 将PDF、Word文档解析结果追加到提示词
        for (OcrFileContent fileContent : fileContents) {
            //将文档解析文本追加到提示词
            appendDocumentText(prompt, fileContent);
        }
        // 构建包含提示词、文档文本和图片媒体的用户消息
        UserMessage userMessage = UserMessage.builder()
                .text(prompt.toString())
                .media(mediaList)
                .metadata(new HashMap<>())
                .build();
        // 设置DashScope多模态消息格式
        userMessage.getMetadata().put(DashScopeApiConstants.MESSAGE_FORMAT, MessageFormat.IMAGE);
        return CollUtil.newArrayList(userMessage);
    }

    /**
     * 功能描述:
     * 〈拼接数据库提示词与字段映射〉
     * @param prompt 数据库提示词
     * @param columnMap 字段映射
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public String buildOcrPrompt(String prompt, Map<String, String> columnMap) {
        return String.format(AiChatConst.OCR_PROMPT_FORMAT, prompt, JSONUtil.toJsonStr(columnMap));
    }

    /**
     * 功能描述:
     * 〈从模型回答中提取并解析JSON对象〉
     * @param responseText 模型回答
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    public Map<String, Object> parseOcrResult(String responseText) {
        if (StrUtil.isBlank(responseText)) {
            throw AiChatExceptionEnum.OCR_RESULT_FORMAT_INVALID.getBaseException();
        }
        int startIndex = responseText.indexOf(AiChatConst.JSON_OBJECT_START);
        if (startIndex < NumberConst.NUM_0) {
            throw AiChatExceptionEnum.OCR_RESULT_FORMAT_INVALID.getBaseException();
        }
        int level = NumberConst.NUM_0;
        boolean stringValue = false;
        boolean escaped = false;
        for (int index = startIndex; index < responseText.length(); index++) {
            char current = responseText.charAt(index);
            if (stringValue) {
                if (escaped) {
                    escaped = false;
                } else if (current == AiChatConst.JSON_ESCAPE_MARK) {
                    escaped = true;
                } else if (current == AiChatConst.JSON_STRING_MARK) {
                    stringValue = false;
                }
                continue;
            }
            if (current == AiChatConst.JSON_STRING_MARK) {
                stringValue = true;
            } else if (current == AiChatConst.JSON_OBJECT_START) {
                level++;
            } else if (current == AiChatConst.JSON_OBJECT_END && --level == NumberConst.NUM_0) {
                try {
                    JSONObject jsonObject = JSONUtil.parseObj(
                            responseText.substring(startIndex, index + NumberConst.NUM_1));
                    Map<String, Object> result = new LinkedHashMap<>();
                    jsonObject.forEach((key, value) -> result.put(key,
                            JSONNull.NULL.equals(value) ? null : value));
                    return result;
                } catch (Exception exception) {
                    throw AiChatExceptionEnum.OCR_RESULT_FORMAT_INVALID.getBaseException(
                            AiChatExceptionEnum.OCR_RESULT_FORMAT_INVALID.getDesc(), exception);
                }
            }
        }
        throw AiChatExceptionEnum.OCR_RESULT_FORMAT_INVALID.getBaseException();
    }

    /**
     * 功能描述:
     * 〈将文档解析文本追加到提示词〉
     * @param prompt 提示词
     * @param fileContent 文件解析结果
     * @author 蝉鸣
     */
    private void appendDocumentText(StringBuilder prompt, OcrFileContent fileContent) {
        if (StrUtil.isBlank(fileContent.text())) {
            return;
        }
        prompt.append(String.format(AiChatConst.OCR_FILE_TEXT_FORMAT,
                fileContent.fileName(), fileContent.text()));
    }

    /**
     * 功能描述:
     * 〈获取并解析用户可访问的OCR文件〉
     * @param fileIds 文件ID集合
     * @return 正常返回:{@link List<OcrFileContent>}
     * @author 蝉鸣
     */
    private List<OcrFileContent> getOcrFileContents(List<Long> fileIds) {
        // 校验文件ID集合
        validateOcrFileIds(fileIds);
        // 批量获取文件信息
        List<DocFileVO> fileVOS = remoteDocService.getDocFiles(fileIds).getData();
        if (CollUtil.isEmpty(fileVOS)) {
            throw AiChatExceptionEnum.OCR_FILE_INFO_ERROR.getBaseException();
        }
        // 按文件ID构建索引并校验文件完整性
        Map<Long, DocFileVO> fileMap = fileVOS.stream()
                .filter(Objects::nonNull)
                .filter(file -> Objects.nonNull(file.getId()))
                .collect(Collectors.toMap(DocFileVO::getId, file -> file, (left, right) -> left));
        if (!fileMap.keySet().containsAll(fileIds)) {
            throw AiChatExceptionEnum.OCR_FILE_NOT_FOUND.getBaseException();
        }
        // 获取当前用户并逐个校验文件权限、解析文件内容
        Long userId = UserCacheUtil.getUserId();
        return fileIds.stream().map(fileId -> {
            DocFileVO file = fileMap.get(fileId);
            if (!Objects.equals(userId, file.getCreateUserId())) {
                throw AiChatExceptionEnum.OCR_FILE_NO_PERMISSION.getBaseException();
            }
            return parseOcrFile(file);
        }).toList();
    }

    /**
     * 功能描述:
     * 〈按文件类型解析图片媒体或文档文本〉
     * @param file 文件信息
     * @return 正常返回:{@link OcrFileContent}
     * @author 蝉鸣
     */
    private OcrFileContent parseOcrFile(DocFileVO file) {
        // 获取文件名称
        String fileName = getOcrFileName(file);
        // 获取文件扩展名
        String extension = getFileExtension(fileName);
        if (!OCR_FILE_EXTENSIONS.contains(extension)) {
            throw AiChatExceptionEnum.OCR_FILE_TYPE_NOT_SUPPORTED.getBaseException();
        }
        // 获取远程文件资源
        UrlResource urlResource = getOcrFileResource(file, fileName);
        // 读取远程文件资源
        String mimeType = detectOcrMimeType(urlResource,fileName);
        // 识别文件真实类型并与扩展名交叉校验
        // 校验扩展名与文件真实类型
        validateOcrMimeType(extension, mimeType);
        // 图片文件作为多模态媒体返回
        if (OCR_IMAGE_EXTENSIONS.contains(extension)) {
            try {
                return new OcrFileContent(fileName,null,new Media(MimeType.valueOf(mimeType),urlResource.getURI()));
            }
            catch (IOException exception) {
                throw AiChatExceptionEnum.OCR_FILE_URL_INVALID.getBaseException(exception);
            }
        }
        // PDF、Word文件通过Tika解析为文本
        List<Document> documents;
        try {
            documents = new TikaDocumentReader(urlResource).get();
        }catch (RuntimeException exception) {
            throw AiChatExceptionEnum.OCR_FILE_READ_ERROR
                    .getBaseException(exception);
        }
        String text = documents.stream()
                .filter(Objects::nonNull)
                .map(Document::getText)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.joining(System.lineSeparator()));
        if (StrUtil.isBlank(text)) {
            throw AiChatExceptionEnum.OCR_DOCUMENT_EMPTY.getBaseException();
        }
        if (text.length() > AiChatConst.OCR_MAX_TEXT_CHARS) {
            text = text.substring(NumberConst.NUM_0,AiChatConst.OCR_MAX_TEXT_CHARS);
        }
        return new OcrFileContent(fileName, text, null);
    }

    /**
     * 功能描述:
     * 〈校验文件ID数量、空值和重复值〉
     * @param fileIds 文件ID集合
     * @author 蝉鸣
     */
    private void validateOcrFileIds(List<Long> fileIds) {
        if (CollUtil.isEmpty(fileIds) || fileIds.size() > AiChatConst.OCR_MAX_FILES) {
            throw AiChatExceptionEnum.OCR_FILE_COUNT_INVALID.getBaseException();
        }
        if (fileIds.stream().anyMatch(Objects::isNull) || new HashSet<>(fileIds).size() != fileIds.size()) {
            throw AiChatExceptionEnum.OCR_FILE_ID_INVALID.getBaseException();
        }
    }

    /**
     * 功能描述:
     * 〈获取带扩展名的文件名称〉
     * @param file 文件信息
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    private String getOcrFileName(DocFileVO file) {
        String fileName = StrUtil.trim(file.getFileName());
        String fileType = StrUtil.trim(file.getFileType());
        if (StrUtil.isBlank(fileType)) {
            return StrUtil.blankToDefault(fileName, SymbolConst.BLANK);
        }
        String suffix = SymbolConst.PERIOD + fileType.toLowerCase(Locale.ROOT);
        return StrUtil.blankToDefault(fileName, SymbolConst.BLANK).toLowerCase(Locale.ROOT).endsWith(suffix)
                ? fileName : StrUtil.blankToDefault(fileName, SymbolConst.BLANK) + suffix;
    }

    /**
     * 功能描述:
     * 〈获取远程文件资源〉
     * @param file 文件信息
     * @param fileName 文件名称
     * @return 正常返回:{@link UrlResource}
     * @author 蝉鸣
     */
    private UrlResource getOcrFileResource(DocFileVO file, String fileName) {
        try {
            return new UrlResource(file.getFileUrl());
        } catch (MalformedURLException ex) {
            throw AiChatExceptionEnum.OCR_FILE_URL_INVALID.getBaseException(ex);
        }
    }

    /**
     * 功能描述:
     * 〈读取文件并限制最大尺寸〉
     * @param resource 文件资源
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    private String detectOcrMimeType(UrlResource resource,String fileName) {
        try (InputStream inputStream = resource.getInputStream()) {
            return tika.detect(inputStream, fileName);
        }
        catch (IOException exception) {
            throw AiChatExceptionEnum.OCR_FILE_READ_ERROR
                    .getBaseException(exception);
        }
    }

    /**
     * 功能描述:
     * 〈校验扩展名与文件真实类型〉
     * @param extension 文件扩展名
     * @param mimeType 文件真实类型
     * @author 蝉鸣
     */
    private void validateOcrMimeType(String extension, String mimeType) {
        boolean valid = switch (extension) {
            case MimeTypeConst.EXTENSION_JPG, MimeTypeConst.EXTENSION_JPEG -> MimeTypeConst.IMAGE_JPEG.equals(mimeType);
            case MimeTypeConst.EXTENSION_PNG -> MimeTypeConst.IMAGE_PNG.equals(mimeType);
            case MimeTypeConst.EXTENSION_BMP -> MimeTypeConst.IMAGE_BMP.equals(mimeType);
            case MimeTypeConst.EXTENSION_TIF, MimeTypeConst.EXTENSION_TIFF -> MimeTypeConst.IMAGE_TIFF.equals(mimeType);
            case MimeTypeConst.EXTENSION_WEBP -> MimeTypeConst.IMAGE_WEBP.equals(mimeType);
            case MimeTypeConst.EXTENSION_PDF -> MimeTypeConst.APPLICATION_PDF.equals(mimeType);
            case MimeTypeConst.EXTENSION_DOC, MimeTypeConst.EXTENSION_DOCX -> OCR_WORD_MIME_TYPES.contains(mimeType);
            case MimeTypeConst.EXTENSION_TXT -> MimeTypeConst.TEXT_PLAIN.equals(mimeType);
            case MimeTypeConst.EXTENSION_MD -> Set.of(MimeTypeConst.TEXT_MARKDOWN,
                    MimeTypeConst.TEXT_PLAIN).contains(mimeType);
            case MimeTypeConst.EXTENSION_RTF -> Set.of(MimeTypeConst.APPLICATION_RTF,
                    MimeTypeConst.TEXT_RTF).contains(mimeType);
            case MimeTypeConst.EXTENSION_XLS -> MimeTypeConst.APPLICATION_EXCEL.equals(mimeType);
            case MimeTypeConst.EXTENSION_XLSX -> MimeTypeConst.APPLICATION_EXCEL_X.equals(mimeType);
            case MimeTypeConst.EXTENSION_CSV -> Set.of(MimeTypeConst.TEXT_CSV,
                    MimeTypeConst.TEXT_PLAIN).contains(mimeType);
            case MimeTypeConst.EXTENSION_PPT -> MimeTypeConst.APPLICATION_POWERPOINT.equals(mimeType);
            case MimeTypeConst.EXTENSION_PPTX -> MimeTypeConst.APPLICATION_POWERPOINT_X.equals(mimeType);
            default -> false;
        };
        if (!valid) {
            throw AiChatExceptionEnum.OCR_FILE_MIME_INVALID.getBaseException();
        }
    }

    /**
     * 功能描述:
     * 〈获取文件扩展名〉
     * @param fileName 文件名称
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    private String getFileExtension(String fileName) {
        int index = StrUtil.blankToDefault(fileName, SymbolConst.BLANK).lastIndexOf(SymbolConst.PERIOD);
        return index < NumberConst.NUM_0 ? SymbolConst.BLANK : fileName.substring(index + NumberConst.NUM_1).toLowerCase(Locale.ROOT);
    }

    /**
     * OCR文件解析结果
     * @param fileName 文件名称
     * @param text 文档文本
     * @param media 图片媒体
     */
    private record OcrFileContent(String fileName, String text, Media media) {
    }
}
