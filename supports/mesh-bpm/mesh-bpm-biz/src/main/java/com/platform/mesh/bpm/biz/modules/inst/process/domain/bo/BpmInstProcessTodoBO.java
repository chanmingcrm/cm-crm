package com.platform.mesh.bpm.biz.modules.inst.process.domain.bo;


import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.bpm.biz.soa.process.run.enums.ProcessRunEnum;
import com.platform.mesh.core.application.domain.bo.BaseBO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;


/**
 * @description 流程过程待办查询BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程过程待办查询BO")
public class BpmInstProcessTodoBO extends BaseBO {

    /**
     * 当前用户ID
     */
    @Schema(description = "当前用户ID")
    private Long userId;

    /**
     * 当前用户ID
     */
    @Schema(description = "当前账户ID")
    private Long accountId;

    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private List<Long> moduleIds;

    /**
     * 通过状态
     */
    @Schema(description = "通过状态")
    private List<Integer> auditPass;

    /**
     * 运行标识
     */
    @SchemaEnum(value = ProcessRunEnum.class, description = "运行标识")
    private Integer runFlag;

    /**
     * 流程父ID
     */
    @SchemaEnum(value = ProcessRunEnum.class, description = "流程父ID")
    private Long instRootId;

    /**
     * 人员类型
     */
    @Schema(description = "人员类型")
    private List<Integer> userTypes = CollUtil.newArrayList();

    /**
     * 人员ID
     */
    @Schema(description = "人员ID")
    private List<Long> userIds = CollUtil.newArrayList();

    /**
     * 组织类型
     */
    @Schema(description = "组织类型")
    private List<Integer> orgTypes = CollUtil.newArrayList();

    /**
     * 组织ID
     */
    @Schema(description = "组织ID")
    private List<Long> orgIds = CollUtil.newArrayList();

    /**
     * 角色类型
     */
    @Schema(description = "角色类型")
    private List<Integer> roleTypes = CollUtil.newArrayList();

    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    private List<Long> roleIds = CollUtil.newArrayList();

}
