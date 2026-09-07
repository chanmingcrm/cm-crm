package com.platform.mesh.crm.biz.mcp.tool.crm.manual;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:
 * 〈处理经营分析场景的业务查询与结果加工〉
 * @author qingfeng
 */
@Service
public class BusinessMcpManual {

    private final CustomerMcpManual customerMcpManual;

    /**
     * 功能描述:
     * 〈处理经营分析场景的业务查询与结果加工〉
     * @param customerMcpManual 客户 MCP 业务处理器
     * @author qingfeng
     */
    public BusinessMcpManual(CustomerMcpManual customerMcpManual) {
        this.customerMcpManual = customerMcpManual;
    }

    /**
     * 功能描述:
     * 〈查询停滞商机并加工下一步动作〉
     * @param arguments MCP 工具参数
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object listStagnantOpportunities(JSONObject arguments) {
        return process(arguments, customerMcpManual.searchStagnantOpportunities(arguments));
    }

    /**
     * 功能描述:
     * 〈加工场景原始执行结果并生成业务卡片〉
     * @param arguments MCP 工具参数
     * @param executionResult 工具原始执行结果
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object process(JSONObject arguments, Object executionResult) {
        // 第一步：无业务数据时不生成空壳卡片，由上层按普通空结果处理。
        if (executionResult == null) {
            return null;
        }

        // 第二步：复制原始字段，保证分页、链路标识等扩展信息不会在加工阶段丢失。
        JSONObject source = JSONUtil.parseObj(executionResult);
        JSONArray records = source.getJSONArray("records");
        records = records == null ? new JSONArray() : records;
        Map<String, Object> response = new LinkedHashMap<>();
        source.forEach(response::put);

        // 第三步：根据确定性停滞依据构建前端可直接渲染的商机列表卡片。
        Map<String, Object> block = new LinkedHashMap<>();
        block.put("id", "stagnant-opportunities-" + System.currentTimeMillis());
        block.put("type", "opportunity_list");
        block.put("title", "停滞商机与下一步动作");
        block.put("subtitle", "CRM实时数据 · 按商机维度分析");
        block.put("status", Map.of("label", records.isEmpty() ? "暂无停滞商机"
                : "待推进 " + records.size() + " 项", "tone", records.isEmpty() ? "success" : "warning"));
        block.put("metrics", List.of(Map.of("label", "停滞商机", "value",
                source.getInt("total", records.size()), "unit", "个", "tone", "warning")));
        block.put("signals", records.stream().map(JSONUtil::parseObj).map(this::signal).toList());
        block.put("evidence", List.of(
                Map.of("label", "停滞规则", "value", "更新时间超过"
                        + source.getInt("stagnantDays", 30) + "天", "source", "CRM商机数据"),
                Map.of("label", "排序规则", "value", source.getStr("sortRule",
                        "风险优先级降序，同优先级按停滞天数降序"), "source", "CRM确定性规则")));
        response.put("businessBlocks", List.of(block));
        return response;
    }

    /**
     * 功能描述:
     * 〈构建业务卡片风险信号〉
     * @param item 单条业务数据
     * @return 业务处理结果
     * @author qingfeng
     */
    private Map<String, Object> signal(JSONObject item) {
        String evidence = item.getStr("stagnationEvidence", "超过停滞阈值");
        String action = item.getStr("nextAction", "请确认下一步推进动作");
        return Map.of(
                "label", item.getStr("opportunityName", "未命名商机"),
                "description", evidence + "；下一步：" + action,
                "metrics", List.of(
                        metric("客户", item.getStr("customerName", "未关联客户"), ""),
                        metric("阶段", item.getStr("stage", "未设置阶段"), ""),
                        metric("停滞天数", item.getLong("stagnantDays", 0L), "天"),
                        metric("商机金额", Convert.toBigDecimal(item.get("opportunityAmount"),
                                BigDecimal.ZERO), "元"),
                        metric("风险", item.getStr("riskLevel", "待评估"), "")),
                "tone", "高".equals(item.getStr("riskLevel")) ? "danger" : "warning");
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
    private Map<String, Object> metric(String label, Object value, String unit) {
        Map<String, Object> metric = new LinkedHashMap<>();
        metric.put("label", label);
        metric.put("value", value);
        if (!unit.isEmpty()) {
            metric.put("unit", unit);
        }
        return metric;
    }
}