package com.platform.mesh.crm.biz.mcp.tool.crm.manual;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;
import com.platform.mesh.crm.biz.mcp.tool.crm.AbstractCrmMcpToolResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 功能描述:
 * 〈处理客户拜访准备与沟通建议业务〉
 * @author qingfeng
 */
@Service
public class CustomerVisitMcpManual extends AbstractCrmMcpToolResult {

    private final CustomerMcpManual customerMcpManual;

    /**
     * 功能描述:
     * 〈处理客户拜访准备与沟通建议业务〉
     * @param customerMcpManual 客户 MCP 业务处理器
     * @author qingfeng
     */
    public CustomerVisitMcpManual(CustomerMcpManual customerMcpManual) {
        this.customerMcpManual = customerMcpManual;
    }

    /**
     * 功能描述:
     * 〈生成指定客户的拜访前准备内容〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object prepareCustomerVisit(JSONObject arguments, JSONObject executionConfig) {
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
        JSONObject source = source(executionResult);
        if (source == null) {
            return null;
        }

        // 依据联系人、跟进、商机和合同数据生成拜访前检查项。
        JSONObject customer = source.getJSONObject("customer");
        Map<String, Object> block = baseBlock(customer, "customer_overview", "客户拜访准备");
        List<Map<String, Object>> signals = new ArrayList<>();
        signals.add(signal("客户概况", "客户编码：" + value(customer, "code", "dataMac"), "info"));
        signals.add(signal("联系人", sizeOf(source.get("contacts")) == 0
                ? "暂无联系人，拜访前需确认关键联系人和决策角色"
                : "已维护 " + sizeOf(source.get("contacts")) + " 位联系人", "info"));
        JSONObject latestFollow = latestFollow(source);
        signals.add(signal("最近跟进", latestFollow == null
                ? "暂无跟进记录，建议先确认客户当前需求"
                : value(latestFollow, "description", "name"), "info"));
        signals.add(signal("提醒任务", latestFollow == null
                ? "本次拜访结束前约定下一次跟进时间"
                : "下次跟进：" + formatTime(latestFollow.get("nextTime")), "warning"));
        signals.add(signal("风险提醒", sizeOf(source.get("contacts")) == 0
                ? "缺少关键联系人；优先确认对接人、决策人和审批流程"
                : "核实当前需求、预算与决策流程是否发生变化", "warning"));
        signals.add(signal("开场话术", "您好，我是负责贵司业务的销售顾问。今天想结合上次沟通事项，"
                + "先确认当前需求进展，再一起明确下一步安排。", "success"));
        signals.add(signal("沟通目标", "确认当前需求、预算、决策人以及明确的下一步时间", "success"));
        block.put("signals", signals);
        block.put("metrics", List.of(
                metric("联系人", sizeOf(source.get("contacts")), "人"),
                metric("商机", sizeOf(source.get("businesses")), "个"),
                metric("合同", contractCount(source.get("contracts")), "份")));
        return appendBusinessBlock(executionResult, block);
    }

    /**
     * 功能描述:
     * 〈提取客户最近一次跟进信息〉
     * @param source 场景原始数据
     * @return 业务处理结果
     * @author qingfeng
     */
    private JSONObject latestFollow(JSONObject source) {
        JSONArray follows = source.getJSONArray("follows");
        return follows == null || follows.isEmpty() ? null : follows.getJSONObject(0);
    }

    /**
     * 功能描述:
     * 〈将业务时间格式化为易读文本〉
     * @param value 待处理的业务值
     * @return 业务处理结果
     * @author qingfeng
     */
    private String formatTime(Object value) {
        if (value instanceof Number number) {
            LocalDateTime dateTime = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(number.longValue()), ZoneId.systemDefault());
            return dateTime.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm"));
        }
        String text = value == null ? null : value.toString();
        return text == null || text.isBlank() ? "未设置" : text.replace('T', ' ');
    }
}