package com.platform.mesh.app.biz.modules.third.thirdformcolumn.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.api.modules.conf.enums.ConfSourceEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 模块转化字段映射设置VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="第三方字段")
public class ThirdFormColumnVO extends BaseVO {


    /**
     * 字段来源
     */
    @SchemaEnum(value = ConfSourceEnum.class, description = "字段来源")
    private Integer sourceFlag;


    /**
     * 来源组件标识
     */
    @Schema(description = "来源组件标识")
    private String compMac;


    /**
     * 来源字段标识
     */
    @Schema(description = "来源字段标识")
    private String columnMac;


    /**
     * 来源字段名称
     */
    @Schema(description = "来源字段名称")
    private String columnName;


    /**
     * 来源字段选项
     */
    @Schema(description = "来源字段选项")
    private Object columnOptionJson;

}