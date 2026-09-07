package com.platform.mesh.upms.biz.modules.log.operate.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
                        
/**
 * @description 操作日志(LogOperate)实体类
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("log_operate")
public class LogOperate extends BasePO {

    /**
    * 日志ID
    */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 操作浏览器
     */
    private String operAgent;

    /**
     *  操作IP
     */
    private String operIp;

    /**
     * 操作地址
     */
    private String operAddr;

    /**
     * 操作路径
     */
    private String operUrl;

    /**
     * 操作参数
     */
    private String operParam;

    /**
     * 操作类型
     */
    private Integer operType;

    /**
     * 操作标识
     */
    private Integer loginType;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 方法请求类型
     */
    private String methodRequest;

    /**
     * 返回状态
     */
    private String resultCode;

    /**
     * 返回信息
     */
    private String resultMsg;

    /**
     * 返回参数
     */
    private String resultData;

    /**
     * 创建人
     */
    private String createUserName;

    /**
    * 创建时间
    */
    private LocalDateTime createTime;

    /**
     * 所属用户ID
     */
    private Long scopeUserId;

    /**
     * 所属部门ID
     */
    private Long scopeOrgId;


}

