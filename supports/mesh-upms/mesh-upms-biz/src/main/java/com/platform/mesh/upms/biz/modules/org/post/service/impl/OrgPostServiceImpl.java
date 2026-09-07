package com.platform.mesh.upms.biz.modules.org.post.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.vo.OrgLevelPostRelVO;
import com.platform.mesh.upms.biz.modules.org.member.domain.dto.OrgMemberDTO;
import com.platform.mesh.upms.biz.modules.org.member.domain.vo.OrgMemberVO;
import com.platform.mesh.upms.biz.modules.org.post.domain.dto.OrgPostAddDTO;
import com.platform.mesh.upms.biz.modules.org.post.domain.dto.OrgPostEditDTO;
import com.platform.mesh.upms.biz.modules.org.post.domain.dto.OrgPostPageDTO;
import com.platform.mesh.upms.biz.modules.org.post.domain.po.OrgPost;
import com.platform.mesh.upms.biz.modules.org.post.domain.vo.OrgPostVO;
import com.platform.mesh.upms.biz.modules.org.post.exception.PostExceptionEnum;
import com.platform.mesh.upms.biz.modules.org.post.mapper.OrgPostMapper;
import com.platform.mesh.upms.biz.modules.org.post.service.IOrgPostService;
import com.platform.mesh.upms.biz.modules.org.post.service.manual.OrgPostServiceManual;
import com.platform.mesh.upms.biz.modules.org.postdatascope.domain.vo.OrgPostDataScopeVO;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 组织信息
 * @author 蝉鸣
 */
@Service()
public class OrgPostServiceImpl extends ServiceImpl<OrgPostMapper, OrgPost> implements IOrgPostService {

    private static final Logger log = LoggerFactory.getLogger(OrgPostServiceImpl.class);

    @Autowired
    private OrgPostServiceManual orgPostServiceManual;

    /***
     * 功能描述:
     * 〈查询岗位分页数据〉
     * @param orgPageDTO orgPageDTO
     * @return 正常返回:{@link MPage<OrgPost>}
     * @author 蝉鸣
     * @since 2024/9/6 20:00
     */
    @Override
    public MPage<OrgPostVO> selectPage(OrgPostPageDTO orgPageDTO) {
        //获取当前层级下的所有子层级
        if(YesOrNoEnum.YES.getValue().equals(orgPageDTO.getNeedChild())){
            List<Long> levelIds = orgPostServiceManual.getChileLevelIds(orgPageDTO.getLevelIds());
            orgPageDTO.setLevelIds(levelIds);
        }
        List<Long> levelIds = orgPageDTO.getLevelIds().stream().filter(ObjectUtil::isNotEmpty).toList();
        orgPageDTO.setLevelIds(levelIds);
        if(ObjectUtil.isEmpty(orgPageDTO.getLevelIds())){
            return new MPage<>();
        }
        MPage<OrgPost> postMPage = MPageUtil.pageEntityToMPage(orgPageDTO,OrgPost.class);
        return this.getBaseMapper().selectRelPage(postMPage, orgPageDTO);
    }

    /**
     * 功能描述:
     * 〈获取岗位〉
     * @param postId postId
     * @return 正常返回:{@link OrgPostVO}
     * @author 蝉鸣
     */
    @Override
    public OrgPostVO getPostInfoById(Long postId) {
        OrgPost orgPost = this.getById(postId);
        //获取岗位与层级关系
        OrgLevelPostRelVO relVO = orgPostServiceManual.getLevelPostRel(postId);
        //获取岗位数据权限
        List<OrgPostDataScopeVO> postDataScopeVOS = orgPostServiceManual.getPostDataScope(postId);
        OrgPostVO orgPostVO = BeanUtil.copyProperties(orgPost, OrgPostVO.class);
        orgPostVO.setLevelId(relVO.getLevelId());
        orgPostVO.setLevelName(relVO.getLevelName());
        orgPostVO.setPostDataScopeVOS(postDataScopeVOS);
        return orgPostVO;
    }

    /**
     * 功能描述:
     * 〈添加岗位〉
     * @param postDTO postDTO
     * @return 正常返回:{@link OrgPostVO}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrgPostVO addPost(OrgPostAddDTO postDTO) {
        //保存岗位信息
        OrgPost orgPost = BeanUtil.copyProperties(postDTO, OrgPost.class);
        this.save(orgPost);
        //添加岗位其他信息
        orgPostServiceManual.addPost(orgPost.getId(),postDTO);
        return BeanUtil.copyProperties(orgPost, OrgPostVO.class);
    }

    /**
     * 功能描述:
     * 〈修改岗位〉
     * @param postDTO postDTO
     * @return 正常返回:{@link OrgMemberVO}
     * @author 蝉鸣
     */
    @Override
    public OrgMemberVO editPost(OrgPostEditDTO postDTO) {
        if(ObjectUtil.isEmpty(postDTO.getPostId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(OrgMemberDTO::getId);
            throw PostExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        OrgPost orgPost = BeanUtil.copyProperties(postDTO, OrgPost.class);
        orgPost.setId(postDTO.getPostId());
        this.updateById(orgPost);
        //添加岗位其他信息
        orgPostServiceManual.editPost(orgPost.getId(),postDTO);
        return BeanUtil.copyProperties(orgPost, OrgMemberVO.class);
    }

    /**
     * 功能描述:
     * 〈删除岗位〉
     * @param postId postId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deletePost(Long postId) {
        // 删除岗位其他信息
        orgPostServiceManual.delPostRel(postId);
        // 删除层级
        return this.removeById(postId);
    }

}

