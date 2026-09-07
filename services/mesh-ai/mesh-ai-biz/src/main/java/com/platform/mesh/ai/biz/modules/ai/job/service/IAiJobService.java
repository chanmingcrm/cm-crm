package com.platform.mesh.ai.biz.modules.ai.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.ai.biz.modules.ai.job.domain.po.AiJob;
import com.platform.mesh.mybatis.plus.extention.MPage;

/**
 * @description AI任务服务
 * @author 蝉鸣
 */
public interface IAiJobService extends IService<AiJob> {

    /**
     * 功能描述:
     * 【查询所有的Ai任务】
     * @author 蝉鸣
     */
    MPage<AiJob> getAllJob(MPage<AiJob> mPage,Integer jobFlag);

}
