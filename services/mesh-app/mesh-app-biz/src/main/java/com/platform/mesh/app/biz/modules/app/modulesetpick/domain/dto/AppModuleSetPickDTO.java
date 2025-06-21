package com.platform.mesh.app.biz.modules.app.modulesetpick.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 模块分配设置DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="模块分配设置DTO")
public class AppModuleSetPickDTO extends BaseDTO {


    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;


    /**
     * 成员ID
     */
    @Schema(description = "成员ID")
    private Long memberId;

    /**
     * 成员名称
     */
    @Schema(description = "成员名称")
    private String memberName;


    /**
     * 分配类型
     */
    @Schema(description = "分配类型")
    private Integer pickType;


    /**
     * 分配参数值
     */
    @Schema(description = "分配参数值")
    private Integer pickValue;

}