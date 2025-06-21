package com.platform.mesh.tmp.biz.modules.appr.approvaldata.domain.vo;

import java.time.LocalDateTime;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description OA办公审批数据VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="OA办公审批数据VO")
public class OaApprovalDataVO extends BaseVO {



    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 父模块ID
     */
    @Schema(description = "父模块ID")
    private Long parentModuleId;

    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

    /**
     * 新增表单ID
     */
    @Schema(description = "新增表单ID")
    private Long addFormId;

    /**
     * 编辑页面ID
     */
    @Schema(description = "编辑页面ID")
    private Long editFormId;

    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private Long dataId;

    /**
     * 字段ID
     */
    @Schema(description = "字段ID")
    private Long columnId;

    /**
     * 字段标识
     */
    @Schema(description = "字段标识")
    private String columnMac;

    /**
     * 字段名称
     */
    @Schema(description = "字段名称")
    private String columnName;

    /**
     * 数据值
     */
    @Schema(description = "数据值")
    private String dataValue;

    /**
     * 数据类型DataTypeEnum
     */
    @Schema(description = "数据类型DataTypeEnum")
    private Integer dataType;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long scopeUserId;

    /**
     * 组织ID
     */
    @Schema(description = "组织ID")
    private Long scopeOrgId;

}