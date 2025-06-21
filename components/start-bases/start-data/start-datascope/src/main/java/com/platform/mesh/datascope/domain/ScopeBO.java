package com.platform.mesh.datascope.domain;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import com.platform.mesh.core.enums.data.DataFlagEnum;
import com.platform.mesh.core.enums.data.DataScopeEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 数据权限BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="BI统计DTO")
public class ScopeBO extends BaseBO {

    /**
     * 数据权限
     */
    @SchemaEnum(value = DataScopeEnum.class, description = "数据权限")
    private Integer dataScope;

    /**
     * 数据标识
     */
    @SchemaEnum(value = DataFlagEnum.class, description = "数据标识")
    private Integer dataFlag;

    /**
     * 数据ID
     */
    @Schema( description = "数据ID")
    private List<Long> dataIds;

}