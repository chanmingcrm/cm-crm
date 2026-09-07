package com.platform.mesh.upms.biz.modules.org.memberuserrel.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberRelBO;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.domain.dto.OrgMemberAddDTO;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.domain.dto.OrgMemberUserDTO;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.domain.po.OrgMemberUserRel;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.mapper.OrgMemberUserRelMapper;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.service.IOrgMemberUserRelService;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.service.manual.OrgMemberUserRelServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 组织信息
 * @author 蝉鸣
 */
@Service()
public class OrgMemberUserRelServiceImpl extends ServiceImpl<OrgMemberUserRelMapper, OrgMemberUserRel> implements IOrgMemberUserRelService {

    @Autowired
    private OrgMemberUserRelServiceManual orgMemberUserRelServiceManual;

    /**
     * 功能描述:
     * 〈保存成员关系信息〉
     * @param addDTO addDTO
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addMemberUser(OrgMemberAddDTO addDTO) {
        //初始化成员信息
        List<Long> userIds = addDTO.getUserDTOS().stream().map(OrgMemberUserDTO::getUserId).toList();
        if(CollUtil.isEmpty(userIds)){
            return Boolean.FALSE;
        }
        //查询已经存在的人员成员关系
        List<OrgMemberUserRel> existList = this.lambdaQuery().in(OrgMemberUserRel::getUserId, userIds).list();
        Map<Long, Long> userMemberMap = orgMemberUserRelServiceManual.getUserMemberMap(userIds, existList);
        List<Long> memberIds = CollUtil.newArrayList(userMemberMap.values());
        List<OrgMemberUserRel> orgMemberUserRelList = orgMemberUserRelServiceManual.initOrgMember(addDTO, userMemberMap);
        //保存关系
        this.lambdaUpdate().in(OrgMemberUserRel::getMemberId,memberIds).remove();
        saveBatch(orgMemberUserRelList);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈删除成员-用户关系〉
     * @param memberId memberId
     * @author 蝉鸣
     */
    @Override
    public void deleteMemberUserByMemberId(Long memberId) {
        this.lambdaUpdate().eq(OrgMemberUserRel::getMemberId,memberId).remove();

    }

    /**
     * 功能描述:
     * 〈获取成员默认关联信息〉
     * @param userId userId
     * @return 正常返回:{@link OrgMemberRelBO}
     * @author 蝉鸣
     */
    @Override
    public OrgMemberRelBO getOrgMemberUserDefaultRelByUserId(Long userId) {
        return this.getBaseMapper().getDefaultRelByUserId(userId);
    }

    /**
     * 功能描述:
     * 〈获取成员默认关联信息〉
     * @param memberId memberId
     * @return 正常返回:{@link OrgMemberRelBO}
     * @author 蝉鸣
     */
    @Override
    public OrgMemberRelBO getOrgMemberUserDefaultRelByMemberId(Long memberId) {
        return this.getBaseMapper().getDefaultRelByMemberId(memberId);
    }

    /**
     * 功能描述:
     * 〈获取成员默认关联信息〉
     * @param memberIds memberIds
     * @return 正常返回:{@link OrgMemberRelBO}
     * @author 蝉鸣
     */
    @Override
    public List<OrgMemberRelBO> getOrgMemberUserDefaultRelByIds(List<Long> memberIds) {
        return this.getBaseMapper().getDefaultRelByMemberIds(memberIds);
    }

    /**
     * 功能描述:
     * 〈获取当前账户下的下属部门〉
     * @param accountId accountId
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    @Override
    public List<Long> getOrgChildLevelByAccountId(Long accountId) {
        if(ObjectUtil.isEmpty(accountId)){
            return CollUtil.newArrayList();
        }
        List<Long> levelIds = this.getBaseMapper().getOrgChildLevelRelByAccountId(accountId, YesOrNoEnum.YES.getValue());
        //当前部门下的所有下级部门
        List<Long> orgIds =orgMemberUserRelServiceManual.getChileLevelIds(levelIds);
        return orgIds.stream().distinct().toList();
    }

    /**
     * 功能描述:
     * 〈获取当前部门下所有的下属人员〉
     * @param levelIds levelIds
     * @return 正常返回:{@link List<OrgMemberRelBO>}
     * @author 蝉鸣
     */
    @Override
    public List<OrgMemberRelBO> getOrgChildUserRelByLevelIds(List<Long> levelIds) {
        if(CollUtil.isEmpty(levelIds)){
            return CollUtil.newArrayList();
        }
        return this.getBaseMapper().getOrgChildUserRelByLevelIds(levelIds.stream().distinct().toList());
    }
}

