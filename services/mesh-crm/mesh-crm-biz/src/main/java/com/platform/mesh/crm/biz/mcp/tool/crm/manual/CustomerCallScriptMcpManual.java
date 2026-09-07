package com.platform.mesh.crm.biz.mcp.tool.crm.manual;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.platform.mesh.crm.biz.mcp.tool.crm.AbstractCrmMcpToolResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:
 * 〈根据客户经营数据生成电话沟通话术〉
 * @author qingfeng
 */
@Service
public class CustomerCallScriptMcpManual extends AbstractCrmMcpToolResult {

    private final CustomerMcpManual customerMcpManual;

    /**
     * 功能描述:
     * 〈根据客户经营数据生成电话沟通话术〉
     * @param customerMcpManual 客户 MCP 业务处理器
     * @author qingfeng
     */
    public CustomerCallScriptMcpManual(CustomerMcpManual customerMcpManual) {
        this.customerMcpManual = customerMcpManual;
    }

    /**
     * 功能描述:
     * 〈生成指定客户的电话沟通话术〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object generateCustomerCallScript(JSONObject arguments, JSONObject executionConfig) {
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
        // 第一步：解析客户全景数据，空结果交由上层按普通空响应处理。
        JSONObject source = source(executionResult);
        if (source == null) {
            return null;
        }
        // 第二步：提取客户基本信息、近期跟进和经营数据，形成针对性沟通话术。
        JSONObject customer = source.getJSONObject("customer");
        String customerName = value(customer, "name", "dataName");
        Map<String, Object> block = baseBlock(customer, "customer_overview", "客户信息与电话沟通话术");
        List<Map<String, Object>> signals = new ArrayList<>();
        signals.add(signal("客户基本信息", "客户编码：" + value(customer, "code", "dataMac")
                + "；联系电话：" + value(customer, "phone"), "info"));
        signals.add(signal("最近跟进", latestFollow(source), "info"));
        signals.add(signal("开场话术", "您好，我是负责贵司业务的销售顾问。想结合我们上次沟通的事项，"
                + "占用您几分钟确认当前进展和下一步安排。", "success"));
        signals.add(signal("需求确认", "目前最需要优先解决的问题是什么？预算、决策人和计划时间是否已有变化？",
                "warning"));
        signals.add(signal("推进目标", "通话结束前确认负责人、待办事项以及双方认可的下一次跟进时间。", "success"));
        block.put("signals", signals);
        block.put("metrics", List.of(
                metric("近期跟进", sizeOf(source.get("follows")), "条"),
                metric("进行中商机", sizeOf(source.get("businesses")), "个"),
                metric("合同", contractCount(source.get("contracts")), "份")));
        // 第三步：补充数据依据与场景摘要，返回前端可直接渲染的话术卡片。
        block.put("evidence", customerEvidence(customer));
        block.put("summary", "已根据" + customerName + "的客户信息、近期跟进、商机和合同生成话术");
        return appendBusinessBlock(executionResult, block);
    }

    /**
     * 功能描述:
     * 〈提取客户最近一次跟进信息〉
     * @param source 场景原始数据
     * @return 业务处理结果
     * @author qingfeng
     */
    private String latestFollow(JSONObject source) {
        JSONArray follows = source.getJSONArray("follows");
        if (follows == null || follows.isEmpty()) {
            return "暂无历史跟进，建议先确认客户当前需求和沟通背景";
        }
        JSONObject follow = follows.getJSONObject(0);
        return value(follow, "description", "name");
    }
}