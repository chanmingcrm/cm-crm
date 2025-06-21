package com.platform.mesh.app.biz.modules.app.formcolumnsetaction.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 单字段动作DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="单字段动作DTO")
public class AppFormColumnSetActionDTO extends BaseDTO {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 动作Hash
     */
    @Schema(description = "动作Hash")
    private String actionHash;


    /**
     * 动作名称
     */
    @Schema(description = "动作名称")
    private String actionName;


    /**
     * 动作类型
     */
    @Schema(description = "动作类型")
    private String actionType;

}