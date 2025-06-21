package com.platform.mesh.file.oss.base.extend.upload.model;

import com.platform.mesh.file.oss.base.extend.upload.constant.UploadExtendConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @description 断点续传参数
 * @author 蝉鸣
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SliceConfig {
    /**
     * 分片大小,默认5MB
     */
    private Long partSize = UploadExtendConst.DEFAULT_PART_SIZE;

    /**
     * 并发线程数,默认等于CPU的核数
     */
    private Integer connectionsNum = UploadExtendConst.DEFAULT_CONNECTIONS_NUM;

    public void init() {
        if (this.getPartSize() <= 0) {
            log.warn("断点续传——分片大小必须大于0");
            this.setPartSize(UploadExtendConst.DEFAULT_PART_SIZE);
        }
        if (this.getConnectionsNum() <= 0) {
            log.warn("断点续传——并发线程数必须大于0");
            this.setConnectionsNum(UploadExtendConst.DEFAULT_CONNECTIONS_NUM);
        }
    }
}
