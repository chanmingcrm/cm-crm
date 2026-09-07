package com.platform.mesh.upms.biz.modules.org.memberuserrel.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysUserBO;
import com.platform.mesh.upms.biz.modules.org.level.service.IOrgLevelService;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.po.OrgLevelPostRel;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.service.IOrgLevelPostRelService;
import com.platform.mesh.upms.biz.modules.org.member.domain.po.OrgMember;
import com.platform.mesh.upms.biz.modules.org.member.service.IOrgMemberService;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.po.OrgMemberPostRel;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.service.IOrgMemberPostRelService;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.domain.dto.OrgMemberAddDTO;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.domain.po.OrgMemberUserRel;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.exception.MemberUserRelExceptionEnum;
import com.platform.mesh.upms.biz.modules.org.post.exception.PostExceptionEnum;
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
    private IOrgLevelPostRelService orgLevelPostRelService;

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
        if(CollUtil.isEmpty(addDTO.getUserDTOS())){
            return orgMemberUserRelList;
        }
        OrgLevelPostRel orgLevelPostRel = orgLevelPostRelService.lambdaQuery()
                .eq(OrgLevelPostRel::getLevelId,addDTO.getLevelId())
                .eq(OrgLevelPostRel::getPostId,addDTO.getPostId()).one();
        if(ObjectUtil.isEmpty(orgLevelPostRel)){
            throw PostExceptionEnum.ADD_NO_INVALID.getBaseException();
        }
        //如果是决策岗位则只能是1人
        if(YesOrNoEnum.YES.getValue().equals(orgLevelPostRel.getLeadFlag())){
            if(addDTO.getUserDTOS().size() > NumberConst.NUM_1){
                throw MemberUserRelExceptionEnum.ADD_NUM_LEAD.getBaseException();
            }
            //查询是否已经存在决策人
            Boolean existed = this.existLead(addDTO.getPostId());
            if(existed){
                throw MemberUserRelExceptionEnum.ADD_EXIST_LEAD.getBaseException();
            }
        }
        List<OrgMemberPostRel> orgMemberPostRelList = CollUtil.newArrayList();
        List<Long> memberIds = CollUtil.newArrayList(userMemberMap.values());
        List<Long> userIds = CollUtil.newArrayList();
        addDTO.getUserDTOS().forEach(userDTO -> {
            userIds.add(userDTO.getUserId());
            if(userMemberMap.containsKey(userDTO.getUserId())){
                Long memberId = userMemberMap.get(userDTO.getUserId());
                //封装成员和人员关系
                OrgMemberUserRel orgMemberUserRel = new OrgMemberUserRel();
                orgMemberUserRel.setUserId(userDTO.getUserId());
                orgMemberUserRel.setMemberId(memberId);
                orgMemberUserRelList.add(orgMemberUserRel);
                //封装成员和岗位关系
                OrgMemberPostRel orgMemberPostRel = new OrgMemberPostRel();
                orgMemberPostRel.setLevelRootId(addDTO.getLevelRootId());
                orgMemberPostRel.setLevelId(addDTO.getLevelId());
                orgMemberPostRel.setPostId(addDTO.getPostId());
                orgMemberPostRel.setMemberId(memberId);
                orgMemberPostRel.setLeadFlag(userDTO.getLeadFlag());
                orgMemberPostRel.setDefaultFlag(userDTO.getDefaultPostFlag());
                if(YesOrNoEnum.YES.getValue().equals(userDTO.getDefaultPostFlag())){
                    //如果是默认岗位则，赋值其他所在岗位为非默认状态，相同公司组织下只能存在一个默认岗位
                    orgMemberPostRelService.lambdaUpdate()
                            .set(OrgMemberPostRel::getDefaultFlag,YesOrNoEnum.NO.getValue())
                            .eq(OrgMemberPostRel::getLevelRootId,addDTO.getLevelRootId()).eq(OrgMemberPostRel::getMemberId,memberId).update();

                }
                orgMemberPostRelList.add(orgMemberPostRel);
            }
        });
        //批量添加成员与岗位关系
        orgMemberPostRelService.lambdaUpdate().eq(OrgMemberPostRel::getPostId,addDTO.getPostId()).in(OrgMemberPostRel::getMemberId,memberIds).remove();
        orgMemberPostRelService.saveBatch(orgMemberPostRelList);
        //查询当前用户账户失效的组织
        List<Long> inValid = sysAccountService.getInvalidOrgAccountByUserIds(userIds);
        if(CollUtil.isEmpty(inValid)){
            return orgMemberUserRelList;
        }
        //初始化账户默认组织
        sysAccountService.lambdaUpdate()
                .set(SysAccount::getScopeRootId, addDTO.getLevelRootId())
                .set(SysAccount::getScopeOrgId, addDTO.getLevelId())
                .isNull(SysAccount::getScopeOrgId)
                .in(SysAccount::getUserId, userIds).update();
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

    /**
     * 功能描述:
     * 〈是否已经存在决策人〉
     * @param postId postId
     * @author 蝉鸣
     */
    public Boolean existLead(Long postId) {
       return orgMemberPostRelService.lambdaQuery()
               .eq(OrgMemberPostRel::getPostId,postId)
               .eq(OrgMemberPostRel::getLeadFlag,YesOrNoEnum.YES.getValue())
               .exists();

    }
}

