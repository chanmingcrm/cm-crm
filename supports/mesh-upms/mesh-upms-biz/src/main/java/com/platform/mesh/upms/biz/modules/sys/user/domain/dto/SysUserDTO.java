package com.platform.mesh.upms.biz.modules.sys.user.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 用户信息
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户信息")
public class SysUserDTO extends BaseDTO {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "手机号码")
    private String phone;

    @Schema(description = "身份证唯一编号")
    private String idCard;

    @Schema(description = "用户性别")
    private Integer gender;

    @Schema(description = "帐号状态（0正常 1停用）")
    private Integer userFlag;

    @Schema(description = "删除标志（0代表存在 2代表删除）")
    private Integer delFlag;

    @Schema(description = "管理员状态(AdminFlagEnum)")
    private Integer adminFlag;

    @Schema(description = "最后登陆IP")
    private String loginIp;

    @Schema(description = "最后登录时间")
    private LocalDateTime loginDate;
}
