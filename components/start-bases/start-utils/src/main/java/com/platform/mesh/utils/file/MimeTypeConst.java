package com.platform.mesh.utils.file;

import com.platform.mesh.core.constants.SymbolConst;

/**
 * @description 媒体类型工具类
 * @author 蝉鸣
 */
public interface MimeTypeConst {

	String IMAGE_PNG = "image/png";

	String IMAGE_JPG = "image/jpg";

	String IMAGE_JPEG = "image/jpeg";

	String IMAGE_BMP = "image/bmp";

	String IMAGE_GIF = "image/gif";

	String IMAGE_TIFF = "image/tiff";

	String IMAGE_WEBP = "image/webp";

	String APPLICATION_PDF = "application/pdf";

	String APPLICATION_MSWORD = "application/msword";

	String APPLICATION_DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

	String APPLICATION_RTF = "application/rtf";

	String APPLICATION_EXCEL = "application/vnd.ms-excel";

	String APPLICATION_EXCEL_X = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	String APPLICATION_POWERPOINT = "application/vnd.ms-powerpoint";

	String APPLICATION_POWERPOINT_X = "application/vnd.openxmlformats-officedocument.presentationml.presentation";

	String TEXT_PLAIN = "text/plain";

	String TEXT_MARKDOWN = "text/markdown";

	String TEXT_RTF = "text/rtf";

	String TEXT_CSV = "text/csv";

	String EXTENSION_JPG = "jpg";

	String EXTENSION_JPEG = "jpeg";

	String EXTENSION_PNG = "png";

	String EXTENSION_PDF = "pdf";

	String EXTENSION_DOC = "doc";

	String EXTENSION_DOCX = "docx";

	String EXTENSION_TXT = "txt";

	String EXTENSION_BMP = "bmp";

	String EXTENSION_TIF = "tif";

	String EXTENSION_TIFF = "tiff";

	String EXTENSION_WEBP = "webp";

	String EXTENSION_MD = "md";

	String EXTENSION_RTF = "rtf";

	String EXTENSION_XLS = "xls";

	String EXTENSION_XLSX = "xlsx";

	String EXTENSION_CSV = "csv";

	String EXTENSION_PPT = "ppt";

	String EXTENSION_PPTX = "pptx";

	String SUFFIX_EXCEL_2003 = ".xls";

	String SUFFIX_EXCEL_2007 = ".xlsx";

	String SUFFIX_JPEG = ".jpg";

	String SUFFIX_XML = ".xml";

	String SUFFIX_PDF = ".pdf";

	String SUFFIX_ZIP = ".zip";

	String SUFFIX_DOC = ".doc";

	String SUFFIX_DOCX = ".docx";

	String SUFFIX_PPT = ".ppt";

	String SUFFIX_PPTX = ".pptx";

	String SUFFIX_EXCEL = ".xls";

	String SUFFIX_EXCEL_X = ".xlsx";

	String SUFFIX_SWF = ".swf";

	String SUFFIX_PROPERTIES = ".properties";

	String SUFFIX_YML = ".yml";

	String SUFFIX_YAML = ".yaml";

	String SUFFIX_JSON = ".json";

	String[] IMAGE_EXTENSION = { "bmp", "gif", "jpg", "jpeg", "png" };

	String[] FLASH_EXTENSION = { "swf", "flv" };

	String[] MEDIA_EXTENSION = { "swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg",
			"asf", "rm", "rmvb" };

	String[] VIDEO_EXTENSION = { "mp4", "avi", "rmvb" };

	String[] DEFAULT_ALLOWED_EXTENSION = {
			// 图片
			"bmp", "gif", "jpg", "jpeg", "png",
			// word excel powerpoint
			"doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt",
			// 压缩文件
			"rar", "zip", "gz", "bz2",
			// 视频格式
			"mp4", "avi", "rmvb",
			// pdf
			"pdf" };
	String REMARK = "注:";

}
