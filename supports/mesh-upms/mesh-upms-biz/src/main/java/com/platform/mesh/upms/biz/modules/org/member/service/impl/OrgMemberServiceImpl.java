package com.platform.mesh.upms.biz.modules.org.member.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.core.enums.data.DataFlagEnum;
import com.platform.mesh.core.enums.data.DataScopeEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.redis.service.RedissonUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberRelBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberTransBO;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysOrgBO;
import com.platform.mesh.upms.api.pub.upms.bo.MsgUpmsBO;
import com.platform.mesh.upms.api.pub.upms.enums.UpmsActionEnum;
import com.platform.mesh.upms.biz.modules.org.level.domain.po.OrgLevel;
import com.platform.mesh.upms.biz.modules.org.level.enums.LevelFlagEnum;
import com.platform.mesh.upms.biz.modules.org.member.domain.dto.OrgMemberDTO;
import com.platform.mesh.upms.biz.modules.org.member.domain.dto.OrgMemberDelDTO;
import com.platform.mesh.upms.biz.modules.org.member.domain.dto.OrgMemberPageDTO;
import com.platform.mesh.upms.biz.modules.org.member.domain.dto.OrgMemberTransDTO;
import com.platform.mesh.upms.biz.modules.org.member.domain.po.OrgMember;
import com.platform.mesh.upms.biz.modules.org.member.domain.vo.OrgMemberInfoVO;
import com.platform.mesh.upms.biz.modules.org.member.domain.vo.OrgMemberVO;
import com.platform.mesh.upms.biz.modules.org.member.exception.MemberExceptionEnum;
import com.platform.mesh.upms.biz.modules.org.member.mapper.OrgMemberMapper;
import com.platform.mesh.upms.biz.modules.org.member.service.IOrgMemberService;
import com.platform.mesh.upms.biz.modules.org.member.service.manual.OrgMemberServiceManual;
import com.platform.mesh.upms.biz.modules.sys.user.domain.vo.SysMemberVO;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 组织信息
 * @author 蝉鸣
 */
@Service()
public class OrgMemberServiceImpl extends ServiceImpl<OrgMemberMapper, OrgMember> implements IOrgMemberService {

    @Autowired
    private OrgMemberServiceManual orgMemberServiceManual;


    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link OrgMemberServiceManual}
     * @author 蝉鸣
     */
    @Override
    public OrgMemberServiceManual getServiceManual() {
        return this.orgMemberServiceManual;
    }

    /***
     * 功能描述:
     * 〈成员列表查询〉
     * @param orgMemberPageDTO orgMemberPageDTO
     * @return 正常返回:{@link MPage<OrgMemberVO>}
     * @author 蝉鸣
     * @since 2024/9/6 19:46
     */
    @Override
    public MPage<OrgMemberVO> selectPage(OrgMemberPageDTO orgMemberPageDTO) {
        MPage<OrgMember> mPage = MPageUtil.pageEntityToMPage(orgMemberPageDTO,OrgMember.class);
        List<Long> levelIds;
        if(CollUtil.isEmpty(orgMemberPageDTO.getLevelIds())){
            //权限查询为空
            DataScopeEnum enumByValue = BaseEnum.getEnumByValue(DataScopeEnum.class, orgMemberPageDTO.getDataScope(), DataScopeEnum.ALL);
            List<SysOrgBO> accountOrgCache = UserCacheUtil.getAccountOrgCache(UserCacheUtil.getAccountId());
            switch (enumByValue){
                case ALL -> levelIds = CollUtil.newArrayList();
                case SUB -> {
                    levelIds = CollUtil.newArrayList();
                    List<Long> levels = accountOrgCache.stream().map(SysOrgBO::getLevelId).distinct().filter(ObjectUtil::isNotEmpty).toList();
                    List<Long> childIds = accountOrgCache.stream().map(SysOrgBO::getLevelIds).distinct().flatMap(Collection::stream).filter(ObjectUtil::isNotEmpty).toList();
                    levelIds.addAll(levels);
                    levelIds.addAll(childIds);
                }
                case LEVEL -> levelIds = accountOrgCache.stream().map(SysOrgBO::getLevelId).distinct().filter(ObjectUtil::isNotEmpty).toList();
                case SELF -> {
                    levelIds = CollUtil.newArrayList();
                    orgMemberPageDTO.setUserIds(CollUtil.newArrayList(UserCacheUtil.getUserId()));
                }
                default -> {
                    levelIds = CollUtil.newArrayList();
                    if(CollUtil.isNotEmpty(orgMemberPageDTO.getLevelIds())){
                        levelIds.addAll(orgMemberPageDTO.getLevelIds());
                    }
                    if(ObjectUtil.isNotEmpty(orgMemberPageDTO.getDataFlag()) && DataFlagEnum.ORG.getValue().equals(orgMemberPageDTO.getDataFlag())){
                        levelIds.addAll(orgMemberPageDTO.getDataIds());
                        if(CollUtil.isNotEmpty(levelIds)) {
                            //获取包含所有的子级
                            levelIds = orgMemberServiceManual.getLevelChildByIds(levelIds);
                        }
                    }
                    if(ObjectUtil.isNotEmpty(orgMemberPageDTO.getDataFlag()) && DataFlagEnum.USER.getValue().equals(orgMemberPageDTO.getDataFlag())){
                        List<OrgMemberBO> memberByIds = getOrgMemberByIds(orgMemberPageDTO.getDataIds());
                        List<Long> userIds = memberByIds.stream().map(OrgMemberBO::getUserId).toList();
                        orgMemberPageDTO.setUserIds(userIds);
                    }
                }
            }
        }else{
            levelIds = orgMemberPageDTO.getLevelIds().stream().filter(ObjectUtil::isNotEmpty).toList();
            //如果是只有一个参数，并且是公司，则附带查询所有
            if(levelIds.size() == NumberConst.NUM_1){
                Long levelId = CollUtil.getFirst(levelIds);
                OrgLevel level = orgMemberServiceManual.getLevelById(levelId);
                if(LevelFlagEnum.COMPANY.getValue().equals(level.getLevelFlag())){
                    levelIds = orgMemberServiceManual.getLevelChildByIds(levelIds);
                }
            }
        }
        orgMemberPageDTO.setLevelIds(levelIds);
        return this.getBaseMapper().selectMemberPage(mPage,orgMemberPageDTO);
    }

    /**
     * 功能描述:
     * 〈获取组织详情〉
     * @param memberId memberId
     * @return 正常返回:{@link OrgMemberVO}
     * @author 蝉鸣
     */
    @Override
    public OrgMemberInfoVO getMemberInfoById(Long memberId) {
        OrgMember orgMember = this.getById(memberId);
        return orgMemberServiceManual.getMemberInfoById(orgMember);
    }

    /**
     * 功能描述:
     * 〈添加成员〉
     * @param memberDTO memberDTO
     * @return 正常返回:{@link OrgMemberVO}
     * @author 蝉鸣
     */
    @Override
    public OrgMemberVO addMember(OrgMemberDTO memberDTO) {
        OrgMember orgMember = BeanUtil.copyProperties(memberDTO, OrgMember.class);
        this.save(orgMember);
        return BeanUtil.copyProperties(orgMember, OrgMemberVO.class);
    }

    /**
     * 功能描述:
     * 〈修改成员〉
     * @param memberDTO memberDTO
     * @return 正常返回:{@link OrgMemberVO}
     * @author 蝉鸣
     */
    @Override
    public OrgMemberVO editMember(OrgMemberDTO memberDTO) {
        if(ObjectUtil.isEmpty(memberDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(OrgMemberDTO::getId);
            throw MemberExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        OrgMember orgMember = BeanUtil.copyProperties(memberDTO, OrgMember.class);
        this.updateById(orgMember);
        return BeanUtil.copyProperties(orgMember, OrgMemberVO.class);
    }

    /**
     * 功能描述:
     * 〈删除成员〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteMember(OrgMemberDelDTO delDTO) {
        // 删除成员关系
        Boolean canDel = orgMemberServiceManual.deleteMemberRel(delDTO);
        // 删除成员
        if(canDel && YesOrNoEnum.YES.getValue().equals(delDTO.getForceFlag())){
            this.removeById(delDTO.getMemberId());
        }
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈根据成员ID查询所属组织信息〉
     * @param userId userId
     * @return 正常返回:{@link List<SysOrgBO>}
     * @author 蝉鸣
     */
    public List<SysOrgBO> getMemberInfoByUserId(Long userId) {
        return this.getBaseMapper().getMemberInfoByUserId(userId);
    }

    /**
     * 功能描述:
     * 〈根据getMemberOrgByUserIdID查询所属组织信息〉
     * @param userId userId
     * @return 正常返回:{@link List<SysOrgBO>}
     * @author 蝉鸣
     */
    public List<SysOrgBO> getMemberOrgByUserId(Long userId) {
        return this.getBaseMapper().getMemberOrgByUserId(userId);
    }

    /**
     * 功能描述:
     * 〈根据用户ID查询所属成员信息〉
     * @param userId userId
     * @return 正常返回:{@link List<SysMemberVO>}
     * @author 蝉鸣
     */
    public List<SysMemberVO> getMemberVOByUserId(Long userId) {
        return this.getBaseMapper().getMemberVOByUserId(userId);
    }

    /**
     * 功能描述:
     * 〈通过ids获取组织成员信息〉
     * @param userIds userIds
     * @return 正常返回:{@link List<OrgMember>}
     * @author 蝉鸣
     */
    @Override
    public List<OrgMember> getOrgMemberByUserIds(List<Long> userIds) {
        return this.getBaseMapper().getOrgMemberByUserIds(userIds);
    }

    /**
     * 功能描述:
     * 〈通过ids获取组织成员信息〉
     * @param memberIds memberIds
     * @return 正常返回:{@link List<OrgMemberBO>}
     * @author 蝉鸣
     */
    @Override
    public List<OrgMemberBO> getOrgMemberByIds(List<Long> memberIds){
        return this.getBaseMapper().getOrgMemberByIds(memberIds);
    }

    /**
     * 功能描述:
     * 〈根据成员名称获取组织下成员信息〉
     * @param memberName memberName
     * @return 正常返回:{@link OrgMember}
     * @author 蝉鸣
     */
    @Override
    public OrgMemberBO getOrgMemberByNameFirst(String memberName) {
        return this.getBaseMapper().getOrgMemberByNameFirst(memberName);
    }

    /**
     * 功能描述:
     * 〈根据成员名称获取组织下成员信息〉
     * @param userId userId
     * @return 正常返回:{@link OrgMember}
     * @author 蝉鸣
     */
    @Override
    public OrgMemberBO getOrgMemberByUserIdFirst(Long userId) {
        return this.getBaseMapper().getOrgMemberByUserIdFirst(userId);
    }

    /**
     * 功能描述:
     * 〈转移成员下的数据〉
     * @param transDTO transDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean transMemberData(OrgMemberTransDTO transDTO) {
        //租户ID
        //获取成员信息
        OrgMemberTransBO transBO = BeanUtil.copyProperties(transDTO, OrgMemberTransBO.class);
        if(ObjectUtil.isEmpty(transBO.getTargetMemberName())
                || ObjectUtil.isEmpty(transBO.getTargetLevelId())
                || ObjectUtil.isEmpty(transBO.getTargetLevelName())){
            throw MemberExceptionEnum.TRANS_NO_INVALID.getBaseException();
        }
        //通过Redisson异步发送
        MsgUpmsBO msgUpmsBO = new MsgUpmsBO();
        Map<String, Object> map = new HashMap<>();
        map.put(StrConst.VALUE,transBO);
        msgUpmsBO.setExtendJson(map);
        msgUpmsBO.setActionType(UpmsActionEnum.TRANS_ORG_DATA.getValue());
        //发送订阅信息
        RedissonUtil.publish(StrConst.ALL,msgUpmsBO);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈查询当前人员的上级〉
     * @param id id
     * @return 正常返回:{@link List<OrgMemberVO>}
     * @author 蝉鸣
     */
    @Override
    public List<OrgMemberVO> getMemberLeadList(Long id) {
        //默认未当前所在默认部门的领导层级
        //查询默认岗位
        Long levelId = orgMemberServiceManual.getDefaultLevel(id);
        //查询上级部门
        List<Long> parentLevels = orgMemberServiceManual.getParentLevel(levelId);
        //查询上级部门领导
        return this.getBaseMapper().getMemberLeadList(parentLevels,YesOrNoEnum.YES.getValue());
    }

    /**
     * 功能描述:
     * 〈获取直属上级〉
     * @param accountId accountId
     * @return 正常返回:{@link OrgMemberRelBO}
     * @author 蝉鸣
     */
    @Override
    public OrgMemberBO getLeaderDirect(Long accountId) {
        //查询上级部门领导
        return this.getBaseMapper().getLeaderDirect(accountId,YesOrNoEnum.YES.getValue());
    }

    /**
     * 功能描述:
     * 〈获取多层上级〉
     * @param accountId accountId
     * @return 正常返回:{@link List<OrgMemberRelBO>}
     * @author 蝉鸣
     */
    @Override
    public List<OrgMemberBO> getLeaderLoop(Long accountId) {
        SysAccountBO accountInfo = UserCacheUtil.getAccountInfoCache(accountId);
        //查询上级部门
        List<Long> parentLevels = orgMemberServiceManual.getParentLevel(accountInfo.getScopeOrgId());
        //查询上级部门领导
        return this.getBaseMapper().getLeaderLoop(parentLevels,YesOrNoEnum.YES.getValue());
    }

}

