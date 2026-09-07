package com.platform.mesh.ai.biz.modules.cc.groupuserrel.domain.dto;

import com.platform.mesh.ai.biz.modules.cc.group.enums.GroupTypeEnum;
import com.platform.mesh.ai.biz.modules.cc.user.enums.UserTypeEnum;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 会话群人员关系DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="会话群人员关系DTO")
public class CcGroupUserRelDTO extends BaseDTO {


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
     * 人员Hash
     */
    @Schema(description = "人员Hash")
    private String userHash;

    /**
     * 人员名称
     */
    @Schema(description = "人员名称")
    private String userName;

    /**
     * 人员类型
     */
    @SchemaEnum(value = UserTypeEnum.class, description = "人员类型")
    private Integer userType;

}
