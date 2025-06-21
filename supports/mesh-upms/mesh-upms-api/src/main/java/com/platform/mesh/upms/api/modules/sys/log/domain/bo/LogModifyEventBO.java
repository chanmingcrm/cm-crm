package com.platform.mesh.upms.api.modules.sys.log.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @description 修改日志(LogModify)实体类
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "修改记录")
public class LogModifyEventBO extends BaseBO {

    /**
     * 旧数据
     */
    @Schema(description = "旧数据")
    private Map<String,Object> oldValue;

    /**
     * 新数据
     */
    @Schema(description = "新数据")
    private Map<String,Object> newValue;

}

