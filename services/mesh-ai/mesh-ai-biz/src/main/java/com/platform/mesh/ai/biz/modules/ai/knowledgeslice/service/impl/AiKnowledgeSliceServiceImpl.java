package com.platform.mesh.ai.biz.modules.ai.knowledgeslice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.ai.biz.modules.ai.knowledgeslice.domain.po.AiKnowledgeSlice;
import com.platform.mesh.ai.biz.modules.ai.knowledgeslice.mapper.AiKnowledgeSliceMapper;
import com.platform.mesh.ai.biz.modules.ai.knowledgeslice.service.IAiKnowledgeSliceService;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description AI知识库分片
 * @author 蝉鸣
 */
@Service
public class AiKnowledgeSliceServiceImpl extends ServiceImpl<AiKnowledgeSliceMapper, AiKnowledgeSlice> implements IAiKnowledgeSliceService {

}
