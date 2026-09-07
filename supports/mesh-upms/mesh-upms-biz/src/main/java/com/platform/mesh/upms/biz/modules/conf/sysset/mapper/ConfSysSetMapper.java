package com.platform.mesh.upms.biz.modules.conf.sysset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.upms.biz.modules.conf.sysset.domain.po.ConfSysSet;
import org.apache.ibatis.annotations.Param;

/**
 * @description 配置系统
 * @author 蝉鸣
 */
public interface ConfSysSetMapper extends BaseMapper<ConfSysSet> {

    ConfSysSet selectMcpEnabled(@Param("tenantId") Long tenantId);

    int upsertMcpSwitch(ConfSysSet confSysSet);

}
