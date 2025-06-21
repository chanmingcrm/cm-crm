package com.platform.mesh.upms.api.modules.sys.log.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import com.platform.mesh.core.enums.custom.LoginTypeEnum;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 操作日志记录
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "操作日志记录")
public class LogOperateBO extends BaseBO {

	/**
	 * 模块名称
	 */
	@Schema(description = "模块名称")
	private String moduleName;

	/**
	 * 操作浏览器
	 */
	@Schema(description = "操作浏览器")
	private String operAgent;

	/**
	 *  操作IP
	 */
	@Schema(description = "操作IP")
	private String operIp;

	/**
	 * 操作地址
	 */
	@Schema(description = "操作地址")
	private String operAddr;

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
	 * 操作类型
	 */
	@SchemaEnum(value = OperateTypeEnum.class, description = "操作类型")
	private Integer operType;

	/**
	 * 操作标识
	 */
	@Schema(description = "操作标识")
	@SchemaEnum(value = LoginTypeEnum.class, description = "操作类型")
	private Integer loginType;

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
	 * 账户ID
	 */
	@Schema(description = "账户ID")
	private Long accountId;

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

}
