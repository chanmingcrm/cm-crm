package com.platform.mesh.bpm.biz.modules.temp.line.domain.dto;


import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 流程线信息DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程线信息DTO")
public class BpmTempLineDTO  extends BaseDTO {

    /**
     * 流程模板Hash
     */
    @Schema(description = "流程模板Id")
    private Long tempProcessId;

    /**
     * 线ID
     */
    @Schema(description = "线ID",hidden = true)
    private Long lineId;

    /**
     * 线Hash
     */
    @Schema(description = "线Hash")
    private String lineHash;

    /**
     * 线名称
     */
    @Schema(description = "线名称")
    private String lineName;

    /**
     * 流程模板线入节点Id
     */
    @Schema(description = "流程模板线入节点Id")
    private Long tempInNodeId;

    /**
     * 流程模板线入节点Hash
     */
    @Schema(description = "流程模板线入节点Hash")
    private String tempInNodeHash;

    /**
     * 流程模板线出节点Id
     */
    @Schema(description = "流程模板线出节点Id")
    private Long tempOutNodeId;

    /**
     * 流程模板线出节点Hash
     */
    @Schema(description = "流程模板线出节点Hash")
    private String tempOutNodeHash;

    /**
     * 流程模板入线Pass
     */
    @Schema(description = "流程模板入线Pass")
    private Integer tempInLinePass;

    /**
     * 流程模板出线Pass
     */
    @Schema(description = "流程模板出线Pass")
    private Integer tempOutLinePass;

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
