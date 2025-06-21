package com.platform.mesh.upms.biz.modules.org.memberuserrel.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysUserBO;
import com.platform.mesh.upms.biz.modules.org.level.service.IOrgLevelService;
import com.platform.mesh.upms.biz.modules.org.member.domain.po.OrgMember;
import com.platform.mesh.upms.biz.modules.org.member.service.IOrgMemberService;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.po.OrgMemberPostRel;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.service.IOrgMemberPostRelService;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.domain.dto.OrgMemberAddDTO;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.domain.po.OrgMemberUserRel;
import com.platform.mesh.upms.biz.modules.sys.account.domain.po.SysAccount;
import com.platform.mesh.upms.biz.modules.sys.account.service.ISysAccountService;
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
public class OrgMemberUserRelServiceManual {

    @Autowired
    private IOrgLevelService orgLevelService;

    @Autowired
    private IOrgMemberPostRelService orgMemberPostRelService;

    @Autowired
    private ISysAccountService sysAccountService;

    /**
     * 功能描述:
     * 〈保存成员信息〉
     * @param addDTO addDTO
     * @param userMemberMap userMemberMap
     * @author 蝉鸣
     */
    public List<OrgMemberUserRel> initOrgMember(OrgMemberAddDTO addDTO, Map<Long, Long> userMemberMap) {
        List<OrgMemberUserRel> orgMemberUserRelList = CollUtil.newArrayList();
        if(CollUtil.isEmpty(addDTO.getUserIds())){
            return orgMemberUserRelList;
        }

        List<OrgMemberPostRel> orgMemberPostRelList = CollUtil.newArrayList();
        List<Long> memberIds = CollUtil.newArrayList(userMemberMap.values());
        addDTO.getUserIds().forEach(userId -> {
            if(userMemberMap.containsKey(userId)){
                Long memberId = userMemberMap.get(userId);
                //封装成员和人员关系
                OrgMemberUserRel orgMemberUserRel = new OrgMemberUserRel();
                orgMemberUserRel.setUserId(userId);
                orgMemberUserRel.setMemberId(memberId);
                orgMemberUserRelList.add(orgMemberUserRel);
                //封装成员和岗位关系
                OrgMemberPostRel orgMemberPostRel = new OrgMemberPostRel();
                orgMemberPostRel.setLevelRootId(addDTO.getLevelRootId());
                orgMemberPostRel.setLevelId(addDTO.getLevelId());
                orgMemberPostRel.setPostId(addDTO.getPostId());
                orgMemberPostRel.setMemberId(memberId);
                orgMemberPostRelList.add(orgMemberPostRel);
            }
        });
        //批量添加成员与岗位关系
        orgMemberPostRelService.lambdaUpdate().eq(OrgMemberPostRel::getPostId,addDTO.getPostId()).in(OrgMemberPostRel::getMemberId,memberIds).remove();
        orgMemberPostRelService.saveBatch(orgMemberPostRelList);
        sysAccountService.lambdaUpdate()
                .set(SysAccount::getScopeRootId, addDTO.getLevelRootId())
                .set(SysAccount::getScopeOrgId, addDTO.getLevelId())
                .isNull(SysAccount::getScopeOrgId)
                .in(SysAccount::getUserId, addDTO.getUserIds()).update();
        //返回成员
        return orgMemberUserRelList;

    }

    /**
     * 功能描述:
     * 〈初始化成员〉
     * @param userIds userIds
     * @author 蝉鸣
     */
    public Map<Long,Long> getUserMemberMap(List<Long> userIds,List<OrgMemberUserRel> existList) {
        Map<Long,Long> userMemberMap = new HashMap<>();
        if(CollUtil.isEmpty(userIds)){
            return userMemberMap;
        }
        if(CollUtil.isNotEmpty(existList)){
            List<Long> exists = existList.stream().map(OrgMemberUserRel::getUserId).toList();
            userIds = userIds.stream().filter(userId->!exists.contains(userId)).toList();
            Map<Long, Long> existsMap = existList.stream().collect(Collectors.toMap(OrgMemberUserRel::getUserId, OrgMemberUserRel::getMemberId));
            userMemberMap.putAll(existsMap);
        }
        List<OrgMember> orgMemberList = CollUtil.newArrayList();
        userIds.forEach(userId -> {
            SysUserBO sysUserBO = UserCacheUtil.getSysUserInfoCache(userId);
            //封装成员
            Long memberId = IdUtil.getSnowflake().nextId();
            OrgMember orgMember = new OrgMember();
            orgMember.setId(memberId);
            orgMember.setMemberName(sysUserBO.getNickName());
            orgMemberList.add(orgMember);
            userMemberMap.put(userId,memberId);
        });
        IOrgMemberService orgMemberService = SpringContextHolderUtil.getBean(IOrgMemberService.class);
        orgMemberService.saveBatch(orgMemberList);
        return userMemberMap;
    }

    /**
     * 功能描述:
     * 〈获取层级子Id〉
     * @param levelIds levelIds
     * @author 蝉鸣
     */
    public List<Long> getChileLevelIds(List<Long> levelIds) {
        return orgLevelService.getLevelChildByIds(levelIds);
    }
}

