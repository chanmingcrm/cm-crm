package com.platform.mesh.app.biz.modules.map.log.domain.dto;

import com.platform.mesh.app.biz.modules.map.log.enums.InOutFlagEnum;
import com.platform.mesh.app.biz.modules.map.log.enums.MapLogFlagEnum;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 单DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="地图打卡DTO")
public class MapLogDTO extends BaseDTO {


    /**
     * 主键ID
     */
    @Schema(description ="主键ID")
    private Long id;


    /**
     * 模块ID
     */
    @Schema(description ="模块ID")
    private Long moduleId;


    /**
     * 数据ID
     */
    @Schema(description ="数据ID")
    private Long dataId;


    /**
     * 批次ID
     */
    @Schema(description ="批次ID")
    private Long batchId;


    /**
     * 打卡类型
     */
    @SchemaEnum(value = MapLogFlagEnum.class, description ="打卡类型")
    private Integer logFlag;


    /**
     * 出入标识
     */
    @SchemaEnum(value = InOutFlagEnum.class, description ="出入标识")
    private Integer inOutFlag;


    /**
     * 经度坐标
     */
    @Schema(description ="经度坐标")
    private String lng;


    /**
     * 维度坐标
     */
    @Schema(description ="维度坐标")
    private String lat;


    /**
     * 坐标
     */
    @Schema(description ="坐标")
    private String location;


    /**
     * 详细地址
     */
    @Schema(description ="详细地址")
    private String address;


    /**
     * 备注信息
     */
    @Schema(description ="备注信息")
    private String remarks;


    /**
     * 图片Id
     */
    @Schema(description ="图片Id")
    private List<Long> fileIds;

}