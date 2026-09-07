package com.platform.mesh.crm.biz.mcp.tool.crm.manual;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.crm.biz.mcp.constant.CrmMcpToolConst;
import com.platform.mesh.crm.biz.modules.crm.oncontract.domain.po.CrmOnContract;
import com.platform.mesh.crm.biz.modules.crm.oncontract.service.ICrmOnContractService;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.po.CrmPreCustomer;
import com.platform.mesh.crm.biz.modules.crm.precustomer.service.ICrmPreCustomerService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 功能描述:
 * 〈处理合同查询、到期提醒和异常分析业务〉
 * @author qingfeng
 */
@Service
public class ContractMcpManual {

    private static final String CONTRACT_EXCEPTION_SCENE = "CONTRACT_EXCEPTIONS";

    private final ICrmOnContractService contractService;
    private final ICrmPreCustomerService customerService;

    /**
     * 功能描述:
     * 〈处理合同查询、到期提醒和异常分析业务〉
     * @param contractService 合同业务服务
     * @param customerService 客户业务服务
     * @author qingfeng
     */
    public ContractMcpManual(ICrmOnContractService contractService,
                             ICrmPreCustomerService customerService) {
        this.contractService = contractService;
        this.customerService = customerService;
    }

    /**
     * 功能描述:
     * 〈分页查询当前账号可见合同〉
     * @param arguments MCP 工具参数
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object searchContracts(JSONObject arguments) {
        return searchContracts(arguments, new JSONObject());
    }

    /**
     * 功能描述:
     * 〈查询即将到期的合同列表〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object listExpiringContracts(JSONObject arguments, JSONObject executionConfig) {
        return searchContracts(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈查询合同异常并生成处理建议〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object listContractExceptions(JSONObject arguments, JSONObject executionConfig) {
        return searchContracts(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈分页查询当前账号可见合同〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object searchContracts(JSONObject arguments, JSONObject executionConfig) {
        String keyword = arguments.getStr(CrmMcpToolConst.ARGUMENT_KEYWORD);
        Long customerId = arguments.getLong(CrmMcpToolConst.ARGUMENT_CUSTOMER_ID);
        Integer page = arguments.getInt(CrmMcpToolConst.ARGUMENT_PAGE);
        Integer pageSize = arguments.getInt(CrmMcpToolConst.ARGUMENT_PAGE_SIZE);
        Integer endingWithinDays = arguments.getInt(CrmMcpToolConst.ARGUMENT_ENDING_WITHIN_DAYS);
        // 第一步：规范分页、关键字及到期天数，限制异常参数影响查询范围。
        int safePage = page == null || page < CrmMcpToolConst.DEFAULT_PAGE
                ? CrmMcpToolConst.DEFAULT_PAGE : page;
        int safePageSize = pageSize == null || pageSize < CrmMcpToolConst.DEFAULT_PAGE
                ? CrmMcpToolConst.DEFAULT_PAGE_SIZE : Math.min(pageSize, CrmMcpToolConst.MAX_PAGE_SIZE);
        String normalizedKeyword = StrUtil.trim(keyword);
        Integer safeEndingWithinDays = endingWithinDays == null ? null
                : Math.max(1, Math.min(endingWithinDays, 3650));
        LocalDateTime now = LocalDateTime.now();
        String sceneCode = executionConfig.getStr("sceneCode");
        // 第二步：按数据库可信场景限定查询范围，避免到期提醒与异常合同混用。
        LambdaQueryWrapper<CrmOnContract> query = buildContractQuery(
                normalizedKeyword, customerId, safeEndingWithinDays, sceneCode, now);
        MPage<CrmOnContract> result = contractService.page(new MPage<>(safePage, safePageSize), query);
        List<Object> items = result.getRecords().stream().map(this::toContractSummary).toList();

        // 第三步：仅到期提醒场景聚合客户名称、金额及续签优先级卡片。
        if (safeEndingWithinDays != null && !CONTRACT_EXCEPTION_SCENE.equals(sceneCode)) {
            List<Long> customerIds = result.getRecords().stream()
                    .map(CrmOnContract::getCustomerId).filter(Objects::nonNull).distinct().toList();
            Map<Long, String> customerNames = customerIds.isEmpty() ? Collections.emptyMap()
                    : customerService.listByIds(customerIds).stream().collect(Collectors.toMap(
                            CrmPreCustomer::getId, CrmPreCustomer::getDataName,
                            (left, right) -> left));
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("sceneCode", executionConfig.getStr("sceneCode"));
            response.put("records", items);
            response.put("total", result.getTotal());
            response.put("current", safePage);
            response.put("size", safePageSize);
            response.put("businessBlocks", List.of(toExpiryBusinessBlock(
                    result.getRecords(), customerNames, result.getTotal(),
                    safeEndingWithinDays, now)));
            return response;
        }
        return new MPage<>().setRecords(items).setTotal(result.getTotal())
                .setCurrent(safePage).setSize(safePageSize);
    }

    /**
     * 功能描述:
     *
     * 〈按可信场景构建合同查询条件〉
     *
     * @param keyword 合同关键字
     * @param customerId 客户 ID
     * @param endingWithinDays 到期天数
     * @param sceneCode 数据库可信场景编码
     * @param now 当前时间
     * @return 合同查询条件
     * @author qingfeng
     */
    static LambdaQueryWrapper<CrmOnContract> buildContractQuery(String keyword,
            Long customerId, Integer endingWithinDays, String sceneCode,
            LocalDateTime now) {
        boolean exceptionScene = CONTRACT_EXCEPTION_SCENE.equals(sceneCode);
        boolean expiringScene = endingWithinDays != null && !exceptionScene;
        return new LambdaQueryWrapper<CrmOnContract>()
                .like(StrUtil.isNotBlank(keyword), CrmOnContract::getDataName, keyword)
                .eq(customerId != null && customerId > 0,
                        CrmOnContract::getCustomerId, customerId)
                .and(exceptionScene, wrapper -> wrapper
                        .lt(CrmOnContract::getEndTime, now)
                        .or()
                        .gt(CrmOnContract::getUnreceivedMoney, BigDecimal.ZERO))
                .ge(expiringScene, CrmOnContract::getEndTime, now)
                .le(expiringScene, CrmOnContract::getEndTime,
                        endingWithinDays == null ? null : now.plusDays(endingWithinDays))
                .eq(CrmOnContract::getDelFlag, NumberConst.NUM_1)
                .orderByAsc(expiringScene, CrmOnContract::getEndTime)
                .orderByDesc(!expiringScene, CrmOnContract::getUpdateTime);
    }

    /**
     * 功能描述:
     * 〈构造即将到期合同经营卡片〉
     * @param contracts 当前页合同
     * @param customerNames 客户名称映射
     * @param total 合同总数
     * @param endingWithinDays 到期范围
     * @param now 当前时间
     * @return 合同到期提醒卡片
     * @author qingfeng
     */
    static Map<String, Object> toExpiryBusinessBlock(List<CrmOnContract> contracts,
            Map<Long, String> customerNames, long total, int endingWithinDays,
            LocalDateTime now) {
        BigDecimal totalMoney = contracts.stream().map(CrmOnContract::getTotalMoney)
                .filter(value -> value != null).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal unreceivedMoney = contracts.stream().map(CrmOnContract::getUnreceivedMoney)
                .filter(value -> value != null).reduce(BigDecimal.ZERO, BigDecimal::add);
        List<Object> signals = contracts.stream()
                .sorted(Comparator.comparingInt((CrmOnContract contract) ->
                        -renewalPriorityScore(contract, now))
                        .thenComparing(CrmOnContract::getEndTime,
                                Comparator.nullsLast(Comparator.naturalOrder())))
                .limit(20).map(contract -> {
            long remainingDays = Math.max(0, ChronoUnit.DAYS.between(now, contract.getEndTime()));
            int priorityScore = renewalPriorityScore(contract, now);
            Map<String, Object> signal = new LinkedHashMap<>();
            signal.put("label", StrUtil.blankToDefault(customerNames.get(contract.getCustomerId()),
                    "未关联客户") + " · " + StrUtil.blankToDefault(contract.getDataName(), "未命名合同"));
            signal.put("priorityScore", priorityScore);
            signal.put("description", "到期时间：" + formatDateTime(contract.getEndTime())
                    + "；续签建议：" + renewalSuggestion(contract, remainingDays));
            signal.put("tone", priorityScore >= 80 ? "danger"
                    : priorityScore >= 50 ? "warning" : "info");
            signal.put("metrics", List.of(
                    Map.of("label", "续签优先级", "value", priorityScore, "unit", "分",
                            "tone", priorityScore >= 80 ? "danger" : "warning"),
                    Map.of("label", "距到期", "value", remainingDays, "unit", "天"),
                    Map.of("label", "合同金额", "value", contract.getTotalMoney() == null
                            ? BigDecimal.ZERO : contract.getTotalMoney(), "unit", "元"),
                    Map.of("label", "未回款", "value", contract.getUnreceivedMoney() == null
                            ? BigDecimal.ZERO : contract.getUnreceivedMoney(), "unit", "元")));
            return (Object) signal;
        }).toList();
        Map<String, Object> block = new LinkedHashMap<>();
        block.put("id", "expiring-contracts-" + endingWithinDays);
        block.put("type", "contract_list");
        block.put("title", "未来" + endingWithinDays + "天合同续签优先级");
        block.put("subtitle", "客户 · 合同金额 · 到期时间 · 回款风险");
        block.put("status", Map.of("label", total == 0 ? "暂无到期合同" : "待续签跟进",
                "tone", total == 0 ? "success" : "warning"));
        block.put("primaryMetric", Map.of("label", "到期合同", "value", total, "unit", "份"));
        block.put("metrics", List.of(
                Map.of("label", "本页合同金额", "value", totalMoney, "unit", "元"),
                Map.of("label", "本页未回款", "value", unreceivedMoney, "unit", "元"),
                Map.of("label", "查询范围", "value", endingWithinDays, "unit", "天")));
        block.put("signals", signals);
        block.put("evidence", List.of(
                Map.of("label", "筛选规则", "value", "结束时间在今天至未来"
                        + endingWithinDays + "天内", "source", "CRM合同数据"),
                Map.of("label", "排序规则", "value",
                        "到期紧迫度最高50分、合同金额最高30分、存在未回款20分；按总分降序",
                        "source", "crm-renewal-priority-v1")));
        return block;
    }

    /**
     * 功能描述:
     * 〈计算合同续签优先级分值〉
     * @param contract 合同数据
     * @param now 当前业务时间
     * @return 业务处理结果
     * @author qingfeng
     */
    private static int renewalPriorityScore(CrmOnContract contract, LocalDateTime now) {
        long days = contract.getEndTime() == null ? 3650
                : Math.max(0, ChronoUnit.DAYS.between(now, contract.getEndTime()));
        int urgency = days <= 7 ? 50 : days <= 15 ? 35 : 20;
        BigDecimal money = contract.getTotalMoney() == null ? BigDecimal.ZERO : contract.getTotalMoney();
        int value = money.compareTo(BigDecimal.valueOf(500000)) >= 0 ? 30
                : money.compareTo(BigDecimal.valueOf(100000)) >= 0 ? 20
                : money.signum() > 0 ? 10 : 0;
        int paymentRisk = contract.getUnreceivedMoney() != null
                && contract.getUnreceivedMoney().signum() > 0 ? 20 : 0;
        return urgency + value + paymentRisk;
    }

    /**
     * 功能描述:
     * 〈生成合同续签行动建议〉
     * @param contract 合同数据
     * @param remainingDays 合同剩余天数
     * @return 业务处理结果
     * @author qingfeng
     */
    private static String renewalSuggestion(CrmOnContract contract, long remainingDays) {
        boolean unpaid = contract.getUnreceivedMoney() != null
                && contract.getUnreceivedMoney().signum() > 0;
        if (remainingDays <= 7) return unpaid ? "立即确认续签意向并同步处理未回款" : "立即确认续签方案和审批时间";
        return unpaid ? "本周沟通续签计划并明确未回款安排" : "本周确认续签意向和下一次沟通时间";
    }

    /**
     * 功能描述:
     * 〈将合同时间格式化为易读文本〉
     * @param value 待处理的业务值
     * @return 业务处理结果
     * @author qingfeng
     */
    private static String formatDateTime(LocalDateTime value) {
        return value == null ? "未设置" : value.format(
                java.time.format.DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm"));
    }

    /**
     * 功能描述:
     * 〈获取当前账号可见的合同详情〉
     * @param arguments MCP 工具参数
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object getContract(JSONObject arguments) {
        Long contractId = arguments.getLong(CrmMcpToolConst.ARGUMENT_CONTRACT_ID);
        if (contractId == null || contractId <= 0) {
            return null;
        }
        CrmOnContract contract = contractService.getOne(new LambdaQueryWrapper<CrmOnContract>()
                .eq(CrmOnContract::getId, contractId)
                .eq(CrmOnContract::getDelFlag, NumberConst.NUM_1), false);
        return contract == null ? null : toContractSummary(contract);
    }

    /**
     * 功能描述:
     * 〈汇总合同金额与回款指标〉
     * @return 业务处理结果
     * @author qingfeng
     */
    public Map<String, Object> contractMetrics() {
        List<CrmOnContract> contracts = contractService.list(
                new LambdaQueryWrapper<CrmOnContract>()
                        .eq(CrmOnContract::getDelFlag, NumberConst.NUM_1));
        Map<String, Object> metrics = new LinkedHashMap<>();
        metrics.put("amountUnit", "元");
        metrics.put("contractTotal", contracts.size());
        metrics.put("contractAmount", sum(contracts, CrmOnContract::getTotalMoney));
        metrics.put("receivedAmount", sum(contracts, CrmOnContract::getReceivedMoney));
        metrics.put("unreceivedAmount", sum(contracts, CrmOnContract::getUnreceivedMoney));
        return metrics;
    }

    /**
     * 功能描述:
     * 〈汇总合同指定金额字段〉
     * @param contracts 合同数据集合
     * @param getter 合同金额字段读取函数
     * @return 业务处理结果
     * @author qingfeng
     */
    private BigDecimal sum(List<CrmOnContract> contracts,
                           java.util.function.Function<CrmOnContract, BigDecimal> getter) {
        return contracts.stream().map(getter).filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 功能描述:
     * 〈将合同转换为安全摘要〉
     * @param contract 合同数据
     * @return 业务处理结果
     * @author qingfeng
     */
    private Object toContractSummary(CrmOnContract contract) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put(CrmMcpToolConst.RESULT_ID, idText(contract.getId()));
        result.put(CrmMcpToolConst.RESULT_NAME, contract.getDataName());
        result.put(CrmMcpToolConst.RESULT_DESCRIPTION, contract.getDataDesc());
        result.put(CrmMcpToolConst.RESULT_UPDATED_AT, contract.getUpdateTime());
        result.put("customerId", idText(contract.getCustomerId()));
        result.put("businessId", idText(contract.getBusinessId()));
        result.put("totalMoney", contract.getTotalMoney());
        result.put("realMoney", contract.getRealMoney());
        result.put("receivedMoney", contract.getReceivedMoney());
        result.put("unreceivedMoney", contract.getUnreceivedMoney());
        result.put("startTime", contract.getStartTime());
        result.put("endTime", contract.getEndTime());
        result.put("processPass", contract.getProcessPass());
        return result;
    }

    /**
     * 功能描述:
     * 〈将业务标识转换为字符串〉
     * @param id 业务数据标识
     * @return 业务处理结果
     * @author qingfeng
     */
    private String idText(Long id) {
        return id == null ? null : id.toString();
    }
}