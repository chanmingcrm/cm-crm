package com.platform.mesh.upms.biz.modules.label.value.domain.dto;

import java.time.LocalDateTime;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 标签值DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="标签值DTO")
public class LabelValueDTO extends BaseDTO {



    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;


    /**
     * 字典ID
     */
    @Schema(description = "字典ID")
    private Long labelId;


    /**
     * 字典标识
     */
    @Schema(description = "字典标识")
    private Integer labelFlag;


    /**
     * 字典编码
     */
    @Schema(description = "字典编码")
    private String labelMac;


    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    private String labelName;


    /**
     * 字典值
     */
    @Schema(description = "字典值")
    private String labelValue;


    /**
     * 字典颜色
     */
    @Schema(description = "字典颜色")
    private String labelColor;


    /**
     * 数据类型
     */
    @Schema(description = "数据类型")
    private Integer dataType;


    /**
     * 默认标识
     */
    @Schema(description = "默认标识")
    private Integer defaultFlag;


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