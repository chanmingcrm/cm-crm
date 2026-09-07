package com.platform.mesh.ai.biz.modules.ai.knowledgedoc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.ai.biz.modules.ai.knowledgedoc.domain.po.AiKnowledgeDoc;
import com.platform.mesh.ai.biz.modules.ai.knowledgedoc.domain.vo.AiKnowledgeDocVO;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Param;

/**
 * @description AI会话
 * @author 蝉鸣
 */
public interface AiKnowledgeDocMapper extends BaseMapper<AiKnowledgeDoc> {

    MPage<AiKnowledgeDocVO> selectMPage(MPage<AiKnowledgeDoc> mPage,@Param("pageDTO") PageDTO pageDTO);
}