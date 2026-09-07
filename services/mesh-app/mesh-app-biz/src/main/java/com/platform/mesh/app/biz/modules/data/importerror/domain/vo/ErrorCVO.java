package com.platform.mesh.app.biz.modules.data.importerror.domain.vo;

import com.platform.mesh.core.application.domain.dto.QueryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 导入错误DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="导入错误DTO")
public class ErrorCVO extends QueryDTO {

    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 当前查询批次参数
     */
    @Schema(description = "当前查询批次参数")
    private Long batchId;

    /**
     * 行号
     */
    private Integer rowNum;

    /**
     * 错误记录
     */
    private String errorRecord;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
