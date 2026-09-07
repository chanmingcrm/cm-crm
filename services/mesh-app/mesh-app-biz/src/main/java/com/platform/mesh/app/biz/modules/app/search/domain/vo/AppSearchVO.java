package com.platform.mesh.app.biz.modules.app.search.domain.vo;

import com.platform.mesh.core.application.domain.dto.CondDTO;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 查询VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="查询VO")
public class AppSearchVO extends BaseVO {


    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;


    /**
     * 应用ID
     */
    @Schema(description = "应用ID")
    private Long appId;


    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;


    /**
     * 查询名称
     */
    @Schema(description = "查询名称")
    private String searchName;


    /**
     * 查询标识
     */
    @Schema(description = "查询标识")
    private Integer searchFlag;


    /**
     * 查询条件
     */
    @Schema(description = "查询条件")
    private List<CondDTO> condDTO;


    /**
     * 查询排序
     */
    @Schema(description = "查询排序")
    private String searchSort;


    /**
     * 默认标识
     */
    @Schema(description = "默认标识")
    private Integer initFlag;


    /**
     * 添加标识
     */
    @Schema(description = "添加标识")
    private Integer addFlag;


    /**
     * 隐藏标识
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "隐藏标识")
    private Integer hideFlag;

}