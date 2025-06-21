package com.platform.mesh.upms.biz.modules.org.post.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.org.post.domain.dto.OrgPostPageDTO;
import com.platform.mesh.upms.biz.modules.org.post.domain.po.OrgPost;
import com.platform.mesh.upms.biz.modules.org.post.domain.vo.OrgPostVO;
import org.apache.ibatis.annotations.Param;

/**
 * @description 岗位
 * @author 蝉鸣
 */
public interface OrgPostMapper extends BaseMapper<OrgPost> {

    /***
     * 功能描述:
     * 〈查询岗位分页数据〉
     * @param orgPageDTO orgPageDTO
     * @return 正常返回:{@link MPage<OrgPostVO>}
     * @author 蝉鸣
     * @since 2024/9/6 20:00
     */
    MPage<OrgPostVO> selectRelPage(MPage<OrgPost> postMPage, @Param("orgPageDTO") OrgPostPageDTO orgPageDTO);
}

