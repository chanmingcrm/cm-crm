package com.platform.mesh.upms.biz.modules.org.member.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import com.platform.mesh.upms.biz.modules.org.level.domain.po.OrgLevel;
import com.platform.mesh.upms.biz.modules.org.level.service.IOrgLevelService;
import com.platform.mesh.upms.biz.modules.org.member.domain.dto.OrgMemberDelDTO;
import com.platform.mesh.upms.biz.modules.org.member.domain.po.OrgMember;
import com.platform.mesh.upms.biz.modules.org.member.domain.vo.OrgMemberInfoVO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.dto.OrgMemberPostRelPageDTO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.po.OrgMemberPostRel;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.vo.OrgMemberPostRelPageVO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.service.IOrgMemberPostRelService;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.service.IOrgMemberUserRelService;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service()
public class OrgMemberServiceManual {

    @Autowired
    private IOrgMemberPostRelService orgMemberPostRelService;

    /**
     * 功能描述:
     * 〈获取成员详情〉
     */
    public OrgMemberInfoVO getMemberInfoById(OrgMember orgMember) {
        OrgMemberInfoVO orgMemberVO = new OrgMemberInfoVO();
        if(ObjectUtil.isEmpty(orgMemberVO)){
            return orgMemberVO;
        }
        //转换VO
        BeanUtil.copyProperties(orgMember, orgMemberVO);
        //获取成员与岗位关系
        OrgMemberPostRelPageDTO pageDTO = new OrgMemberPostRelPageDTO();
        pageDTO.setMemberId(orgMember.getId());
        //不分页
        pageDTO.setPageSize(NumberConst.NUM__1);
        MPage<OrgMemberPostRelPageVO> pageVOMPage = orgMemberPostRelService.selectPostPage(pageDTO);
        if(CollUtil.isEmpty(pageVOMPage.getRecords())){
            return orgMemberVO;
        }
        List<OrgMemberPostRelPageVO> records = pageVOMPage.getRecords();
        orgMemberVO.setRelList(records);
        return orgMemberVO;
    }

    /**
     * 功能描述: 
     * 〈删除成员关系〉
     */
    public Boolean deleteMemberRel(OrgMemberDelDTO delDTO) {
        List<Long> levelIds = CollUtil.newArrayList(delDTO.getLevelId());
        String childrenSql = SqlUtil.getCommonChildrenSql(OrgLevel.class, delDTO.getLevelId());
        //查询子项
        IOrgLevelService orgLevelService = SpringContextHolderUtil.getBean(IOrgLevelService.class);
        List<OrgLevel> childList = orgLevelService.lambdaQuery().apply(childrenSql).list();
        if(CollUtil.isNotEmpty(childList)){
            List<Long> ids = childList.stream().map(OrgLevel::getId).toList();
            levelIds.addAll(ids);
        }
        //删除岗位关系
        IOrgMemberPostRelService orgMemberPostRelService = SpringContextHolderUtil.getBean(IOrgMemberPostRelService.class);
        boolean exists = orgMemberPostRelService.lambdaQuery()
                .eq(OrgMemberPostRel::getMemberId, delDTO.getMemberId())
                .notIn(OrgMemberPostRel::getLevelId, levelIds).exists();
        //如果是删除所有
        if(!exists && YesOrNoEnum.YES.getValue().equals(delDTO.getForceFlag())){
            //删除人员关系
            IOrgMemberUserRelService orgMemberUserRelService = SpringContextHolderUtil.getBean(IOrgMemberUserRelService.class);
            orgMemberUserRelService.deleteMemberUserByMemberId(delDTO.getMemberId());
            orgMemberPostRelService.lambdaUpdate()
                    .eq(OrgMemberPostRel::getMemberId, delDTO.getMemberId())
                    .in(OrgMemberPostRel::getLevelId, levelIds).remove();
            return Boolean.TRUE;
        }
        orgMemberPostRelService.lambdaUpdate()
                .eq(OrgMemberPostRel::getMemberId, delDTO.getMemberId())
//                .eq(OrgMemberPostRel::getDefaultFlag, YesOrNoEnum.NO.getValue())
                .in(OrgMemberPostRel::getLevelId, levelIds).remove();
        return Boolean.FALSE;
    }

    /**
     * 功能描述:
     * 〈获取所有子级层级〉
     */
    public List<Long> getLevelChildByIds(List<Long> levelIds) {
        //避免循环依赖
        IOrgLevelService orgLevelService = SpringContextHolderUtil.getBean(IOrgLevelService.class);
        return orgLevelService.getLevelChildByIds(levelIds);
    }

    /**
     * 功能描述:
     * 〈查询默认岗位〉
     */
    public Long getDefaultLevel(Long memberId) {
        IOrgMemberPostRelService orgMemberPostRelService = SpringContextHolderUtil.getBean(IOrgMemberPostRelService.class);
        List<OrgMemberPostRel> memberPostRels = orgMemberPostRelService.lambdaQuery()
                .eq(OrgMemberPostRel::getMemberId, memberId)
                .list();
        if(ObjectUtil.isEmpty(memberPostRels)){
            return null;
        }
        //查询是否有默认标识
        List<OrgMemberPostRel> postRels = memberPostRels.stream().filter(rel -> YesOrNoEnum.YES.getValue().equals(rel.getDefaultFlag())).toList();
        if(CollUtil.isEmpty(postRels)){
            return CollUtil.getFirst(memberPostRels).getLevelId();
        }
        return CollUtil.getFirst(postRels).getLevelId();
    }

    /**
     * 功能描述:
     * 〈查询上级部门〉
     */
    public List<Long> getParentLevel(Long levelId) {
        IOrgLevelService orgLevelService = SpringContextHolderUtil.getBean(IOrgLevelService.class);
        String parentsSql = SqlUtil.getCommonParentSql(OrgLevel.class, levelId);
        //查询父部门
        List<OrgLevel> parentList = orgLevelService.lambdaQuery().apply(parentsSql).list();
        if(CollUtil.isEmpty(parentList)){
            return CollUtil.newArrayList();
        }
        return parentList.stream().map(OrgLevel::getId).toList();
    }

    /**
     * 功能描述:
     * 〈查询部门〉
     */
    public OrgLevel getLevelById(Long levelId) {
        IOrgLevelService orgLevelService = SpringContextHolderUtil.getBean(IOrgLevelService.class);
        return orgLevelService.getById(levelId);
    }
}

