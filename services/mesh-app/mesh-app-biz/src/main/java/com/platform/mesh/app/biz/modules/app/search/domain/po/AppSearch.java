package com.platform.mesh.app.biz.modules.app.search.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 查询DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "app_search", autoResultMap = true)
public class AppSearch extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 应用ID
    */
    private Long appId;


    /**
    * 模块ID
    */
    private Long moduleId;


    /**
    * 查询名称
    */
    private String searchName;


    /**
    * 查询标识
    */
    private Integer searchFlag;


    /**
    * 查询数据
    */
    private String searchData;


    /**
    * 查询排序
    */
    private String searchSort;


    /**
    * 默认标识
    */
    private Integer initFlag;


    /**
    * 添加标识
    */
    private Integer addFlag;


    /**
    * 隐藏标识
    */
    private Integer hideFlag;


    /**
    * 删除标识
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