package com.platform.mesh.uaa.biz.modules.tenant.client.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 授权客户端租户关系DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "oauth2_third_client", autoResultMap = true)
public class TenantClient extends BasePO {


    /**
    * id
    */
    @TableId( type = IdType.ASSIGN_ID)
    private String id;

    /**
    * 用户ID
    */
    private Long userId;

    /**
    * 代理ID
    */
    private String agentId;

    /**
    * 客户端id
    */
    private String clientId;

    /**
    * 客户端密钥
    */
    private String clientSecret;

    /**
    * 客户端来源
    */
    private Integer clientSource;

    /**
    * 回调地址
    */
    private String redirectUri;

    /**
    * 是否已删除 0-未删除，1-已删除
    */
    private Integer delFlag;

    /**
    * 创建人ID
    */
    private Long createUserId;

    /**
    * 创建时间 
    */
    private LocalDateTime createTime;

}