package com.platform.mesh.upms.api.modules.dict.base.domian.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 字典基础BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="字典值BO")
public class DictBaseValueBO extends BaseBO {



    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 字典ID
     */
    @Schema(description = "字典ID")
    private Long dictId;


    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    private String dictName;


    /**
     * 字典值
     */
    @Schema(description = "字典值")
    private String dictValue;


}