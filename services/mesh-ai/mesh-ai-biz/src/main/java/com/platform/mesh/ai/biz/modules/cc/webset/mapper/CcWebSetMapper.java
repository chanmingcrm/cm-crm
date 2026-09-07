package com.platform.mesh.ai.biz.modules.cc.webset.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.ai.biz.modules.cc.webset.domain.po.CcWebSet;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @description CcWebSet
 * @author 蝉鸣
 */
public interface CcWebSetMapper extends BaseMapper<CcWebSet> {

    /** 访客接口按公开配置ID定位租户，不能依赖登录态租户。 */
    @InterceptorIgnore(tenantLine = "true")
    @Select("SELECT * FROM cc_web_set WHERE id = #{webSetId} LIMIT 1")
    CcWebSet selectPublicById(@Param("webSetId") Long webSetId);
}
