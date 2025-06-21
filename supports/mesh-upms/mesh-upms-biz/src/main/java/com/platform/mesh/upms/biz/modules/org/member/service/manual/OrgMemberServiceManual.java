package com.platform.mesh.upms.biz.modules.org.member.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.org.level.service.IOrgLevelService;
import com.platform.mesh.upms.biz.modules.org.member.domain.po.OrgMember;
import com.platform.mesh.upms.biz.modules.org.member.domain.vo.OrgMemberInfoVO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.dto.OrgMemberPostRelPageDTO;
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
     * 功能描述: <br>
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
     * 功能描述: <br>
     * 〈删除成员关系〉
     */
    public void deleteMemberRel(Long memberId) {
        //删除岗位关系
        IOrgMemberPostRelService orgMemberPostRelService = SpringContextHolderUtil.getBean(IOrgMemberPostRelService.class);
        orgMemberPostRelService.deleteMemberPostByMemberId(memberId);
        //删除人员关系
        IOrgMemberUserRelService orgMemberUserRelService = SpringContextHolderUtil.getBean(IOrgMemberUserRelService.class);
        orgMemberUserRelService.deleteMemberUserByMemberId(memberId);
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
}

