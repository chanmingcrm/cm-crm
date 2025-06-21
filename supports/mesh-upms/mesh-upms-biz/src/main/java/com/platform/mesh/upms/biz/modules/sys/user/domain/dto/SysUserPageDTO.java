package com.platform.mesh.upms.biz.modules.sys.user.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 用户列表查询对象
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户列表查询对象")
public class SysUserPageDTO extends PageDTO {

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickName;

    /**
     * 身份证唯一编号
     */
    @Schema(description = "身份证唯一编号")
    private String idCard;

    /**
     * 用户性别
     */
    @Schema(description = "用户性别")
    private String gender;

    /**
     * 帐号状态（UserFlagEnum）
     */
    @Schema(description = "帐号状态")
    private Integer userFlag;
}
