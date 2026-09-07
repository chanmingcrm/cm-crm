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
 * 〈处理客户经营优先级与管理分析场景〉
 * @author qingfeng
 */
@Service
public class CustomerPriorityMcpManual {

    private final CustomerMcpManual customerMcpManual;

    /**
     * 功能描述:
     * 〈处理客户经营优先级与管理分析场景〉
     * @param customerMcpManual 客户 MCP 业务处理器
     * @author qingfeng
     */
    public CustomerPriorityMcpManual(CustomerMcpManual customerMcpManual) {
        this.customerMcpManual = customerMcpManual;
    }

    /**
     * 功能描述:
     * 〈按确定性规则计算客户风险优先级〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object rankCustomerRisks(JSONObject arguments, JSONObject executionConfig) {
        return process(arguments, execute(arguments, executionConfig));
    }

    /**
     * 功能描述:
     * 〈查询长期未跟进客户〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object listLongInactiveCustomers(JSONObject arguments, JSONObject executionConfig) {
        return execute(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈查询逾期未跟进客户〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object listOverdueFollowCustomers(JSONObject arguments, JSONObject executionConfig) {
        return execute(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈查询今天需要执行的销售任务〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object getTodaySalesTasks(JSONObject arguments, JSONObject executionConfig) {
        return execute(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈查询逾期未完成的销售任务〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object getUnfinishedSalesTasks(JSONObject arguments, JSONObject executionConfig) {
        return execute(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈查询存在历史成交的沉睡客户〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object listDormantCustomers(JSONObject arguments, JSONObject executionConfig) {
        return execute(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈检查客户经营数据完整性〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object checkCustomerDataCompleteness(JSONObject arguments, JSONObject executionConfig) {
        return execute(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈生成销售团队经营晨报〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object generateTeamDailyReport(JSONObject arguments, JSONObject executionConfig) {
        return execute(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈分析销售团队工作负荷〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object analyzeTeamWorkload(JSONObject arguments, JSONObject executionConfig) {
        return execute(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈分析受控 CRM 经营指标〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object analyzeBusinessMetrics(JSONObject arguments, JSONObject executionConfig) {
        return execute(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈查询当前主动经营风险预警〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object listActiveRiskAlerts(JSONObject arguments, JSONObject executionConfig) {
        return execute(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈根据经营风险生成销售任务草稿〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object generateTaskDraft(JSONObject arguments, JSONObject executionConfig) {
        return execute(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈执行当前场景对应的客户经营查询〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    private Object execute(JSONObject arguments, JSONObject executionConfig) {
        return customerMcpManual.searchCustomerPriorities(arguments, executionConfig);
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
        // 第一步：无业务数据时不生成默认客户卡片，避免答非所问。
        if (executionResult == null) {
            return null;
        }

        // 第二步：保留原始规则版本、阈值及明细字段，在其基础上追加展示卡片。
        JSONObject source = JSONUtil.parseObj(executionResult);
        JSONArray items = source.getJSONArray("items");
        items = items == null ? new JSONArray() : items;
        int inactiveDays = source.getInt("inactiveDays", 30);
        Map<String, Object> response = new LinkedHashMap<>();
        source.forEach(response::put);

        Map<String, Object> block = new LinkedHashMap<>();
        // 第三步：根据后端确定性分值构建指标、风险信号和行动建议。
        block.put("id", "customer-priorities-" + System.currentTimeMillis());
        block.put("type", "daily_action");
        block.put("title", "今日客户经营优先级");
        block.put("subtitle", "后端规则排序 · AI仅解释原因");
        block.put("status", Map.of("label", items.isEmpty() ? "暂无任务"
                : "待处理 " + items.size() + " 项", "tone", items.isEmpty() ? "success" : "warning"));
        long inactiveCount = items.stream().map(JSONUtil::parseObj)
                .filter(item -> item.getLong("inactiveDays", 0L) >= inactiveDays).count();
        block.put("metrics", List.of(
                Map.of("label", "重点风险客户", "value", items.size(), "unit", "家", "tone", "primary"),
                Map.of("label", "长期未跟进", "value", inactiveCount, "unit", "家", "tone", "warning")));
        block.put("signals", items.stream().map(JSONUtil::parseObj).map(this::signal).toList());
        block.put("evidence", List.of(
                Map.of("label", "排序规则", "value", "长期未跟进50分、存在商机30分、存在未回款20分",
                        "source", "CRM确定性规则"),
                Map.of("label", "行动建议", "value", "优先联系排名靠前且存在商机的客户，确认需求、决策进度并约定下次跟进时间",
                        "source", "CRM经营建议")));
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
        return Map.of(
                "label", item.getStr("customerName", "未命名客户"),
                "description", item.getStr("recommendation", "请确认下一步经营动作"),
                "metrics", List.of(
                        metric("优先级分", item.getInt("priorityScore", 0), "分", "warning"),
                        metric("未跟进天数", item.getLong("inactiveDays", 0L), "天", null),
                        metric("商机金额", Convert.toBigDecimal(item.get("opportunityAmount"), BigDecimal.ZERO), "元", "primary"),
                        metric("未回款", Convert.toBigDecimal(item.get("unreceivedMoney"), BigDecimal.ZERO), "元", "danger"),
                        metric("风险", item.getStr("risk", "待评估"), "", "warning")),
                "tone", item.getInt("priorityScore", 0) >= 50 ? "warning" : "info");
    }

    /**
     * 功能描述:
     * 〈构建业务卡片指标〉
     * @param label 展示标签
     * @param value 待处理的业务值
     * @param unit 指标单位
     * @param tone 展示颜色语义
     * @return 业务处理结果
     * @author qingfeng
     */
    private Map<String, Object> metric(String label, Object value, String unit, String tone) {
        Map<String, Object> metric = new LinkedHashMap<>();
        metric.put("label", label);
        metric.put("value", value);
        if (!unit.isEmpty()) {
            metric.put("unit", unit);
        }
        if (tone != null) {
            metric.put("tone", tone);
        }
        return metric;
    }
}