package com.platform.mesh.ai.biz.job.service.impl;

import com.platform.mesh.ai.biz.job.service.IAiArticleJobService;
import com.platform.mesh.ai.biz.job.service.manual.AiArticleJobServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description AI文章生成任务服务
 * @author 蝉鸣
 */
@Service
public class AiArticleJobServiceImpl implements IAiArticleJobService {

    @Autowired
    private AiArticleJobServiceManual aiArticleJobServiceManual;

    /**
     * 功能描述:
     * 【生成CRM相关文章并保存在线文档】
     * @author 蝉鸣
     */
    @Override
    public void generateCrmArticle() {
        aiArticleJobServiceManual.generateCrmArticle();
    }
}
