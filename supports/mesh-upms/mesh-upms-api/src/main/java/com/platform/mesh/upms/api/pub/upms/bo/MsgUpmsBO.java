package com.platform.mesh.upms.api.pub.upms.bo;

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
public class MsgUpmsBO extends BaseBO {

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