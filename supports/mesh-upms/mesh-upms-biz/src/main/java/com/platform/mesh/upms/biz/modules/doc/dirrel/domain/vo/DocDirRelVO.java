package com.platform.mesh.upms.biz.modules.doc.dirrel.domain.vo;

import java.time.LocalDateTime;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 文件VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="文件VO")
public class DocDirRelVO extends BaseVO {



    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;


    /**
     * 目录ID
     */
    @Schema(description = "目录ID")
    private Long dirId;


    /**
     * 文件ID
     */
    @Schema(description = "文件ID")
    private Long fileId;


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