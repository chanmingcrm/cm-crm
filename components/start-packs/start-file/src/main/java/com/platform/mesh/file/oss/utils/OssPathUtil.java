package com.platform.mesh.file.oss.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.core.constants.SymbolConst;
import lombok.extern.slf4j.Slf4j;

/**
 * @description 文件路径工具类
 * @author 蝉鸣
 */
@Slf4j
public class OssPathUtil {

    /**
     * 功能描述:
     * 〈校验路径〉
     * @param basePath basePath
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public static String valid(String basePath) {
        // 根路径未配置时，默认路径为 /
        if (ObjectUtil.isEmpty(basePath)) {
            basePath = StrUtil.SLASH;
        }
        // 将路径分隔符统一转为 /
        basePath = basePath.replaceAll("\\\\", StrUtil.SLASH).replaceAll("//", StrUtil.SLASH);

        // 将配置默认转为绝对路径
        if (!basePath.startsWith(StrUtil.SLASH)) {
            basePath = StrUtil.SLASH + basePath;
        }
        return validUrl(basePath);
    }

    /**
     * 功能描述:
     * 〈校验路径〉
     * @param urlPath urlPath
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public static String validUrl(String urlPath) {
        // 根路径未配置时，默认路径为 /
        // 将路径分隔符统一转为 /
//        urlPath = urlPath.replaceAll("\\\\", StrUtil.SLASH).replaceAll("//", StrUtil.SLASH);
        if (!urlPath.endsWith(StrUtil.SLASH)) {
            urlPath = urlPath + StrUtil.SLASH;
        }
        return urlPath;
    }

    /**
     * 功能描述:
     * 〈路径转换，将路径分隔符转为统一的 / 分隔〉
     * @param key 路径
     * @param isAbsolute 是否绝对路径  true：绝对路径；false：相对路径
     * @return 正常返回:{@link String} 以 / 为分隔的路径
     * @author 蝉鸣
     */
    public static String convertPath(String key, Boolean isAbsolute) {
        key = key.replaceAll("\\\\", StrUtil.SLASH).replaceAll("//", StrUtil.SLASH);
        if (isAbsolute && !key.startsWith(StrUtil.SLASH)) {
            key = StrUtil.SLASH + key;
        } else if (!isAbsolute && key.startsWith(StrUtil.SLASH)) {
            key = key.replaceFirst(StrUtil.SLASH, SymbolConst.BLANK);
        }
        return key;
    }

    /**
     * 功能描述:
     * 〈获取相对根路径的绝对路径〉
     * @param path 全路径
     * @param basePath 根路径
     * @param isAbsolute 是否绝对路径  true：绝对路径；false：相对路径
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public static String replaceKey(String path, String basePath, Boolean isAbsolute) {
        String newPath;
        if (StrUtil.SLASH.equals(basePath)) {
            newPath = convertPath(path, isAbsolute);
        } else {
            newPath = convertPath(path, isAbsolute).replaceAll(convertPath(basePath, isAbsolute), SymbolConst.BLANK);
        }
        return convertPath(newPath, isAbsolute);
    }
}
