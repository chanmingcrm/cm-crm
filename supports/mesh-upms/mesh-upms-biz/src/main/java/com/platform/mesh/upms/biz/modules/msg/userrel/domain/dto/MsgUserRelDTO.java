package com.platform.mesh.upms.biz.modules.msg.userrel.domain.dto;

import java.time.LocalDateTime;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 消息接收DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="消息接收DTO")
public class MsgUserRelDTO extends BaseDTO {



    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;


    /**
     * 消息ID
     */
    @Schema(description = "消息ID")
    private Long msgId;


    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;


    /**
     * 已读标识
     */
    @Schema(description = "已读标识")
    private Integer readFlag;


    /**
     * 删除标识
     */
    @Schema(description = "删除标识")
    private Integer delFlag;


    /**
     * 创建者ID
     */
    @Schema(description = "创建者ID")
    private Long createUserId;


    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;


    /**
     * 更新者ID
     */
    @Schema(description = "更新者ID")
    private Long updateUserId;


    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;


    /**
     * 数据权限用户ID
     */
    @Schema(description = "数据权限用户ID")
    private Long scopeUserId;


    /**
     * 数据权限层级ID
     */
    @Schema(description = "数据权限层级ID")
    private Long scopeOrgId;


}