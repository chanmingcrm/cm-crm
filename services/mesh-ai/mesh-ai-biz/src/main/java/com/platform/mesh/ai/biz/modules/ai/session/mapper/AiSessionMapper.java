package com.platform.mesh.ai.biz.modules.ai.session.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.ai.biz.modules.ai.session.domain.dto.AiSessionPageDTO;
import com.platform.mesh.ai.biz.modules.ai.session.domain.po.AiSession;
import com.platform.mesh.ai.biz.modules.ai.session.domain.vo.AiSessionVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Param;

/**
 * @description AI会话
 * @author 蝉鸣
 */
public interface AiSessionMapper extends BaseMapper<AiSession> {

    MPage<AiSessionVO> selectMPage(MPage<AiSession> mPage,@Param("pageDTO") AiSessionPageDTO pageDTO);
}