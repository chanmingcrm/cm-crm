package com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 单字段请求DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="单字段请求DTO")
public class AppFormColumnSetRequireQueryDTO extends PageDTO {


    /**
     *  模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;


    /**
     *  表单ID
     */
    @Schema(description = "表单ID")
    private Long formId;


    /**
     *  字段ID
     */
    @Schema(description = "字段ID")
    private Long columnId;


    /**
     *  动作ID
     */
    @Schema(description = "动作ID")
    private Long actionId;


    /**
     *  事件ID
     */
    @Schema(description = "事件ID")
    private Long eventId;

}