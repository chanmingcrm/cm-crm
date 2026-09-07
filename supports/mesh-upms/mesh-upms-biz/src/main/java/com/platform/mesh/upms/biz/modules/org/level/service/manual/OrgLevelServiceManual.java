package com.platform.mesh.upms.biz.modules.org.level.service.manual;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.enums.data.DataFlagEnum;
import com.platform.mesh.core.enums.data.DataScopeEnum;
import com.platform.mesh.redis.service.RedissonUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgLevelBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysOrgBO;
import com.platform.mesh.upms.api.pub.upms.bo.MsgUpmsBO;
import com.platform.mesh.upms.api.pub.upms.enums.UpmsActionEnum;
import com.platform.mesh.upms.biz.modules.org.level.domain.po.OrgLevel;
import com.platform.mesh.upms.biz.modules.org.level.domain.vo.OrgLevelVO;
import com.platform.mesh.upms.biz.modules.org.level.enums.LevelFlagEnum;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.po.OrgLevelPostRel;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.service.IOrgLevelPostRelService;
import com.platform.mesh.upms.biz.modules.org.member.domain.po.OrgMember;
import com.platform.mesh.upms.biz.modules.org.member.service.IOrgMemberService;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.po.OrgMemberPostRel;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.service.IOrgMemberPostRelService;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.domain.po.OrgMemberUserRel;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.service.IOrgMemberUserRelService;
import com.platform.mesh.upms.biz.modules.org.post.domain.po.OrgPost;
import com.platform.mesh.upms.biz.modules.org.post.service.IOrgPostService;
import com.platform.mesh.upms.biz.modules.org.postdatascope.domain.po.OrgPostDataScope;
import com.platform.mesh.upms.biz.modules.org.postdatascope.service.IOrgPostDataScopeService;
import com.platform.mesh.upms.biz.modules.sys.user.domain.po.SysUser;
import com.platform.mesh.utils.format.TreeUtil;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    /**
     * 功能描述:
     * 〈初始化层级〉
     * @param sysUser sysUser
     * @author 蝉鸣
     */
    public OrgLevel initTenantLevel(SysUser sysUser) {
        //初始化层级
        Long levelId = IdUtil.getSnowflake().nextId();
        OrgLevel orgLevel = new OrgLevel();
        orgLevel.setId(levelId);
        orgLevel.setRootId(levelId);
        orgLevel.setParentId(NumberConst.NUM_0.longValue());
        orgLevel.setLevelFlag(LevelFlagEnum.COMPANY.getValue());
        orgLevel.setLevelName(LevelFlagEnum.COMPANY.getDesc());
        orgLevel.setCreateUserId(sysUser.getUserId());
        orgLevel.setUpdateUserId(sysUser.getUserId());
        orgLevel.setScopeUserId(sysUser.getUserId());
        orgLevel.setScopeOrgId(levelId);
        return orgLevel;
    }

    /**
     * 功能描述:
     * 〈初始化岗位〉
     * @param orgLevel orgLevel
     * @author 蝉鸣
     */
    public OrgPost initTenantPost(OrgLevel orgLevel) {
        //岗位
        IOrgPostService postService = SpringContextHolderUtil.getBean(IOrgPostService.class);
        OrgPost orgPost = new OrgPost();
        orgPost.setPostName(LevelFlagEnum.COMPANY.getDesc());
        orgLevel.setCreateUserId(orgLevel.getScopeUserId());
        orgLevel.setUpdateUserId(orgLevel.getScopeUserId());
        orgPost.setScopeUserId(orgLevel.getScopeUserId());
        orgPost.setScopeOrgId(orgLevel.getScopeOrgId());
        postService.save(orgPost);
        return orgPost;
    }

    /**
     * 功能描述:
     * 〈初始化岗位〉
     * @param orgLevel orgLevel
     * @author 蝉鸣
     */
    public void initTenantLevelPostRel(OrgLevel orgLevel,Long postId) {
        IOrgLevelPostRelService levelPostRelService = SpringContextHolderUtil.getBean(IOrgLevelPostRelService.class);
        OrgLevelPostRel postRel = new OrgLevelPostRel();
        postRel.setLevelRootId(orgLevel.getRootId());
        postRel.setLevelId(orgLevel.getId());
        postRel.setPostId(postId);
        postRel.setCreateUserId(orgLevel.getScopeUserId());
        postRel.setUpdateUserId(orgLevel.getScopeUserId());
        postRel.setScopeUserId(orgLevel.getScopeUserId());
        postRel.setScopeOrgId(orgLevel.getScopeOrgId());
        levelPostRelService.save(postRel);
    }

    /**
     * 功能描述:
     * 〈初始化岗位权限〉
     * @param orgLevel orgLevel
     * @author 蝉鸣
     */
    public void initTenantPostScope(OrgLevel orgLevel,Long postId) {
        IOrgPostDataScopeService postDataScopeService = SpringContextHolderUtil.getBean(IOrgPostDataScopeService.class);
        OrgPostDataScope dataScope = new OrgPostDataScope();
        dataScope.setPostId(postId);
        dataScope.setDataScope(DataScopeEnum.ALL.getValue());
        dataScope.setDataFlag(DataFlagEnum.ORG.getValue());
        dataScope.setDataId(orgLevel.getId());
        dataScope.setDataName(orgLevel.getLevelName());
        dataScope.setCreateUserId(orgLevel.getScopeUserId());
        dataScope.setUpdateUserId(orgLevel.getScopeUserId());
        dataScope.setScopeUserId(orgLevel.getScopeUserId());
        dataScope.setScopeOrgId(orgLevel.getScopeOrgId());
        postDataScopeService.save(dataScope);
    }

    /**
     * 功能描述:
     * 〈初始化成员〉
     * @param orgLevel orgLevel
     * @author 蝉鸣
     */
    public OrgMember initTenantMember(SysUser sysUser, OrgLevel orgLevel) {
        //岗位
        IOrgMemberService memberService = SpringContextHolderUtil.getBean(IOrgMemberService.class);
        OrgMember orgMember = new OrgMember();
        orgMember.setMemberName(sysUser.getPhone());
        orgMember.setCreateUserId(sysUser.getUserId());
        orgMember.setUpdateUserId(sysUser.getUserId());
        orgMember.setScopeUserId(sysUser.getUserId());
        orgMember.setScopeOrgId(orgLevel.getScopeOrgId());
        memberService.save(orgMember);
        return orgMember;
    }


    /**
     * 功能描述:
     * 〈初始化成员与人员关系〉
     * @param orgMember orgMember
     * @param userId userId
     * @author 蝉鸣
     */
    public void initTenantMemberUserRel(OrgMember orgMember,Long userId) {
        IOrgMemberUserRelService memberUserRelService = SpringContextHolderUtil.getBean(IOrgMemberUserRelService.class);
        OrgMemberUserRel memberUserRel = new OrgMemberUserRel();
        memberUserRel.setMemberId(orgMember.getId());
        memberUserRel.setUserId(userId);
        memberUserRel.setCreateUserId(userId);
        memberUserRel.setUpdateUserId(userId);
        memberUserRel.setScopeUserId(userId);
        memberUserRel.setScopeOrgId(orgMember.getScopeOrgId());
        memberUserRelService.save(memberUserRel);
    }

    /**
     * 功能描述:
     * 〈初始化成员与岗位关系〉
     * @param orgLevel orgLevel
     * @param orgMember orgMember
     * @param postId postId
     * @author 蝉鸣
     */
    public void initTenantMemberPostRel(OrgLevel orgLevel,OrgMember orgMember,Long postId) {
        IOrgMemberPostRelService memberPostRelService = SpringContextHolderUtil.getBean(IOrgMemberPostRelService.class);
        OrgMemberPostRel memberPostRel = new OrgMemberPostRel();
        memberPostRel.setLevelRootId(orgLevel.getRootId());
        memberPostRel.setLevelId(orgLevel.getId());
        memberPostRel.setPostId(postId);
        memberPostRel.setMemberId(orgMember.getId());
        memberPostRel.setCreateUserId(orgMember.getScopeUserId());
        memberPostRel.setUpdateUserId(orgMember.getScopeUserId());
        memberPostRel.setScopeUserId(orgMember.getScopeUserId());
        memberPostRel.setScopeOrgId(orgMember.getScopeOrgId());
        memberPostRelService.save(memberPostRel);
    }

    /**
     * 功能描述:
     * 〈批量转换组织数据〉
     * @param levelId levelId
     * @param levelName levelName
     * @author 蝉鸣
     */
    public void syncOrgName(Long levelId, String levelName) {
        //获取成员信息
        OrgLevelBO levelBO = new OrgLevelBO();
        levelBO.setId(levelId);
        levelBO.setLevelName(levelName);
        //通过Redisson异步发送
        MsgUpmsBO msgUpmsBO = new MsgUpmsBO();
        Map<String, Object> map = new HashMap<>();
        map.put(StrConst.VALUE,levelBO);
        msgUpmsBO.setExtendJson(map);
        msgUpmsBO.setActionType(UpmsActionEnum.SYNC_ORG_NAME.getValue());
        //发送订阅信息
        RedissonUtil.publish(StrConst.ALL,msgUpmsBO);
    }
}

