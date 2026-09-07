package com.platform.mesh.utils.file;

import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.net.URI;

/**
 * @description 文件类型工具类
 * @author 蝉鸣
 */
public class DocFileUtil {

	/**
	 * 功能描述:
	 * 〈获取文件类型〉
	 * 例如: file.txt, 返回: txt
	 * @param file 文件名
	 * @return 正常返回:{@link String} 后缀（不含".")
	 * @author 蝉鸣
	 */
	public static String getFileType(File file) {
		if (null == file) {
			return StringUtils.EMPTY;
		}
		return getFileType(file.getName());
	}

	/**
	 * 功能描述:
	 * 〈获取文件类型〉
	 * 例如: file.txt, 返回: txt
	 * @param fileName 文件名
	 * @return 正常返回:{@link String} 后缀（不含".")
	 * @author 蝉鸣
	 */
	public static String getFileType(String fileName) {
		int separatorIndex = fileName.lastIndexOf(SymbolConst.PERIOD);
		if (separatorIndex < NumberConst.NUM_0) {
			return SymbolConst.BLANK;
		}
		return fileName.substring(separatorIndex + NumberConst.NUM_1).toLowerCase();
	}

	/**
	 * 功能描述:
	 * 〈获取URI〉
	 * @param url url
	 * @return 正常返回:{@link URI}
	 * @author 蝉鸣
	 */
	public static URI getUrlStrToUri(String url) {
		try {
			return new URI(url).toURL().toURI();
		}catch (Exception ignored){
		}
		return null;
	}

	/**
	 * 功能描述:
	 * 〈获取类型转换〉
	 * @param uri uri
	 * @return 正常返回:{@link Resource}
	 * @author 蝉鸣
	 */
	public static Resource getUriToResource(URI uri) {
		try {
			// 直接使用UrlResource包装URI
			return new UrlResource(uri);
		} catch (Exception e) {
			// 处理异常（如URI无效、资源不可访问等）
			throw new RuntimeException("无法将URI转换为Resource", e);
		}
	}

}
