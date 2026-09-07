package com.platform.mesh.ai.biz.chat.service.manual;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.ai.dashscope.chat.MessageFormat;
import com.alibaba.cloud.ai.dashscope.common.DashScopeApiConstants;
import com.platform.mesh.ai.biz.chat.domain.dto.AiFieldDTO;
import com.platform.mesh.ai.biz.chat.domain.dto.AiImageDTO;
import com.platform.mesh.ai.biz.chat.domain.dto.AiMsgDTO;
import com.platform.mesh.ai.biz.chat.exception.AiChatExceptionEnum;
import com.platform.mesh.ai.biz.modules.ai.agent.domain.po.AiAgent;
import com.platform.mesh.ai.biz.modules.ai.agent.service.IAiAgentService;
import com.platform.mesh.ai.biz.modules.ai.agentklrel.domain.po.AiAgentKlRel;
import com.platform.mesh.ai.biz.modules.ai.agentklrel.service.IAiAgentKlRelService;
import com.platform.mesh.ai.biz.modules.ai.knowledgedoc.service.IAiKnowledgeDocService;
import com.platform.mesh.ai.biz.modules.ai.model.domain.po.AiModel;
import com.platform.mesh.ai.biz.modules.ai.model.service.IAiModelService;
import com.platform.mesh.ai.biz.modules.ai.session.domain.po.AiSession;
import com.platform.mesh.ai.biz.modules.ai.session.service.IAiSessionService;
import com.platform.mesh.ai.biz.modules.ai.sessionhis.domain.po.AiSessionHis;
import com.platform.mesh.ai.biz.modules.ai.sessionhis.service.IAiSessionHisService;
import com.platform.mesh.ai.biz.soa.agent.AiAgentService;
import com.platform.mesh.ai.biz.soa.agent.domain.bo.AgentMsgBO;
import com.platform.mesh.ai.biz.soa.agent.enums.AgentFlagEnum;
import com.platform.mesh.ai.biz.soa.agent.factory.AiAgentFactory;
import com.platform.mesh.ai.biz.soa.base.constant.AiBaseConst;
import com.platform.mesh.ai.biz.soa.model.AiModelService;
import com.platform.mesh.ai.biz.soa.model.enums.ModelFlagEnum;
import com.platform.mesh.ai.biz.soa.model.factory.AiModelFactory;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.api.modules.doc.domain.vo.DocFileVO;
import com.platform.mesh.upms.api.modules.doc.feign.RemoteDocService;
import com.platform.mesh.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.content.Media;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.document.Document;
import org.springframework.ai.image.ImageMessage;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description AI生成
 * @author 蝉鸣
 */
@Service
public class AiChatServiceManual{

    private static final Logger log = LoggerFactory.getLogger(AiChatServiceManual.class);

    @Autowired
    private IAiModelService aiModelService;

    @Autowired
    private AiModelFactory aiModelFactory;

    @Autowired
    private IAiSessionService aiSessionService;

    @Autowired
    private IAiSessionHisService aiSessionHisService;

    @Autowired
    private IAiKnowledgeDocService aiKnowledgeDocService;

    @Autowired
    private IAiAgentService aiAgentService;

    @Autowired
    private AiAgentFactory aiAgentFactory;

    @Autowired
    private IAiAgentKlRelService aiAgentKlRelService;

    @Autowired
    private RemoteDocService remoteDocService;

    /**
     * 功能描述:
     * 〈文本生成〉
     * @param modelId modelId
     * @return 正常返回:{@link AiModel}
     * @author 蝉鸣
     */
    public AiModel getAiModel(Long modelId) {
        AiModel aiModel = aiModelService.getById(modelId);
        if(Objects.isNull(aiModel)){
            throw AiChatExceptionEnum.ADD_NO_MODEL.getBaseException();
        }
        return aiModel;
    }

    /**
     * 功能描述:
     * 〈获取模型服务〉
     * @param aiModel aiModel
     * @return 正常返回:{@link AiModelService}
     * @author 蝉鸣
     */
    public AiModelService getAiModelService(AiModel aiModel) {
        //获取模型工厂
        ModelFlagEnum enumByValue = BaseEnum.getEnumByValue(ModelFlagEnum.class, aiModel.getModelFlag());
        //获取模型服务
        AiModelService modelService = aiModelFactory.getAiModelService(enumByValue);
        if(Objects.isNull(modelService)){
            throw AiChatExceptionEnum.ADD_NO_MODEL_SERVICE.getBaseException();
        }
        return modelService;
    }

    /**
     * 功能描述:
     * 〈获取OCR消息转换器〉
     * @return 正常返回:{@link BeanOutputConverter<T>}
     * @author 蝉鸣
     */
    public <T> BeanOutputConverter<T> getOutputConverter() {
        ParameterizedTypeReference<T> parameterizedTypeReference = new ParameterizedTypeReference<>() {
        };
        return new BeanOutputConverter<>(parameterizedTypeReference);
    }

    /**
     * 功能描述:
     * 〈获取文本消息体〉
     * @param msgDTO msgDTO
     * @return 正常返回:{@link List<Message>}
     * @author 蝉鸣
     */
    public List<Message> genChatMessages(AiMsgDTO msgDTO) {
        //消息合集
        List<Message> chatMessages = CollUtil.newArrayList();
        // 仅添加有效系统提示，避免空消息干扰模型的工具选择。
        if (StrUtil.isNotBlank(msgDTO.getPrompt())) {
            chatMessages.add(new SystemMessage(msgDTO.getPrompt()));
        }
        // 只有明确启用上下文时才加载历史，实时业务查询不复用旧数据。
        if (Objects.equals(msgDTO.getContextFlag(), YesOrNoEnum.YES.getValue())) {
            List<Message> hisMessages = this.getSessionHis(msgDTO.getSessionId());
            chatMessages.addAll(hisMessages);
        }
        //当前消息
        chatMessages.add(new UserMessage(msgDTO.getContent()));
        if(CollUtil.isEmpty(msgDTO.getKnowledgeIds())){
            return chatMessages;
        }
        //知识库消息
        List<Long> klIds = this.getKlIds(msgDTO.getSessionId());
        if(CollUtil.isNotEmpty(klIds)){
            msgDTO.getKnowledgeIds().addAll(klIds);
        }
        List<Message> knowledgeMessages = this.getKnowledge(msgDTO.getKnowledgeIds(),msgDTO.getContent());
        chatMessages.addAll(knowledgeMessages);
        return chatMessages;
    }

    /**
     * 功能描述:
     * 〈获取图片消息体〉
     * @param msgDTO msgDTO
     * @return 正常返回:{@link List<Message>}
     * @author 蝉鸣
     */
    public List<ImageMessage> genImageMessages(AiImageDTO msgDTO) {
        //消息合集
        List<ImageMessage> imageMessages = CollUtil.newArrayList();
        //当前消息
        imageMessages.add(new ImageMessage(msgDTO.getPrompt()));
        return imageMessages;
    }

    /**
     * 功能描述:
     * 〈异步生成图片〉
     * @param imageModel  imageModel
     * @param imagePrompt imagePrompt
     * @author 蝉鸣
     */
    @Async
    public CompletableFuture<String> getImageAsync(ImageModel imageModel, ImagePrompt imagePrompt) {
        try {
            //生成对象
            ImageResponse response = imageModel.call(imagePrompt);
            if (response.getResult() == null) {
                throw new IllegalArgumentException("生成结果为空");
            }
            // 2. 上传到文件服务
            String url = response.getResult().getOutput().getUrl();
            byte[] fileContent = StrUtil.isNotEmpty(url) ? Base64.decode(url)
                    : HttpUtil.downloadBytes(response.getResult().getOutput().getUrl());
            log.info("生成内容{}", url);
            return CompletableFuture.completedFuture(url);
        } catch (Exception ex) {
            log.error("生成异常", ex);
        }
        return CompletableFuture.completedFuture(null);
    }

    /**
     * 功能描述:
     * 〈获取OCR消息体〉
     * @param msgDTO msgDTO
     * @return 正常返回:{@link List<Message>}
     * @author 蝉鸣
     */
    public List<Message> genOcrMessages(AiMsgDTO msgDTO) {
        //消息合集
        List<Message> chatMessages = CollUtil.newArrayList();
        URI uri;
        try {
             uri = new URI("https://static.twelvet.cn/ai/ocr.jpg").toURL().toURI();
        }catch (Exception ex){
            log.error("无效的解析图片地址");
            return chatMessages;
        }
        if(ObjectUtil.isNull(uri)){
            return chatMessages;
        }
        List<Media> mediaList = List
                .of(new Media(MimeTypeUtils.IMAGE_JPEG,uri));
        // 编写ocr识别提示词
        String ocrContent = String.format("""
					读取发票中的金额和发票编号等关键信息
					%s
					""", "");
//        String ocrContent = String.format("""
//					读取发票中的金额和发票编号等关键信息
//					%s
//					""", converter.getFormat());
        // 设置媒体文件消息
        UserMessage userMessage = UserMessage.builder()
                .text(msgDTO.getContent())
                .media(mediaList)
                .metadata(new HashMap<>())
                .build();
        // 设置文件格式
        userMessage.getMetadata().put(DashScopeApiConstants.MESSAGE_FORMAT, MessageFormat.IMAGE);
        chatMessages.add(userMessage);
        return chatMessages;
    }

    /**
     * 功能描述:
     * 〈获取OCR消息体〉
     * @param fieldDTO fieldDTO
     * @return 正常返回:{@link List<Message>}
     * @author 蝉鸣
     */
    public List<Message> genOcrMessagesWithField(AiFieldDTO fieldDTO) {
        //消息合集
        List<Message> chatMessages = CollUtil.newArrayList();
        List<Media> mediaList = CollUtil.newArrayList();
        try {
            List<DocFileVO> fileVOS = remoteDocService.getDocFiles(fieldDTO.getFileIds()).getData();
            for (DocFileVO fileVO : fileVOS) {
                URI uri = new URI(fileVO.getFileUrl()).toURL().toURI();
                mediaList.add(new Media(MimeTypeUtils.IMAGE_JPEG,uri));
            }
        }catch (Exception ex){
            log.error("无效的解析图片地址");
            return chatMessages;
        }
        if(CollUtil.isEmpty(mediaList)){
            return chatMessages;
        }
        // 设置媒体文件消息
        UserMessage userMessage = UserMessage.builder()
                .text(fieldDTO.getPrompt())
                .media(mediaList)
                .metadata(new HashMap<>())
                .build();
        // 设置文件格式
        userMessage.getMetadata().put(DashScopeApiConstants.MESSAGE_FORMAT, MessageFormat.IMAGE);
        chatMessages.add(userMessage);
        return chatMessages;
    }

    /**
     * 功能描述:
     * 〈获取文字转语音消息体〉
     * @return 正常返回:{@link TextToSpeechPrompt}
     * @author 蝉鸣
     */
    public TextToSpeechPrompt genTextToSpeechPrompt(AiMsgDTO msgDTO) {
        return new TextToSpeechPrompt(msgDTO.getContent());
    }

    /**
     * 功能描述:
     * 〈获取会话〉
     * @return 正常返回:{@link AiSession}
     * @author 蝉鸣
     */
    public AiSession getAiSession(Long sessionId) {
        AiSession aiSession = aiSessionService.getById(sessionId);
        if(Objects.isNull(aiSession)){
            throw AiChatExceptionEnum.ADD_NO_SESSION.getBaseException();
        }
        return aiSession;
    }

    /**
     * 功能描述:
     * 〈保存回家历史记录信息〉
     * @return 正常返回:{@link List<Message>}
     * @author 蝉鸣
     */
    public List<Message> getSessionHis(Long sessionId) {
        List<Message> hisMessages = CollUtil.newArrayList();
        MPage<AiSessionHis> mPage = new MPage<>();
        mPage.setSize(NumberConst.NUM_10);
        MPage<AiSessionHis> hisMPage = aiSessionHisService.lambdaQuery()
                .eq(AiSessionHis::getSessionId, sessionId)
                .orderByDesc(AiSessionHis::getCreateTime)
                .page(mPage);
        List<AiSessionHis> pageRecords = hisMPage.getRecords();
        if(CollUtil.isEmpty(pageRecords)){
            return hisMessages;
        }
        // 反转列表,使得最新的消息在最后
        CollUtil.reverse(pageRecords);
        pageRecords.forEach(his -> {
            if(ObjectUtil.isNotEmpty(his.getRequireParams())){
                hisMessages.add(new UserMessage(his.getRequireParams()));
            }
            if(ObjectUtil.isNotEmpty(his.getResponseMsg())){
                hisMessages.add(new AssistantMessage(his.getResponseMsg()));
            }
        });
        return hisMessages;
    }
    /**
     * 功能描述:
     * 〈保存回家历史记录信息〉
     * @author 蝉鸣
     */
    public void addSessionHis(AiSession aiSession, AiMsgDTO msgDTO, String newContent) {
        //组装历史数据
        AiSessionHis aiSessionHis = new AiSessionHis();
        aiSessionHis.setSessionId(aiSession.getId());
        aiSessionHis.setModelName(aiSession.getModelName());
        aiSessionHis.setModelFlag(aiSession.getModelFlag());
        aiSessionHis.setModelType(aiSession.getModelType());
        aiSessionHis.setRequirePrompt(msgDTO.getPrompt());
        aiSessionHis.setRequireParams(fitSessionHistory(msgDTO.getContent()));
        aiSessionHis.setResponseMsg(newContent);
        aiSessionHis.setCreateUserId(aiSession.getCreateUserId());
        aiSessionHis.setCreateTime(LocalDateTime.now());
        aiSessionHis.setUpdateUserId(aiSession.getUpdateUserId());
        aiSessionHis.setUpdateTime(LocalDateTime.now());
        aiSessionHis.setScopeUserId(aiSession.getScopeUserId());
        aiSessionHis.setScopeOrgId(aiSession.getScopeOrgId());
        aiSessionHisService.save(aiSessionHis);
    }

    /**
     * 功能描述:
     * 〈将用户请求裁剪到会话历史字段允许的长度〉
     *
     * <p>完整请求仍用于本次模型及工具调用，这里仅约束持久化内容，避免超长的
     * 工具参数或业务描述导致会话历史写入失败。</p>
     *
     * @param content 用户原始请求
     * @return 可安全写入会话历史的请求摘要
     * @author qingfeng
     */
    static String fitSessionHistory(String content) {
        return StrUtil.subPre(content, 255);
    }

    /**
     * 功能描述:
     * 〈获取知识库消息〉
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    public List<Long> getKlIds(Long sessionId) {
        //查询会话记录
        AiSession aiSession = getAiSession(sessionId);
        if(ObjectUtil.isEmpty(aiSession)){
            return CollUtil.newArrayList();
        }
        Long agentId = aiSession.getAgentId();
        List<AiAgentKlRel> klRels = aiAgentKlRelService.lambdaQuery().eq(AiAgentKlRel::getAgentId,agentId).list();
        if(CollUtil.isEmpty(klRels)){
            return CollUtil.newArrayList();
        }
        return klRels.stream().map(AiAgentKlRel::getKlId).distinct().toList();
    }

    /**
     * 功能描述:
     * 〈获取知识库消息〉
     * @return 正常返回:{@link List<Message>}
     * @author 蝉鸣
     */
    public List<Message> getKnowledge(List<Long> knowledgeIds, String content) {
        //查询符合条件的知识库
        //使用向量存储模型查询信息
        List<Document> documents = new ArrayList<>();
        for (Long knowledgeId : knowledgeIds) {
            documents.addAll(aiKnowledgeDocService.getKnowledgeDocument(knowledgeId,content));
        }
        if (ObjectUtil.isEmpty(content)) {
            return CollUtil.newArrayList();
        }
        //构建上下文
        String context = getContext(documents);
        return CollUtil.newArrayList(new UserMessage(context));
    }

    /**
     * 功能描述:
     * 〈获取知识库文档构建上下文〉
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public String getContext(List<Document> documents) {
        //构建上下文
        String reference = documents.stream()
                .map(doc -> String.format(AiBaseConst.KNOWLEDGE_MESSAGE_REFERENCE, doc.getId(), doc.getText()))
                .collect(Collectors.joining(SymbolConst.NEW_LINE_1));
        return String.format(AiBaseConst.KNOWLEDGE_MESSAGE_TEMPLATE, reference);
    }

    /**
     * 功能描述:
     * 〈获取智能体〉
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public AiAgent getAiAgentById(Long agentId) {
        return aiAgentService.getAiAgentBOById(agentId);
    }

    /**
     * 功能描述:
     * 〈获取知识库文档构建上下文〉
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public Flux<Result<String>> callByAgentStream(AiMsgDTO msgDTO) {
        AiAgent aiAgent = aiAgentService.getAiAgentBOById(msgDTO.getSessionId());
        if(ObjectUtil.isEmpty(aiAgent)){
            return Flux.just();
        }
        //智能体问答
        AgentFlagEnum enumByValue = BaseEnum.getEnumByValue(AgentFlagEnum.class, aiAgent.getAgentFlag());
        if(ObjectUtil.isEmpty(enumByValue)){
            return Flux.just();
        }
        AiAgentService aiAgentService = aiAgentFactory.getAiAgentService(enumByValue);
        AgentMsgBO agentMsgBO = new AgentMsgBO();
        agentMsgBO.setPrompt(msgDTO.getPrompt());
        agentMsgBO.setContent(msgDTO.getContent());
        agentMsgBO.setMessages(msgDTO.getMessages());
        return aiAgentService.chatByAiAgentStream(aiAgent,agentMsgBO);
    }

    /**
     * 功能描述:
     * 〈获取知识库文档构建上下文〉
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public String callByAgent(AiMsgDTO msgDTO) {
        AiAgent aiAgent = aiAgentService.getAiAgentBOById(msgDTO.getSessionId());
        if(ObjectUtil.isEmpty(aiAgent)){
            return SymbolConst.BLANK;
        }
        //智能体问答
        AgentFlagEnum enumByValue = BaseEnum.getEnumByValue(AgentFlagEnum.class, aiAgent.getAgentFlag());
        if(ObjectUtil.isEmpty(enumByValue)){
            return SymbolConst.BLANK;
        }
        AiAgentService aiAgentService = aiAgentFactory.getAiAgentService(enumByValue);
        AgentMsgBO agentMsgBO = new AgentMsgBO();
        agentMsgBO.setPrompt(msgDTO.getPrompt());
        agentMsgBO.setContent(msgDTO.getContent());
        agentMsgBO.setMessages(msgDTO.getMessages());
        return aiAgentService.chatByAiAgent(aiAgent,agentMsgBO);
    }

    /**
     * 功能描述:
     * 〈组装字段映射信息〉
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public Message getSysMessages(Map<String, String> columnMap) {
        Collection<String> values = columnMap.values();
        StringBuilder builder = StrUtil.builder();
        builder.append("## 工作步骤");
        builder.append("1.对话内容进行挖掘和提取关键信息，需要挖掘和提取的关键字段有:");
        for (String value : values) {
            builder.append("-").append(value).append("-");
        }
        builder.append("2.按照以下对应字段信息进行输出JSON:");
        builder.append(JSONUtil.toJsonStr(columnMap));
        return new SystemMessage(builder.toString());
    }

}
