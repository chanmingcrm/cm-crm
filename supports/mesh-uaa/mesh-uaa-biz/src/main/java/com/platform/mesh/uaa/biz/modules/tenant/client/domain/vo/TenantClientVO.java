package com.platform.mesh.uaa.biz.modules.tenant.client.domain.vo;

import java.time.LocalDateTime;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 授权客户端租户关系VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="授权客户端租户关系VO")
public class TenantClientVO extends BaseVO {



    /**
     * id
     */
    @Schema(description = "id")
    private String id;

    /**
     * 代理ID
     */
    @Schema(description = "代理ID")
    private String agentId;

    /**
     * 客户端id
     */
    @Schema(description = "客户端id")
    private String clientId;

    /**
     * 客户端密钥
     */
    @Schema(description = "客户端密钥")
    private String clientSecret;

    /**
     * 客户端来源
     */
    @SchemaEnum(value = SourceFlagEnum.class, description = "客户端来源")
    private Integer clientSource;

    /**
     * 回调地址
     */
    @Schema(description = "回调地址")
    private String redirectUri;

    /**
     * 是否已删除 0-未删除，1-已删除
     */
    @Schema(description = "是否已删除 0-未删除，1-已删除")
    private Integer delFlag;

    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    private Long createUserId;

    /**
     * 创建时间 
     */
    @Schema(description = "创建时间 ")
    private LocalDateTime createTime;

}