package com.platform.mesh.ai.biz.modules.ai.job.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.ai.biz.modules.ai.job.domain.po.AiJob;
import com.platform.mesh.ai.biz.modules.ai.job.mapper.AiJobMapper;
import com.platform.mesh.ai.biz.modules.ai.job.service.IAiJobService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.springframework.stereotype.Service;

/**
 * @description AI任务
 * @author 蝉鸣
 */
@Service
public class AiJobServiceImpl extends ServiceImpl<AiJobMapper, AiJob> implements IAiJobService {

    /**
     * 功能描述:
     * 【查询所有的Ai任务】
     * @author 蝉鸣
     */
    @Override
    public MPage<AiJob> getAllJob(MPage<AiJob> mPage,Integer jobFlag) {
        return this.getBaseMapper().getAllJob(mPage,jobFlag);
    }
}
