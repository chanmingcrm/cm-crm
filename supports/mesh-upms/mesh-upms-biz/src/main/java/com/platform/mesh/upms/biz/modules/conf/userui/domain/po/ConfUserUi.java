package com.platform.mesh.upms.biz.modules.conf.userui.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 配置UIDTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "conf_user_ui", autoResultMap = true)
public class ConfUserUi extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
    * 父ID
    */
    private Long userId;

    /**
    * 个性化主题
    */
    private String uiTheme;

    /**
    * 个性化色彩
    */
    private String uiColor;

    /**
    * 个性化字体
    */
    private String uiFont;

    /**
    * 个性化字体大小
    */
    private Integer uiFontSize;

    /**
    * 个性化背景图片
    */
    private String uiBackImg;

    /**
    * 个性化显示语言
    */
    private String uiLocale;

    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 用户ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long scopeUserId;

    /**
     * 组织ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long scopeOrgId;

}