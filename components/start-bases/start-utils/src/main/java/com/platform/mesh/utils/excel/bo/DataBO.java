package com.platform.mesh.utils.excel.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 导出对象数据BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="导出对象数据BO")
public class DataBO extends BaseBO {

    /**
     * 键
     */
    @Schema(description = "键")
    private String key;

    /**
     * 值
     */
    @Schema(description = "值")
    private Object value;


}