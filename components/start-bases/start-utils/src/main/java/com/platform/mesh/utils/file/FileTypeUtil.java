package com.platform.mesh.utils.file;

import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * @description 文件类型工具类
 * @author 蝉鸣
 */
public class FileTypeUtil {

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
	 * 〈获取文件类型〉
	 * @param photoByte 文件字节码
	 * @return 正常返回:{@link String} 后缀（不含".")
	 * @author 蝉鸣
	 */
	public static String getFileExtendName(byte[] photoByte) {
		String strFileExtendName = MimeTypeConst.getExtension(MimeTypeConst.IMAGE_JPG);
		boolean conditionGif = (photoByte[NumberConst.NUM_0] == NumberConst.NUM_71)
				&& (photoByte[NumberConst.NUM_1] == NumberConst.NUM_73)
				&& (photoByte[NumberConst.NUM_2] == NumberConst.NUM_70)
				&& (photoByte[NumberConst.NUM_3] == NumberConst.NUM_56)
				&& ((photoByte[NumberConst.NUM_4] == NumberConst.NUM_55) || (photoByte[NumberConst.NUM_4] == NumberConst.NUM_57))
				&& (photoByte[NumberConst.NUM_5] == NumberConst.NUM_97);
		boolean conditionJpg = (photoByte[NumberConst.NUM_6] == NumberConst.NUM_74)
				&& (photoByte[NumberConst.NUM_7] == NumberConst.NUM_70)
				&& (photoByte[NumberConst.NUM_8] == NumberConst.NUM_73)
				&& (photoByte[NumberConst.NUM_9] == NumberConst.NUM_70);
		boolean conditionBmp = (photoByte[NumberConst.NUM_0] == NumberConst.NUM_66)
				&& (photoByte[NumberConst.NUM_1] == NumberConst.NUM_77);
		boolean conditionPng =(photoByte[NumberConst.NUM_1] == NumberConst.NUM_80)
				&& (photoByte[NumberConst.NUM_2] == NumberConst.NUM_78)
				&& (photoByte[NumberConst.NUM_3] == NumberConst.NUM_71);
		if (conditionGif) {
			strFileExtendName = MimeTypeConst.getExtension(MimeTypeConst.IMAGE_GIF);
		}
		else if (conditionJpg) {
			strFileExtendName = MimeTypeConst.getExtension(MimeTypeConst.IMAGE_JPG);
		}
		else if (conditionBmp) {
			strFileExtendName = MimeTypeConst.getExtension(MimeTypeConst.IMAGE_BMP);
		}
		else if (conditionPng) {
			strFileExtendName = MimeTypeConst.getExtension(MimeTypeConst.IMAGE_PNG);
		}
		return strFileExtendName;
	}

}
