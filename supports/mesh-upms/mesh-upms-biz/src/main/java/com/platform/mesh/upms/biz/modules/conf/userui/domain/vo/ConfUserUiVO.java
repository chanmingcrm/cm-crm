package com.platform.mesh.upms.biz.modules.conf.userui.domain.vo;

import java.time.LocalDateTime;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 配置UIVO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="配置UIVO")
public class ConfUserUiVO extends BaseVO {



    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 父ID
     */
    @Schema(description = "父ID")
    private Long userId;

    /**
     * 个性化主题
     */
    @Schema(description = "个性化主题")
    private String uiTheme;

    /**
     * 个性化色彩
     */
    @Schema(description = "个性化色彩")
    private String uiColor;

    /**
     * 个性化字体
     */
    @Schema(description = "个性化字体")
    private String uiFont;

    /**
     * 个性化字体大小
     */
    @Schema(description = "个性化字体大小")
    private Integer uiFontSize;

    /**
     * 个性化背景图片
     */
    @Schema(description = "个性化背景图片")
    private String uiBackImg;

    /**
     * 个性化显示语言
     */
    @Schema(description = "个性化显示语言")
    private String uiLocale;

    /**
     * 创建者ID
     */
    @Schema(description = "创建者ID")
    private Long createUserId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新者ID
     */
    @Schema(description = "更新者ID")
    private Long updateUserId;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 数据权限用户ID
     */
    @Schema(description = "数据权限用户ID")
    private Long scopeUserId;

    /**
     * 数据权限机构ID
     */
    @Schema(description = "数据权限机构ID")
    private Long scopeOrgId;

}