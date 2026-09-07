package com.platform.mesh.ai.biz.job;

import com.platform.mesh.ai.biz.job.service.IAiArticleJobService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description AI文章生成定时任务调度服务
 * @author 蝉鸣
 */
@Component
public class AiArticleJob {

    private static final Logger log = LoggerFactory.getLogger(AiArticleJob.class);

    @Autowired
    private IAiArticleJobService aiArticleJobService;

    /**
     * 功能描述:
     * 【生成CRM相关文章并保存在线文档】
     * @author 蝉鸣
     */
    @XxlJob("aiCrmArticleJobHandler")
    public void aiCrmArticleJobHandler() {
        log.info(">>>AI CRM文章生成定时任务开始执行！！！");
        XxlJobHelper.log(">>>AI CRM文章生成定时任务开始执行！！！");
        aiArticleJobService.generateCrmArticle();
        log.info(">>>AI CRM文章生成定时任务结束执行！！！");
    }
}
