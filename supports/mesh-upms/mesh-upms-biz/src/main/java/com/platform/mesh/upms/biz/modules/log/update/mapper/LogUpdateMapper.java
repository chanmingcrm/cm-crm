package com.platform.mesh.upms.biz.modules.log.update.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.upms.biz.modules.log.update.domain.po.LogUpdate;
import org.apache.ibatis.annotations.Param;

/**
 * @description
 * @author 蝉鸣
 */
public interface LogUpdateMapper extends BaseMapper<LogUpdate> {

    LogUpdate getLastOne(@Param("logFLag") Integer logFLag);
}

