package com.platform.mesh.ai.biz.modules.cc.setwork.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.ai.biz.modules.cc.setwork.domain.po.CcSetWork;
import org.apache.ibatis.annotations.Param;

import java.time.LocalTime;
import java.util.List;

/**
 * @description CcSetWork
 * @author 蝉鸣
 */
public interface CcSetWorkMapper extends BaseMapper<CcSetWork> {

    @InterceptorIgnore(tenantLine = "true")
    List<CcSetWork> getCurrentSetWork(@Param("localTime") LocalTime localTime);
}