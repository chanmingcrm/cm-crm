package com.platform.mesh.app.api.modules.init.db.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 转化数据DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="转化数据DTO")
public class DbTransDTO extends BaseDTO {


    /**
     * 转化ID
     */
    @Schema(description = "转化ID")
    private Long transId;

    /**
     * 成员ID
     */
    @Schema(description = "成员ID")
    private Long memberId;

    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private List<Long> dataIds;

}