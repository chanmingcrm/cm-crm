package com.platform.mesh.crm.biz.mcp.tool.crm.manual;

import cn.hutool.json.JSONObject;
import com.platform.mesh.crm.biz.mcp.tool.crm.AbstractCrmMcpToolResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * 功能描述:
 * 〈处理客户全景、健康度和经营摘要场景〉
 * @author qingfeng
 */
@Service
public class Customer360McpManual extends AbstractCrmMcpToolResult {

    private final CustomerMcpManual customerMcpManual;

    /**
     * 功能描述:
     * 〈处理客户全景、健康度和经营摘要场景〉
     * @param customerMcpManual 客户 MCP 业务处理器
     * @author qingfeng
     */
    public Customer360McpManual(CustomerMcpManual customerMcpManual) {
        this.customerMcpManual = customerMcpManual;
    }

    /**
     * 功能描述:
     * 〈获取指定客户的三百六十度经营视图〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object getCustomer360(JSONObject arguments, JSONObject executionConfig) {
        return execute(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈分析指定客户的健康度和流失风险〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object analyzeCustomerHealth(JSONObject arguments, JSONObject executionConfig) {
        return execute(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈生成指定客户的合同续签准备方案〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object prepareContractRenewal(JSONObject arguments, JSONObject executionConfig) {
        return execute(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈生成指定客户的多维经营摘要〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object generateCustomerSummary(JSONObject arguments, JSONObject executionConfig) {
        return execute(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈分析指定客户重点商机的成交风险〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object analyzeOpportunityRisk(JSONObject arguments, JSONObject executionConfig) {
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
        return process(arguments, customerMcpManual.getCustomer360(arguments, executionConfig));
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
        // 第一步：解析客户全景原始结果，空数据不生成无意义卡片。
        JSONObject source = source(executionResult);
        if (source == null) {
            return null;
        }

        // 第二步：客户主数据用于卡片标题和数据依据，关联数据汇总为多维经营指标。
        JSONObject customer = source.getJSONObject("customer");
        String sceneCode = source.getStr("sceneCode");
        String blockType = "OPPORTUNITY_RISK".equals(sceneCode)
                ? "opportunity" : "customer_overview";
        Map<String, Object> block = baseBlock(customer, blockType,
                subtitle(sceneCode));
        block.put("metrics", List.of(
                metric("联系人", sizeOf(source.get("contacts")), "人"),
                metric("进行中商机", sizeOf(source.get("businesses")), "个"),
                metric("近期跟进", sizeOf(source.get("follows")), "条"),
                metric("合同", contractCount(source.get("contracts")), "份")));
        // 第三步：按场景生成风险、机会或续签信号，并保留客户主数据依据。
        List<Map<String, Object>> signals = sceneSignals(sceneCode, source);
        if (!signals.isEmpty()) {
            block.put("signals", signals);
        }
        block.put("evidence", customerEvidence(customer));
        return appendBusinessBlock(executionResult, block);
    }

    /**
     * 功能描述:
     * 〈获取当前经营场景的卡片副标题〉
     * @param sceneCode 经营场景编码
     * @return 业务处理结果
     * @author qingfeng
     */
    private String subtitle(String sceneCode) {
        return switch (sceneCode == null ? "" : sceneCode) {
            case "CUSTOMER_HEALTH" -> "客户健康度与流失风险";
            case "CONTRACT_RENEWAL" -> "合同续签准备与沟通话术";
            case "CUSTOMER_SUMMARY" -> "客户多维经营摘要";
            case "OPPORTUNITY_RISK" -> "重点商机成交风险";
            default -> "客户360经营视图";
        };
    }

    /**
     * 功能描述:
     * 〈根据经营场景构建风险和机会信号〉
     * @param sceneCode 经营场景编码
     * @param source 场景原始数据
     * @return 业务处理结果
     * @author qingfeng
     */
    private List<Map<String, Object>> sceneSignals(String sceneCode, JSONObject source) {
        List<Map<String, Object>> signals = new ArrayList<>();
        int contacts = sizeOf(source.get("contacts"));
        int businesses = sizeOf(source.get("businesses"));
        int follows = sizeOf(source.get("follows"));
        int contracts = contractCount(source.get("contracts"));
        if ("CUSTOMER_HEALTH".equals(sceneCode)) {
            int score = 100 - (contacts == 0 ? 25 : 0) - (businesses == 0 ? 20 : 0)
                    - (contracts == 0 ? 15 : 0) - (follows == 0 ? 30 : 0);
            signals.add(signal("健康度结论", score >= 80 ? "健康" : score >= 60 ? "需关注" : "较高风险",
                    score >= 80 ? "success" : score >= 60 ? "warning" : "danger"));
            signals.add(signal("风险依据", "联系人" + contacts + "人、进行中商机" + businesses
                    + "个、近期跟进" + follows + "条、合同" + contracts + "份", "warning"));
            signals.add(signal("建议动作", contacts == 0
                    ? "优先补齐关键联系人，并确认需求、预算和下一步时间"
                    : "保持跟进节奏，推动明确商机或续签动作", "success"));
        } else if ("CONTRACT_RENEWAL".equals(sceneCode)) {
            signals.add(signal("续签现状", contracts == 0
                    ? "当前未发现有效合同，请先核实合同关系"
                    : "已关联" + contracts + "份合同，建议确认到期日与续签意向", "warning"));
            signals.add(signal("续签话术", "您好，想结合现有合作情况确认贵司下一周期的使用计划，"
                    + "并提前对齐续签范围、预算和审批时间。", "success"));
            signals.add(signal("下一步", "明确续签负责人、方案确认时间和下一次沟通节点", "success"));
        } else if ("CUSTOMER_SUMMARY".equals(sceneCode)) {
            signals.add(signal("经营概况", "联系人" + contacts + "人、商机" + businesses
                    + "个、近期跟进" + follows + "条、合同" + contracts + "份", "info"));
            signals.add(signal("待办建议", contacts == 0
                    ? "补齐联系人并确认下一步经营目标"
                    : "回顾最近约定并持续推进当前经营事项", "warning"));
        } else if ("OPPORTUNITY_RISK".equals(sceneCode)) {
            signals.add(signal("成交风险", businesses == 0
                    ? "未发现进行中商机，暂无法形成成交判断"
                    : "需核实商机阶段、决策人和预计成交时间", businesses == 0 ? "danger" : "warning"));
            signals.add(signal("积极信号", follows > 0
                    ? "存在" + follows + "条近期跟进，可据此继续推进"
                    : "暂未发现近期互动", follows > 0 ? "success" : "warning"));
            signals.add(signal("下一步动作", "确认阻塞点、最终决策人和明确的下一次跟进时间", "success"));
        }
        return signals;
    }
}