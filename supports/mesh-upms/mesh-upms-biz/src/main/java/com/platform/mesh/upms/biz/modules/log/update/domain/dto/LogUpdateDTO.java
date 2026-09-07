package com.platform.mesh.upms.biz.modules.log.update.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.custom.LoginTypeEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


/**
 * @description 更新日志)DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="更新日志")
public class LogUpdateDTO extends BaseDTO {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 日志类型
     */
    @SchemaEnum(value = LoginTypeEnum.class, description = "日志类型")
    private Integer logFlag;

    /**
     * 更新编号
     */
    @Schema(description = "更新编号")
    private Integer logCode = NumberConst.NUM_0;

    /**
     * 更新版本
     */
    @Schema(description = "更新版本")
    private String logVersion;

    /**
     * 更新标题
     */
    @Schema(description = "更新标题")
    private String logTitle;

    /**
     * 更新内容
     */
    @Schema(description = "更新内容")
    private String logContext;

    /**
     * 强制更新
     */
    @Schema(description = "强制更新")
    private Integer clientForce;

    /**
     * 强制重启
     */
    @Schema(description = "强制重启")
    private Integer clientRestart;

    /**
     * 安卓WGT
     */
    @Schema(description = "安卓WGT")
    private String clientAndroidWgt;

    /**
     * 安卓URL
     */
    @Schema(description = "安卓URL")
    private String clientAndroidUrl;

    /**
     * 苹果WGT
     */
    @Schema(description = "苹果WGT")
    private String clientIosWgt;

    /**
     * 苹果URL
     */
    @Schema(description = "苹果URL")
    private String clientIosUrl;

    /**
     * 发布时间
     */
    @Schema(description = "发布时间")
    private LocalDateTime releaseTime;
}
