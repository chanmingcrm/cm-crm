package com.platform.mesh.utils.format;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import org.springframework.util.AntPathMatcher;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 工具类
 * @author 蝉鸣
 */
public class FormatUtil {

	/**
	 * 功能描述:
	 * 〈查找指定字符串是否匹配指定字符串列表中的任意一个字符串〉
	 * @param str 指定字符串
	 * @param strList 需要检查的字符串数组
	 * @return 正常返回:{@link boolean} 是否匹配
	 * @author 蝉鸣
	 */
	public static boolean matches(String str, List<String> strList) {
		if (StrUtil.isEmpty(str) || CollUtil.isEmpty(strList)) {
			return false;
		}
		for (String pattern : strList) {
			if (isMatch(pattern, str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 功能描述:
	 * 〈判断url是否与规则配置: ? 表示单个字符; * 表示一层路径内的任意字符串，不可跨层级; ** 表示任意层路径;〉
	 * @param pattern 匹配规则
	 * @param url 需要匹配的url
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	public static boolean isMatch(String pattern, String url) {
		AntPathMatcher matcher = new AntPathMatcher();
		return matcher.match(pattern, url);
	}

	/**
	 * 功能描述:
	 * 〈返回枚举类的说明〉
	 * @param clazz clazz
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	public static <E extends BaseEnum<?, V>, V extends Serializable> String  getEnumDescJSON(Class<E> clazz){
		StringBuilder builder = StrUtil.builder();
		Arrays.stream(clazz.getEnumConstants())
				.forEach(e->{
					JSONObject jsonObject = new JSONObject();
					jsonObject.set(StrConst.VALUE, e.getValue());
					jsonObject.set(StrConst.DESC, e.getDesc());
					builder.append(jsonObject)
							.append(SymbolConst.SPACE)
							.append(SymbolConst.HTML_BR)
							.append(SymbolConst.SPACE);
				});
		String jsonStr = builder.toString();
		jsonStr = jsonStr.replaceAll(SymbolConst.QUOTE, StrUtil.EMPTY);
		jsonStr = jsonStr.replaceAll(SymbolConst.TAB, SymbolConst.NBSP+SymbolConst.NBSP);
		jsonStr = jsonStr.replaceAll(SymbolConst.NEW_LINE, SymbolConst.HTML_BR);
		String enumName = StrUtil.builder().append(SymbolConst.SPACE)
				.append(SymbolConst.SPACE)
				.append(SymbolConst.HTML_BR)
				.append(SymbolConst.SPACE)
				.append(clazz.getSimpleName())
				.append(SymbolConst.COLON)
				.append(SymbolConst.SPACE)
				.append(SymbolConst.HTML_BR)
				.append(SymbolConst.SPACE)
				.toString();
		return StrUtil.builder()
				.append(enumName)
				.append(jsonStr)
				.append(SymbolConst.SPACE)
				.append(SymbolConst.HTML_BR)
				.toString();
	}
	/***
	 * 功能描述:
	 * 〈将下划线转驼峰〉
	 * @param jsonObject jsonObject
	 * @return 正常返回:{@link JSONObject}
	 * @author 蝉鸣
	 * @since 2024/8/29 9:37
	 */
	public static JSONObject convertKeysToCamelCase(JSONObject jsonObject) {
		if (jsonObject == null) {
			return null;
		}

		LinkedHashMap<String, Object> camelCaseMap = new LinkedHashMap<>();
		for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			String camelCaseKey = StrUtil.toCamelCase(key);
			camelCaseMap.put(camelCaseKey, value);
		}

		return JSONUtil.parseObj(JSONUtil.toJsonStr(camelCaseMap));
	}


	/**
	 * 功能描述:
	 * 〈解密加密字符串〉
	 * @param encryptStr encryptStr
	 * @param encryptCode encryptCode
	 * @return 正常返回:{@link Boolean}
	 * @author 蝉鸣
	 */
	public static String decryptStr(String encryptStr, String encryptCode, String prefix) {
		if(ObjectUtil.isEmpty(encryptStr) || ObjectUtil.isEmpty(encryptCode) || ObjectUtil.isEmpty(prefix)){
			return null;
		}
		//base64解密
		String decodeStr = Base64.decodeStr(encryptCode);
		//判断校验码格式是否正确
		if(!decodeStr.startsWith(prefix)){
			return null;
		}
		String replaced = decodeStr.replace(prefix, SymbolConst.BLANK);
		byte[] bytes = replaced.getBytes();
		if (bytes.length < NumberConst.NUM_16) {
			return null;
		}
		byte[] iv = Arrays.copyOfRange(bytes, NumberConst.NUM_0, NumberConst.NUM_16); // 前 16 字节是 IV
		byte[] key = Arrays.copyOfRange(bytes, NumberConst.NUM_16, bytes.length);
		//AES解密密码
		AES aes = new AES(Mode.CBC.name(), "PKCS7Padding", key, iv);
		return aes.decryptStr(encryptStr);
	}
}
