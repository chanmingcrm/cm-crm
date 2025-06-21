package com.platform.mesh.bpm.biz.modules.temp.line.domain.po;


import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 流程线信息
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "bpm_temp_line", autoResultMap = true)
public class BpmTempLine extends BasePO {

    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 线Hash
     */
    private String lineHash;

    /**
     * 线名称
     */
    private String lineName;

    /**
     * 流程模板ID
     */
    private Long tempProcessId;

    /**
     * 流程模板Hash
     */
    private String tempProcessHash;

    /**
     * 流程模板线入节点ID
     */
    private Long tempInNodeId;

    /**
     * 流程模板线入节点Hash
     */
    private String tempInNodeHash;

    /**
     * 流程模板线出节点ID
     */
    private Long tempOutNodeId;

    /**
     * 流程模板线出节点Hash
     */
    private String tempOutNodeHash;

    /**
     * 流程模板入线Pass
     */
    private Integer tempInLinePass;

    /**
     * 流程模板出线Pass
     */
    private Integer tempOutLinePass;

    /**
     * 线类型
     */
    private Integer lineType;

    /**
     * 线图标
     */
    private String lineSvg;

    /**
     * 线主题
     */
    private String lineTheme;

    /**
     * 开始点x坐标
     */
    private Integer pointStartX;

    /**
     * 开始点y坐标
     */
    private Integer pointStartY;

    /**
     * 结束点x坐标
     */
    private Integer pointStopX;

    /**
     * 结束点y坐标
     */
    private Integer pointStopY;

    /**
     * 线所有点坐标
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private JSONArray pointList;

    /**
     * 文本x坐标
     */
    private Integer textX;

    /**
     * 文本y坐标
     */
    private Integer textY;

    /**
     * 节点文本描述
     */
    private String textDesc;

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

