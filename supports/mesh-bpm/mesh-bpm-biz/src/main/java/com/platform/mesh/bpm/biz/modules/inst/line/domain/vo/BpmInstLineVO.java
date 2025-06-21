package com.platform.mesh.bpm.biz.modules.inst.line.domain.vo;


import cn.hutool.json.JSONArray;
import com.platform.mesh.bpm.biz.modules.inst.line.enums.InstLinePassEnum;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


/**
 * @description 动作VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程线信息VO")
public class BpmInstLineVO extends BaseVO {


    /**
     * 线ID
     */
    @Schema(description = "线ID")
    private Long id;

    /**
     * 流程实例Hash
     */
    @Schema(description = "流程实例Hash")
    private String instProcessId;

    /**
     * 线名称
     */
    @Schema(description = "线名称")
    private String lineName;

    /**
     * 流程实例线入节点Id
     */
    @Schema(description = "流程实例线入节点Id")
    private Long instInNodeId;

    /**
     * 流程实例线出节点Id
     */
    @Schema(description = "流程实例线出节点Id")
    private Long instOutNodeId;

    /**
     * 流程实例入线Pass
     */
    @SchemaEnum(value = InstLinePassEnum.class, description = "流程实例入线Pass")
    private Integer instInLinePass;

    /**
     * 流程实例出线Pass
     */
    @SchemaEnum(value = InstLinePassEnum.class, description = "流程实例出线Pass")
    private Integer instOutLinePass;

    /**
     * 线类型
     */
    @Schema(description = "线类型")
    private Integer lineType;

    /**
     * 线图标
     */
    @Schema(description = "线图标")
    private String lineSvg;

    /**
     * 线主题
     */
    @Schema(description = "线主题")
    private String lineTheme;

    /**
     * 线类型
     */
    @Schema(description = "开始点x坐标")
    private Integer pointStartX;

    /**
     * 线类型
     */
    @Schema(description = "开始点y坐标")
    private Integer pointStartY;

    /**
     * 线类型
     */
    @Schema(description = "结束点x坐标")
    private Integer pointStopX;

    /**
     * 线类型
     */
    @Schema(description = "结束点y坐标")
    private Integer pointStopY;

    /**
     * 线类型
     */
    @Schema(description = "线所有点坐标")
    private JSONArray pointList;

    /**
     * 文本x坐标
     */
    @Schema(description = "文本x坐标")
    private Integer textX;

    /**
     * 文本y坐标
     */
    @Schema(description = "文本y坐标")
    private Integer textY;

    /**
     * 节点文本描述
     */
    @Schema(description = "节点文本描述")
    private String textDesc;
}
