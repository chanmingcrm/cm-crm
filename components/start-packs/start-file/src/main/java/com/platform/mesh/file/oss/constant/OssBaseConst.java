package com.platform.mesh.file.oss.constant;

/**
 * @description OSS基本变量
 * @author 蝉鸣
 */
public interface OssBaseConst {

//-- --------------------------------------------------------
//        -- 默认配置字段
//-- --------------------------------------------------------
    String OSS = "oss";

//-- --------------------------------------------------------
//        -- 默认文件字段
//-- --------------------------------------------------------
    String FILE = "F";
    String DIRECTORY = "D";

//-- --------------------------------------------------------
//        -- 默认数值
//-- --------------------------------------------------------
    Integer KB = 1024;

    Integer MB = 1024 * KB;

//-- --------------------------------------------------------
//        -- 静态文件访问地址
//--
    String PREVIEW_IMAGES_PATH = "/images/preview";

    String PATTERN_PREVIEW_IMAGES_PATH = PREVIEW_IMAGES_PATH+"/**";

    String LOCALE_FILE = "file:";

}
