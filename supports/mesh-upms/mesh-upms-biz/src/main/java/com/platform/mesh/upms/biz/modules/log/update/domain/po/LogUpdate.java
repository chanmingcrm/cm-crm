package com.platform.mesh.upms.biz.modules.log.update.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
                        
/**
 * @description 更新日志实体类
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("log_update")
public class LogUpdate extends BasePO {

    /**
    * 日志ID
    */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 更新编号
     */
    private Integer logCode;

    /**
     * 日志类型
     */
    private Integer logFlag;

    /**
     * 更新版本
     */
    private String logVersion;

    /**
     * 更新标题
     */
    private String logTitle;

    /**
     * 更新内容
     */
    private String logContext;

    /**
     * 强制更新
     */
    private Integer clientForce;

    /**
     * 强制重启
     */
    private Integer clientRestart;

    /**
     * 安卓WGT
     */
    private String clientAndroidWgt;

    /**
     * 安卓URL
     */
    private String clientAndroidUrl;

    /**
     * 苹果WGT
     */
    private String clientIosWgt;

    /**
     * 苹果URL
     */
    private String clientIosUrl;

    /**
     * 发布时间
     */
    private LocalDateTime releaseTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}

