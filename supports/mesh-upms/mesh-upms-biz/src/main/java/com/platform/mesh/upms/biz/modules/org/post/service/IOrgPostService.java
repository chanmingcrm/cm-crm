package com.platform.mesh.upms.biz.modules.org.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.org.member.domain.vo.OrgMemberVO;
import com.platform.mesh.upms.biz.modules.org.post.domain.dto.OrgPostAddDTO;
import com.platform.mesh.upms.biz.modules.org.post.domain.dto.OrgPostEditDTO;
import com.platform.mesh.upms.biz.modules.org.post.domain.dto.OrgPostPageDTO;
import com.platform.mesh.upms.biz.modules.org.post.domain.po.OrgPost;
import com.platform.mesh.upms.biz.modules.org.post.domain.vo.OrgPostVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 组织信息
 * @author 蝉鸣
 */
public interface IOrgPostService extends IService<OrgPost> {

    /***
     * 功能描述:
     * 〈查询岗位分页〉
     * @param orgPostPageDTO orgPostPageDTO
     * @return 正常返回:{@link MPage<OrgPost>}
     * @author 蝉鸣
     * @since 2024/9/6 19:57
     */
    MPage<OrgPostVO> selectPage(OrgPostPageDTO orgPostPageDTO);

    /**
     * 功能描述:
     * 〈获取岗位〉
     * @param id id
     * @return 正常返回:{@link OrgPostVO}
     * @author 蝉鸣
     */
    OrgPostVO getPostInfoById(Long id);

    /**
     * 功能描述:
     * 〈添加岗位〉
     * @param postDTO postDTO
     * @return 正常返回:{@link OrgPostVO}
     * @author 蝉鸣
     */
    OrgPostVO addPost(OrgPostAddDTO postDTO);

    /**
     * 功能描述:
     * 〈修改岗位〉
     * @param postDTO postDTO
     * @return 正常返回:{@link OrgMemberVO}
     * @author 蝉鸣
     */
    OrgMemberVO editPost(OrgPostEditDTO postDTO);

    /**
     * 功能描述:
     * 〈删除岗位〉
     * @param postId postId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deletePost(Long postId);

}

