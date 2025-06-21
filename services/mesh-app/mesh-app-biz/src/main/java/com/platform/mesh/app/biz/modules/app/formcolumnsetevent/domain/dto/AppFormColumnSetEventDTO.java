package com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.dto;

import cn.hutool.json.JSONObject;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 单字段事件DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="单字段事件DTO")
public class AppFormColumnSetEventDTO extends BaseDTO {


    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;


    /**
     *  动作ID
     */
    @Schema(description = "动作ID")
    private Long actionId;


    /**
     * 动作Hash
     */
    @Schema(description = "动作Hash")
    private String actionHash;


    /**
     *  动作名称
     */
    @Schema(description = "动作名称")
    private String actionName;


    /**
     * 事件Hash
     */
    @Schema(description = "事件Hash")
    private String eventHash;


    /**
     * 事件名称
     */
    @Schema(description = "事件名称")
    private String eventName;


    /**
     * 事件类型
     */
    @Schema(description = "事件类型")
    private String eventType;


    /**
     * 事件流
     */
    @Schema(description = "事件流")
    private JSONObject eventFlow;

}