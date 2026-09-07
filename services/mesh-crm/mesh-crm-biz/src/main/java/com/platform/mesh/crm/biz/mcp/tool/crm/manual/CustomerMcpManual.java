package com.platform.mesh.crm.biz.mcp.tool.crm.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.platform.mesh.ai.api.modules.mcp.constants.McpConst;
import com.platform.mesh.crm.biz.bi.crm.domain.dto.TodoPDTO;
import com.platform.mesh.crm.biz.bi.crm.enums.TodoTypeEnum;
import com.platform.mesh.crm.biz.bi.crm.service.ICrmTodoService;
import com.platform.mesh.app.api.modules.app.domain.bo.AppFormColumnBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppFormBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddSimpDTO;
import com.platform.mesh.app.api.modules.app.enums.comp.FormTypeEnum;
import com.platform.mesh.app.api.modules.app.feign.RemoteAppService;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.core.application.domain.dto.CondDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.core.enums.logic.ref.LogicRefEnum;
import com.platform.mesh.crm.biz.mcp.constant.CrmMcpToolConst;
import com.platform.mesh.crm.biz.modules.crm.onbusiness.domain.po.CrmOnBusiness;
import com.platform.mesh.crm.biz.modules.crm.onbusiness.service.ICrmOnBusinessService;
import com.platform.mesh.crm.biz.modules.crm.onfollow.domain.po.CrmOnFollow;
import com.platform.mesh.crm.biz.modules.crm.onfollowdata.domain.po.CrmOnFollowData;
import com.platform.mesh.crm.biz.modules.crm.onfollow.service.ICrmOnFollowService;
import com.platform.mesh.crm.biz.modules.crm.onfollowrel.po.CrmOnFollowRel;
import com.platform.mesh.crm.biz.modules.crm.onfollowrel.service.ICrmOnFollowRelService;
import com.platform.mesh.crm.biz.modules.crm.precontacts.domain.po.CrmPreContacts;
import com.platform.mesh.crm.biz.modules.crm.precontacts.service.ICrmPreContactsService;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.po.CrmPreCustomer;
import com.platform.mesh.crm.biz.modules.crm.precustomer.service.ICrmPreCustomerService;
import com.platform.mesh.crm.biz.modules.crm.precustomerdata.domain.po.CrmPreCustomerData;
import com.platform.mesh.crm.biz.modules.crm.precustomerdata.service.ICrmPreCustomerDataService;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import com.platform.mesh.utils.excel.enums.CompTypeEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 功能描述:
 * 〈处理客户基础查询、经营聚合和跟进写入业务〉
 * @author qingfeng
 */
@Service
public class CustomerMcpManual {
    private static final String CUSTOMER_DESCRIPTION_COLUMN = "textarea_1747035257615_30069_text";
    private static final String CONTACT_CUSTOMER_COLUMN = "customer_id_json";

    private static final int DEFAULT_INACTIVE_DAYS = 30;
    private static final int MAX_INACTIVE_DAYS = 365;
    private static final int DEFAULT_PRIORITY_LIMIT = 10;
    private static final int DEFAULT_STAGNANT_DAYS = 30;
    private static final int INACTIVE_SCORE = 50;
    private static final int OPPORTUNITY_SCORE = 30;
    private static final int UNRECEIVED_SCORE = 20;

    private final ICrmPreCustomerService customerService;
    private final ICrmPreCustomerDataService customerDataService;
    private final ICrmPreContactsService contactsService;
    private final ICrmOnBusinessService businessService;
    private final ICrmOnFollowRelService followRelService;
    private final ICrmOnFollowService followService;
    private final RemoteAppService remoteAppService;
    private final ContractMcpManual contractMcpManual;
    private final ICrmTodoService todoService;

    /**
     * 功能描述:
     * 〈处理客户基础查询、经营聚合和跟进写入业务〉
     * @param customerService 客户业务服务
     * @param customerDataService 客户动态字段数据服务
     * @param contactsService 联系人业务服务
     * @param businessService 商机业务服务
     * @param followRelService 跟进关联关系服务
     * @param followService 跟进记录业务服务
     * @param remoteAppService 远程应用配置服务
     * @param contractMcpManual 合同 MCP 业务处理器
     * @param todoService 销售任务业务服务
     * @author qingfeng
     */
    public CustomerMcpManual(ICrmPreCustomerService customerService,
                             ICrmPreCustomerDataService customerDataService,
                             ICrmPreContactsService contactsService,
                             ICrmOnBusinessService businessService,
                             ICrmOnFollowRelService followRelService,
                             ICrmOnFollowService followService,
                             RemoteAppService remoteAppService,
                             ContractMcpManual contractMcpManual,
                             ICrmTodoService todoService) {
        this.customerService = customerService;
        this.customerDataService = customerDataService;
        this.contactsService = contactsService;
        this.businessService = businessService;
        this.followRelService = followRelService;
        this.followService = followService;
        this.remoteAppService = remoteAppService;
        this.contractMcpManual = contractMcpManual;
        this.todoService = todoService;
    }

    /**
     * 功能描述:
     * 〈获取当前 CRM 连接和账号信息〉
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object connectionInfo() {
        // 优先读取可信 MCP 身份，并兼容站内 AI 助手的登录态上下文。
        LoginUserBO user = UserCacheUtil.getLoginUser();
        if (user == null) {
            return connectionInfo(false, null);
        }
        // 过滤 MCP 临时账号名，避免将内部身份标识暴露给客户端
        String nickname = StrUtil.trim(user.getNickname());
        if (StrUtil.startWith(nickname, McpConst.PRINCIPAL_PREFIX)) {
            nickname = null;
        }
        return connectionInfo(true, StrUtil.blankToDefault(nickname, null));
    }

    /**
     * 功能描述:
     * 〈分页搜索当前账号可见客户〉
     * @param arguments MCP 工具参数
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object searchCustomers(JSONObject arguments) {
        String keyword = arguments.getStr(CrmMcpToolConst.ARGUMENT_KEYWORD);
        Integer page = arguments.getInt(CrmMcpToolConst.ARGUMENT_PAGE);
        Integer pageSize = arguments.getInt(CrmMcpToolConst.ARGUMENT_PAGE_SIZE);
        // 第一步：规范分页和搜索关键字，限制异常参数影响查询范围。
        int safePage = page == null || page < CrmMcpToolConst.DEFAULT_PAGE
                ? CrmMcpToolConst.DEFAULT_PAGE : page;
        int safePageSize = pageSize == null || pageSize < CrmMcpToolConst.DEFAULT_PAGE
                ? CrmMcpToolConst.DEFAULT_PAGE_SIZE : Math.min(pageSize, CrmMcpToolConst.MAX_PAGE_SIZE);
        String normalizedKeyword = StrUtil.trim(keyword);
        // 第二步：定位当前客户模块的 ES 索引，排除开放模块的重复配置。
        ArrayList<String> schemas = CollUtil.newArrayList(SqlUtil.getTableName(CrmPreCustomer.class, TableName.class));
        List<AppModuleBaseBO> baseBOS = remoteAppService.getModuleBaseInfoBySchema(schemas).getData();
        List<AppModuleBaseBO> moduleBaseBOS = baseBOS.stream()
                .filter(base -> !YesOrNoEnum.YES.getValue().equals(base.getOpenFlag()))
                .sorted(Comparator.comparing(AppModuleBaseBO::getAiFlag, Comparator.nullsLast(Comparator.reverseOrder()))
                        .thenComparing(Comparator.comparing(AppModuleBaseBO::getCreateTime).reversed()))
                .toList();
        if (CollUtil.isEmpty(moduleBaseBOS)) {
            return null;
        }
        AppModuleBaseBO first = CollUtil.getFirst(moduleBaseBOS);
        List<String> moduleIds = moduleBaseBOS.stream().map(AppModuleBaseBO::getId).map(StrUtil::toString).toList();
        // 第三步：构造模块范围和关键字条件并查询 ES。
        List<CondDTO> condDTOS = CollUtil.newArrayList();
        CondDTO moduleDTO = new CondDTO();
        moduleDTO.setColumnMac(StrConst.MODULE_ID);
        moduleDTO.setCondRef(LogicRefEnum.EQ);
        moduleDTO.setSearchValues(moduleIds);
        condDTOS.add(moduleDTO);
        if (ObjectUtil.isNotEmpty(normalizedKeyword)) {
            CondDTO keyDTO = new CondDTO();
            keyDTO.setColumnMac(StrConst.DATA_NAME);
            keyDTO.setCondRef(LogicRefEnum.LIKE);
            keyDTO.setSearchValues(CollUtil.newArrayList(normalizedKeyword));
            condDTOS.add(keyDTO);
        }
        EsDocPGetDTO docPGetDTO = new EsDocPGetDTO();
        docPGetDTO.setPageNum(safePage);
        docPGetDTO.setPageSize(safePageSize);
        docPGetDTO.setIndexName(first.getModuleIndex());
        docPGetDTO.setCondDTO(condDTOS);

        PageVO<Object> esPage = customerService.selectEsPage(docPGetDTO);
        // 第四步：按当前列表字段配置裁剪结果并脱敏手机号。
        List<AppFormColumnBO> visibleColumns = remoteAppService.fastColumnByModuleAndFormType(
                first.getId(), FormTypeEnum.HEAD_LIST.getValue()).getData();
        List<Object> records = esPage.getRecords().stream()
                .map(record -> toVisibleCustomerSummary(record, visibleColumns))
                .toList();
        esPage.setRecords(records);
        return esPage;
    }

    /**
     * 功能描述:
     * 〈获取当前账号可见的客户摘要〉
     * @param arguments MCP 工具参数
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object getCustomer(JSONObject arguments) {
        Long customerId = arguments.getLong(CrmMcpToolConst.ARGUMENT_CUSTOMER_ID);
        CrmPreCustomer customer = findCustomer(customerId);
        return customer == null ? null : toCustomerSummary(customer);
    }

    /**
     * 功能描述:
     * 〈获取指定客户的关联业务数据〉
     * @param arguments MCP 工具参数
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object getCustomerRelations(JSONObject arguments) {
        Long customerId = arguments.getLong(CrmMcpToolConst.ARGUMENT_CUSTOMER_ID);
        // 第一步：校验客户是否存在且对当前账号可见。
        CrmPreCustomer customer = findCustomer(customerId);
        if (customer == null) {
            return null;
        }
        customerId = customer.getId();

        // 第二步：分别查询最近联系人和商机。
        List<Object> contacts = contactsService.list(
                        new LambdaQueryWrapper<CrmPreContacts>()
                                .eq(CrmPreContacts::getCustomerId, customerId)
                                .eq(CrmPreContacts::getDelFlag, NumberConst.NUM_1)
                                .orderByDesc(CrmPreContacts::getUpdateTime)
                                .last("LIMIT " + CrmMcpToolConst.MAX_RELATIONS))
                .stream().map(this::toContactSummary).toList();

        List<Object> businesses = businessService.list(
                        new LambdaQueryWrapper<CrmOnBusiness>()
                                .eq(CrmOnBusiness::getCustomerId, customerId)
                                .eq(CrmOnBusiness::getDelFlag, NumberConst.NUM_1)
                                .orderByDesc(CrmOnBusiness::getUpdateTime)
                                .last("LIMIT " + CrmMcpToolConst.MAX_RELATIONS))
                .stream().map(this::toBusinessSummary).toList();

        // 第三步：先查询关系表，再批量获取有效跟进记录。
        List<Long> followIds = followRelService.list(
                        new LambdaQueryWrapper<CrmOnFollowRel>()
                                .eq(CrmOnFollowRel::getRelDataId, customerId)
                                .eq(CrmOnFollowRel::getRelModuleId, customer.getModuleId())
                                .orderByDesc(CrmOnFollowRel::getUpdateTime)
                                .last("LIMIT " + CrmMcpToolConst.MAX_RELATIONS))
                .stream().map(CrmOnFollowRel::getDataId).distinct().toList();
        List<Object> follows = followIds.isEmpty()
                ? Collections.emptyList()
                : followService.list(new LambdaQueryWrapper<CrmOnFollow>()
                                .in(CrmOnFollow::getId, followIds)
                                .eq(CrmOnFollow::getDelFlag, NumberConst.NUM_1)
                                .orderByDesc(CrmOnFollow::getUpdateTime)
                                .last("LIMIT " + CrmMcpToolConst.MAX_RELATIONS))
                        .stream().map(this::toFollowSummary).toList();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("contacts", contacts);
        response.put("businesses", businesses);
        response.put("follows", follows);
        return response;
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
        // 第一步：兼容客户名称参数并解析为稳定客户主键。
        resolveCustomerId(arguments);
        Long customerId = arguments.getLong(CrmMcpToolConst.ARGUMENT_CUSTOMER_ID);
        CrmPreCustomer customer = findCustomer(customerId);
        if (customer == null) {
            return null;
        }

        // 第二步：聚合客户基础信息和联系人、商机、跟进数据。
        customerId = customer.getId();
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("sceneCode", executionConfig.getStr("sceneCode"));
        response.put("customer", toCustomerSummary(customer));
        Object relations = getCustomerRelations(arguments);
        if (relations instanceof Map<?, ?> relationMap) {
            relationMap.forEach((key, value) -> response.put(String.valueOf(key), value));
        }
        // 第三步：复用合同工具查询客户合同，避免复制合同查询规则。
        JSONObject contractArguments = new JSONObject();
        contractArguments.set(CrmMcpToolConst.ARGUMENT_CUSTOMER_ID, customerId);
        contractArguments.set(CrmMcpToolConst.ARGUMENT_PAGE, CrmMcpToolConst.DEFAULT_PAGE);
        contractArguments.set(CrmMcpToolConst.ARGUMENT_PAGE_SIZE, CrmMcpToolConst.MAX_RELATIONS);
        response.put("contracts", contractMcpManual.searchContracts(contractArguments));
        return response;
    }

    /**
     * 功能描述:
     * 〈执行客户经营优先级统一查询〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object searchCustomerPriorities(JSONObject arguments, JSONObject executionConfig) {
        // 第一步：规范未跟进阈值和返回数量。
        Integer inactiveDaysValue = arguments.getInt(CrmMcpToolConst.ARGUMENT_INACTIVE_DAYS);
        int inactiveDays = inactiveDaysValue == null ? DEFAULT_INACTIVE_DAYS
                : Math.max(1, Math.min(inactiveDaysValue, MAX_INACTIVE_DAYS));
        Integer pageSizeValue = arguments.getInt(CrmMcpToolConst.ARGUMENT_PAGE_SIZE);
        int limit = pageSizeValue == null ? DEFAULT_PRIORITY_LIMIT
                : Math.max(1, Math.min(pageSizeValue, CrmMcpToolConst.MAX_PAGE_SIZE));
        String sceneCode = executionConfig.getStr("sceneCode");
        if ("TODAY_SALES_TASKS".equals(sceneCode)) {
            return searchSalesTasks(sceneCode, TodoTypeEnum.TODO, limit, true);
        }
        if ("UNFINISHED_SALES_TASKS".equals(sceneCode)) {
            return searchSalesTasks(sceneCode, TodoTypeEnum.OVER, limit, true);
        }
        if ("OVERDUE_FOLLOW_CUSTOMERS".equals(sceneCode)) {
            return searchSalesTasks(sceneCode, TodoTypeEnum.OVER, limit, false);
        }
        LocalDateTime now = LocalDateTime.now();

        // 第二步：批量查询客户、商机和跟进，避免逐客户查询造成 N+1 问题。
        List<CrmPreCustomer> customers = customerService.list(
                new LambdaQueryWrapper<CrmPreCustomer>()
                        .eq(CrmPreCustomer::getDelFlag, NumberConst.NUM_1)
                        .orderByAsc(CrmPreCustomer::getUpdateTime));
        List<Long> customerIds = customers.stream().map(CrmPreCustomer::getId).toList();
        List<CrmOnBusiness> allBusinesses = customerIds.isEmpty() ? Collections.emptyList()
                : businessService.list(new LambdaQueryWrapper<CrmOnBusiness>()
                        .in(CrmOnBusiness::getCustomerId, customerIds)
                        .eq(CrmOnBusiness::getDelFlag, NumberConst.NUM_1));
        Map<Long, java.math.BigDecimal> opportunityByCustomer = new HashMap<>();
        allBusinesses.forEach(business -> opportunityByCustomer.merge(
                business.getCustomerId(), ObjectUtil.defaultIfNull(
                        business.getRealMoney(), java.math.BigDecimal.ZERO),
                java.math.BigDecimal::add));
        List<CrmOnFollowRel> relations = customerIds.isEmpty() ? Collections.emptyList()
                : followRelService.list(new LambdaQueryWrapper<CrmOnFollowRel>()
                        .in(CrmOnFollowRel::getRelDataId, customerIds));
        List<Long> followIds = relations.stream().map(CrmOnFollowRel::getDataId).distinct().toList();
        Map<Long, CrmOnFollow> followById = followIds.isEmpty() ? Collections.emptyMap()
                : followService.listByIds(followIds).stream()
                        .collect(java.util.stream.Collectors.toMap(CrmOnFollow::getId, item -> item));
        Map<Long, List<LocalDateTime>> followActivityByCustomer = new HashMap<>();
        relations.forEach(relation -> {
            CrmOnFollow follow = followById.get(relation.getDataId());
            if (follow != null && NumberConst.NUM_1.equals(follow.getDelFlag())
                    && follow.getCreateTime() != null) {
                followActivityByCustomer.computeIfAbsent(relation.getRelDataId(), key -> new ArrayList<>())
                        .add(follow.getCreateTime());
            }
        });
        // 第三步：由后端规则计算分值，AI 仅解释风险依据和推荐动作。
        List<Map<String, Object>> candidateItems = customers.stream().map(customer -> {
            long days = inactiveDays(customer.getUpdateTime(),
                    followActivityByCustomer.getOrDefault(customer.getId(), Collections.emptyList()), now);
            java.math.BigDecimal opportunityAmount = opportunityByCustomer.getOrDefault(
                    customer.getId(), java.math.BigDecimal.ZERO);
            int score = (days >= inactiveDays ? INACTIVE_SCORE : 0)
                    + (opportunityAmount.signum() > 0 ? OPPORTUNITY_SCORE : 0)
                    + (customer.getUnreceivedMoney() != null
                    && customer.getUnreceivedMoney().signum() > 0 ? UNRECEIVED_SCORE : 0);
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("customerId", idText(customer.getId()));
            item.put("customerName", customer.getDataName());
            item.put("priorityScore", score);
            item.put("inactiveDays", days);
            item.put("opportunityAmount", opportunityAmount);
            item.put("unreceivedMoney", customer.getUnreceivedMoney());
            item.put("risk", days >= inactiveDays ? "长期未跟进" : "需持续经营");
            item.put("recommendation", days >= inactiveDays
                    ? "本周联系客户并确认当前需求、决策进度和下一步时间"
                    : "按最近约定持续推进并补充明确的下次跟进时间");
            return item;
        }).sorted(Comparator.comparingInt(item -> -Convert.toInt(item.get("priorityScore"), 0))).toList();
        if ("CUSTOMER_DATA_COMPLETENESS".equals(sceneCode)) {
            return dataCompleteness(sceneCode, customers);
        }
        if ("TEAM_DAILY_REPORT".equals(sceneCode)
                || "BUSINESS_METRICS".equals(sceneCode)) {
            return businessMetrics(sceneCode, customers, allBusinesses);
        }
        if ("TEAM_WORKLOAD".equals(sceneCode)) {
            return teamWorkload(sceneCode, customers, allBusinesses);
        }
        if ("DORMANT_CUSTOMERS".equals(sceneCode)) {
            return dormantCustomers(sceneCode, customers, now, limit);
        }
        if ("ACTIVE_RISK_ALERTS".equals(sceneCode)
                || "TASK_DRAFT".equals(sceneCode)) {
            Map<String, Object> alerts = new LinkedHashMap<>();
            alerts.put("sceneCode", sceneCode);
            alerts.put("customerRisks", actionablePriorityItems(candidateItems).stream().limit(limit).toList());
            alerts.put("contractMetrics", contractMcpManual.contractMetrics());
            return alerts;
        }
        List<Map<String, Object>> items = actionablePriorityItems(candidateItems).stream()
                .limit(limit).toList();
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("sceneCode", executionConfig.getStr("sceneCode"));
        response.put("ruleVersion", "crm-priority-v1");
        response.put("inactiveDays", inactiveDays);
        response.put("items", items);
        return response;
    }

    /**
     * 功能描述:
     * 〈计算客户经营数据完整性指标〉
     * @param sceneCode 经营场景编码
     * @param customers 客户数据集合
     * @return 业务处理结果
     * @author qingfeng
     */
    private Object dataCompleteness(String sceneCode, List<CrmPreCustomer> customers) {
        List<CrmPreContacts> contacts = contactsService.list(
                new LambdaQueryWrapper<CrmPreContacts>()
                        .eq(CrmPreContacts::getDelFlag, NumberConst.NUM_1));
        Set<Long> contactCustomerIds = contacts.stream().map(CrmPreContacts::getCustomerId)
                .filter(Objects::nonNull).collect(java.util.stream.Collectors.toSet());
        long completeCustomers = customers.stream().filter(customer ->
                StrUtil.isNotBlank(customer.getDataName())
                        && StrUtil.isNotBlank(customer.getPhone())
                        && contactCustomerIds.contains(customer.getId())).count();
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("sceneCode", sceneCode);
        response.put("customerTotal", customers.size());
        response.put("completeCustomerTotal", completeCustomers);
        response.put("customerCompletenessRate", percentage(completeCustomers, customers.size()));
        response.put("customerWithContactRate", percentage(contactCustomerIds.size(), customers.size()));
        response.put("contractMetrics", contractMcpManual.contractMetrics());
        return response;
    }

    /**
     * 功能描述:
     * 〈汇总客户、合同和商机经营指标〉
     * @param sceneCode 经营场景编码
     * @param customers 客户数据集合
     * @param businesses 商机数据集合
     * @return 业务处理结果
     * @author qingfeng
     */
    private Object businessMetrics(String sceneCode, List<CrmPreCustomer> customers,
                                   List<CrmOnBusiness> businesses) {
        LocalDateTime monthStart = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();
        long newCustomers = customers.stream().filter(customer -> customer.getCreateTime() != null
                && !customer.getCreateTime().isBefore(monthStart)).count();
        long won = businesses.stream().filter(item -> NumberConst.NUM_1.equals(item.getProcessPass())).count();
        long lost = businesses.stream().filter(item -> NumberConst.NUM_2.equals(item.getProcessPass())).count();
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("sceneCode", sceneCode);
        response.put("customerTotal", customers.size());
        response.put("newCustomerTotal", newCustomers);
        response.putAll(contractMcpManual.contractMetrics());
        response.put("opportunityTotal", businesses.size());
        response.put("wonOpportunityTotal", won);
        response.put("lostOpportunityTotal", lost);
        response.put("opportunityAmount", businesses.stream().map(CrmOnBusiness::getRealMoney)
                .filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add));
        return response;
    }

    /**
     * 功能描述:
     * 〈汇总销售团队客户与任务负荷〉
     * @param sceneCode 经营场景编码
     * @param customers 客户数据集合
     * @param businesses 商机数据集合
     * @return 业务处理结果
     * @author qingfeng
     */
    private Object teamWorkload(String sceneCode, List<CrmPreCustomer> customers,
                                List<CrmOnBusiness> businesses) {
        Map<Long, Long> customersByOwner = customers.stream().filter(item -> item.getScopeUserId() != null)
                .collect(java.util.stream.Collectors.groupingBy(CrmPreCustomer::getScopeUserId,
                        java.util.stream.Collectors.counting()));
        Map<Long, Long> opportunitiesByOwner = businesses.stream().filter(item -> item.getScopeUserId() != null)
                .collect(java.util.stream.Collectors.groupingBy(CrmOnBusiness::getScopeUserId,
                        java.util.stream.Collectors.counting()));
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("sceneCode", sceneCode);
        response.put("customersByOwner", customersByOwner);
        response.put("opportunitiesByOwner", opportunitiesByOwner);
        return response;
    }

    /**
     * 功能描述:
     * 〈筛选并汇总沉睡客户〉
     * @param sceneCode 经营场景编码
     * @param customers 客户数据集合
     * @param now 当前业务时间
     * @param limit 最大返回数量
     * @return 业务处理结果
     * @author qingfeng
     */
    private Object dormantCustomers(String sceneCode, List<CrmPreCustomer> customers,
                                    LocalDateTime now, int limit) {
        List<Object> records = customers.stream()
                .filter(customer -> customer.getTotalMoney() != null
                        && customer.getTotalMoney().signum() > 0)
                .filter(customer -> customer.getUpdateTime() == null
                        || customer.getUpdateTime().isBefore(now.minusMonths(6)))
                .sorted(Comparator.comparing(CrmPreCustomer::getUpdateTime,
                        Comparator.nullsFirst(Comparator.naturalOrder())))
                .limit(limit).map(this::toCustomerSummary).toList();
        return Map.of("sceneCode", sceneCode, "records", records,
                "total", records.size(), "inactiveMonths", 6);
    }

    /**
     * 功能描述:
     * 〈计算数据完整性百分比〉
     * @param value 待处理的业务值
     * @param total 数据总数
     * @return 业务处理结果
     * @author qingfeng
     */
    private double percentage(long value, long total) {
        return total == 0 ? 100D : Math.round(value * 1000D / total) / 10D;
    }

    /**
     * 功能描述:
     * 〈按任务类型查询销售工作安排〉
     * @param sceneCode 经营场景编码
     * @param todoType 销售任务类型
     * @param limit 最大返回数量
     * @param includeBusinesses 是否包含关联商机数据
     * @return 业务处理结果
     * @author qingfeng
     */
    private Object searchSalesTasks(String sceneCode, TodoTypeEnum todoType,
                                    int limit, boolean includeBusinesses) {
        TodoPDTO query = new TodoPDTO();
        query.setPageNum(CrmMcpToolConst.DEFAULT_PAGE);
        query.setPageSize(limit);
        query.setTodoType(todoType.getValue());
        PageVO<Object> customerTasks = todoService.todoRelCustomerToday(query);
        PageVO<Object> businessTasks = includeBusinesses
                ? todoService.todoRelBusinessToday(query) : new PageVO<>();
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("sceneCode", sceneCode);
        response.put("taskType", todoType.getDesc());
        response.put("customerTasks", ObjectUtil.defaultIfNull(
                customerTasks.getRecords(), Collections.emptyList()));
        response.put("businessTasks", ObjectUtil.defaultIfNull(
                businessTasks.getRecords(), Collections.emptyList()));
        response.put("customerTotal", ObjectUtil.defaultIfNull(customerTasks.getTotal(), 0L));
        response.put("businessTotal", ObjectUtil.defaultIfNull(businessTasks.getTotal(), 0L));
        return response;
    }

    /**
     * 功能描述:
     * 〈查询超过停滞阈值的商机〉
     * @param arguments MCP 工具参数
     * @return 业务处理结果
     * @author qingfeng
     */
    public Object searchStagnantOpportunities(JSONObject arguments) {
        Integer stagnantDaysValue = arguments.getInt("stagnantDays");
        int stagnantDays = stagnantDaysValue == null ? DEFAULT_STAGNANT_DAYS
                : Math.max(1, Math.min(stagnantDaysValue, MAX_INACTIVE_DAYS));
        Integer pageSizeValue = arguments.getInt(CrmMcpToolConst.ARGUMENT_PAGE_SIZE);
        int limit = pageSizeValue == null ? DEFAULT_PRIORITY_LIMIT
                : Math.max(1, Math.min(pageSizeValue, CrmMcpToolConst.MAX_PAGE_SIZE));
        LocalDateTime now = LocalDateTime.now();

        // 第一步：按更新时间筛选超过阈值的有效商机。
        List<CrmOnBusiness> opportunities = businessService.list(
                new LambdaQueryWrapper<CrmOnBusiness>()
                        .eq(CrmOnBusiness::getDelFlag, NumberConst.NUM_1)
                        .le(CrmOnBusiness::getUpdateTime, now.minusDays(stagnantDays))
                        .orderByAsc(CrmOnBusiness::getUpdateTime));
        // 第二步：批量补充客户名称，避免逐商机回查客户。
        List<Long> customerIds = opportunities.stream().map(CrmOnBusiness::getCustomerId)
                .filter(Objects::nonNull).distinct().toList();
        Map<Long, String> customerNames = customerIds.isEmpty() ? Collections.emptyMap()
                : customerService.listByIds(customerIds).stream().collect(
                        java.util.stream.Collectors.toMap(CrmPreCustomer::getId,
                                CrmPreCustomer::getDataName, (left, right) -> left));
        // 第三步：计算风险等级、停滞依据和行动建议并按优先级排序。
        List<Map<String, Object>> records = opportunities.stream()
                .map(opportunity -> toStagnantOpportunity(opportunity,
                        customerNames.get(opportunity.getCustomerId()), now, stagnantDays))
                .sorted(Comparator.comparingInt(item -> -Convert.toInt(item.get("priorityScore"), 0)))
                .limit(limit)
                .toList();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("records", records);
        response.put("total", opportunities.size());
        response.put("stagnantDays", stagnantDays);
        response.put("sortRule", "风险优先级降序，同优先级按停滞天数降序");
        return response;
    }

    /**
     * 将商机转换为包含风险等级、停滞依据和下一步动作的安全摘要。
     */
    static Map<String, Object> toStagnantOpportunity(CrmOnBusiness business,
            String customerName, LocalDateTime now, int stagnantDaysThreshold) {
        LocalDateTime updatedAt = business.getUpdateTime();
        long stagnantDays = updatedAt == null ? stagnantDaysThreshold
                : Math.max(0, ChronoUnit.DAYS.between(updatedAt, now));
        BigDecimal amount = ObjectUtil.defaultIfNull(business.getRealMoney(), BigDecimal.ZERO);
        String stage = StrUtil.blankToDefault(business.getProcessStage(), "未设置阶段");
        boolean highRisk = stagnantDays >= 60 || amount.compareTo(BigDecimal.valueOf(500000)) >= 0;
        String riskLevel = highRisk ? "高" : stagnantDays >= stagnantDaysThreshold ? "中" : "低";
        int priorityScore = highRisk ? 80 : "中".equals(riskLevel) ? 50 : 20;

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("opportunityId", business.getId() == null ? null : String.valueOf(business.getId()));
        result.put("opportunityName", StrUtil.blankToDefault(business.getDataName(), "未命名商机"));
        result.put("customerId", business.getCustomerId() == null
                ? null : String.valueOf(business.getCustomerId()));
        result.put("customerName", StrUtil.blankToDefault(customerName, "未关联客户"));
        result.put("stage", stage);
        result.put("updatedAt", updatedAt);
        result.put("stagnantDays", stagnantDays);
        result.put("opportunityAmount", amount);
        result.put("riskLevel", riskLevel);
        result.put("priorityScore", priorityScore);
        result.put("stagnationEvidence", "商机在“" + stage + "”阶段已" + stagnantDays
                + "天未更新，超过" + stagnantDaysThreshold + "天停滞阈值");
        result.put("nextAction", highRisk
                ? "立即联系客户确认阻塞点、决策人和预计成交时间，并约定下一次跟进"
                : "本周联系客户确认当前阻塞点，并约定明确的下一次跟进时间");
        return result;
    }

    /**
     * 过滤无任何确定性风险信号的客户，保证展示数量与实际明细一致。
     */
    static List<Map<String, Object>> actionablePriorityItems(List<Map<String, Object>> items) {
        return items.stream()
                .filter(item -> Convert.toInt(item.get("priorityScore"), 0) > 0)
                .toList();
    }

    /**
     * 将客户优先级数据转换为统一的卡片指标。
     */
    static List<Map<String, Object>> priorityMetrics(Map<String, Object> item) {
        return List.of(
                Map.of("label", "优先级分", "value", Convert.toInt(
                        item.get("priorityScore"), 0), "unit", "分", "tone", "warning"),
                Map.of("label", "未跟进天数", "value", Convert.toLong(
                        item.get("inactiveDays"), 0L), "unit", "天"),
                Map.of("label", "商机金额", "value", Convert.toBigDecimal(
                        item.get("opportunityAmount"), BigDecimal.ZERO), "unit", "元", "tone", "primary"),
                Map.of("label", "未回款", "value", Convert.toBigDecimal(
                        item.get("unreceivedMoney"), BigDecimal.ZERO), "unit", "元", "tone", "danger"),
                Map.of("label", "风险", "value", ObjectUtil.defaultIfNull(
                        item.get("risk"), "待评估"), "tone", "warning"));
    }

    /**
     * 根据客户更新时间和最近跟进时间计算未互动天数。
     */
    static long inactiveDays(LocalDateTime fallbackActivity,
            List<LocalDateTime> followActivities, LocalDateTime now) {
        LocalDateTime lastActivity = followActivities.stream().filter(Objects::nonNull)
                .max(LocalDateTime::compareTo).orElse(fallbackActivity);
        return lastActivity == null ? 0 : Math.max(0, ChronoUnit.DAYS.between(lastActivity, now));
    }

    /**
     * 功能描述:
     * 〈预览或确认创建客户〉
     * @param arguments MCP 工具参数
     * @return 业务处理结果
     * @author qingfeng
     */
    @Transactional(rollbackFor = Exception.class)
    public Object createCustomer(JSONObject arguments) {
        // 步骤一：读取并清理客户、联系人字段，空值不参与预览、签名和最终写入。
        String customerName = StrUtil.trim(arguments.getStr(CrmMcpToolConst.ARGUMENT_CUSTOMER_NAME));
        String description = StrUtil.trim(arguments.getStr(CrmMcpToolConst.ARGUMENT_CUSTOMER_DESCRIPTION));
        Map<String, String> customerFields = new LinkedHashMap<>();
        customerFields.put(CrmMcpToolConst.ARGUMENT_PHONE,
                StrUtil.trim(arguments.getStr(CrmMcpToolConst.ARGUMENT_PHONE)));
        customerFields.put(CrmMcpToolConst.ARGUMENT_EMAIL,
                StrUtil.trim(arguments.getStr(CrmMcpToolConst.ARGUMENT_EMAIL)));
        customerFields.put(CrmMcpToolConst.ARGUMENT_ADDRESS,
                StrUtil.trim(arguments.getStr(CrmMcpToolConst.ARGUMENT_ADDRESS)));
        customerFields.entrySet().removeIf(entry -> StrUtil.isBlank(entry.getValue()));
        Map<String, String> contactFields = new LinkedHashMap<>();
        contactFields.put(CrmMcpToolConst.ARGUMENT_CONTACT_NAME,
                StrUtil.trim(arguments.getStr(CrmMcpToolConst.ARGUMENT_CONTACT_NAME)));
        contactFields.put(CrmMcpToolConst.ARGUMENT_CONTACT_JOB_TITLE,
                StrUtil.trim(arguments.getStr(CrmMcpToolConst.ARGUMENT_CONTACT_JOB_TITLE)));
        contactFields.put(CrmMcpToolConst.ARGUMENT_CONTACT_PHONE,
                StrUtil.trim(arguments.getStr(CrmMcpToolConst.ARGUMENT_CONTACT_PHONE)));
        contactFields.put(CrmMcpToolConst.ARGUMENT_CONTACT_EMAIL,
                StrUtil.trim(arguments.getStr(CrmMcpToolConst.ARGUMENT_CONTACT_EMAIL)));
        contactFields.put(CrmMcpToolConst.ARGUMENT_CONTACT_ADDRESS,
                StrUtil.trim(arguments.getStr(CrmMcpToolConst.ARGUMENT_CONTACT_ADDRESS)));
        contactFields.entrySet().removeIf(entry -> StrUtil.isBlank(entry.getValue()));
        if (StrUtil.isBlank(customerName)) {
            throw new IllegalArgumentException("客户名称不能为空");
        }

        // 步骤二：确定客户模块，并以本次完整业务参数生成不可篡改的确认口令。
        ArrayList<String> schemas = CollUtil.newArrayList(
                SqlUtil.getTableName(CrmPreCustomer.class, TableName.class));
        List<AppModuleBaseBO> customerModules = remoteAppService
                .getModuleBaseInfoBySchema(schemas).getData();
        Long moduleId = resolveCustomerModuleId(
                arguments.getLong(CrmMcpToolConst.ARGUMENT_MODULE_ID), customerModules);
        String token = customerConfirmationToken(moduleId, customerName, description,
                customerFields, contactFields);

        // 步骤三：首次调用只返回预览；只有携带相同参数及有效口令时才进入写入阶段。
        boolean confirmed = Boolean.TRUE.equals(arguments.getBool(CrmMcpToolConst.ARGUMENT_CONFIRM));
        if (!confirmed) {
            return customerCreationResult("PREVIEW", moduleId, customerName, description,
                    customerFields, contactFields, token, null, null);
        }
        if (!MessageDigest.isEqual(token.getBytes(StandardCharsets.UTF_8),
                StrUtil.nullToEmpty(arguments.getStr(CrmMcpToolConst.ARGUMENT_CONFIRM_TOKEN))
                        .getBytes(StandardCharsets.UTF_8))) {
            throw new IllegalArgumentException("确认口令无效，请重新生成操作预览");
        }

        // 步骤四：使用客户模块新增表单完成主记录写入，并补齐动态字段和 ES 文档。
        AppFormBO addForm = remoteAppService.fastFormByModuleAndFormType(
                moduleId, FormTypeEnum.FORM_ADD.getValue()).getData();
        if (addForm == null || addForm.getId() == null) {
            throw new IllegalStateException("未找到客户新增表单配置");
        }
        Map<String, Object> document = new LinkedHashMap<>();
        document.put(StrConst.DATA_NAME, customerName);
        if (StrUtil.isNotBlank(description)) {
            document.put(StrConst.DATA_DESC, description);
            document.put(CUSTOMER_DESCRIPTION_COLUMN, description);
        }
        document.putAll(customerFields);
        DataAddSimpDTO addDTO = new DataAddSimpDTO()
                .setModuleId(moduleId)
                .setFormId(addForm.getId())
                .setDocData(document);
        CrmPreCustomer customer = customerService.addDataSimp(
                addDTO, CrmPreCustomer.class, CrmPreCustomerData.class);
        persistMissingCustomerFields(customerModules, moduleId, customer, customerFields);

        // 步骤五：名片包含联系人时，在同一应用内创建联系人并建立客户关联。
        Long contactId = createRelatedContact(customer, customerModules, contactFields);
        return customerCreationResult("EXECUTED", moduleId, customerName, description,
                customerFields, contactFields, token, customer.getId(), contactId);
    }

    /**
     * 功能描述:
     * 〈在客户所属应用内创建联系人并建立客户关联〉
     * @param customer 已创建的客户
     * @param customerModules 可用客户模块
     * @param contactFields 联系人字段
     * @return 联系人 ID；未提供联系人姓名时返回 null
     * @author qingfeng
     */
    private Long createRelatedContact(CrmPreCustomer customer,
            List<AppModuleBaseBO> customerModules, Map<String, String> contactFields) {
        String contactName = contactFields.get(CrmMcpToolConst.ARGUMENT_CONTACT_NAME);
        if (StrUtil.isBlank(contactName)) {
            return null;
        }
        List<AppModuleBaseBO> contactModules = remoteAppService.getModuleBaseInfoBySchema(
                CollUtil.newArrayList(SqlUtil.getTableName(CrmPreContacts.class, TableName.class))).getData();
        AppModuleBaseBO customerModule = customerModules.stream()
                .filter(module -> Objects.equals(customer.getModuleId(), module.getId()))
                .findFirst().orElseThrow(() -> new IllegalStateException("未找到客户模块配置"));
        Set<Long> parentIds = new HashSet<>();
        parentIds.add(customerModule.getParentId());
        contactModules.stream().map(AppModuleBaseBO::getParentId).forEach(parentIds::add);
        Map<Long, Long> parentApplicationIds = remoteAppService
                .getModuleBaseInfoByIds(new ArrayList<>(parentIds)).getData().stream()
                .collect(java.util.stream.Collectors.toMap(AppModuleBaseBO::getId,
                        AppModuleBaseBO::getParentId, (left, right) -> left));
        Long contactModuleId = resolveContactModuleId(
                customerModule, contactModules, parentApplicationIds);
        AppModuleBaseBO contactModule = contactModules.stream()
                .filter(module -> Objects.equals(contactModuleId, module.getId()))
                .findFirst().orElseThrow(() -> new IllegalStateException("未找到联系人模块配置"));
        AppFormBO addForm = remoteAppService.fastFormByModuleAndFormType(
                contactModuleId, FormTypeEnum.FORM_ADD.getValue()).getData();
        if (addForm == null || addForm.getId() == null) {
            throw new IllegalStateException("未找到联系人新增表单配置");
        }
        List<AppFormColumnBO> columns = remoteAppService.fastColumnByModuleAndFormType(
                contactModuleId, FormTypeEnum.FORM_ADD.getValue()).getData();
        Map<String, Object> document = new LinkedHashMap<>();
        document.put(StrConst.DATA_NAME, contactName);
        document.put("customerId", customer.getId());
        document.put(CONTACT_CUSTOMER_COLUMN, AppUtil.getJsonData(customer.getId(), customer.getDataName()));
        putContactColumn(columns, document, "职务",
                contactFields.get(CrmMcpToolConst.ARGUMENT_CONTACT_JOB_TITLE));
        putContactColumn(columns, document, "手机号",
                contactFields.get(CrmMcpToolConst.ARGUMENT_CONTACT_PHONE));
        putContactColumn(columns, document, "邮箱",
                contactFields.get(CrmMcpToolConst.ARGUMENT_CONTACT_EMAIL));
        putContactColumn(columns, document, "地址",
                contactFields.get(CrmMcpToolConst.ARGUMENT_CONTACT_ADDRESS));
        CrmPreContacts contact = contactsService.addDataSimp(
                new DataAddSimpDTO().setModuleId(contactModuleId).setFormId(addForm.getId())
                        .setDocData(document),
                CrmPreContacts.class,
                com.platform.mesh.crm.biz.modules.crm.precontactsdata.domain.po.CrmPreContactsData.class);
        if (!Objects.equals(contact.getCustomerId(), customer.getId())) {
            contact.setCustomerId(customer.getId());
            contactsService.updateById(contact);
            contactsService.getAppServiceManual().editEsData(
                    contactModule.getModuleIndex(), contact, new LinkedHashMap<>(document));
        }
        return contact.getId();
    }

    /**
     * 功能描述:
     * 〈按表单字段名称写入联系人动态字段〉
     * @param columns 联系人新增表单字段
     * @param document 待写入文档
     * @param columnName 业务字段名称
     * @param value 字段值
     * @author qingfeng
     */
    private void putContactColumn(List<AppFormColumnBO> columns, Map<String, Object> document,
            String columnName, String value) {
        if (StrUtil.isBlank(value)) {
            return;
        }
        columns.stream().filter(column -> columnName.equals(column.getColumnName()))
                .map(AppFormColumnBO::getColumnMac).findFirst()
                .ifPresent(columnMac -> document.put(columnMac, value));
    }

    /**
     * 功能描述:
     * 〈补写新增流程未持久化的客户动态字段，并同步 ES 文档〉
     * @param customerModules 可用客户模块
     * @param moduleId 当前客户模块 ID
     * @param customer 已创建的客户
     * @param customerFields 客户扩展字段
     * @author qingfeng
     */
    private void persistMissingCustomerFields(List<AppModuleBaseBO> customerModules, Long moduleId,
            CrmPreCustomer customer, Map<String, String> customerFields) {
        if (customerFields.isEmpty()) {
            return;
        }
        Set<String> persistedFields = customerDataService.lambdaQuery()
                .eq(CrmPreCustomerData::getDataId, customer.getId())
                .in(CrmPreCustomerData::getColumnMac, customerFields.keySet())
                .list().stream()
                .map(CrmPreCustomerData::getColumnMac)
                .collect(java.util.stream.Collectors.toSet());
        Map<String, Object> missingFields = customerFields.entrySet().stream()
                .filter(entry -> !persistedFields.contains(entry.getKey()))
                .collect(java.util.stream.Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (left, right) -> right, LinkedHashMap::new));
        if (missingFields.isEmpty()) {
            return;
        }
        AppModuleBaseBO customerModule = customerModules.stream()
                .filter(module -> Objects.equals(moduleId, module.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("未找到客户模块配置"));
        List<AppFormColumnBO> columns = remoteAppService.fastColumnByModuleAndFormType(
                moduleId, FormTypeEnum.FORM_EDIT.getValue()).getData();
        List<CrmPreCustomerData> missingData = customerService.getAppServiceManual().getDbDataSimp(
                customerModule, customer.getId(), CrmPreCustomerData.class, columns, missingFields);
        if (!missingData.isEmpty()) {
            customerDataService.saveBatch(missingData);
            customerService.getAppServiceManual().editEsData(
                    customerModule.getModuleIndex(), customer, new LinkedHashMap<>(missingFields));
        }
    }

    /**
     * 功能描述:
     * 〈基于账号、模块及预览字段生成客户创建确认口令〉
     * @param moduleId 客户模块 ID
     * @param customerName 客户名称
     * @param description 客户描述
     * @param customerFields 客户扩展字段
     * @param contactFields 联系人字段
     * @return SHA-256 确认口令
     * @author qingfeng
     */
    private String customerConfirmationToken(Long moduleId, String customerName, String description,
            Map<String, String> customerFields, Map<String, String> contactFields) {
        LoginUserBO user = UserCacheUtil.getLoginUser();
        Long accountId = user == null ? null : user.getAccountId();
        return cn.hutool.crypto.digest.DigestUtil.sha256Hex(
                moduleId + "|" + customerName + "|" + StrUtil.nullToEmpty(description) + "|" + customerFields
                        + "|" + contactFields
                        + "|" + accountId);
    }

    /**
     * 功能描述:
     * 〈构建客户创建预览或执行结果及前端业务卡片〉
     * @param state 当前执行状态
     * @param moduleId 客户模块 ID
     * @param customerName 客户名称
     * @param description 客户描述
     * @param customerFields 客户扩展字段
     * @param contactFields 联系人字段
     * @param token 确认口令
     * @param recordId 客户记录 ID
     * @param contactId 联系人记录 ID
     * @return MCP 标准业务结果
     * @author qingfeng
     */
    private Map<String, Object> customerCreationResult(String state, Long moduleId,
            String customerName, String description, Map<String, String> customerFields,
            Map<String, String> contactFields, String token, Long recordId, Long contactId) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("state", state);
        response.put("moduleId", String.valueOf(moduleId));
        response.put("customerName", customerName);
        response.put("customerDescription", description);
        response.put("confirmToken", token);
        response.put("recordId", recordId == null ? null : String.valueOf(recordId));
        response.put("contactId", contactId == null ? null : String.valueOf(contactId));
        String type = recordId == null ? "customer_confirmation" : "execution_result";
        boolean createContact = StrUtil.isNotBlank(
                contactFields.get(CrmMcpToolConst.ARGUMENT_CONTACT_NAME));
        Map<String, Object> block = new LinkedHashMap<>();
        block.put("id", type + "-" + token.substring(0, 12));
        block.put("type", type);
        block.put("title", recordId == null
                ? (createContact ? "确认创建客户及联系人" : "确认创建客户")
                : (createContact ? "客户及联系人已创建" : "客户已创建"));
        block.put("subtitle", customerName);
        block.put("status", Map.of("label", recordId == null ? "等待确认" : "写入成功",
                "tone", recordId == null ? "warning" : "success"));
        List<Map<String, Object>> evidence = new ArrayList<>();
        evidence.add(Map.of("label", "客户名称", "value", customerName));
        if (StrUtil.isNotBlank(description)) {
            evidence.add(Map.of("label", "补充信息", "value", description));
        }
        Map<String, String> labels = Map.of("phone", "手机号", "email", "邮箱", "address", "客户地址");
        customerFields.forEach((key, value) -> evidence.add(Map.of(
                "label", labels.getOrDefault(key, key), "value", value)));
        Map<String, String> contactLabels = Map.of(
                CrmMcpToolConst.ARGUMENT_CONTACT_NAME, "联系人姓名",
                CrmMcpToolConst.ARGUMENT_CONTACT_JOB_TITLE, "联系人职务",
                CrmMcpToolConst.ARGUMENT_CONTACT_PHONE, "联系人手机号",
                CrmMcpToolConst.ARGUMENT_CONTACT_EMAIL, "联系人邮箱",
                CrmMcpToolConst.ARGUMENT_CONTACT_ADDRESS, "联系人地址");
        contactFields.forEach((key, value) -> evidence.add(Map.of(
                "label", contactLabels.getOrDefault(key, key), "value", value)));
        block.put("evidence", evidence);
        String fieldPrompt = customerFields.entrySet().stream()
                .map(entry -> "，" + entry.getKey() + "=\"" + entry.getValue() + "\"")
                .collect(java.util.stream.Collectors.joining());
        String contactPrompt = contactFields.entrySet().stream()
                .map(entry -> "，" + entry.getKey() + "=\"" + entry.getValue() + "\"")
                .collect(java.util.stream.Collectors.joining());
        block.put("actions", recordId == null ? List.of(Map.of(
                "code", "confirm_create_customer",
                "label", createContact ? "确认创建客户及联系人" : "确认创建客户",
                "tone", "primary",
                "prompt", "调用 crm_create_customer 执行已确认写入：customerName=\"" +
                        customerName + "\"" + fieldPrompt + contactPrompt + "，customerDescription=\"" +
                        StrUtil.nullToEmpty(description) + "\"，moduleId=\"" + moduleId +
                        "\"，confirm=true，confirmToken=\"" +
                        token + "\"",
                "confirmationRequired", true)) : Collections.emptyList());
        response.put("businessBlocks", List.of(block));
        return response;
    }

    /**
     * 功能描述:
     * 〈预览或确认创建客户跟进记录〉
     * @param arguments MCP 工具参数
     * @return 业务处理结果
     * @author qingfeng
     */
    @Transactional(rollbackFor = Exception.class)
    public Object createCustomerFollow(JSONObject arguments) {
        // 第一步：校验客户、跟进内容并生成与当前用户绑定的确认令牌。
        resolveCustomerId(arguments);
        Long customerId = arguments.getLong(CrmMcpToolConst.ARGUMENT_CUSTOMER_ID);
        String content = StrUtil.trim(arguments.getStr(CrmMcpToolConst.ARGUMENT_CONTENT));
        String nextTimeText = normalizeNextTime(
                StrUtil.trim(arguments.getStr(CrmMcpToolConst.ARGUMENT_NEXT_TIME)), LocalDateTime.now());
        arguments.set(CrmMcpToolConst.ARGUMENT_NEXT_TIME, nextTimeText);
        CrmPreCustomer customer = findCustomer(customerId);
        if (customer == null || StrUtil.isBlank(content)) {
            throw new IllegalArgumentException("客户和跟进内容不能为空");
        }
        customerId = customer.getId();
        String token = confirmationToken(customerId, content, nextTimeText);
        boolean confirmed = Boolean.TRUE.equals(arguments.getBool(CrmMcpToolConst.ARGUMENT_CONFIRM));
        if (!confirmed) {
            return followResult("PREVIEW", customer, content, nextTimeText, token, null);
        }

        // 第二步：确认写入时使用常量时间比较校验令牌，防止参数被修改。
        if (!MessageDigest.isEqual(token.getBytes(StandardCharsets.UTF_8),
                StrUtil.nullToEmpty(arguments.getStr(CrmMcpToolConst.ARGUMENT_CONFIRM_TOKEN))
                        .getBytes(StandardCharsets.UTF_8))) {
            throw new IllegalArgumentException("确认口令无效，请重新生成操作预览");
        }
        // 第三步：通过应用配置定位跟进模块和新增表单，沿用标准业务写入链路。
        Long followModuleId = findPrimaryModuleId(CrmOnFollow.class);
        AppFormBO addForm = remoteAppService.fastFormByModuleAndFormType(
                followModuleId, FormTypeEnum.FORM_ADD.getValue()).getData();
        if (addForm == null || addForm.getId() == null) {
            throw new IllegalStateException("未找到跟进记录新增表单配置");
        }
        // 第四步：补充客户关联字段后写入数据库；标准服务负责后续 ES 同步。
        DataAddSimpDTO addDTO = new DataAddSimpDTO()
                .setModuleId(followModuleId)
                .setFormId(addForm.getId())
                .setDocData(buildFollowDocData(content, parseNextTime(nextTimeText)));
        addCustomerRelation(addDTO.getDocData(), followModuleId, customer);
        CrmOnFollow follow = followService.addDataSimp(
                addDTO, CrmOnFollow.class, CrmOnFollowData.class);
        return followResult("EXECUTED", customer, content, nextTimeText, token, follow.getId());
    }

    /**
     * 功能描述:
     * 〈补充跟进记录与客户之间的关联关系〉
     * @param document 待写入搜索引擎的跟进文档
     * @param followModuleId 跟进记录模块标识
     * @param customer 客户数据
     * @author qingfeng
     */
    private void addCustomerRelation(Map<String, Object> document, Long followModuleId,
            CrmPreCustomer customer) {
        List<AppFormColumnBO> columns = remoteAppService.fastColumnByModuleAndFormType(
                followModuleId, FormTypeEnum.FORM_ADD.getValue()).getData();
        AppFormColumnBO relationColumn = columns.stream()
                .filter(column -> CompTypeEnum.RELEVANCE.getDesc().equals(column.getCompMac())
                        || CompTypeEnum.RELEVANCE_ALL_FIELD.getDesc().equals(column.getCompMac()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("跟进记录新增表单缺少关联业务字段"));
        Map<String, Object> relationValue = new LinkedHashMap<>();
        relationValue.put(CompTypeEnum.RELEVANCE.getIdMac(), String.valueOf(customer.getId()));
        relationValue.put(StrUtil.toCamelCase(StrConst.MODULE_ID),
                String.valueOf(customer.getModuleId()));
        relationValue.put("moduleName", "客户");
        relationValue.put("name", customer.getDataName());
        document.put(relationColumn.getColumnMac(), List.of(relationValue));
    }

    /**
     * 构造标准跟进文档字段，供数据库和 ES 写入链路共同使用。
     */
    static Map<String, Object> buildFollowDocData(String content, LocalDateTime nextTime) {
        Map<String, Object> document = new LinkedHashMap<>();
        document.put(StrConst.DATA_NAME, StrUtil.maxLength(content, 64));
        document.put(StrConst.DATA_DESC, content);
        document.put(ObjFieldUtil.getFieldName(CrmOnFollow::getNextTime), nextTime);
        return document;
    }

    /**
     * 功能描述:
     * 〈构建客户跟进预览或写入结果〉
     * @param state 跟进操作状态
     * @param customer 客户数据
     * @param content 本次跟进内容
     * @param nextTime 下次跟进时间
     * @param token 写入操作确认令牌
     * @param recordId 跟进记录标识
     * @return 业务处理结果
     * @author qingfeng
     */
    private Map<String, Object> followResult(String state, CrmPreCustomer customer,
            String content, String nextTime, String token, Long recordId) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("state", state);
        response.put("customerId", String.valueOf(customer.getId()));
        response.put("customerName", customer.getDataName());
        response.put("content", content);
        response.put("nextTime", nextTime);
        response.put("confirmToken", token);
        response.put("recordId", recordId == null ? null : String.valueOf(recordId));
        String type = recordId == null ? "follow_confirmation" : "execution_result";
        Map<String, Object> block = new LinkedHashMap<>();
        block.put("id", type + "-" + token.substring(0, 12));
        block.put("type", type);
        block.put("title", recordId == null ? "确认创建客户跟进" : "跟进记录已创建");
        block.put("subtitle", customer.getDataName());
        block.put("status", Map.of("label", recordId == null ? "等待确认" : "写入成功",
                "tone", recordId == null ? "warning" : "success"));
        block.put("evidence", List.of(
                Map.of("label", "跟进内容", "value", content),
                Map.of("label", "下次跟进", "value", StrUtil.blankToDefault(nextTime, "未设置"))));
        block.put("actions", recordId == null ? List.of(Map.of(
                "code", "confirm_create_follow", "label", "确认写入CRM", "tone", "primary",
                "prompt", "调用 crm_create_customer_follow 执行已确认写入：customerId=\"" +
                        customer.getId() + "\"，content=\"" + content + "\"，nextTime=\"" +
                        StrUtil.nullToEmpty(nextTime) + "\"，confirm=true，confirmToken=\"" + token + "\"",
                "confirmationRequired", true)) : Collections.emptyList());
        response.put("businessBlocks", List.of(block));
        return response;
    }

    /**
     * 功能描述:
     * 〈生成跟进写入操作的确认令牌〉
     * @param customerId 客户标识
     * @param content 本次跟进内容
     * @param nextTime 下次跟进时间
     * @return 业务处理结果
     * @author qingfeng
     */
    private String confirmationToken(Long customerId, String content, String nextTime) {
        return cn.hutool.crypto.digest.DigestUtil.sha256Hex(
                customerId + "|" + content + "|" + StrUtil.nullToEmpty(nextTime)
                        + "|" + UserCacheUtil.getLoginUser().getAccountId());
    }

    /**
     * 功能描述:
     * 〈解析并校验下次跟进时间〉
     * @param value 待处理的业务值
     * @return 业务处理结果
     * @author qingfeng
     */
    private static LocalDateTime parseNextTime(String value) {
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    static String normalizeNextTime(String value, LocalDateTime now) {
        LocalDateTime nextTime = parseNextTime(value);
        if (nextTime == null) {
            return value;
        }
        LocalDateTime normalized = nextTime.isBefore(now)
                ? now.plusDays(1).withHour(nextTime.getHour())
                        .withMinute(nextTime.getMinute()).withSecond(0).withNano(0)
                : nextTime;
        return normalized.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    /**
     * 功能描述:
     * 〈查询指定业务实体的主模块标识〉
     * @param entityType 业务实体类型
     * @return 业务处理结果
     * @author qingfeng
     */
    private Long findPrimaryModuleId(Class<?> entityType) {
        ArrayList<String> schemas = CollUtil.newArrayList(
                SqlUtil.getTableName(entityType, TableName.class));
        List<AppModuleBaseBO> modules = remoteAppService.getModuleBaseInfoBySchema(schemas).getData();
        return modules.stream()
                .filter(module -> !YesOrNoEnum.YES.getValue().equals(module.getOpenFlag()))
                .sorted(Comparator.comparing(AppModuleBaseBO::getAiFlag,
                        Comparator.nullsLast(Comparator.reverseOrder()))
                        .thenComparing(Comparator.comparing(AppModuleBaseBO::getCreateTime).reversed()))
                .map(AppModuleBaseBO::getId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("未找到跟进记录模块配置"));
    }

    /**
     * 功能描述:
     * 〈根据客户名称补全客户标识〉
     * @param arguments MCP 工具参数
     * @author qingfeng
     */
    private void resolveCustomerId(JSONObject arguments) {
        if (arguments.getLong(CrmMcpToolConst.ARGUMENT_CUSTOMER_ID) != null) {
            return;
        }
        String customerName = StrUtil.trim(arguments.getStr("customerName"));
        if (StrUtil.isBlank(customerName)) {
            return;
        }
        CrmPreCustomer customer = customerService.getOne(
                new LambdaQueryWrapper<CrmPreCustomer>()
                        .eq(CrmPreCustomer::getDataName, customerName)
                        .eq(CrmPreCustomer::getDelFlag, NumberConst.NUM_1)
                        .orderByDesc(CrmPreCustomer::getUpdateTime)
                        .last("LIMIT 1"), false);
        if (customer != null) {
            arguments.set(CrmMcpToolConst.ARGUMENT_CUSTOMER_ID, customer.getId());
        }
    }

    /**
     * 功能描述:
     * 〈按数据权限查询指定客户〉
     * @param customerId 客户标识
     * @return 业务处理结果
     * @author qingfeng
     */
    private CrmPreCustomer findCustomer(Long customerId) {
        // 非法客户 ID 直接返回空结果
        if (customerId == null || customerId <= 0) {
            return null;
        }
        // 客户详情与开放模块页面保持一致，仅忽略组织数据范围。
        CrmPreCustomer customer = customerService.getOne(
                new LambdaQueryWrapper<CrmPreCustomer>()
                        .eq(CrmPreCustomer::getId, customerId)
                        .eq(CrmPreCustomer::getDelFlag, NumberConst.NUM_1), false);
        if (customer != null) {
            return customer;
        }
        // MCP 参数经过部分模型网关时可能先按 IEEE-754 数字解析，雪花 ID
        // 最低若干位会被舍入；在当前数据窗口内校准真实主键。
        if (customerId > 1024) {
            customer = customerService.getOne(
                    new LambdaQueryWrapper<CrmPreCustomer>()
                            .between(CrmPreCustomer::getId, customerId - 1024, customerId + 1024)
                            .eq(CrmPreCustomer::getDelFlag, NumberConst.NUM_1)
                            .orderByDesc(CrmPreCustomer::getUpdateTime)
                            .last("LIMIT 1"), false);
            if (customer != null) {
                return customer;
            }
        }
        // 兼容模型误将纯数字客户编码当作 customerId 的历史会话。
        return customerService.getOne(
                new LambdaQueryWrapper<CrmPreCustomer>()
                        .eq(CrmPreCustomer::getDataSerial, String.valueOf(customerId))
                        .eq(CrmPreCustomer::getDelFlag, NumberConst.NUM_1), false);
    }

    /**
     * 功能描述:
     * 〈将客户转换为基础摘要〉
     * @param customer 客户数据
     * @return 业务处理结果
     * @author qingfeng
     */
    private Object toCustomerSummary(Object customer) {
        JSONObject result = JSONUtil.parseObj(customer);
        CrmPreCustomer preCustomer = BeanUtil.toBean(customer, CrmPreCustomer.class);
        result.set(ObjFieldUtil.getFieldName(CrmPreCustomer::getPhone), maskPhone(preCustomer.getPhone()));
        return result;
    }

    /**
     * 功能描述:
     * 〈按字段权限构建客户可见摘要〉
     * @param customer 客户数据
     * @param visibleColumns 当前用户可见字段配置
     * @return 业务处理结果
     * @author qingfeng
     */
    private Object toVisibleCustomerSummary(Object customer, List<AppFormColumnBO> visibleColumns) {
        JSONObject source = JSONUtil.parseObj(customer);
        Map<String, Object> result = new LinkedHashMap<>();
        // 后续 MCP 调用必须使用数据库主键，避免与纯数字客户编码混淆。
        Long canonicalId = resolveCanonicalCustomerId(source);
        if (canonicalId != null) {
            result.put("customerId", String.valueOf(canonicalId));
        }
        if (CollUtil.isEmpty(visibleColumns)) {
            return result;
        }
        String phoneField = ObjFieldUtil.getFieldName(CrmPreCustomer::getPhone);
        // 按页面表头顺序输出字段，并使用页面显示名称作为结果字段名。
        for (AppFormColumnBO column : visibleColumns) {
            if (StrUtil.isBlank(column.getColumnMac()) || StrUtil.isBlank(column.getColumnName())) {
                continue;
            }
            Object value = AppUtil.getColumnValue(column.getColumnMac(), source);
            if (phoneField.equals(column.getColumnMac())) {
                value = maskPhone(value == null ? null : value.toString());
            }
            result.putIfAbsent(column.getColumnName(), value);
        }
        return result;
    }

    /**
     * 功能描述:
     * 〈从客户数据中解析标准客户标识〉
     * @param source 场景原始数据
     * @return 业务处理结果
     * @author qingfeng
     */
    private Long resolveCanonicalCustomerId(JSONObject source) {
        String idField = ObjFieldUtil.getFieldName(CrmPreCustomer::getId);
        String dataNameField = ObjFieldUtil.getFieldName(CrmPreCustomer::getDataName);
        String phoneField = ObjFieldUtil.getFieldName(CrmPreCustomer::getPhone);
        Object approximateIdValue = AppUtil.getColumnValue(idField, source);
        Long approximateId = Convert.toLong(approximateIdValue);
        String dataName = Convert.toStr(AppUtil.getColumnValue(dataNameField, source));
        String phone = Convert.toStr(AppUtil.getColumnValue(phoneField, source));
        if (approximateId != null && approximateId > 1024) {
            CrmPreCustomer preciseCustomer = customerService.getOne(
                    new LambdaQueryWrapper<CrmPreCustomer>()
                            .between(CrmPreCustomer::getId, approximateId - 1024, approximateId + 1024)
                            .eq(StrUtil.isNotBlank(dataName), CrmPreCustomer::getDataName, dataName)
                            .eq(StrUtil.isNotBlank(phone), CrmPreCustomer::getPhone, phone)
                            .eq(CrmPreCustomer::getDelFlag, NumberConst.NUM_1)
                            .last("LIMIT 1"), false);
            if (preciseCustomer != null) {
                return preciseCustomer.getId();
            }
        }
        if (StrUtil.isBlank(dataName)) {
            return null;
        }
        CrmPreCustomer customer = customerService.getOne(
                new LambdaQueryWrapper<CrmPreCustomer>()
                        .eq(CrmPreCustomer::getDataName, dataName)
                        .eq(StrUtil.isNotBlank(phone), CrmPreCustomer::getPhone, phone)
                        .eq(CrmPreCustomer::getDelFlag, NumberConst.NUM_1)
                        .orderByDesc(CrmPreCustomer::getUpdateTime)
                        .last("LIMIT 1"), false);
        return customer == null ? null : customer.getId();
    }

    /**
     * 功能描述:
     * 〈将联系人转换为安全摘要〉
     * @param contact 联系人数据
     * @return 业务处理结果
     * @author qingfeng
     */
    private Object toContactSummary(CrmPreContacts contact) {
        return summary(contact.getId(), contact.getDataName(),
                contact.getDataDesc(), contact.getUpdateTime());
    }

    /**
     * 功能描述:
     * 〈将商机转换为安全摘要〉
     * @param business 商机数据
     * @return 业务处理结果
     * @author qingfeng
     */
    private Object toBusinessSummary(CrmOnBusiness business) {
        Map<String, Object> result = summary(business.getId(), business.getDataName(),
                business.getDataDesc(), business.getUpdateTime());
        result.put("realMoney", business.getRealMoney());
        result.put("processStage", business.getProcessStage());
        return result;
    }

    /**
     * 功能描述:
     * 〈将跟进记录转换为安全摘要〉
     * @param follow 跟进记录数据
     * @return 业务处理结果
     * @author qingfeng
     */
    private Object toFollowSummary(CrmOnFollow follow) {
        Map<String, Object> result = summary(follow.getId(), follow.getDataName(),
                follow.getDataDesc(), follow.getUpdateTime());
        result.put("nextTime", follow.getNextTime());
        return result;
    }

    /**
     * 功能描述:
     * 〈获取当前 CRM 连接和账号信息〉
     * @param connected CRM 是否已连接
     * @param accountNickname 当前账号昵称
     * @return 业务处理结果
     * @author qingfeng
     */
    private Map<String, Object> connectionInfo(boolean connected, String accountNickname) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("connected", connected);
        result.put("accountNickname", accountNickname);
        result.put("accessMode", CrmMcpToolConst.ACCESS_MODE_READ_ONLY);
        return result;
    }

    /**
     * 功能描述:
     * 〈构建统一业务摘要结构〉
     * @param id 业务数据标识
     * @param name 业务数据名称
     * @param description 业务描述
     * @param updatedAt 业务更新时间
     * @return 业务处理结果
     * @author qingfeng
     */
    private Map<String, Object> summary(Long id, String name, String description, Object updatedAt) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put(CrmMcpToolConst.RESULT_ID, idText(id));
        result.put(CrmMcpToolConst.RESULT_NAME, name);
        result.put(CrmMcpToolConst.RESULT_DESCRIPTION, description);
        result.put(CrmMcpToolConst.RESULT_UPDATED_AT, updatedAt);
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

    /**
     * 功能描述:
     * 〈对客户手机号进行脱敏处理〉
     * @param phone 客户手机号
     * @return 业务处理结果
     * @author qingfeng
     */
    private static String maskPhone(String phone) {
        // 清理手机号首尾空白并处理空值
        String normalized = StrUtil.trim(phone);
        if (StrUtil.isBlank(normalized)) {
            return null;
        }
        // 短号码全部隐藏，标准号码保留前三位和后四位
        if (normalized.length() <= 7) {
            return "*".repeat(normalized.length());
        }
        return normalized.substring(0, 3)
                + "*".repeat(normalized.length() - 7)
                + normalized.substring(normalized.length() - 4);
    }

    /**
     * 功能描述:
     * 〈解析本次创建使用的客户模块〉
     * @param requestedModuleId 用户明确指定的客户模块 ID
     * @param customerModules 当前可用客户模块
     * @return 客户模块 ID
     * @author qingfeng
     */
    private Long resolveCustomerModuleId(Long requestedModuleId,
            List<AppModuleBaseBO> customerModules) {
        List<AppModuleBaseBO> availableModules = customerModules.stream()
                .filter(module -> !YesOrNoEnum.YES.getValue().equals(module.getOpenFlag()))
                .toList();
        if (requestedModuleId != null) {
            return availableModules.stream()
                    .filter(module -> requestedModuleId.equals(module.getId()))
                    .map(AppModuleBaseBO::getId)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("指定模块不是可用的客户模块"));
        }
        return availableModules.stream()
                .sorted(Comparator.comparing(AppModuleBaseBO::getAiFlag,
                                Comparator.nullsLast(Comparator.reverseOrder()))
                        .thenComparing(AppModuleBaseBO::getCreateTime,
                                Comparator.nullsLast(Comparator.reverseOrder())))
                .map(AppModuleBaseBO::getId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("未找到客户模块配置"));
    }

    /**
     * 功能描述:
     * 〈解析与客户处于同一应用的联系人模块〉
     * @param customerModule 当前客户模块
     * @param relatedModules 可用联系人模块
     * @param parentApplicationIds 模块分组与应用 ID 映射
     * @return 联系人模块 ID
     * @author qingfeng
     */
    private Long resolveContactModuleId(AppModuleBaseBO customerModule,
            List<AppModuleBaseBO> relatedModules, Map<Long, Long> parentApplicationIds) {
        Long applicationId = parentApplicationIds.get(customerModule.getParentId());
        return relatedModules.stream()
                .filter(module -> !YesOrNoEnum.YES.getValue().equals(module.getOpenFlag()))
                .filter(module -> Objects.equals(applicationId,
                        parentApplicationIds.get(module.getParentId())))
                .sorted(Comparator.comparing(AppModuleBaseBO::getCreateTime,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .map(AppModuleBaseBO::getId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("未找到当前应用的联系人模块配置"));
    }
}
