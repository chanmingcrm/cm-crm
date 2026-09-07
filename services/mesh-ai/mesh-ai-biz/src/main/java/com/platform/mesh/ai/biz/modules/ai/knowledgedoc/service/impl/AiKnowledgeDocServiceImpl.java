package com.platform.mesh.ai.biz.modules.ai.knowledgedoc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.ai.biz.modules.ai.knowledge.domain.po.AiKnowledge;
import com.platform.mesh.ai.biz.modules.ai.knowledgedoc.domain.dto.AiKnowledgeDocDTO;
import com.platform.mesh.ai.biz.modules.ai.knowledgedoc.domain.po.AiKnowledgeDoc;
import com.platform.mesh.ai.biz.modules.ai.knowledgedoc.domain.vo.AiKnowledgeDocVO;
import com.platform.mesh.ai.biz.modules.ai.knowledgedoc.exception.AiKnowledgeDocExceptionEnum;
import com.platform.mesh.ai.biz.modules.ai.knowledgedoc.mapper.AiKnowledgeDocMapper;
import com.platform.mesh.ai.biz.modules.ai.knowledgedoc.service.IAiKnowledgeDocService;
import com.platform.mesh.ai.biz.modules.ai.knowledgedoc.service.manual.AiKnowledgeDocServiceManual;
import com.platform.mesh.ai.biz.modules.ai.model.domain.po.AiModel;
import com.platform.mesh.ai.biz.soa.store.AiStoreService;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description AI会话
 * @author 蝉鸣
 */
@Service
public class AiKnowledgeDocServiceImpl extends ServiceImpl<AiKnowledgeDocMapper, AiKnowledgeDoc> implements IAiKnowledgeDocService {

    @Autowired
    private AiKnowledgeDocServiceManual aiKnowledgeDocServiceManual;

    /**
     * 功能描述:
     * 〈获取当前AI知识库附件分页信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<AiKnowledgeDocVO>}
     * @author 蝉鸣
     */
    @Override
    public MPage<AiKnowledgeDocVO> selectPage(PageDTO pageDTO) {
        MPage<AiKnowledgeDoc> mPage = MPageUtil.pageEntityToMPage(pageDTO, AiKnowledgeDoc.class);
        return this.getBaseMapper().selectMPage(mPage,pageDTO);
    }

    /**
     * 功能描述: 
     * 〈获取当前AI会话信息〉
     * @param knowledgeDocId knowledgeDocId
     * @return 正常返回:{@link AiKnowledgeDocVO}
     * @author 蝉鸣
     */
    @Override
    public AiKnowledgeDocVO getAiKnowledgeDocById(Long knowledgeDocId) {
        AiKnowledgeDoc aiKnowledgeDoc = this.getById(knowledgeDocId);
        return BeanUtil.copyProperties(aiKnowledgeDoc, AiKnowledgeDocVO.class);
    }

    /**
     * 功能描述:
     * 〈新增AI会话〉
     * @param knowledgeDocDTO knowledgeDocDTO
     * @return 正常返回:{@link AiKnowledgeDocVO}
     * @author 蝉鸣
     */
    @Override
    public AiKnowledgeDocVO addAiKnowledgeDoc(AiKnowledgeDocDTO knowledgeDocDTO) {
        AiKnowledgeDoc aiKnowledgeDoc = new AiKnowledgeDoc();
        BeanUtil.copyProperties(knowledgeDocDTO, aiKnowledgeDoc, ObjFieldUtil.ignoreDefault());
        //保存DB文档信息
        this.save(aiKnowledgeDoc);
        //保存文档分片信息
        aiKnowledgeDocServiceManual.addAiKnowledgeDoc(aiKnowledgeDoc);
        return BeanUtil.copyProperties(aiKnowledgeDoc, AiKnowledgeDocVO.class);
    }

    /**
     * 功能描述:
     * 〈修改AI会话〉
     * @param knowledgeDocDTO knowledgeDocDTO
     * @return 正常返回:{@link AiKnowledgeDocVO}
     * @author 蝉鸣
     */
    @Override
    public AiKnowledgeDocVO editAiKnowledgeDoc(AiKnowledgeDocDTO knowledgeDocDTO) {
        if(ObjectUtil.isEmpty(knowledgeDocDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(AiKnowledgeDocDTO::getId);
            throw AiKnowledgeDocExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        AiKnowledgeDoc aiKnowledgeDoc = BeanUtil.copyProperties(knowledgeDocDTO, AiKnowledgeDoc.class);
        this.updateById(aiKnowledgeDoc);
        //更新文档分片信息先删除后增加
        aiKnowledgeDocServiceManual.deleteAiKnowledgeDoc(aiKnowledgeDoc);
        aiKnowledgeDocServiceManual.addAiKnowledgeDoc(aiKnowledgeDoc);
        return BeanUtil.copyProperties(aiKnowledgeDoc, AiKnowledgeDocVO.class);
    }

    /**
     * 功能描述:
     * 〈删除AI会话〉
     * @param knowledgeDocId knowledgeDocId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteAiKnowledgeDoc(Long knowledgeDocId) {
        AiKnowledgeDoc aiKnowledgeDoc = getById(knowledgeDocId);
        if(ObjectUtil.isEmpty(aiKnowledgeDoc)){
            return Boolean.TRUE;
        }
        //删除分片以及向量
        aiKnowledgeDocServiceManual.deleteAiKnowledgeDoc(aiKnowledgeDoc);
        //删除当前文档
        this.lambdaUpdate().eq(AiKnowledgeDoc::getId,knowledgeDocId).remove();
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈根据知识库ID 获取向量文档〉
     * @param knowledgeId knowledgeId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public List<Document> getKnowledgeDocument(Long knowledgeId, String content) {
        //获取知识库
        AiKnowledge knowledge = aiKnowledgeDocServiceManual.getKnowledgeById(knowledgeId);
        //获取知识库模型
        AiModel aiModel = aiKnowledgeDocServiceManual.getModel(knowledge.getModelId());
        VectorStore vectorStore = aiKnowledgeDocServiceManual.getVectorStore(aiModel, knowledge.getStoreFlag());
        //获取向量数据库
        AiStoreService aiStoreService = aiKnowledgeDocServiceManual.getAiStoreService(knowledge.getStoreFlag());
        //构建搜素表达式
        Filter.Expression filterExpression = aiKnowledgeDocServiceManual.getFilterExpression(knowledgeId);
        return aiStoreService.searchVectorStore(vectorStore,content, NumberConst.NUM_1,0.6,filterExpression);
    }

}
