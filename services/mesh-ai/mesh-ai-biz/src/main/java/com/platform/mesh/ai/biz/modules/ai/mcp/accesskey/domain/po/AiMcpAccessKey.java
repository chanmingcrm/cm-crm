package com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.domain.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description MCP访问密钥PO
 * @author qingfeng
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "ai_mcp_access_key", autoResultMap = true)
public class AiMcpAccessKey extends BasePO {

    /** 主键ID */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 密钥名称 */
    private String keyName;

    /** 对外公开的密钥标识 */
    private String publicId;

    /** 密钥哈希值，禁止序列化输出 */
    @JsonIgnore
    private String secretHash;

    /** 账号ID */
    private Long accountId;

    /** 用户ID */
    private Long userId;

    /** 状态：1有效，0已吊销 */
    private Integer status;

    /** 到期时间，为空表示永久有效 */
    private LocalDateTime expiresAt;

    /** 最近使用时间 */
    private LocalDateTime lastUsedAt;

    /** 吊销时间 */
    private LocalDateTime revokedAt;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 修改时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
