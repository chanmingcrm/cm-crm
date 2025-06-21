package com.platform.mesh.upms.biz.modules.dict.value.domain.dto;

import java.time.LocalDateTime;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 字典值DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="字典值DTO")
public class DictValueDTO extends BaseDTO {



    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;


    /**
     * 字典ID
     */
    @Schema(description = "字典ID")
    private Long dictId;


    /**
     * 字典标识
     */
    @Schema(description = "字典标识")
    private Integer dictFlag;


    /**
     * 字典编码
     */
    @Schema(description = "字典编码")
    private String dictMac;


    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    private String dictName;


    /**
     * 字典值
     */
    @Schema(description = "字典值")
    private String dictValue;


    /**
     * 字典颜色
     */
    @Schema(description = "字典颜色")
    private String dictColor;


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