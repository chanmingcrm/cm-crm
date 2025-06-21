package com.platform.mesh.upms.biz.modules.org.level.service.manual;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysOrgBO;
import com.platform.mesh.upms.biz.modules.org.level.domain.po.OrgLevel;
import com.platform.mesh.upms.biz.modules.org.level.domain.vo.OrgLevelVO;
import com.platform.mesh.upms.biz.modules.org.level.enums.LevelFlagEnum;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.po.OrgLevelPostRel;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.service.IOrgLevelPostRelService;
import com.platform.mesh.upms.biz.modules.org.member.service.IOrgMemberService;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.po.OrgMemberPostRel;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.service.IOrgMemberPostRelService;
import com.platform.mesh.utils.format.TreeUtil;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service()
public class OrgLevelServiceManual {

    @Autowired
    private IOrgMemberService orgMemberService;

    /**
     * 功能描述:
     * 〈获取组织详情〉
     * @param orgLevel orgLevel
     * @return 正常返回:{@link OrgLevelVO}
     * @author 蝉鸣
     */
    public OrgLevelVO getLevelInfoById(OrgLevel orgLevel) {
        OrgLevelVO orgLevelVO = new OrgLevelVO();
        if(ObjectUtil.isEmpty(orgLevelVO)){
            return orgLevelVO;
        }
        //转换VO
        BeanUtil.copyProperties(orgLevel, orgLevelVO);
        return orgLevelVO;
    }

    /**
     * 功能描述:
     * 〈获取树形结构数据〉
     * @param orgLevels orgLevels
     * @return 正常返回:{@link List<OrgLevelVO>}
     * @author 蝉鸣
     */
    public List<OrgLevelVO> getLevelTree(List<OrgLevel> orgLevels) {
        if(CollUtil.isEmpty(orgLevels)){
            return CollUtil.newArrayList();
        }
        //转换VO
        List<OrgLevelVO> orgLevelVos = orgLevels.stream().map(this::getLevelInfoById).collect(Collectors.toList());
        //封装树结构
        return TreeUtil.packageTree(0L,orgLevelVos);
    }

    /**
     * 功能描述:
     * 〈通过用户ID查询用户组织信息〉
     * @param userId userId
     * @return 正常返回:{@link List<OrgLevel>}
     * @author 蝉鸣
     */
    public List<SysOrgBO> getOrgInfoByUserId(Long userId) {
        return orgMemberService.getMemberInfoByUserId(userId);
    }

    /**
     * 功能描述:
     * 〈获取最近顶层ID〉
     * @param parentList parentList
     * @param orgLevel orgLevel
     * @return 正常返回:{@link OrgLevel}
     * @author 蝉鸣
     */
    public OrgLevel getLastRootLevel(List<OrgLevel> parentList, OrgLevel orgLevel) {
        if(CollUtil.isEmpty(parentList) || ObjectUtil.isEmpty(orgLevel)){
            return orgLevel;
        }
        if(orgLevel.getParentId().equals(NumberConst.NUM_0.longValue())){
            return orgLevel;
        }
        List<OrgLevel> list = parentList.stream().filter(level -> orgLevel.getParentId().equals(level.getId())).toList();
        if(CollUtil.isEmpty(list)){
            return orgLevel;
        }
        OrgLevel parent = CollUtil.getFirst(list);
        if(LevelFlagEnum.COMPANY.getValue().equals(parent.getLevelFlag())){
            return parent;
        }else{
            //递归查询
            return getLastRootLevel(parentList, parent);
        }
    }

    /**
     * 功能描述:
     * 〈删除层级关系〉
     * @param levelIds levelIds
     * @author 蝉鸣
     */
    public void deleteLevelRel(List<Long> levelIds) {
        //删除岗位
        IOrgLevelPostRelService levelPostRelService = SpringContextHolderUtil.getBean(IOrgLevelPostRelService.class);
        levelPostRelService.lambdaUpdate().in(OrgLevelPostRel::getLevelId,levelIds).remove();
        //删除成员岗位
        IOrgMemberPostRelService memberPostRelService = SpringContextHolderUtil.getBean(IOrgMemberPostRelService.class);
        memberPostRelService.lambdaUpdate().in(OrgMemberPostRel::getLevelId,levelIds).remove();
    }

}

