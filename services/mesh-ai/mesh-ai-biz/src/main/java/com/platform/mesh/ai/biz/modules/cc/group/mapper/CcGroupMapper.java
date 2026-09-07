package com.platform.mesh.ai.biz.modules.cc.group.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.ai.biz.modules.cc.group.domain.dto.CcGroupPageDTO;
import com.platform.mesh.ai.biz.modules.cc.group.domain.po.CcGroup;
import com.platform.mesh.ai.biz.modules.cc.group.domain.vo.CcGroupVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Param;

/**
 * @description CcGroup
 * @author 蝉鸣
 */
public interface CcGroupMapper extends BaseMapper<CcGroup> {

    /**
     * 功能描述:
     * 〈分页查询会话群〉
     * @param mPage mPage
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<CcGroupVO>}
     * @author Codex
     */
    MPage<CcGroupVO> selectMPage(MPage<CcGroup> mPage, @Param("pageDTO") CcGroupPageDTO pageDTO);

    /**
     * 功能描述:
     * 〈根据群Hash获取会话群〉
     * @param groupHash groupHash
     * @return 正常返回:{@link CcGroup}
     * @author Codex
     */
    @InterceptorIgnore(tenantLine = "true")
    CcGroup getCcGroupByHash(@Param("groupHash") String groupHash);

    /**
     * 保存官网访客联系方式，只允许更新 webSetId 所属租户内的会话。
     */
    @InterceptorIgnore(tenantLine = "true")
    int saveConsultationLead(@Param("groupHash") String groupHash,
                             @Param("phone") String phone,
                             @Param("intent") String intent,
                             @Param("sourcePage") String sourcePage);

    /**
     * 功能描述:
     * 〈设置群回复类型〉
     * @param replyType replyType
     * @param groupHash groupHash
     * @param tenantId tenantId
     * @author Codex
     */
    @InterceptorIgnore(tenantLine = "true")
    void setReplyType(@Param("replyType") Integer replyType,@Param("groupHash") String groupHash);

    /**
     * 功能描述:
     * 〈设置会话状态〉
     * @param status status
     * @param groupHash groupHash
     * @param tenantId tenantId
     * @author Codex
     */
    @InterceptorIgnore(tenantLine = "true")
    void setStatus(@Param("status") Integer status, @Param("groupHash") String groupHash);

}
