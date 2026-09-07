package com.platform.mesh.ai.biz.soa.base.constant;

/**
 * @description AI基本变量
 * @author 蝉鸣
 */
public interface AiBaseConst {

//-- --------------------------------------------------------
//        -- AI字段
//-- --------------------------------------------------------
    String SPRING_AI = "spring.ai";

    String MESH_AI = "mesh.ai";


//-- --------------------------------------------------------
//        -- AI知识库
//-- --------------------------------------------------------
    String VECTOR_STORE_METADATA_KNOWLEDGE_ID = "ai_knowledge_id";

    String VECTOR_STORE_METADATA_KNOWLEDGE_DOC_ID = "ai_knowledge_doc_id";

    String VECTOR_STORE_METADATA_KNOWLEDGE_SLICE_ID = "ai_knowledge_slice_id";

//-- --------------------------------------------------------
//        -- AI业务提示词
//-- --------------------------------------------------------
    String KNOWLEDGE_MESSAGE_TEMPLATE =  """
                使用 <Reference></Reference> 标记中的内容作为本次对话的参考:
                %s
                回答要求：
                避免提及你是从 <Reference></Reference> 获取的知识。
                """;

    String KNOWLEDGE_MESSAGE_REFERENCE = """
                <Reference>
                [来源: %s]
                %s
                </Reference>
                """;

}
