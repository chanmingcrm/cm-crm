package com.platform.mesh.upms.biz.modules.org.memberpostrel.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.po.OrgLevelPostRel;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.vo.OrgLevelPostRelVO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.dto.OrgMemberPostRelDTO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.dto.OrgMemberPostRelPageDTO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.dto.OrgMemberPostRelTransDTO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.po.OrgMemberPostRel;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.vo.OrgMemberLevelVO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.vo.OrgMemberPostRelPageVO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.exception.MemberPostRelExceptionEnum;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.mapper.OrgMemberPostRelMapper;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.service.IOrgMemberPostRelService;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.service.manual.OrgMemberPostRelServiceManual;
import com.platform.mesh.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 组织信息
 * @author 蝉鸣
 */
@Service()
public class OrgMemberPostRelServiceImpl extends ServiceImpl<OrgMemberPostRelMapper, OrgMemberPostRel> implements IOrgMemberPostRelService {

    @Autowired
    private OrgMemberPostRelServiceManual orgMemberPostRelServiceManual;


    /**
     * 功能描述:
     * 〈获取成员-层级分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<MPage<OrgMemberLevelVO>>}
     * @author 蝉鸣
     */
    @Override
    public MPage<OrgMemberLevelVO> selectLevelPage(OrgMemberPostRelPageDTO pageDTO){
        Long accountId = UserCacheUtil.getAccountId();
        MPage<OrgMemberLevelVO> levelMPage = MPageUtil.pageEntityToMPage(pageDTO, OrgMemberLevelVO.class);
        return this.getBaseMapper().selectLevelPage(levelMPage,accountId);
    }

    /**
     * 功能描述:
     * 〈获取成员-岗位分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<MPage<OrgLevelPostRelVO>>}
     * @author 蝉鸣
     */
    @Override
    public MPage<OrgMemberPostRelPageVO> selectPostPage(OrgMemberPostRelPageDTO pageDTO) {
        if(ObjectUtil.isEmpty(pageDTO.getMemberId()) && ObjectUtil.isEmpty(pageDTO.getPostId())){
            throw MemberPostRelExceptionEnum.ADD_NO_ARGS.getBaseException();
        }
        MPage<OrgMemberPostRel> userMPage = MPageUtil.pageEntityToMPage(pageDTO, OrgMemberPostRel.class);
        return this.getBaseMapper().selectPageRel(userMPage,pageDTO);
    }

    /**
     * 功能描述:
     * 〈添加成员岗位〉
     * @param memberPostRelDTO memberPostRelDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public Boolean addMemberPost(OrgMemberPostRelDTO memberPostRelDTO) {
        if(CollUtil.isEmpty(memberPostRelDTO.getPostIds())){
            return Boolean.FALSE;
        }
        //查询岗位与层级关系
        List<OrgLevelPostRel> levelPostRelList = orgMemberPostRelServiceManual.getLevelPostRelList(memberPostRelDTO.getPostIds());
        if(CollUtil.isEmpty(levelPostRelList)){
            return Boolean.FALSE;
        }
        Map<Long, OrgLevelPostRel> postRelMap = levelPostRelList.stream().collect(Collectors.toMap(OrgLevelPostRel::getPostId, Function.identity()));
        List<OrgMemberPostRel> list = CollUtil.newArrayList();
        for (Long postId : memberPostRelDTO.getPostIds()) {
            if(postRelMap.containsKey(postId)){
                OrgMemberPostRel orgMemberPostRel = new OrgMemberPostRel();
                orgMemberPostRel.setMemberId(memberPostRelDTO.getMemberId());
                orgMemberPostRel.setPostId(postId);
                OrgLevelPostRel levelPostRel = postRelMap.get(postId);
                orgMemberPostRel.setLevelRootId(levelPostRel.getLevelRootId());
                orgMemberPostRel.setLevelId(levelPostRel.getLevelId());
                list.add(orgMemberPostRel);
            }
        }
        if(CollUtil.isEmpty(list)){
            return Boolean.FALSE;
        }
        //删除旧数据
        this.lambdaUpdate()
                .eq(OrgMemberPostRel::getMemberId,memberPostRelDTO.getMemberId())
                .in(OrgMemberPostRel::getPostId,memberPostRelDTO.getPostIds())
                .remove();
        //新增数据
        this.saveBatch(list);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈删除成员岗位〉
     * @param relId relId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteMemberPost(Long relId) {
        removeById(relId);
        return Boolean.TRUE;
    }


    /**
     * 功能描述:
     * 〈删除成员岗位〉
     * @param memberId memberId
     * @author 蝉鸣
     */
    @Override
    public void deleteMemberPostByMemberId(Long memberId) {
        this.lambdaUpdate().eq(OrgMemberPostRel::getMemberId,memberId).remove();
    }

    /**
     * 功能描述:
     * 〈转移成员岗位〉
     * @param transDTO transDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public Boolean transMemberPost(OrgMemberPostRelTransDTO transDTO) {
        if(CollUtil.isEmpty(transDTO.getMemberIds())){
            return Boolean.FALSE;
        }
        OrgLevelPostRel orgLevelPostRel = orgMemberPostRelServiceManual.getLevelPostRel(transDTO.getTargetId());
        //岗位必须是与层级关联,否则视为岗位无效不存在
        if(ObjectUtil.isEmpty(orgLevelPostRel)){
            return Boolean.FALSE;
        }
        if(ObjectUtil.isNotEmpty(transDTO.getSourceId())){
            //删除与旧岗位关系
            this.lambdaUpdate().eq(OrgMemberPostRel::getPostId, transDTO.getSourceId()).in(OrgMemberPostRel::getMemberId,transDTO.getMemberIds()).remove();
        }
        //新增关系
        List<OrgMemberPostRel> memberPostRels = transDTO.getMemberIds().stream().map(rel -> {
            OrgMemberPostRel orgMemberPostRel = new OrgMemberPostRel();
            orgMemberPostRel.setMemberId(rel);
            orgMemberPostRel.setLevelId(orgLevelPostRel.getLevelId());
            orgMemberPostRel.setPostId(transDTO.getTargetId());
            return orgMemberPostRel;
        }).toList();
        this.saveBatch(memberPostRels);
        return Boolean.TRUE;
    }
}

