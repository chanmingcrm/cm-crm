package com.platform.mesh.upms.biz.modules.org.member.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysOrgBO;
import com.platform.mesh.upms.biz.modules.org.member.domain.dto.OrgMemberDTO;
import com.platform.mesh.upms.biz.modules.org.member.domain.dto.OrgMemberPageDTO;
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

import java.util.List;

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
        if(CollUtil.isNotEmpty(orgMemberPageDTO.getLevelIds())) {
            //获取包含所有的子级
            List<Long> levelIds = orgMemberServiceManual.getLevelChildByIds(orgMemberPageDTO.getLevelIds());
            orgMemberPageDTO.setLevelIds(levelIds);
        }
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
     * @param memberId memberId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteMember(Long memberId) {
        // 删除成员关系
        orgMemberServiceManual.deleteMemberRel(memberId);
        // 删除成员
        return this.removeById(memberId);
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

}

