package com.platform.mesh.utils.spring;

import cn.hutool.core.util.ArrayUtil;
import com.platform.mesh.core.constants.NumberConst;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @description spring工具类 方便在非spring管理环境中获取bean
 * @author 蝉鸣
 */
@Service
@Lazy(false)
public final class SpringContextHolderUtil implements ApplicationContextAware, DisposableBean, BeanFactoryPostProcessor {

	private final static Logger log = LoggerFactory.getLogger(SpringContextHolderUtil.class);

	/**
	 * Spring应用上下文环境
	 */
	private static ConfigurableListableBeanFactory beanFactory;

	/**
	 * -- GETTER --
	 *  取得存储在静态变量中的ApplicationContext.
	 */
	@Getter
	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口, 注入Context到静态变量中.
	 */
	@Override
	public void setApplicationContext(@NotNull ApplicationContext applicationContext) {
		SpringContextHolderUtil.applicationContext = applicationContext;
	}

	@Override
	public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
		SpringContextHolderUtil.beanFactory = beanFactory;
	}

	/**
	 * 获取对象
	 * @param name String
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws BeansException bean异常
	 * @author 蝉鸣
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) beanFactory.getBean(name);
	}

	/**
	 * 功能描述:
	 * 〈获取类型为requiredType的对象〉
	 * @param clz Class<T>
	 * @return <T>
	 * @throws BeansException bean异常
	 * @author 蝉鸣
	 */
	public static <T> T getBean(Class<T> clz) throws BeansException {
        return (T) beanFactory.getBean(clz);
	}

	/**
	 * 功能描述:
	 * 〈根据注解类型获取对象〉
	 * @param annotationType annotationType
	 * @return Map<String, Object>
	 * @author 蝉鸣
	 */
	public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType){
		return applicationContext.getBeansWithAnnotation(annotationType);
	}

	/**
	 * 功能描述:
	 * 〈如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true〉
	 * @param name bean name
	 * @return boolean
	 * @author 蝉鸣
	 */
	public static boolean containsBean(String name) {
		return beanFactory.containsBean(name);
	}

	/**
	 * 功能描述:
	 * 〈判断以给定名字注册的bean定义是一个singleton还是一个prototype。〉
	 * 	如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
	 * @param name name
	 * @return boolean
	 * @throws NoSuchBeanDefinitionException Bean异常
	 * @author 蝉鸣
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.isSingleton(name);
	}

	/**
	 * 功能描述:
	 * 〈如果给定的bean名字在bean定义中有类型，则返回这些类型〉
	 * @param name name
	 * @return Class 注册对象的类型
	 * @throws NoSuchBeanDefinitionException Bean异常
	 * @author 蝉鸣
	 */
	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.getType(name);
	}

	/**
	 *
	 * 功能描述:
	 * 〈如果给定的bean名字在bean定义中有别名，则返回这些别名〉
	 * @param name name
	 * @throws NoSuchBeanDefinitionException Bean异常
	 * @author 蝉鸣
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.getAliases(name);
	}

	/**
	 * 功能描述:
	 * 〈获取aop代理对象〉
	 * @param invoker invoker
	 * @author 蝉鸣
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getAopProxy(T invoker) {
		return (T) AopContext.currentProxy();
	}

	/**
	 * 获取当前运行环境
	 * @return 当前的环境配置列表
	 * @author 蝉鸣
	 */
	public static String[] getActiveProfiles() {
		return applicationContext.getEnvironment().getActiveProfiles();
	}

	/**
	 * 功能描述:
	 * 〈获取当前的环境配置，当有多个环境配置时，只获取第一个〉
	 * @return 当前的环境配置
	 * @author 蝉鸣
	 */
	public static String getActiveProfile() {
		final String[] activeProfiles = getActiveProfiles();
		return ArrayUtil.isNotEmpty(activeProfiles) ? activeProfiles[NumberConst.NUM_0] : null;
	}

	/**
	 * 功能描述:
	 * 〈发布事件〉
	 * @param event event
	 * @author 蝉鸣
	 */
	public static void publishEvent(ApplicationEvent event) {
		if (applicationContext == null) {
			return;
		}
		applicationContext.publishEvent(event);
	}

	/**
	 * 功能描述:
	 * 〈清除SpringContextHolder中的ApplicationContext为Null.〉
	 * @author 蝉鸣
	 */
	public static void clearHolder() {
		if (log.isDebugEnabled()) {
			log.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
		}
		applicationContext = null;
	}

	/**
	 * 功能描述:
	 * 〈实现DisposableBean接口, 在Context关闭时清理静态变量.〉
	 * @author 蝉鸣
	 */
	@Override
	public void destroy() {
		SpringContextHolderUtil.clearHolder();
	}

}