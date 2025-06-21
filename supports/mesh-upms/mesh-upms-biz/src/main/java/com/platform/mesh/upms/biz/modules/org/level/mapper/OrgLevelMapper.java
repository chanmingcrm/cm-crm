package com.platform.mesh.upms.biz.modules.org.level.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.upms.biz.modules.org.level.domain.po.OrgLevel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 组织层级
 * @author 蝉鸣
 */
public interface OrgLevelMapper extends BaseMapper<OrgLevel> {

    /**
     * 功能描述:
     * 〈通过用户ID查询用户角色信息〉
     * @param userId userId
     * @return 正常返回:{@link List<OrgLevel>}
     * @author 蝉鸣
     */
    List<OrgLevel> getOrgInfoByUserId(@Param("userId") Long userId);
}

