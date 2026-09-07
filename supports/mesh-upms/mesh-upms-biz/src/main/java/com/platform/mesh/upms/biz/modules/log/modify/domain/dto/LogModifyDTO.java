package com.platform.mesh.upms.biz.modules.log.modify.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 日志(LogModify)DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="日志")
public class LogModifyDTO extends BaseDTO {

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
     * 批次ID
     */
    @Schema(description = "批次ID")
    private Long batchId;

    /**
     * 字段名称
     */
    @Schema(description = "字段名称")
    private String keyName;

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
     * 操作类型
     */
    @SchemaEnum(value = OperateTypeEnum.class, description = "操作类型")
    private Integer operateType;

}
