package com.platform.mesh.ai.biz.modules.ai.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.ai.biz.modules.ai.knowledge.domain.po.AiKnowledge;
import com.platform.mesh.ai.biz.modules.ai.knowledge.domain.vo.AiKnowledgeVO;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Param;

/**
 * @description AI会话
 * @author 蝉鸣
 */
public interface AiKnowledgeMapper extends BaseMapper<AiKnowledge> {

    MPage<AiKnowledgeVO> selectMPage(MPage<AiKnowledge> mPage,@Param("pageDTO") PageDTO pageDTO);
}