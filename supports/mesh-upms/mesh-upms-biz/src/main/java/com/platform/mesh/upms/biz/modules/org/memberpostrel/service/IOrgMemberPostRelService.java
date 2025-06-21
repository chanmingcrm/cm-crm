package com.platform.mesh.upms.biz.modules.org.memberpostrel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.vo.OrgLevelPostRelVO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.dto.OrgMemberPostRelDTO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.dto.OrgMemberPostRelPageDTO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.dto.OrgMemberPostRelTransDTO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.po.OrgMemberPostRel;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.vo.OrgMemberLevelVO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.vo.OrgMemberPostRelPageVO;
import com.platform.mesh.utils.result.Result;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 成员信息
 * @author 蝉鸣
 */
public interface IOrgMemberPostRelService extends IService<OrgMemberPostRel> {

    /**
     * 功能描述:
     * 〈获取成员-层级分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<MPage<OrgMemberLevelVO>>}
     * @author 蝉鸣
     */
    MPage<OrgMemberLevelVO> selectLevelPage(OrgMemberPostRelPageDTO pageDTO);

    /**
     * 功能描述:
     * 〈获取成员-岗位分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<MPage<OrgLevelPostRelVO>>}
     * @author 蝉鸣
     */
    MPage<OrgMemberPostRelPageVO> selectPostPage(OrgMemberPostRelPageDTO pageDTO);

    /**
     * 功能描述:
     * 〈添加成员岗位〉
     * @param memberPostRelDTO memberPostRelDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean addMemberPost(OrgMemberPostRelDTO memberPostRelDTO);

    /**
     * 功能描述:
     * 〈删除成员岗位〉
     * @param relId relId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteMemberPost(Long relId);

    /**
     * 功能描述:
     * 〈删除成员岗位〉
     * @param memberId memberId
     * @author 蝉鸣
     */
    void deleteMemberPostByMemberId(Long memberId);

    /**
     * 功能描述:
     * 〈转移成员岗位〉
     * @param transDTO transDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean transMemberPost(OrgMemberPostRelTransDTO transDTO);
}

