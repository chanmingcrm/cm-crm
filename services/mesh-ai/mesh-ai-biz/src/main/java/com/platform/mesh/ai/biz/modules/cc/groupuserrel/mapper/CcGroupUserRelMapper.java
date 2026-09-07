package com.platform.mesh.ai.biz.modules.cc.groupuserrel.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.ai.biz.modules.cc.groupuserrel.domain.dto.CcGroupUserRelPageDTO;
import com.platform.mesh.ai.biz.modules.cc.groupuserrel.domain.po.CcGroupUserRel;
import com.platform.mesh.core.application.domain.bo.SimpBO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description CcGroupUserRel
 * @author 蝉鸣
 */
public interface CcGroupUserRelMapper extends BaseMapper<CcGroupUserRel> {

    @InterceptorIgnore(tenantLine = "true")
    MPage<CcGroupUserRel> selectMPage(MPage<CcGroupUserRel> mPage,@Param("pageDTO") CcGroupUserRelPageDTO pageDTO);

    @InterceptorIgnore(tenantLine = "true")
    CcGroupUserRel getByGroupHashAndType(@Param("groupHash") String groupHash, @Param("userType") Integer userType);

    @InterceptorIgnore(tenantLine = "true")
    List<SimpBO> getUserUnReadNum(@Param("userHash") String userHash);
}