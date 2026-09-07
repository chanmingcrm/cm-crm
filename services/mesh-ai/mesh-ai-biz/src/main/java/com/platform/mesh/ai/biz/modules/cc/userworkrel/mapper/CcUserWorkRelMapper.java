package com.platform.mesh.ai.biz.modules.cc.userworkrel.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.ai.biz.modules.cc.userworkrel.domain.bo.CcUserWorkRelBO;
import com.platform.mesh.ai.biz.modules.cc.userworkrel.domain.po.CcUserWorkRel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description CcUserWorkRel
 * @author 蝉鸣
 */
public interface CcUserWorkRelMapper extends BaseMapper<CcUserWorkRel> {

    @InterceptorIgnore(tenantLine = "true")
    List<CcUserWorkRelBO> getWorkUserList(@Param("workIds") List<Long> workIds, @Param("userType") Integer userType);
}