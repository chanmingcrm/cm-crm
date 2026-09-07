package com.platform.mesh.ai.biz.modules.ai.job.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.mesh.ai.biz.modules.ai.job.domain.po.AiJob;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Param;

/**
 * @description AI任务配置
 * @author 蝉鸣
 */
public interface AiJobMapper extends BaseMapper<AiJob> {

    /**
     * 功能描述:
     * 【分页获取AI任务配置】
     * @param page page
     * @return 正常返回:{@link IPage<AiJob>}
     * @author 蝉鸣
     */
    @InterceptorIgnore(tenantLine = "true")
    MPage<AiJob> getAllJob(IPage<AiJob> page,@Param("jobFlag") Integer jobFlag);
}
