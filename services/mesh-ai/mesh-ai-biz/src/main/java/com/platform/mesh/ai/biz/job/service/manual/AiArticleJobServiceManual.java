package com.platform.mesh.ai.biz.job.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.ai.biz.job.domain.dto.AiArticleJobConfigDTO;
import com.platform.mesh.ai.biz.job.exception.AiJobExceptionEnum;
import com.platform.mesh.ai.biz.modules.ai.agent.domain.po.AiAgent;
import com.platform.mesh.ai.biz.modules.ai.agent.service.IAiAgentService;
import com.platform.mesh.ai.biz.modules.ai.job.domain.po.AiJob;
import com.platform.mesh.ai.biz.modules.ai.job.enums.AiJobFlagEnum;
import com.platform.mesh.ai.biz.modules.ai.job.service.IAiJobService;
import com.platform.mesh.ai.biz.soa.agent.AiAgentService;
import com.platform.mesh.ai.biz.soa.agent.domain.bo.AgentMsgBO;
import com.platform.mesh.ai.biz.soa.agent.enums.AgentFlagEnum;
import com.platform.mesh.ai.biz.soa.agent.factory.AiAgentFactory;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.api.modules.doc.domain.dto.DocOnlineSaveDTO;
import com.platform.mesh.upms.api.modules.doc.feign.RemoteDocService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @description AI文章生成任务辅助服务
 * @author 蝉鸣
 */
@Service
public class AiArticleJobServiceManual {

    private final static Logger log = LoggerFactory.getLogger(AiArticleJobServiceManual.class);

    /**
     * AI文章生成调用削峰信号量，限制当前服务实例内同一时间只请求一次智能体。
     */
    private static final Semaphore AI_ARTICLE_SEMAPHORE = new Semaphore(NumberConst.NUM_1);

    /**
     * 等待获取AI文章生成执行许可的最长时间，超过后跳过当前文章，避免任务无限堆积。
     */
    private static final Long AI_ARTICLE_ACQUIRE_TIMEOUT_SECONDS = NumberConst.NUM_600.longValue();

    /**
     * 每次AI文章生成请求结束后的缓冲间隔，避免连续请求过快打满智能体服务。
     */
    private static final Long AI_ARTICLE_INTERVAL_SECONDS = NumberConst.NUM_1.longValue();

    @Autowired
    private IAiAgentService aiAgentService;

    @Autowired
    private IAiJobService aiJobService;

    @Autowired
    private AiAgentFactory aiAgentFactory;

    @Autowired
    private RemoteDocService remoteDocService;

    /**
     * 功能描述:
     * 【生成CRM相关文章并保存在线文档】
     * @author 蝉鸣
     */
    public void generateCrmArticle() {
        Integer pageNum = NumberConst.NUM_1;
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPageSize(NumberConst.NUM_100);
        {
            while (true) {
                pageDTO.setPageNum(pageNum);
                MPage<AiJob> mPage = MPageUtil.pageEntityToMPage(pageDTO, AiJob.class);
                MPage<AiJob> aiJobPage = aiJobService.getAllJob(mPage, AiJobFlagEnum.GEN_ARTICLE.getValue());
                if (CollUtil.isEmpty(aiJobPage.getRecords())) {
                    break;
                }
                for (AiJob aiJob : aiJobPage.getRecords()) {
                    //执行生成逻辑
                    generateCrmArticle(aiJob);
                }
                if (pageNum >= aiJobPage.getPages()) {
                    break;
                }
                pageNum++;
            }
        }
    }

    /**
     * 功能描述:
     * 【根据AI任务配置生成CRM相关文章并保存在线文档】
     * @param aiJob aiJob
     * @author 蝉鸣
     */
    public void generateCrmArticle(AiJob aiJob) {
        //获取任务参数配置
        AiArticleJobConfigDTO jobConfig = getJobConfig(aiJob);
        if (ObjectUtil.isEmpty(jobConfig) || StrUtil.isBlank(jobConfig.getTopic())) {
            return;
        }
        //获取智能体
        AiAgent aiAgent = getAiAgent(aiJob.getAgentId());
        if (ObjectUtil.isEmpty(aiAgent)) {
            return;
        }
        AgentFlagEnum agentFlagEnum = BaseEnum.getEnumByValue(AgentFlagEnum.class, aiAgent.getAgentFlag());
        AiAgentService agentService = aiAgentFactory.getAiAgentService(agentFlagEnum);
        if (ObjectUtil.isEmpty(agentService)) {
            return;
        }
        for (int i = NumberConst.NUM_0; i < jobConfig.getCount(); i++) {
            generateCrmArticle(aiJob, jobConfig, agentService, aiAgent, i);
        }
    }

    /**
     * 功能描述:
     * 【生成单篇CRM文章并保存在线文档，内部通过信号量限制智能体调用并发】
     * @param aiJob aiJob
     * @param jobConfig jobConfig
     * @param agentService agentService
     * @param aiAgent aiAgent
     * @param index 当前任务内第几篇文章
     * @author 蝉鸣
     */
    private void generateCrmArticle(AiJob aiJob, AiArticleJobConfigDTO jobConfig, AiAgentService agentService,
                                    AiAgent aiAgent, Integer index) {
        long startTime = System.currentTimeMillis();
        String userHash = "ai-article-job-" + aiJob.getId() + "-" + index + "-" + IdUtil.fastSimpleUUID();
        boolean acquired = Boolean.FALSE;
        try {
            // 获取执行许可，避免高并发场景下多个任务同时请求智能体。
            acquired = AI_ARTICLE_SEMAPHORE.tryAcquire(AI_ARTICLE_ACQUIRE_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            if (!acquired) {
                log.warn("AI article generate skipped by rate limit jobId={}, index={}, userHash={}, wait={}s",
                        aiJob.getId(), index, userHash, AI_ARTICLE_ACQUIRE_TIMEOUT_SECONDS);
                return;
            }
            String content = agentService.chatByAiAgent(aiAgent, buildAgentMsg(jobConfig, userHash));
            if (StrUtil.isBlank(content)) {
                log.error("{} jobId={}, index={}, userHash={}", AiJobExceptionEnum.JOB_GEN_ERROR.getDesc(),
                        aiJob.getId(), index, userHash);
                return;
            }
            DocOnlineSaveDTO saveDTO = buildSaveDTO(content);
            if(ObjectUtil.isEmpty(saveDTO)){
                log.error("{} jobId={}, index={}, userHash={}, contentLength={}",
                        AiJobExceptionEnum.JOB_GEN_ERROR.getDesc(), aiJob.getId(), index, userHash, content.length());
                return;
            }
            remoteDocService.saveOnline(saveDTO);
            log.info("AI article generate success jobId={}, index={}, userHash={}, cost={}ms",
                    aiJob.getId(), index, userHash, System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            log.error("{} jobId={}, index={}, userHash={}, cost={}ms",
                    AiJobExceptionEnum.JOB_GEN_ERROR.getDesc(), aiJob.getId(), index, userHash,
                    System.currentTimeMillis() - startTime, e);
        } finally {
            if (acquired) {
                sleepQuietly();
                AI_ARTICLE_SEMAPHORE.release();
            }
        }
    }

    /**
     * 功能描述:
     * 【AI文章生成请求结束后短暂休眠，用于削峰缓冲】
     * @author 蝉鸣
     */
    private void sleepQuietly() {
        try {
            TimeUnit.SECONDS.sleep(AI_ARTICLE_INTERVAL_SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 功能描述:
     * 【获取任务配置】
     * @param aiJob aiJob
     * @return 正常返回:{@link AiArticleJobConfigDTO}
     * @author 蝉鸣
     */
    public AiArticleJobConfigDTO getJobConfig(AiJob aiJob) {
        if (ObjectUtil.isEmpty(aiJob) || ObjectUtil.isEmpty(aiJob.getParams())) {
            return null;
        }
        AiArticleJobConfigDTO jobConfig = JSONUtil.toBean(JSONUtil.toJsonStr(aiJob.getParams()), AiArticleJobConfigDTO.class);
        //设置生成数量
        if (ObjectUtil.isEmpty(jobConfig.getCount()) || jobConfig.getCount() < NumberConst.NUM_1) {
            jobConfig.setCount(NumberConst.NUM_1);
        }
        if (jobConfig.getCount() > NumberConst.NUM_5) {
            jobConfig.setCount(NumberConst.NUM_5);
        }
        return jobConfig;
    }

    /**
     * 功能描述:
     * 【获取智能体】
     * @param agentId agentId
     * @return 正常返回:{@link AiAgent}
     * @author 蝉鸣
     */
    public AiAgent getAiAgent(Long agentId) {
        if (ObjectUtil.isNotEmpty(agentId)) {
            return aiAgentService.getById(agentId);
        }
        return null;
    }

    /**
     * 功能描述:
     * 【构建智能体消息】
     * @param jobConfig jobConfig
     * @param userHash 智能体会话用户标识，不同文章使用不同值以隔离上下文
     * @return 正常返回:{@link AgentMsgBO}
     * @author 蝉鸣
     */
    public AgentMsgBO buildAgentMsg(AiArticleJobConfigDTO jobConfig, String userHash) {
        return new AgentMsgBO()
                .setContent(jobConfig.getTopic())
                .setUserHash(userHash);
    }

    /**
     * 功能描述:
     * 【构建在线文档保存参数】
     * @param content content
     * @return 正常返回:{@link DocOnlineSaveDTO}
     * @author 蝉鸣
     */
    public DocOnlineSaveDTO buildSaveDTO(String content) {
        if(!JSONUtil.isTypeJSON(content)){
            return null;
        }
        content = sanitize(content);
        JSONObject jsonObject = JSONUtil.parseObj(content);
        if(!jsonObject.containsKey("output")){
            return null;
        }
        Object object = jsonObject.get("output");
        if(ObjectUtil.isEmpty(object)){
            return null;
        }
        return JSONUtil.toBean(JSONUtil.parseObj(object), DocOnlineSaveDTO.class);
    }

    /**
     * 功能描述:
     * 【移除控制字符】
     * @param content content
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    private String sanitize(String content) {
        if (content == null) return null;

        // 移除所有 ASCII 0-31 的控制字符（保留 \t \n \r）
        // \t = 9, \n = 10, \r = 13
        String cleaned = content.replaceAll("[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F]", "");

        // 额外处理：如果还有残留的 code 31
        cleaned = cleaned.replace((char) 31, ' ');

        return cleaned;
    }

}
