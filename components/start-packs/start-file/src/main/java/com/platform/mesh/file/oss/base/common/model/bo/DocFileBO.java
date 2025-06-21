package com.platform.mesh.file.oss.base.common.model.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 文件BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class DocFileBO extends BaseBO {


    /**
     * 主键ID
     */
    private Long id;


    /**
     * 文件标识
     */
    private Integer fileFlag;


    /**
     * 文件编码
     */
    private String fileMac;


    /**
     * 文件名称
     */
    private String fileName;


    /**
     * 文件别名
     */
    private String fileAlias;


    /**
     * 文件类型
     */
    private String fileType;


    /**
     * 文件来源
     */
    private String fileSource;


    /**
     * 文件路径
     */
    private String fileEndpoint;


    /**
     * 文件桶
     */
    private String fileBucket;


    /**
     * 文件地址
     */
    private String fileAddr;

    /**
     * 文件批次
     */
    private String fileBatch;


    /**
     * 文件大小
     */
    private Long fileSize;

}