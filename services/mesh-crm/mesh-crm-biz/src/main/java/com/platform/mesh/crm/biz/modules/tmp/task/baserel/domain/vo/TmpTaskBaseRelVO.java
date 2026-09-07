package com.platform.mesh.crm.biz.modules.tmp.task.baserel.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 任务数据关联VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="任务数据关联VO")
public class TmpTaskBaseRelVO extends BaseVO {



    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;


    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;


    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private Long dataId;


    /**
     * 任务ID
     */
    @Schema(description = "任务ID")
    private Long taskId;


    /**
     * 版本编号
     */
    @Schema(description = "版本编号")
    private String versionMac;


    /**
     * 版本描述
     */
    @Schema(description = "版本描述")
    private String versionDesc;


}