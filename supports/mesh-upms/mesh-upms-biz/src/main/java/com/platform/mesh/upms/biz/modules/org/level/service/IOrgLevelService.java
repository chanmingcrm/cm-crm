package com.platform.mesh.upms.biz.modules.org.level.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysOrgBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysOrgInfoBO;
import com.platform.mesh.upms.biz.modules.org.level.domain.dto.OrgLevelDTO;
import com.platform.mesh.upms.biz.modules.org.level.domain.dto.OrgLevelPageDTO;
import com.platform.mesh.upms.biz.modules.org.level.domain.po.OrgLevel;
import com.platform.mesh.upms.biz.modules.org.level.domain.vo.OrgLevelVO;
import com.platform.mesh.upms.biz.modules.sys.user.domain.po.SysUser;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 组织信息
 * @author 蝉鸣
 */
public interface IOrgLevelService extends IService<OrgLevel> {


    /***
     * 功能描述:
     * 〈组织列表分页查询〉
     * @param orgLevelPageDTO orgLevelPageDTO
     * @return 正常返回:{@link MPage<OrgLevel>}
     * @author 蝉鸣
     * @since 2024/8/29 15:35
     */
    MPage<OrgLevel> selectPage(OrgLevelPageDTO orgLevelPageDTO);

    /**
     * 功能描述:
     * 〈获取组织树结构〉
     * @param levelId levelId
     * @return 正常返回:{@link SysOrgInfoBO}
     * @author 蝉鸣
     */
    SysOrgInfoBO getOrgInfoByLevelId(Long levelId);

    /**
     * 功能描述:
     * 〈通过账户ID查询用户角色信息〉
     * @param accountId accountId
     * @return 正常返回:{@link List<OrgLevel>}
     * @author 蝉鸣
     */
    List<SysOrgBO> getOrgInfoByAccountId(Long accountId);

    /**
     * 功能描述:
     * 〈获取当前组织信息〉
     * @param levelId levelId
     * @return 正常返回:{@link OrgLevelVO}
     * @author 蝉鸣
     */
    OrgLevelVO getLevelInfoById(Long levelId);

    /**
     * 功能描述:
     * 〈获取组织树结构〉
     * @param levelId levelId
     * @return 正常返回:{@link List<OrgLevelVO>}
     * @author 蝉鸣
     */
    List<OrgLevelVO> getLevelTree(Long levelId);

    /**
     * 功能描述:
     * 〈新增层级〉
     * @param levelDTO levelDTO
     * @return 正常返回:{@link OrgLevelVO}
     * @author 蝉鸣
     */
    OrgLevelVO addLevel(OrgLevelDTO levelDTO);

    /**
     * 功能描述:
     * 〈修改层级〉
     * @param menuDTO menuDTO
     * @return 正常返回:{@link OrgLevelVO}
     * @author 蝉鸣
     */
    OrgLevelVO editLevel(OrgLevelDTO menuDTO);

    /**
     * 功能描述:
     * 〈获取所有子级包含自己〉
     * @param levelIds levelIds
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    List<Long> getLevelChildByIds(List<Long> levelIds);

    /**
     * 功能描述:
     * 〈删除层级〉
     * @param levelIds levelIds
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteLevel(List<Long> levelIds);
}

