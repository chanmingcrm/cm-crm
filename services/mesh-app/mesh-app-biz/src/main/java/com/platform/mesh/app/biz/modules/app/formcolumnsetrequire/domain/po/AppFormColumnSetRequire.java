package com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.po;

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.platform.mesh.core.application.domain.po.BasePO;
import com.platform.mesh.mybatis.plus.annotation.IgnoreDataScope;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 字段请求实体对象
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "app_form_column_set_require", autoResultMap = true)
public class AppFormColumnSetRequire extends BasePO {


    /**
    * ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 模块ID
    */
    private Long moduleId;


    /**
    * 表单ID
    */
    private Long formId;


    /**
    * 组件类型
    */
    private Integer compType;


    /**
    * 组件Mac
    */
    private String compMac;


    /**
    * 字段ID
    */
    private Long columnId;


    /**
    * 字段标识
    */
    private String columnMac;


    /**
    * 字段名称
    */
    private String columnName;


    /**
    *  动作ID
    */
    private Long actionId;


    /**
    *  动作名称
    */
    private String actionName;


    /**
    *  事件ID
    */
    private Long eventId;


    /**
    *  事件名称
    */
    private String eventName;


    /**
    *  激活标识
    */
    private Integer activeFlag;


    /**
    *  内容类型
    */
    private String contentType;


    /**
    * 请求Hash
    */
    private String requireHash;


    /**
    * 请求名称
    */
    private String requireName;


    /**
    * 请求类型
    */
    private String requireType;


    /**
    * 请求环境
    */
    private String requireEnv;


    /**
    * 请求地址
    */
    private String requireUrl;


    /**
    * 请求方式
    */
    private String requireMethod;


    /**
    * 请求代理
    */
    private Integer requireProxy;


    /**
    * 请求参数
    */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private JSONArray requireParams;


    /**
    * 请求参数方式
    */
    private String requireParamsMethod;


    /**
    * 响应结果名称
    */
    private String responseResultName;


    /**
    * 响应码名称
    */
    private String responseCodeName;


    /**
    * 响应码默认值
    */
    private Integer responseCodeValue;


    /**
    * 响应码正确数据名称
    */
    private String responseDataName;


    /**
    * 响应码错误数据名称
    */
    private String responseMsgName;



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
    @IgnoreDataScope()
    @TableField(fill = FieldFill.INSERT)
    private Long scopeUserId;

    /**
     * 组织ID
     */
    @IgnoreDataScope()
    @TableField(fill = FieldFill.INSERT)
    private Long scopeOrgId;

}