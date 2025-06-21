package com.platform.mesh.file.oss.base.extend.upload.constant;

import com.platform.mesh.file.oss.base.extend.upload.model.UploadProcess;
import com.platform.mesh.file.oss.constant.OssBaseConst;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @description 上传变量
 * @author 蝉鸣
 */
public class UploadExtendConst {

//-- --------------------------------------------------------
//        -- 上传进度
//-- --------------------------------------------------------

    /**
     * 功能描述:
     * 〈分片进度〉
     * 1:本方式，不需借助其他缓存中间件
     * 2:其他方式:引入redis/mdb等其他缓存方式(未实现)
     */
    public static final Map<String, UploadProcess> UPLOAD_PROCESS_STORAGE = new ConcurrentHashMap<>();



//-- --------------------------------------------------------
//        -- 线程配置
//-- --------------------------------------------------------
    /**
     * 信号量加密
     */
    public static Semaphore uploadSemaphore = new Semaphore(1);


//-- --------------------------------------------------------
//        -- 分片配置
//-- --------------------------------------------------------
    /**
     * 默认分片大小 (最小1M)
     */
    public static Long DEFAULT_PART_SIZE = 2L * OssBaseConst.MB;

    /**
     * 默认最大分片数
     */
    public static Long DEFAULT_PART_NUM = 10L;

    /**
     * 默认缓冲区大小
     */
    public static Integer DEFAULT_BUFFER_SIZE = 2 * OssBaseConst.KB;

    /**
     * 上传文件临时缓冲目录
     */
    public static final String DEFAULT_WINDOW_UPLOAD_PATH = "D:/data/upload/";
    public static final String DEFAULT_LINUX_UPLOAD_PATH = "/data/upload/";


//-- --------------------------------------------------------
//        -- 线程配置
//-- --------------------------------------------------------
    /**
     * 默认并发线程数
     */
    public static Integer DEFAULT_CONNECTIONS_NUM = Runtime.getRuntime().availableProcessors();

    public static Long DEFAULT_CONNECTION_TIMEOUT = TimeUnit.MINUTES.toMillis(5);


//-- --------------------------------------------------------
//        -- 文件权限
//-- --------------------------------------------------------
    /**
     * 只读权限
     */
    public static String READ = "r";
    /**
     * 写权限
     */
    public static String WRITE = "w";
}
