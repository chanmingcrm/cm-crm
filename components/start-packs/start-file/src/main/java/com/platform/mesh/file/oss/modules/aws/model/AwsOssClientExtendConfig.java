package com.platform.mesh.file.oss.modules.aws.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description 上传扩展参数
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
public class AwsOssClientExtendConfig {

    private Boolean accelerateModeEnabled = false;

    private Boolean checksumValidationEnabled = false;

    private Boolean multiRegionEnabled = false;

    private Boolean chunkedEncodingEnabled = false;

    private Boolean pathStyleAccessEnabled = false;

    private Boolean useArnRegionEnabled = false;

    private Boolean fipsEnabled = false;

    private Boolean dualstackEnabled = false;

}
