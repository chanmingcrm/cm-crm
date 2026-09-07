package com.platform.mesh.upms.biz.modules.doc.dir.domain.dto;

import java.time.LocalDateTime;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.biz.modules.doc.dir.enums.DocFlagEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 文件目录DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="文件目录DTO")
public class DocDirDTO extends BaseDTO {



    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;


    /**
     * 父ID
     */
    @Schema(description = "父ID")
    private Long parentId;


    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;


    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private Long dataId;


    /**
     * 开放标识
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "开放标识")
    private Integer openFlag;

    /**
     * 目录标识
     */
    @SchemaEnum(value = DocFlagEnum.class, description = "目录标识")
    private Integer dirFlag;


    /**
     * 目录编码
     */
    @Schema(description = "目录编码")
    private String dirMac;


    /**
     * 目录名称
     */
    @Schema(description = "目录名称")
    private String dirName;


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