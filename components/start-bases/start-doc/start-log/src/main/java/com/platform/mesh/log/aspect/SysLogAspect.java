package com.platform.mesh.log.aspect;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.log.event.SysOperationLogEvent;
import com.platform.mesh.log.exception.LogExceptionEnum;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.security.utils.SecurityUtils;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogOperateBO;
import com.platform.mesh.utils.http.IpUtil;
import com.platform.mesh.utils.result.Result;
import com.platform.mesh.utils.spring.ServletUtil;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @description 操作日志记录处理
 * @author 蝉鸣
 */
@Aspect
public class SysLogAspect {

	private static final Logger log = LoggerFactory.getLogger(SysLogAspect.class);

	/**
	 * 配置切入点
	 */
	@Pointcut("@annotation(com.platform.mesh.log.annotation.Log)")
	public void logPointCut() {
	}

	/**
	 * 处理完请求后执行
	 * @param joinPoint 切点
	 */
	@AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
	public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
		handleLog(joinPoint, null, jsonResult);
	}

	/**
	 * 拦截异常操作
	 * @param joinPoint 切点
	 * @param e 异常
	 */
	@AfterThrowing(value = "logPointCut()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
		handleLog(joinPoint, e, null);
	}

	/**
	 * 功能描述:
	 * 〈构建操作日志〉
	 * @param joinPoint joinPoint
	 * @param e e
	 * @param jsonResult jsonResult
	 */
	protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
		try {
			// 获得注解
			Log controllerLog = getAnnotationLog(joinPoint);
			if (controllerLog == null) {
				return;
			}
			LogOperateBO operationLog = new LogOperateBO();
			// 获取当前的用户
			LoginUserBO loginUserBO = SecurityUtils.getLoginUser();
			if (ObjectUtil.isNotEmpty(loginUserBO)) {
				operationLog.setAccountId(loginUserBO.getAccountId());
				operationLog.setCreateUserName(loginUserBO.getUsername());
			}
			// *========数据库日志=========*//
			// 请求的地址
			HttpServletRequest request = ServletUtil.getRequestInst();
			operationLog.setOperAgent(request.getHeader(HttpHeaders.USER_AGENT));
			operationLog.setOperIp(IpUtil.getHostIp());
			operationLog.setOperAddr(IpUtil.getIpAddr(operationLog.getOperIp()));
			operationLog.setOperUrl(request.getRequestURI());

			// 返回参数:保存返回值最大限制2000，避免数据太大报错
			if(jsonResult instanceof Result<?> result){
                operationLog.setResultCode(result.getCode().toString());
				operationLog.setResultMsg(result.getMsg());
				if (controllerLog.isSaveResponseData()) {
					operationLog.setResultData(StrUtil.sub(JSONUtil.toJsonStr(jsonResult),NumberConst.NUM_0, NumberConst.NUM_2000));
				}
			}else{
				operationLog.setResultCode(StrUtil.toString(HttpStatus.OK.value()));
				operationLog.setResultMsg(HttpStatus.OK.getReasonPhrase());
				if (controllerLog.isSaveResponseData()) {
					operationLog.setResultData(StrUtil.sub(StrUtil.toString(jsonResult), NumberConst.NUM_0, NumberConst.NUM_2000));
				}
				if (ObjectUtil.isNotEmpty(e)) {
					operationLog.setResultCode(StrUtil.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()));
					operationLog.setResultMsg(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
					if (controllerLog.isSaveResponseData()) {
						operationLog.setResultData(StrUtil.sub(e.getMessage(), NumberConst.NUM_0, NumberConst.NUM_2000));
					}
				}
			}

			// 设置方法名称
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			operationLog.setMethodName(className.concat(SymbolConst.COMMA).concat(methodName).concat(SymbolConst.OPEN_PARENTHESIS).concat(SymbolConst.CLOSE_PARENTHESIS));
			// 设置请求方式
			operationLog.setMethodRequest(ServletUtil.getRequestInst().getMethod());
			// 处理设置注解上的参数
			getControllerMethodDescription(joinPoint, controllerLog, operationLog);
			// 发送Spring事件保存数据库
			SpringContextHolderUtil.publishEvent(new SysOperationLogEvent(operationLog));
		}
		catch (Exception exp) {
			// 记录本地异常日志
			log.error("==前置通知异常==");
			log.error("异常信息:{}", exp.getMessage());
			throw LogExceptionEnum.LOG_NO_ARGS.getBaseException(exp);
		}
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * @param log 日志
	 * @param operationLog 操作日志
	 */
	public void getControllerMethodDescription(JoinPoint joinPoint, Log log, LogOperateBO operationLog) {
		// 设置action动作
		operationLog.setOperType(log.operateType().getValue());
		// 设置标题
		operationLog.setModuleName(log.moduleName());
		// 设置操作人类别
		operationLog.setLoginType(log.loginType().getValue());
		// 是否需要保存request，参数和值
		if (log.isSaveRequestData()) {
			// 获取参数的信息，传入到数据库中。
			setRequestValue(joinPoint, operationLog);
		}
	}

	/**
	 * 获取请求的参数，放到log中
	 * @param operationLog 操作日志
	 */
	private void setRequestValue(JoinPoint joinPoint, LogOperateBO operationLog) {
		String requestMethod = operationLog.getMethodRequest();
		if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
			String params;
			String contentType = ServletUtil.getRequestInst().getContentType();
			if (!StrUtil.isEmpty(contentType) && contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
				params = "FILE";
			}
			else {
				try {
					params = argsArrayToString(joinPoint.getArgs());
				}
				catch (IOException e) {
					throw LogExceptionEnum.LOG_NO_ARGS.getBaseException();
				}
			}

			operationLog.setOperParam(StringUtils.substring(params, 0, 2000));
		}
		else {
			Map<?, ?> paramsMap = (Map<?, ?>) ServletUtil.getRequestInst()
					.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			operationLog.setOperParam(StringUtils.substring(paramsMap.toString(), 0, 1000));
		}
	}

	/**
	 * 是否存在注解，如果存在就获取
	 * @param joinPoint JoinPoint
	 * @return Log
	 */
	private Log getAnnotationLog(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		if (method != null) {
			return method.getAnnotation(Log.class);
		}
		return null;
	}

	/**
	 * 参数拼装
	 */
	private String argsArrayToString(Object[] paramsArray) throws IOException {
		StringBuilder params = new StringBuilder();
		if (paramsArray != null) {
			for (Object o : paramsArray) {
				if (!isFilterObject(o)) {
					params.append(JSONUtil.toJsonStr(o)).append(" ");
				}
			}
		}
		return params.toString().trim();
	}

	/**
	 * 判断是否需要过滤的对象。
	 * @param o 对象信息。
	 * @return 如果是需要过滤的对象，则返回true；否则返回false。
	 */
	public boolean isFilterObject(final Object o) {
		return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
	}

}
