package com.platform.mesh.app.biz.modules.app.formcolumnsetprocess.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 单字段事件VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="单字段事件VO")
public class AppFormColumnSetProcessVO extends BaseVO {



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
     *  actionHash
     */
    @Schema(description = "actionHash")
    private String actionHash;


    /**
     *  动作名称
     */
    @Schema(description = "动作名称")
    private String actionName;


    /**
     * 事件Id
     */
    @Schema(description = "事件Id")
    private Long eventId;


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
     * 流程模板Id
     */
    @Schema(description = "流程模板Id")
    private Long tempProcessId;


    /**
     * 流程模板版本
     */
    @Schema(description = "流程模板版本")
    private String tempProcessVersion;

}