package com.platform.mesh.file.oss.modules.aws.model;

import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.file.oss.base.extend.upload.constant.UploadExtendConst;
import com.platform.mesh.file.oss.base.extend.upload.model.SliceConfig;
import com.platform.mesh.file.oss.utils.OssPathUtil;
import com.platform.mesh.utils.spring.SysOSUtil;
import lombok.Data;

/**
 * @description Oss节点信息
 * @author 蝉鸣
 */
@Data
public class AwsOssClientBaseConfig {

    /**
     * 自定义oss标识参数
     */
    private String ossNode;
    private String basePath;
    private String tempPath;
    /**
     * oss基础通用配置
     */
    private String endPoint;
    private String region;
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String domain;
    /**
     * 额外配置参数
     */
    private AwsOssClientExtendConfig extendConfig = new AwsOssClientExtendConfig();
    /**
     * 断点续传参数
     */
    private SliceConfig sliceConfig = new SliceConfig();

    /**
     * 初始化参数
     */
    public void init() {
        this.sliceConfig.init();
        basePath = OssPathUtil.valid(basePath);
        if(ObjectUtil.isEmpty(tempPath)){
            if(SysOSUtil.isWindows()){
                tempPath = UploadExtendConst.DEFAULT_WINDOW_UPLOAD_PATH;
            } else if (SysOSUtil.isLinux()) {
                tempPath = UploadExtendConst.DEFAULT_LINUX_UPLOAD_PATH;
            }
        }
        tempPath = OssPathUtil.valid(tempPath);
    }
}
