package com.platform.mesh.crm.biz.mcp.tool.factory;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能描述:
 * 〈声明数据库工具名称对应的 CRM MCP 业务入口〉
 * @author qingfeng
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CrmMcpTool {

    /**
     * 功能描述:
     * 〈按候选字段顺序获取首个有效文本值〉
     * @return 业务处理结果
     * @author qingfeng
     */
    String value();
}