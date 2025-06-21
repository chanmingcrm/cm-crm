package com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.dto;

import cn.hutool.json.JSONArray;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 单字段请求DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="单字段请求DTO")
public class AppFormColumnSetRequireDTO extends BaseDTO {


    /**
     *  ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     *  模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

    /**
     *  表单ID
     */
    @Schema(description = "表单ID")
    private Long formId;

    /**
     *  动作ID
     */
    @Schema(description = "动作ID")
    private Long actionId;


    /**
     *  动作名称
     */
    @Schema(description = "动作名称")
    private String actionName;


    /**
     *  事件ID
     */
    @Schema(description = "事件ID")
    private Long eventId;


    /**
     *  事件名称
     */
    @Schema(description = "事件名称")
    private String eventName;


    /**
     *  激活标识
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "激活标识")
    private Integer activeFlag;


    /**
     *  内容类型
     */
    @Schema(description = "内容类型")
    private String contentType;


    /**
     * 请求Hash
     */
    @Schema(description = "请求Hash")
    private String requireHash;


    /**
     * 请求名称
     */
    @Schema(description = "请求名称")
    private String requireName;


    /**
     * 请求类型
     */
    @Schema(description = "请求类型")
    private String requireType;


    /**
     * 请求环境
     */
    @Schema(description = "请求环境")
    private String requireEnv;


    /**
     * 请求地址
     */
    @Schema(description = "请求地址")
    private String requireUrl;


    /**
     * 请求方式
     */
    @Schema(description = "请求方式")
    private String requireMethod;


    /**
     * 请求代理
     */
    @Schema(description = "请求代理")
    private Integer requireProxy;


    /**
     * 请求参数
     */
    @Schema(description = "请求参数")
    private JSONArray requireParams;


    /**
     * 请求参数方式
     */
    @Schema(description = "请求参数方式")
    private String requireParamsMethod;


    /**
     * 响应结果集名称
     */
    @Schema(description = "响应结果集名称")
    private String responseResultName;


    /**
     * 响应码名称
     */
    @Schema(description = "响应码名称")
    private String responseCodeName;


    /**
     * 响应码默认值
     */
    @Schema(description = "响应码默认值")
    private Integer responseCodeValue;


    /**
     * 响应码正确数据名称
     */
    @Schema(description = "响应码正确数据名称")
    private String responseDataName;


    /**
     * 响应码错误数据名称
     */
    @Schema(description = "响应码错误数据名称")
    private String responseMsgName;

}