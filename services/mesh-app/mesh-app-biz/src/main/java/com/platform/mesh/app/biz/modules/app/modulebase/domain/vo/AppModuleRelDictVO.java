package com.platform.mesh.app.biz.modules.app.modulebase.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 模块VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="模块VO")
public class AppModuleRelDictVO extends BaseVO {

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

}