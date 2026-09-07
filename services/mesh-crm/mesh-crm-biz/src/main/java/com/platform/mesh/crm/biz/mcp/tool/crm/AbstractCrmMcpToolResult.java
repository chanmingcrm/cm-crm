package com.platform.mesh.crm.biz.mcp.tool.crm;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:
 * 〈提供 CRM MCP 场景结果卡片的通用加工能力〉
 * @author qingfeng
 */
public abstract class AbstractCrmMcpToolResult {
    /**
     * 功能描述:
     * 〈在原始执行结果中追加业务展示卡片〉
     * @param executionResult 工具原始执行结果
     * @param block 待追加的业务卡片
     * @return 业务处理结果
     * @author qingfeng
     */
    protected Object appendBusinessBlock(Object executionResult, Map<String, Object> block) {
        if (executionResult == null) {
            return null;
        }
        JSONObject source = JSONUtil.parseObj(executionResult);
        Map<String, Object> response = new LinkedHashMap<>();
        source.forEach(response::put);
        response.put("businessBlocks", List.of(block));
        return response;
    }

    /**
     * 功能描述:
     * 〈将原始执行结果转换为 JSON 对象〉
     * @param executionResult 工具原始执行结果
     * @return 业务处理结果
     * @author qingfeng
     */
    protected JSONObject source(Object executionResult) {
        return executionResult == null ? null : JSONUtil.parseObj(executionResult);
    }

    /**
     * 功能描述:
     * 〈构建客户场景通用业务卡片〉
     * @param customer 客户数据
     * @param type 业务卡片类型
     * @param subtitle 业务卡片副标题
     * @return 业务处理结果
     * @author qingfeng
     */
    protected Map<String, Object> baseBlock(JSONObject customer, String type, String subtitle) {
        Map<String, Object> block = new LinkedHashMap<>();
        block.put("id", type + "-" + value(customer, "id"));
        block.put("type", type);
        block.put("title", value(customer, "name", "dataName"));
        block.put("subtitle", subtitle);
        block.put("status", Map.of("label", "实时数据", "tone", "success"));
        return block;
    }

    /**
     * 功能描述:
     * 〈构建客户基本信息的数据依据〉
     * @param customer 客户数据
     * @return 业务处理结果
     * @author qingfeng
     */
    protected List<Map<String, Object>> customerEvidence(JSONObject customer) {
        return List.of(
                Map.of("label", "客户编码", "value", value(customer, "code", "dataMac"),
                        "source", "CRM客户主数据"),
                Map.of("label", "客户名称", "value", value(customer, "name", "dataName"),
                        "source", "CRM客户主数据"));
    }

    /**
     * 功能描述:
     * 〈构建业务卡片指标〉
     * @param label 展示标签
     * @param value 待处理的业务值
     * @param unit 指标单位
     * @return 业务处理结果
     * @author qingfeng
     */
    protected Map<String, Object> metric(String label, Object value, String unit) {
        return Map.of("label", label, "value", value, "unit", unit);
    }

    /**
     * 功能描述:
     * 〈构建业务卡片风险信号〉
     * @param label 展示标签
     * @param description 业务描述
     * @param tone 展示颜色语义
     * @return 业务处理结果
     * @author qingfeng
     */
    protected Map<String, Object> signal(String label, String description, String tone) {
        return Map.of("label", label, "description", description, "tone", tone);
    }

    /**
     * 功能描述:
     * 〈统计合同结果中的记录数量〉
     * @param contractsValue 合同查询结果
     * @return 业务处理结果
     * @author qingfeng
     */
    protected int contractCount(Object contractsValue) {
        if (contractsValue == null) return 0;
        JSONObject contracts = JSONUtil.parseObj(contractsValue);
        return sizeOf(contracts.get("records"));
    }

    /**
     * 功能描述:
     * 〈计算集合或分页结果的记录数量〉
     * @param value 待处理的业务值
     * @return 业务处理结果
     * @author qingfeng
     */
    protected int sizeOf(Object value) {
        return value instanceof Collection<?> collection ? collection.size() : 0;
    }

    /**
     * 功能描述:
     * 〈按候选字段顺序获取首个有效文本值〉
     * @param object 待读取的 JSON 对象
     * @param keys 候选字段名称
     * @return 业务处理结果
     * @author qingfeng
     */
    protected String value(JSONObject object, String... keys) {
        if (object == null) return "--";
        for (String key : keys) {
            String value = Convert.toStr(object.get(key));
            if (StrUtil.isNotBlank(value)) return value;
        }
        return "--";
    }
}