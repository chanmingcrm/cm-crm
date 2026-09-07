package com.platform.mesh.app.biz.modules.map.log.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 地图打卡PO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "map_log", autoResultMap = true)
public class MapLog extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 模块ID
    */
    private Long moduleId;


    /**
    * 数据ID
    */
    private Long dataId;


    /**
    * 批次ID
    */
    private Long batchId;


    /**
     * 打卡类型
     */
    private Integer logFlag;


    /**
     * 出入标识
     */
    private Integer inOutFlag;


    /**
     * 经度坐标
     */
    private String lng;


    /**
     * 维度坐标
     */
    private String lat;


    /**
     * 坐标
     */
    private String location;


    /**
     * 详细地址
     */
    private String address;


    /**
     * 备注信息
     */
    private String remarks;


    /**
    * 删除标识YesOrNoEnum
    */
    private Integer delFlag;

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

}