package com.platform.mesh.upms.biz.modules.log.operate.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


/**
 * @description 日志(LogOperate)DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="日志")
public class LogOperateDTO extends BaseDTO {

    /**
     * 模块名称
     */
    @Schema(description = "模块名称")
    private String moduleName;

    /**
     * 操作地址
     */
    @Schema(description = "操作地址")
    private String operAddr;

    /**
     *  操作IP
     */
    @Schema(description = "操作IP")
    private String operIp;

    /**
     * 操作路径
     */
    @Schema(description = "操作路径")
    private String operUrl;

    /**
     * 操作参数
     */
    @Schema(description = "操作参数")
    private String operParam;

    /**
     * 操作标识
     */
    @Schema(description = "操作标识")
    private Integer operFlag;

    /**
     * 操作类型
     */
    @Schema(description = "操作类型")
    private Integer operType;

    /**
     * 方法名称
     */
    @Schema(description = "方法名称")
    private String methodName;

    /**
     * 方法请求类型
     */
    @Schema(description = "方法请求类型")
    private String methodRequest;

    /**
     * 返回状态
     */
    @Schema(description = "返回状态")
    private String resultCode;

    /**
     * 返回信息
     */
    @Schema(description = "返回信息")
    private String resultMsg;

    /**
     * 返回参数
     */
    @Schema(description = "返回参数")
    private String resultData;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createUserName;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 所属用户ID
     */
    @Schema(description = "所属用户ID")
    private Long scopeUserId;

    /**
     * 所属部门ID
     */
    @Schema(description = "所属部门ID")
    private Long scopeOrgId;


}
