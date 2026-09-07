package com.platform.mesh.ai.biz.modules.cc.group.domain.vo;

import com.platform.mesh.ai.biz.modules.cc.group.enums.CcGroupStatusEnum;
import com.platform.mesh.ai.biz.modules.cc.group.enums.GroupTypeEnum;
import com.platform.mesh.ai.biz.modules.cc.user.enums.UserTypeEnum;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


/**
 * @description 客服会话VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="客服会话VO")
public class CcGroupVO extends BaseVO {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 群Hash
     */
    @Schema(description = "群Hash")
    private String groupHash;

    /**
     * 群名称
     */
    @Schema(description = "群名称")
    private String groupName;

    /**
     * 群类型
     */
    @SchemaEnum(value = GroupTypeEnum.class, description = "群类型")
    private Integer groupType;

    /**
     * 回复类型
     */
    @SchemaEnum(value = UserTypeEnum.class, description = "回复类型")
    private Integer replyType;

    /**
     * 会话状态
     */
    @SchemaEnum(value = CcGroupStatusEnum.class, description = "会话状态")
    private Integer status;

    /**
     * 访客来源
     */
    @Schema(description = "访客来源")
    private String source;

    /**
     * 来源页面
     */
    @Schema(description = "来源页面")
    private String sourcePage;

    /**
     * 访客名称
     */
    @Schema(description = "访客名称")
    private String visitorName;

    /**
     * 访客联系方式
     */
    @Schema(description = "访客联系方式")
    private String visitorContact;

    /**
     * 访客公司
     */
    @Schema(description = "访客公司")
    private String visitorCompany;

    /**
     * 访客需求
     */
    @Schema(description = "访客需求")
    private String visitorDemand;

    /**
     * 首次响应时间
     */
    @Schema(description = "首次响应时间")
    private LocalDateTime firstResponseAt;

    /**
     * 最后一条消息时间
     */
    @Schema(description = "最后一条消息时间")
    private LocalDateTime lastMsgAt;

    /**
     * 关闭时间
     */
    @Schema(description = "关闭时间")
    private LocalDateTime closedAt;

    /**
     * 当前接待客服
     */
    @Schema(description = "当前接待客服")
    private String assigneeUserHash;

    /**
     * 会话标签
     */
    @Schema(description = "会话标签")
    private String tags;

    /**
     * 会话摘要
     */
    @Schema(description = "会话摘要")
    private String summary;

    /**
     * 线索状态：0未创建，1待跟进，2已跟进
     */
    @Schema(description = "线索状态：0未创建，1待跟进，2已跟进")
    private Integer leadStatus;

    /**
     * CRM线索ID
     */
    @Schema(description = "CRM线索ID")
    private Long leadId;

    /**
     * 未读数量
     */
    @Schema(description = "未读数量")
    private Integer unReadNum;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
