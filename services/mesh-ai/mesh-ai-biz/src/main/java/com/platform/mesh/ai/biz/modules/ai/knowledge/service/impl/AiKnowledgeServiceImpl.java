package com.platform.mesh.ai.biz.modules.ai.knowledge.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.ai.biz.modules.ai.knowledge.domain.dto.AiKnowledgeDTO;
import com.platform.mesh.ai.biz.modules.ai.knowledge.domain.po.AiKnowledge;
import com.platform.mesh.ai.biz.modules.ai.knowledge.domain.vo.AiKnowledgeVO;
import com.platform.mesh.ai.biz.modules.ai.knowledge.exception.AiKnowledgeExceptionEnum;
import com.platform.mesh.ai.biz.modules.ai.knowledge.mapper.AiKnowledgeMapper;
import com.platform.mesh.ai.biz.modules.ai.knowledge.service.IAiKnowledgeService;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description AI知识库
 * @author 蝉鸣
 */
@Service
public class AiKnowledgeServiceImpl extends ServiceImpl<AiKnowledgeMapper, AiKnowledge> implements IAiKnowledgeService {

    /**
     * 功能描述:
     * 〈获取当前AI知识库分页信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<AiKnowledgeVO>}
     * @author 蝉鸣
     */
    @Override
    public MPage<AiKnowledgeVO> selectPage(PageDTO pageDTO) {
        MPage<AiKnowledge> mPage = MPageUtil.pageEntityToMPage(pageDTO, AiKnowledge.class);
        return this.getBaseMapper().selectMPage(mPage,pageDTO);
    }
    
    /**
     * 功能描述: 
     * 〈获取当前AI知识库信息〉
     * @param knowledgeId knowledgeId
     * @return 正常返回:{@link AiKnowledgeVO}
     * @author 蝉鸣
     */
    @Override
    public AiKnowledgeVO getAiKnowledgeById(Long knowledgeId) {
        AiKnowledge aiKnowledge = this.getById(knowledgeId);
        return BeanUtil.copyProperties(aiKnowledge, AiKnowledgeVO.class);
    }

    /**
     * 功能描述:
     * 〈新增AI知识库〉
     * @param knowledgeDTO knowledgeDTO
     * @return 正常返回:{@link AiKnowledgeVO}
     * @author 蝉鸣
     */
    @Override
    public AiKnowledgeVO addAiKnowledge(AiKnowledgeDTO knowledgeDTO) {
        AiKnowledge aiKnowledge = BeanUtil.copyProperties(knowledgeDTO, AiKnowledge.class);
        this.save(aiKnowledge);
        return BeanUtil.copyProperties(aiKnowledge, AiKnowledgeVO.class);
    }

    /**
     * 功能描述:
     * 〈修改AI知识库〉
     * @param knowledgeDTO knowledgeDTO
     * @return 正常返回:{@link AiKnowledgeVO}
     * @author 蝉鸣
     */
    @Override
    public AiKnowledgeVO editAiKnowledge(AiKnowledgeDTO knowledgeDTO) {
        if(ObjectUtil.isEmpty(knowledgeDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(AiKnowledgeDTO::getId);
            throw AiKnowledgeExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        AiKnowledge aiKnowledge = BeanUtil.copyProperties(knowledgeDTO, AiKnowledge.class);
        this.updateById(aiKnowledge);
        return BeanUtil.copyProperties(aiKnowledge, AiKnowledgeVO.class);
    }

    /**
     * 功能描述:
     * 〈删除AI知识库〉
     * @param knowledgeId knowledgeId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteAiKnowledge(Long knowledgeId) {
        return this.removeById(knowledgeId);
    }

}
