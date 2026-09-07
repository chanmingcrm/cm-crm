package com.platform.mesh.upms.api.modules.sys.log.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.utils.excel.enums.DataTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 修改日志(LogModify)实体类
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "修改记录")
public class ModifyDataBO extends BaseBO {

    /**
     * 字段名称
     */
    @Schema(description = "字段名称")
    private String compMac;

    /**
     * 字段名称
     */
    @Schema(description = "字段名称")
    private String columnMac;

    /**
     * 字段名称
     */
    @Schema(description = "字段名称")
    private String columnName;

    /**
     * 变更值
     */
    @Schema(description = "变更值")
    private Object valueOld;

    /**
     * 变更值
     */
    @Schema(description = "变更值")
    private Object valueNew;

    /**
     * 数据类型
     */
    @SchemaEnum(value = DataTypeEnum.class, description = "数据类型")
    private Integer dataType;

    /**
     * 操作类型
     */
    @SchemaEnum(value = OperateTypeEnum.class, description = "操作类型")
    private Integer operateType;


}

