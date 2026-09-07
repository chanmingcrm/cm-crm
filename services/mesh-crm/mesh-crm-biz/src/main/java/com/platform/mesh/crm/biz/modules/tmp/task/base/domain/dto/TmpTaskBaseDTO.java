package com.platform.mesh.crm.biz.modules.tmp.task.base.domain.dto;

import com.platform.mesh.app.api.modules.app.domain.dto.DataAddSimpDTO;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.domain.dto.TmpTaskBaseRelDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 任务VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="任务VO")
public class TmpTaskBaseDTO extends DataAddSimpDTO {

    /**
     * 关联数据
     */
    @Schema(description = "关联数据")
    private List<TmpTaskBaseRelDTO> relDTOList;

}