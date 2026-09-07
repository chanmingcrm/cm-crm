package com.platform.mesh.message.jpush.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="极光推送BO对象")
public class JPushBO {

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 消息体
     */
    @Schema(description = "消息体")
    private String content;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private String badge;

    /**
     * 别名
     */
    @Schema(description = "别名")
    private List<String> aliasList;

    /**
     * 注册ID
     */
    @Schema(description = "注册ID")
    private List<String> registIdList;

    /**
     * 推送标签列表
     */
    @Schema(description = "推送标签列表")
    private List<String> tagList;

    /**
     * 扩展参数
     */
    @Schema(description = "扩展参数")
    private Map<String,Object> extendMap;

    /**
     * 是否广播
     */
    @Schema(description = "是否广播")
    private Boolean isAll = Boolean.FALSE;

}
