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

	/**
	 * 功能描述: <br>
	 * 〈获取后缀名称〉
	 * @param prefix prefix
	 * @author 蝉鸣
	 */
	public static String getExtension(String prefix) {
		return switch (prefix) {
			case IMAGE_PNG -> "png";
			case IMAGE_JPG -> "jpg";
			case IMAGE_JPEG -> "jpeg";
			case IMAGE_BMP -> "bmp";
			case IMAGE_GIF -> "gif";
			case SUFFIX_XML -> "xml";
			case SUFFIX_JSON -> "json";
			default -> SymbolConst.BLANK;
		};
	}

}
