package com.platform.mesh.app.biz.modules.app.formbase.domain.vo;

import com.platform.mesh.app.api.modules.app.enums.comp.FormTypeEnum;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.vo.AppFormColumnVO;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 单VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="单VO")
public class AppFormBaseVO extends BaseVO {



    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;


    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;


    /**
     * 表单标识
     */
    @Schema(description = "表单标识")
    private String formMac;


    /**
     * 表单名称
     */
    @Schema(description = "表单名称")
    private String formName;


    /**
     * 表单类型FormTypeEnum
     */
    @SchemaEnum(value = FormTypeEnum.class, description = "表单类 型FormTypeEnum")
    private Integer formType;


    /**
     * 表单请求地址
     */
    @Schema(description = "表单请求地址")
    private String formUrl;


    /**
     * 表单logo
     */
    @Schema(description = "表单logo")
    private String formLogo;


    /**
     * 表单排序
     */
    @Schema(description = "表单排序")
    private Integer formSort;


    /**
     * 表单版本
     */
    @Schema(description = "表单版本")
    private String formVersion;


    /**
     * 表单背景
     */
    @Schema(description = "表单背景")
    private String styleBackground;


    /**
     * 表单css脚本
     */
    @Schema(description = "表单css脚本")
    private String styleCss;


    /**
     * 默认标识YesOrNoEnum
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "默认标识YesOrNoEnum")
    private Integer defaultFlag;


    /**
     * 删除标识YesOrNoEnum
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "删除标识YesOrNoEnum")
    private Integer delFlag;


    @Schema(description = "表单关联字段列表")
    private List<AppFormColumnVO> appFormColumnVOList;
}