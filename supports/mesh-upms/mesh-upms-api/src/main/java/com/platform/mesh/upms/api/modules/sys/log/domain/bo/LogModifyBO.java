package com.platform.mesh.upms.api.modules.sys.log.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 修改日志(LogModify)实体类
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "修改记录")
public class LogModifyBO extends BaseBO {

    /**
     * 模块ID
     */
    @Schema(description = "用户ID")
    private Long moduleId;

    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private Long dataId;

    /**
     * 批次ID
     */
    @Schema(description = "批次ID")
    private Long batchId;

    /**
     * 字段名称
     */
    @Schema(description = "字段名称")
    private String fieldName;

    /**
     * 原始值
     */
    @Schema(description = "原始值")
    private String valueOld;

    /**
     * 变更值
     */
    @Schema(description = "变更值")
    private String valueNew;

    /**
     * 数据类型
     */
    @Schema(description = "数据类型")
    private Integer dataType;

    /**
    * 创建时间
    */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}

