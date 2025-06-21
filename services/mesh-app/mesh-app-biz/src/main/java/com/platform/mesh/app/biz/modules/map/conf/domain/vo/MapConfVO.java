package com.platform.mesh.app.biz.modules.map.conf.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 地图配置VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="地图配置VO")
public class MapConfVO extends BaseVO {


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
     * 打卡类型
     */
    @Schema(description ="打卡类型")
    private Integer logFlag;


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
     * 半径范围
     */
    @Schema(description ="半径范围")
    private Integer rangeRadius;

}