package com.platform.mesh.app.api.modules.pub.type.app.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @description 应用消息BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="应用消息BO")
public class MsgAppBO extends BaseBO {

    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

    /**
     * 模块空间
     */
    @Schema(description = "模块空间")
    private String moduleSchema;

    /**
     * 模块索引
     */
    @Schema(description = "模块索引")
    private String moduleIndex;

    /**
     * 事件类型
     */
    @Schema(description = "事件类型")
    private Integer actionType;

    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private Long dataId;

    /**
     * 扩展数据
     */
    @Schema(description = "扩展数据")
    private Map<String,Object> extendJson;

}