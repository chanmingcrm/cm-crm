package com.platform.mesh.upms.biz.captcha.domain.dto;


import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 验证码校验DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="验证码校验DTO")
public class CaptchaCheckDTO extends BaseDTO {

    // 验证码id
    @Schema(description = "验证码id")
    private String id;
    // 验证码数据
    @Schema(description = "验证码数据")
    private ImageCaptchaTrackDTO data;
}
