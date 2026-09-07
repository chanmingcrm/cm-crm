package com.platform.mesh.ai.biz.modules.ai.sessionhis.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description AI会话历史PO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "ai_session_his", autoResultMap = true)
public class AiSessionHis extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 会话ID
     */
    private Long sessionId;

    /**
    * 模型ID
    */
    private String modelName;

    /**
    * 会话历史标识
    */
    private Integer modelFlag;

    /**
    * 会话历史类别
    */
    private Integer modelType;

    /**
    * 对话角色
    */
    private String requireRole;

    /**
    * 请求提示词
    */
    private String requirePrompt;

    /**
    * 请求参数
    */
    private String requireParams;

    /**
    * 响应码
    */
    private String responseCode;

    /**
    * 响应内容
    */
    private String responseMsg;

    /**
    * 累计 Tokens
    */
    private Long totalTokens;

    /**
     * 创建人ID
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    private Long updateUserId;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 用户ID
     */
    private Long scopeUserId;

    /**
     * 组织ID
     */
    private Long scopeOrgId;

}