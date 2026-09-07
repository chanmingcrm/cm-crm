package com.platform.mesh.crm.biz.mcp.tool.factory;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.crm.biz.mcp.constant.CrmMcpToolConst;
import com.platform.mesh.crm.biz.mcp.tool.exception.CrmMcpToolExceptionEnum;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能描述:
 * 〈发现并注册 CRM MCP 工具方法，提供统一调用入口〉
 * @author qingfeng
 */
@Component
public class CrmMcpToolFactory implements BeanFactoryAware, SmartInitializingSingleton {

    private final Map<String, RegisteredTool> registry = new ConcurrentHashMap<>();
    private ConfigurableListableBeanFactory beanFactory;

    /**
     * 功能描述:
     * 〈保存用于发现 CRM 工具 Bean 的容器工厂〉
     * @param beanFactory Spring Bean 工厂
     * @author qingfeng
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (!(beanFactory instanceof ConfigurableListableBeanFactory configurableBeanFactory)) {
            throw new IllegalStateException("CRM MCP 工具注册需要 ConfigurableListableBeanFactory");
        }
        this.beanFactory = configurableBeanFactory;
    }

    /**
     * 功能描述:
     * 〈在单例初始化完成后扫描并注册工具方法〉
     * @author qingfeng
     */
    @Override
    public void afterSingletonsInstantiated() {
        // 第一步：遍历已经完成初始化的单例 Bean，避免提前实例化非必要组件。
        for (String beanName : beanFactory.getSingletonNames()) {
            Object bean = beanFactory.getSingleton(beanName);
            if (bean != null) {
                registerBean(bean);
            }
        }
    }

    /**
     * 功能描述:
     * 〈发现并注册单个 Bean 中声明的 MCP 工具方法〉
     * @param bean 工具方法所属 Bean
     * @author qingfeng
     */

    void registerBean(Object bean) {
        // 第二步：只提取声明 CrmMcpTool 注解的方法，并使用注解中的 toolName 注册。
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        Map<Method, CrmMcpTool> methods = MethodIntrospector.selectMethods(targetClass,
                (MethodIntrospector.MetadataLookup<CrmMcpTool>) method ->
                        AnnotatedElementUtils.findMergedAnnotation(method, CrmMcpTool.class));
        methods.forEach((method, annotation) -> register(annotation.value(), bean, method));
    }

    /**
     * 功能描述:
     * 〈调用数据库工具名称对应的 CRM 业务方法〉
     * @param toolName 数据库维护的工具名称
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object invoke(String toolName, String arguments, String executionConfig) {
        // 第一步：根据数据库 toolName 精确定位已注册工具，不执行场景字符串二次分发。
        RegisteredTool tool = registry.get(toolName);
        if (tool == null) {
            throw CrmMcpToolExceptionEnum.MCP_NO_REGISTER.getBaseException();
        }
        // 第二步：解析调用参数及数据库执行配置，并用默认配置补齐缺失参数。
        JSONObject input = parse(arguments);
        JSONObject config = parse(executionConfig);
        input.remove("sceneCode");
        mergeDefaults(input, config.getJSONObject("defaults"));
        // 第三步：按照工具方法签名传递参数并返回业务层结果。
        return tool.invoke(input, config);
    }

    /**
     * 功能描述:
     * 〈注册数据库工具名称对应的执行方法〉
     * @param toolName 数据库维护的工具名称
     * @param bean 工具方法所属 Bean
     * @param method 待注册或校验的方法
     * @author qingfeng
     */
    private void register(String toolName, Object bean, Method method) {
        // 第一步：校验工具名称和方法签名，阻止无效工具进入运行期注册表。
        if (toolName == null || toolName.isBlank()) {
            throw new IllegalStateException("CRM MCP 工具名称不能为空");
        }
        validateSignature(method);
        // 第二步：解析代理对象可调用的方法，并以原子方式写入注册表。
        Method invocableMethod = AopUtils.selectInvocableMethod(method, bean.getClass());
        ReflectionUtils.makeAccessible(invocableMethod);
        RegisteredTool previous = registry.putIfAbsent(toolName,
                new RegisteredTool(bean, invocableMethod));
        if (previous != null) {
            throw new IllegalStateException("CRM MCP 工具名称重复: " + toolName);
        }
    }

    /**
     * 功能描述:
     * 〈校验 MCP 工具方法参数签名〉
     * @param method 待注册或校验的方法
     * @author qingfeng
     */
    private void validateSignature(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        boolean supported = parameterTypes.length == 0
                || parameterTypes.length == 1 && parameterTypes[0] == JSONObject.class
                || parameterTypes.length == 2 && parameterTypes[0] == JSONObject.class
                && parameterTypes[1] == JSONObject.class;
        if (!supported) {
            throw new IllegalStateException("CRM MCP 工具方法参数仅支持 JSONObject arguments、executionConfig: "
                    + method.toGenericString());
        }
    }

    /**
     * 功能描述:
     * 〈将 JSON 文本解析为工具参数对象〉
     * @param json JSON 参数文本
     * @return 业务处理结果
     * @author qingfeng
     */
    private JSONObject parse(String json) {
        String value = json == null || json.isBlank()
                ? CrmMcpToolConst.EMPTY_ARGUMENTS : json;
        return JSONUtil.parseObj(value);
    }

    /**
     * 功能描述:
     * 〈将数据库执行配置合并到调用参数〉
     * @param input 本次调用参数
     * @param defaults 数据库维护的默认执行参数
     * @author qingfeng
     */
    private void mergeDefaults(JSONObject input, JSONObject defaults) {
        if (defaults == null) {
            return;
        }
        defaults.forEach((key, value) -> {
            if (!input.containsKey(key) || input.get(key) == null) {
                input.set(key, value);
            }
        });
    }

    /**
     * 功能描述:
     * 〈保存已注册工具的目标对象和执行方法〉
     * @param bean 工具方法所属 Bean
     * @param method 待注册或校验的方法
     * @author qingfeng
     */
    private record RegisteredTool(Object bean, Method method) {
        /**
         * 功能描述:
         * 〈调用数据库工具名称对应的 CRM 业务方法〉
         * @param arguments MCP 工具参数
         * @param executionConfig 数据库维护的工具执行配置
         * @return 业务处理结果
         * @author qingfeng
         */
        private Object invoke(JSONObject arguments, JSONObject executionConfig) {
            return switch (method.getParameterCount()) {
                case 0 -> ReflectionUtils.invokeMethod(method, bean);
                case 1 -> ReflectionUtils.invokeMethod(method, bean, arguments);
                case 2 -> ReflectionUtils.invokeMethod(method, bean, arguments, executionConfig);
                default -> throw new IllegalStateException("不支持的 CRM MCP 工具方法签名");
            };
        }
    }
}