package com.platform.mesh.upms.biz.modules.doc.file.domain.dto;

import java.time.LocalDateTime;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 文件DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="文件DTO")
public class DocFileDTO extends BaseDTO {



    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;


    /**
     * 文件标识
     */
    @Schema(description = "文件标识")
    private Integer fileFlag;


    /**
     * 文件编码
     */
    @Schema(description = "文件编码")
    private String fileMac;


    /**
     * 文件名称
     */
    @Schema(description = "文件名称")
    private String fileName;


    /**
     * 文件别名
     */
    @Schema(description = "文件别名")
    private String fileAlias;


    /**
     * 文件类型
     */
    @Schema(description = "文件类型")
    private String fileType;


    /**
     * 文件来源
     */
    @Schema(description = "文件来源")
    private String fileSource;


    /**
     * 文件路径
     */
    @Schema(description = "文件路径")
    private String fileEndpoint;


    /**
     * 文件桶
     */
    @Schema(description = "文件桶")
    private String fileBucket;


    /**
     * 文件地址
     */
    @Schema(description = "文件地址")
    private String fileAddr;


    /**
     * 文件批次
     */
    @Schema(description = "文件批次")
    private String fileBatch;


    /**
     * 文件大小
     */
    @Schema(description = "文件大小")
    private Integer fileSize;


    /**
     * 新增标识
     */
    @Schema(description = "新增标识")
    private Integer addFlag;


    /**
     * 关联ID
     */
    @Schema(description = "关联ID")
    private Long relId;


    /**
     * 创建者ID
     */
    @Schema(description = "创建者ID")
    private Long createUserId;


    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;


    /**
     * 更新者ID
     */
    @Schema(description = "更新者ID")
    private Long updateUserId;


    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;


    /**
     * 数据权限用户ID
     */
    @Schema(description = "数据权限用户ID")
    private Long scopeUserId;


    /**
     * 数据权限机构ID
     */
    @Schema(description = "数据权限机构ID")
    private Long scopeOrgId;

}