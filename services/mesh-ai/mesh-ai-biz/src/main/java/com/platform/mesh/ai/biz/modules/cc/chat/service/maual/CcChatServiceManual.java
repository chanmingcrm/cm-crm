package com.platform.mesh.ai.biz.modules.cc.chat.service.maual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.ai.biz.modules.ai.agent.domain.po.AiAgent;
import com.platform.mesh.ai.biz.modules.ai.agent.service.IAiAgentService;
import com.platform.mesh.ai.biz.modules.cc.chat.exception.CcChatExceptionEnum;
import com.platform.mesh.ai.biz.modules.cc.chat.domain.AiReplyStreamBuffer;
import com.platform.mesh.ai.biz.modules.cc.group.domain.po.CcGroup;
import com.platform.mesh.ai.biz.modules.cc.group.enums.CcGroupStatusEnum;
import com.platform.mesh.ai.biz.modules.cc.group.enums.GroupTypeEnum;
import com.platform.mesh.ai.biz.modules.cc.group.service.ICcGroupService;
import com.platform.mesh.ai.biz.modules.cc.groupuserrel.domain.po.CcGroupUserRel;
import com.platform.mesh.ai.biz.modules.cc.groupuserrel.service.ICcGroupUserRelService;
import com.platform.mesh.ai.biz.modules.cc.msg.domain.po.CcSessionMsg;
import com.platform.mesh.ai.biz.modules.cc.msg.service.ICcSessionMsgService;
import com.platform.mesh.ai.biz.modules.cc.setword.domain.po.CcSetWord;
import com.platform.mesh.ai.biz.modules.cc.setword.enums.WordFlagEnum;
import com.platform.mesh.ai.biz.modules.cc.setword.service.ICcSetWordService;
import com.platform.mesh.ai.biz.modules.cc.user.domain.vo.CcUserVO;
import com.platform.mesh.ai.biz.modules.cc.user.enums.UserTypeEnum;
import com.platform.mesh.ai.biz.modules.cc.userworkrel.service.ICcUserWorkRelService;
import com.platform.mesh.ai.biz.modules.cc.webset.domain.po.CcWebSet;
import com.platform.mesh.ai.biz.modules.cc.webset.service.ICcWebSetService;
import com.platform.mesh.ai.biz.soa.agent.AiAgentService;
import com.platform.mesh.ai.biz.soa.agent.domain.bo.AgentMsgBO;
import com.platform.mesh.ai.biz.soa.agent.enums.AgentFlagEnum;
import com.platform.mesh.ai.biz.soa.agent.factory.AiAgentFactory;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.crm.api.modules.crm.feign.RemoteCrmService;
import com.platform.mesh.mybatis.plus.enums.MateFillEnum;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;
import com.platform.mesh.netty.server.constant.ChannelConst;
import com.platform.mesh.netty.server.domain.bo.CcMsgBO;
import com.platform.mesh.netty.server.domain.bo.CcMsgVO;
import com.platform.mesh.netty.server.domain.bo.CcAiStreamVO;
import com.platform.mesh.netty.server.enums.CcMsgNoticeEnum;
import com.platform.mesh.netty.server.soa.msg.factory.CcChannelFactory;
import com.platform.mesh.utils.format.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description AIMcp
 * @author 蝉鸣
 */
@Service
public class CcChatServiceManual {

    private static final Logger log = LoggerFactory.getLogger(CcChatServiceManual.class);

    private static final String AI_USER_HASH_PREFIX = "ai:";

    private static final String AI_FALLBACK_USER_HASH = AI_USER_HASH_PREFIX + "0";

    @Autowired
    private ICcGroupService ccGroupService;

    @Autowired
    private ICcGroupUserRelService ccGroupUserRelService;

    @Autowired
    private ICcSessionMsgService ccSessionMsgService;

    @Autowired
    private CcAiConversationHistory ccAiConversationHistory;

    @Autowired
    private IAiAgentService aiAgentService;

    @Autowired
    private AiAgentFactory aiAgentFactory;

    @Autowired
    private CcChannelFactory ccChannelFactory;

    @Autowired
    private ICcUserWorkRelService ccUserWorkRelService;

    @Autowired
    private ICcSetWordService ccSetWordService;

    @Autowired
    private ICcWebSetService ccWebSetService;

    @Autowired
    private RemoteCrmService remoteCrmService;

    @Value("${mesh.cc.ai-stream.enabled-web-set-ids:}")
    private String aiStreamEnabledWebSetIds;

    @Value("${mesh.cc.ai-stream.total-timeout-seconds:60}")
    private long aiStreamTimeoutSeconds;


    /**
     * 功能描述:
     * 〈创建房间〉
     * @param ccMsgBO ccMsgBO
     * @author 蝉鸣
     */
    public void createGroupSave(CcMsgBO ccMsgBO) {
        DataScopeHandler.setEnableDataScope(Boolean.FALSE);
        try {
            //查询当前房间是否存在
            boolean existed = ccGroupService.lambdaQuery().eq(CcGroup::getGroupHash, ccMsgBO.getGroupHash()).exists();
            if(existed){
                ccGroupService.setStatus(CcGroupStatusEnum.CHATTING.getValue(), ccMsgBO.getGroupHash());
                refreshGroupVisitorInfo(ccMsgBO);
                return;
            }
            //创建组
            LocalDateTime dateTime = LocalDateTime.now();
            CcGroup ccGroup = new CcGroup();
            ccGroup.setGroupType(GroupTypeEnum.TEMP.getValue());
            ccGroup.setGroupHash(ccMsgBO.getGroupHash());
            ccGroup.setGroupName(StrUtil.blankToDefault(ccMsgBO.getVisitorName(), DateTimeUtil.localDateTimeToStr(dateTime)));
            ccGroup.setStatus(CcGroupStatusEnum.CHATTING.getValue());
            ccGroup.setSource(StrUtil.blankToDefault(ccMsgBO.getSource(), "官网"));
            ccGroup.setSourcePage(ccMsgBO.getSourcePage());
            ccGroup.setVisitorName(ccMsgBO.getVisitorName());
            ccGroup.setVisitorContact(ccMsgBO.getVisitorContact());
            ccGroup.setVisitorCompany(ccMsgBO.getVisitorCompany());
            ccGroup.setVisitorDemand(ccMsgBO.getVisitorDemand());
            ccGroup.setLastMsgAt(dateTime);
            ccGroup.setLeadStatus(0);
            ccGroup.setCreateTime(dateTime);
            ccGroupService.save(ccGroup);

            //初始化人员为当前群创建人
            CcGroupUserRel ccGroupUserRel = new CcGroupUserRel();
            ccGroupUserRel.setGroupId(ccGroup.getId());
            ccGroupUserRel.setGroupHash(ccGroup.getGroupHash());
            ccGroupUserRel.setGroupName(ccGroup.getGroupName());
            ccGroupUserRel.setGroupType(ccGroup.getGroupType());
            ccGroupUserRel.setUserHash(ccMsgBO.getUserHash());
            ccGroupUserRel.setUserName(ccMsgBO.getUserHash());
            ccGroupUserRel.setUserType(UserTypeEnum.INIT.getValue());
            ccGroupUserRel.setCreateTime(LocalDateTime.now());
            ccGroupUserRelService.save(ccGroupUserRel);
        } finally {
            DataScopeHandler.unEnableDataScope();
        }
    }

    /**
     * 功能描述:
     * 〈提醒客户端更新群信息〉
     * @param ccMsgBO ccMsgBO
     * @author 蝉鸣
     */
    public void createGroupBack(CcMsgBO ccMsgBO) {
        //向客户端发送更新群信息
        CcMsgVO ccMsgVO = new CcMsgVO();
        ccMsgVO.setNoticeType(CcMsgNoticeEnum.SYSTEM_GROUP.getValue());
//        ccMsgVO.setGroupHash(ChannelConst.COMMON_CHANNEL);
        ccMsgVO.setGroupHash(ccMsgBO.getGroupHash());
        // 向所有用户发送房间列表
        ccChannelFactory.sendGroupMsg(ccMsgVO.getGroupHash(),ccMsgVO);
        backToCommonForGroupChanged(ccMsgBO);
    }

    /**
     * 功能描述:
     * 〈提醒客服端刷新会话列表〉
     * @param ccMsgBO ccMsgBO
     * @author Codex
     */
    public void backToCommonForGroupChanged(CcMsgBO ccMsgBO) {
        if(ObjectUtil.isEmpty(ccMsgBO.getGroupHash())){
            return;
        }
        CcMsgVO ccMsgVO = new CcMsgVO();
        ccMsgVO.setNoticeType(CcMsgNoticeEnum.SYSTEM_GROUP.getValue());
        ccMsgVO.setGroupHash(ChannelConst.COMMON_CHANNEL);
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.set(ChannelConst.GROUP_INFO, ccMsgBO.getGroupHash());
        ccMsgVO.setData(jsonObject);
        ccChannelFactory.sendGroupMsg(ccMsgVO.getGroupHash(),ccMsgVO);
    }

    /**
     * 功能描述:
     * 〈加入房间〉
     * @param ccMsgBO ccMsgBO
     * @author 蝉鸣
     */
    public void joinGroupSave(CcMsgBO ccMsgBO) {
        CcGroup ccGroup = ccGroupService.getCcGroupByHash(ccMsgBO.getGroupHash());
        CcGroupUserRel ccGroupUserRel = BeanUtil.copyProperties(ccGroup, CcGroupUserRel.class);
        ccGroupUserRel.setGroupId(ccGroup.getId());
        ccGroupUserRel.setUserName(ccMsgBO.getUserHash());
        ccGroupUserRel.setUserHash(ccMsgBO.getUserHash());
        ccGroupUserRel.setUserType(ccMsgBO.getUserType());
        ccGroupUserRel.setCreateTime(LocalDateTime.now());
        
        boolean existed = ccGroupUserRelService.lambdaQuery()
                .eq(CcGroupUserRel::getGroupHash, ccGroupUserRel.getGroupHash())
                .eq(CcGroupUserRel::getUserHash, ccGroupUserRel.getUserHash())
                .exists();
        if(existed){
            ccGroupUserRelService.lambdaUpdate()
                    .set(CcGroupUserRel::getLastVisitTime, LocalDateTime.now())
                    .eq(CcGroupUserRel::getGroupHash,ccGroupUserRel.getGroupHash())
                    .eq(CcGroupUserRel::getUserHash, ccGroupUserRel.getUserHash())
                    .update();
			ccGroupService.setStatus(CcGroupStatusEnum.CHATTING.getValue(), ccGroupUserRel.getGroupHash());
		} else {
			ccGroupUserRelService.save(ccGroupUserRel);
		}
        
    }

    /**
     * 功能描述:
     * 〈加入房间响应〉
     * @param ccMsgBO ccMsgBO
     * @author 蝉鸣
     */
    public void joinGroupBack(CcMsgBO ccMsgBO) {
        // 通知用户加入成功
        CcMsgVO ccMsgVO = new CcMsgVO();
        ccMsgVO.setNoticeType(CcMsgNoticeEnum.SYSTEM_JOIN.getValue());
        ccMsgVO.setGroupHash(ccMsgBO.getGroupHash());
        ccMsgVO.setUserHash(ccMsgBO.getUserHash());
        ccChannelFactory.sendGroupMsg(ccMsgBO.getGroupHash(),ccMsgVO);
        if(!UserTypeEnum.INIT.getValue().equals(ccMsgBO.getUserType())){
            return;
        }
        //获取分配的智能客服
        CcUserVO nextCcUser = ccUserWorkRelService.getNextCcUser(UserTypeEnum.AI.getValue());
        if(Objects.isNull(nextCcUser)){
            //提示客服进行留言
            backToCustomerForLeave(ccMsgBO);
            backToSystemForUnassignedNotice(ccMsgBO);
        }else{
            //加入客服人员并返回欢迎提示语信息
            backToCustomerForServer(ccMsgBO,nextCcUser);
        }
    }

    /**
     * 功能描述:
     * 〈消息持久化处理〉
     * @param ccMsgBO ccMsgBO
     * @author 蝉鸣
     */
    public Long msgSendSave(CcMsgBO ccMsgBO) {
        
        CcSessionMsg ccSessionMsg = BeanUtil.copyProperties(ccMsgBO, CcSessionMsg.class);
        ccSessionMsg.setGroupType(GroupTypeEnum.TEMP.getValue());
        ccSessionMsg.setUserType(ccMsgBO.getUserType());
        ccSessionMsg.setCreateTime(LocalDateTime.now());
        ccSessionMsgService.save(ccSessionMsg);
        return ccSessionMsg.getId();
        
    }

    /**
     * 功能描述:
     * 〈消息回复〉
     * @param ccMsgBO ccMsgBO
     * @author 蝉鸣
     */
    public void msgSendBack(CcMsgBO ccMsgBO, Long currentMessageId) {
        //查询当房间是否存在
        CcGroup ccGroup = ccGroupService.getCcGroupByHash(ccMsgBO.getGroupHash());
        if(!UserTypeEnum.INIT.getValue().equals(ccMsgBO.getUserType())){
            return;
        }
        //获取当前群信息获取回复类型
        UserTypeEnum enumByValue = BaseEnum.getEnumByValue(UserTypeEnum.class, ccGroup.getReplyType(), UserTypeEnum.AI);
        //如果当前是人工回复,则无需处理
        if(enumByValue.equals(UserTypeEnum.HUMAN)){
            //提醒当前客服人员消息
            humanMsgNoice(ccMsgBO);
            return;
        }
        //如果是智能体回复
        if(msgNeedHuman(ccMsgBO.getMsgContent())){
            //消息提醒需要人工回复
            humanMsg(ccMsgBO);
        }else{
            //智能体回复
            aiAgentMsg(ccMsgBO, currentMessageId);
        }

    }

    /**
     * 功能描述:
     * 〈消息回复〉
     * @param wordFlag wordFlag
     * @author 蝉鸣
     */
    public List<CcSetWord> getSetWordList(Integer wordFlag) {
        
        //获取关键词
        return ccSetWordService.lambdaQuery()
                .eq(CcSetWord::getWordFlag, wordFlag)
                .list();
    }

    /**
     * 功能描述:
     * 〈消息回复〉
     * @param content content
     * @param tenantId tenantId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public Boolean msgNeedHuman(String content) {
        //获取关键词
        List<CcSetWord> wordList = getSetWordList(WordFlagEnum.HUMAN_WORD.getValue());
        if(CollUtil.isEmpty(wordList)){
            return Boolean.FALSE;
        }
        return wordList.stream()
                .map(CcSetWord::getWordContent)
                .filter(StrUtil::isNotBlank)
                .anyMatch(word -> StrUtil.contains(content, StrUtil.trim(word)));
    }

    /**
     * 功能描述:
     * 〈离开房间持久化处理〉
     * @param ccMsgBO ccMsgBO
     * @author Codex
     */
    public void leaveGroupSave(CcMsgBO ccMsgBO) {
        if(ObjectUtil.isEmpty(ccMsgBO.getGroupHash())){
            return;
        }
        if(ObjectUtil.isEmpty(ccMsgBO.getUserType()) || UserTypeEnum.INIT.getValue().equals(ccMsgBO.getUserType())){
            ccGroupService.setStatus(CcGroupStatusEnum.OFFLINE.getValue(), ccMsgBO.getGroupHash());
        }
    }

    /**
     * 功能描述:
     * 〈智能体消息回复〉
     * @param ccMsgBO ccMsgBO
     * @author 蝉鸣
     */
    public void aiAgentMsg(CcMsgBO ccMsgBO, Long currentMessageId) {
        //获取AiAgent
        AiAgent aiAgent = getAiAgentForGroup(ccMsgBO);
        if(ObjectUtil.isNull(aiAgent)){
            //如果没有智能体，只提示AI暂不可用，不自动切换人工。
            backToGroupForAiUnavailable(ccMsgBO, null);
            return;
        }
        //智能体问答
        AgentFlagEnum enumByValue = BaseEnum.getEnumByValue(AgentFlagEnum.class, aiAgent.getAgentFlag());
        if(ObjectUtil.isEmpty(enumByValue)){
            backToGroupForAiUnavailable(ccMsgBO, aiAgent);
            return;
        }
        AiAgentService aiAgentService = aiAgentFactory.getAiAgentService(enumByValue);
        if(ObjectUtil.isEmpty(aiAgentService)){
            backToGroupForAiUnavailable(ccMsgBO, aiAgent);
            return;
        }
        List<Message> history = ccAiConversationHistory.load(ccMsgBO, currentMessageId);
        if(isAiStreamEnabled(ccMsgBO)){
            streamAiAgentMsg(ccMsgBO, aiAgent, aiAgentService, history);
            return;
        }
        AgentMsgBO msgBO = new AgentMsgBO();
        msgBO.setUserHash(ccMsgBO.getUserHash());
        msgBO.setContent(ccMsgBO.getMsgContent());
        msgBO.setMessages(history);
        String ccByAiAgent;
        try {
            ccByAiAgent = aiAgentService.chatByAiAgent(aiAgent, msgBO);
        } catch (Exception e) {
            log.error("AI客服回复失败，groupHash={}, agentId={}", ccMsgBO.getGroupHash(), aiAgent.getId(), e);
            backToGroupForAiMsg(ccMsgBO, aiAgent, CcChatExceptionEnum.AI_UNAVAILABLE_MESSAGE.getDesc());
            return;
        }
        if(ObjectUtil.isEmpty(ccByAiAgent)){
            log.warn("AI客服未返回内容，groupHash={}, agentId={}", ccMsgBO.getGroupHash(), aiAgent.getId());
            backToGroupForAiMsg(ccMsgBO, aiAgent, CcChatExceptionEnum.AI_UNAVAILABLE_MESSAGE.getDesc());
            return;
        }
        if(JSONUtil.isTypeJSON(ccByAiAgent)){
            //保存线索信息到crm线索中
            JSONObject jsonObject = JSONUtil.parseObj(ccByAiAgent);
            ccByAiAgent = jsonObject.get("context").toString();
            Object object = jsonObject.get("data");
            if(ObjectUtil.isNotEmpty(object)){
                JSONObject data = JSONUtil.parseObj(object);
                remoteCrmService.addCrmDrainage(JSONUtil.toJsonStr(data));
            }
        }
        backToGroupForAiMsg(ccMsgBO, aiAgent, ccByAiAgent);
    }

    void streamAiAgentMsg(CcMsgBO ccMsgBO, AiAgent aiAgent, AiAgentService aiAgentService,
                          List<Message> history) {
        String streamId = UUID.randomUUID().toString();
        String aiUserHash = AI_USER_HASH_PREFIX + aiAgent.getId();
        AgentMsgBO request = new AgentMsgBO()
                .setUserHash(ccMsgBO.getUserHash())
                .setContent(ccMsgBO.getMsgContent())
                .setMessages(history);
        AiReplyStreamBuffer buffer = new AiReplyStreamBuffer();
        AtomicInteger sequence = new AtomicInteger();
        AtomicInteger chunkCount = new AtomicInteger();
        AtomicLong firstChunkAt = new AtomicLong();
        long startedAt = System.currentTimeMillis();

        sendAiStreamEvent(ccMsgBO, aiUserHash, CcMsgNoticeEnum.AI_STREAM_START,
                new CcAiStreamVO().setStreamId(streamId));
        aiAgentService.chatByAiAgentStream(aiAgent, request)
                .timeout(Duration.ofSeconds(aiStreamTimeoutSeconds > 0 ? aiStreamTimeoutSeconds : 60))
                .subscribe(result -> {
                    String delta = ObjectUtil.isNull(result) ? null : result.getData();
                    String visibleDelta = buffer.append(delta);
                    if(ObjectUtil.isNotEmpty(visibleDelta)){
                        firstChunkAt.compareAndSet(0, System.currentTimeMillis());
                        chunkCount.incrementAndGet();
                        sendAiStreamChunk(ccMsgBO, aiUserHash, streamId, sequence.incrementAndGet(), visibleDelta);
                    }
                }, error -> {
                    String status = error instanceof TimeoutException ? "timeout" : "error";
                    String message = error instanceof TimeoutException
                            ? "回复超时，请稍后重试"
                            : CcChatExceptionEnum.AI_UNAVAILABLE_MESSAGE.getDesc();
                    sendAiStreamEnd(ccMsgBO, aiUserHash, streamId, status, message);
                    log.warn("AI客服流失败，groupHash={}, agentId={}, streamId={}, type={}, elapsedMs={}",
                            maskGroupHash(ccMsgBO.getGroupHash()), aiAgent.getId(), streamId,
                            error.getClass().getSimpleName(), System.currentTimeMillis() - startedAt);
                }, () -> finishAiStream(ccMsgBO, aiAgent, aiUserHash, streamId, buffer,
                        sequence, chunkCount, firstChunkAt.get(), startedAt));
    }

    private void finishAiStream(CcMsgBO ccMsgBO, AiAgent aiAgent, String aiUserHash, String streamId,
                                AiReplyStreamBuffer buffer, AtomicInteger sequence, AtomicInteger chunkCount,
                                long firstChunkAt, long startedAt) {
        AiReplyStreamBuffer.Result finalResult = buffer.finish();
        if(!finalResult.valid()){
            sendAiStreamEnd(ccMsgBO, aiUserHash, streamId, "error",
                    CcChatExceptionEnum.AI_UNAVAILABLE_MESSAGE.getDesc());
            return;
        }
        if(finalResult.structured()){
            if(ObjectUtil.isNotEmpty(finalResult.drainageData())){
                JSONObject data = finalResult.drainageData();
                try {
                    remoteCrmService.addCrmDrainage(JSONUtil.toJsonStr(data));
                } catch (Exception e) {
                    log.warn("AI客服线索保存失败，groupHash={}, agentId={}, streamId={}, type={}",
                            maskGroupHash(ccMsgBO.getGroupHash()), aiAgent.getId(), streamId,
                            e.getClass().getSimpleName());
                }
            }
            chunkCount.incrementAndGet();
            sendAiStreamChunk(ccMsgBO, aiUserHash, streamId, sequence.incrementAndGet(), finalResult.visibleText());
        }
        CcMsgBO aiBotMsg = BeanUtil.copyProperties(ccMsgBO, CcMsgBO.class);
        aiBotMsg.setMsgContent(finalResult.visibleText());
        aiBotMsg.setUserHash(aiUserHash);
        aiBotMsg.setUserType(UserTypeEnum.AI.getValue());
        try {
            msgSendSave(aiBotMsg);
        } catch (Exception e) {
            log.warn("AI客服完整消息保存失败，groupHash={}, agentId={}, streamId={}, type={}",
                    maskGroupHash(ccMsgBO.getGroupHash()), aiAgent.getId(), streamId, e.getClass().getSimpleName());
        } finally {
            sendAiStreamEnd(ccMsgBO, aiUserHash, streamId, "completed", null);
        }
        log.info("AI客服流完成，groupHash={}, agentId={}, streamId={}, firstChunkMs={}, totalMs={}, chunks={}, length={}",
                maskGroupHash(ccMsgBO.getGroupHash()), aiAgent.getId(), streamId,
                firstChunkAt == 0 ? -1 : firstChunkAt - startedAt,
                System.currentTimeMillis() - startedAt, chunkCount.get(), finalResult.visibleText().length());
    }

    private void sendAiStreamChunk(CcMsgBO ccMsgBO, String aiUserHash, String streamId,
                                   int sequence, String delta) {
        sendAiStreamEvent(ccMsgBO, aiUserHash, CcMsgNoticeEnum.AI_STREAM_CHUNK,
                new CcAiStreamVO().setStreamId(streamId).setSequence(sequence).setDelta(delta));
    }

    private void sendAiStreamEnd(CcMsgBO ccMsgBO, String aiUserHash, String streamId,
                                 String status, String message) {
        sendAiStreamEvent(ccMsgBO, aiUserHash, CcMsgNoticeEnum.AI_STREAM_END,
                new CcAiStreamVO().setStreamId(streamId).setStatus(status).setMessage(message));
    }

    private void sendAiStreamEvent(CcMsgBO ccMsgBO, String aiUserHash,
                                   CcMsgNoticeEnum notice, CcAiStreamVO data) {
        CcMsgVO message = new CcMsgVO()
                .setNoticeType(notice.getValue())
                .setGroupHash(ccMsgBO.getGroupHash())
                .setUserHash(aiUserHash)
                .setUserType(UserTypeEnum.AI.getValue())
                .setData(data);
        ccChannelFactory.sendGroupMsg(ccMsgBO.getGroupHash(), message);
    }

    private boolean isAiStreamEnabled(CcMsgBO ccMsgBO) {
        if(ObjectUtil.isEmpty(ccMsgBO.getWebSetId()) || StrUtil.isBlank(aiStreamEnabledWebSetIds)){
            return false;
        }
        Set<String> enabledIds = new HashSet<>(Arrays.asList(aiStreamEnabledWebSetIds.split(",")));
        return enabledIds.stream().map(String::trim).anyMatch(String.valueOf(ccMsgBO.getWebSetId())::equals);
    }

    private String maskGroupHash(String groupHash) {
        if(StrUtil.length(groupHash) <= 6){
            return "***";
        }
        return StrUtil.subPre(groupHash, 3) + "***" + StrUtil.subSuf(groupHash, 3);
    }

    /**
     * 功能描述:
     * 〈发送AI客服消息〉
     * @param ccMsgBO ccMsgBO
     * @param aiAgent aiAgent
     * @param content content
     * @author Codex
     */
    private void backToGroupForAiMsg(CcMsgBO ccMsgBO, AiAgent aiAgent, String content) {
        backToGroupForAiMsg(ccMsgBO, AI_USER_HASH_PREFIX + aiAgent.getId(), content);
    }

    /**
     * 功能描述:
     * 〈发送AI客服不可用提示〉
     * @param ccMsgBO ccMsgBO
     * @param aiAgent aiAgent
     * @author Codex
     */
    private void backToGroupForAiUnavailable(CcMsgBO ccMsgBO, AiAgent aiAgent) {
        String aiUserHash = ObjectUtil.isNotEmpty(aiAgent) && ObjectUtil.isNotEmpty(aiAgent.getId())
                ? AI_USER_HASH_PREFIX + aiAgent.getId()
                : AI_FALLBACK_USER_HASH;
        backToGroupForAiMsg(ccMsgBO, aiUserHash, CcChatExceptionEnum.AI_UNAVAILABLE_MESSAGE.getDesc());
    }

    /**
     * 功能描述:
     * 〈发送AI客服消息〉
     * @param ccMsgBO ccMsgBO
     * @param aiUserHash aiUserHash
     * @param content content
     * @author Codex
     */
    private void backToGroupForAiMsg(CcMsgBO ccMsgBO, String aiUserHash, String content) {
        CcMsgBO aiBotMsg = BeanUtil.copyProperties(ccMsgBO, CcMsgBO.class);
        aiBotMsg.setMsgContent(content);
        aiBotMsg.setUserHash(aiUserHash);
        aiBotMsg.setUserType(UserTypeEnum.AI.getValue());
        msgSendSave(aiBotMsg);
        //将信息发送所有人
        backToGroupForMsg(aiBotMsg, aiBotMsg.getUserHash());
    }

    /**
     * 功能描述:
     * 〈获取当前会话可用的AI智能体〉
     * @param ccMsgBO ccMsgBO
     * @return 正常返回:{@link AiAgent}
     * @author Codex
     */
    private AiAgent getAiAgentForGroup(CcMsgBO ccMsgBO) {
        AiAgent aiAgent = this.aiAgentService.getAiAgentBOByGroupHash(ccMsgBO.getGroupHash());
        if(ObjectUtil.isNotEmpty(aiAgent)){
            return aiAgent;
        }
        CcUserVO nextAiUser = ccUserWorkRelService.getNextCcUser(UserTypeEnum.AI.getValue());
        if(Objects.isNull(nextAiUser)){
            log.warn("未找到当前排班AI客服，groupHash={}", ccMsgBO.getGroupHash());
            return null;
        }
        aiAgent = getAiAgentByCcUser(nextAiUser);
        if(ObjectUtil.isEmpty(aiAgent)){
            log.warn("AI客服未绑定有效智能体，groupHash={}, userHash={}, agentId={}",
                    ccMsgBO.getGroupHash(), nextAiUser.getUserHash(), nextAiUser.getAgentId());
            return null;
        }
        bindAiUserToGroup(ccMsgBO, nextAiUser);
        return aiAgent;
    }

    /**
     * 功能描述:
     * 〈根据AI客服获取智能体〉
     * @param aiUser aiUser
     * @return 正常返回:{@link AiAgent}
     * @author Codex
     */
    private AiAgent getAiAgentByCcUser(CcUserVO aiUser) {
        if(ObjectUtil.isNotEmpty(aiUser.getAgentId())){
            return aiAgentService.getAiAgentBOById(aiUser.getAgentId());
        }
        String userHash = StrUtil.removePrefix(StrUtil.nullToEmpty(aiUser.getUserHash()), AI_USER_HASH_PREFIX);
        if(StrUtil.isBlank(userHash)){
            return null;
        }
        try {
            return aiAgentService.getAiAgentBOById(Long.valueOf(userHash));
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    /**
     * 功能描述:
     * 〈将AI客服绑定到会话，便于后续消息继续复用同一个智能体〉
     * @param ccMsgBO ccMsgBO
     * @param aiUser aiUser
     * @author Codex
     */
    private void bindAiUserToGroup(CcMsgBO ccMsgBO, CcUserVO aiUser) {
        CcGroup ccGroup = ccGroupService.getCcGroupByHash(ccMsgBO.getGroupHash());
        if(ObjectUtil.isEmpty(ccGroup)){
            return;
        }
        CcGroupUserRel userRel = BeanUtil.copyProperties(ccGroup, CcGroupUserRel.class);
        userRel.setId(null);
        userRel.setGroupId(ccGroup.getId());
        userRel.setUserHash(aiUser.getUserHash());
        userRel.setUserName(aiUser.getUserName());
        userRel.setUserType(UserTypeEnum.AI.getValue());
        userRel.setCreateTime(LocalDateTime.now());
		boolean existed = ccGroupUserRelService.lambdaQuery()
                    .eq(CcGroupUserRel::getGroupHash, userRel.getGroupHash())
                    .eq(CcGroupUserRel::getUserHash, userRel.getUserHash())
                    .exists();
		if(!existed){
			ccGroupUserRelService.save(userRel);
		}
    }

    /**
     * 功能描述:
     * 〈人工消息〉
     * @param ccMsgBO ccMsgBO
     * @author 蝉鸣
     */
    public void humanMsg(CcMsgBO ccMsgBO) {
        //获取当前组下是否有同类型
        CcGroupUserRel ccGroupUserRel = ccGroupUserRelService.getByGroupHashAndType(ccMsgBO.getGroupHash(),UserTypeEnum.HUMAN.getValue());
        CcUserVO nextCcUser;
        if(ObjectUtil.isEmpty(ccGroupUserRel)){
            //获取下一分配客服人员
            nextCcUser = ccUserWorkRelService.getNextCcUser(UserTypeEnum.HUMAN.getValue());
        }else{
            //获取当前人员
            nextCcUser = BeanUtil.copyProperties(ccGroupUserRel, CcUserVO.class);
        }
        //如果客服人员为空则视为非服务期间,请留言
        if(Objects.isNull(nextCcUser)){
            //提示客服进行留言
            backToCustomerForLeave(ccMsgBO);
            backToSystemForUnassignedNotice(ccMsgBO);
        }else{
            //设置群回复类型为人工
            ccGroupService.setReplyType(UserTypeEnum.HUMAN.getValue(),ccMsgBO.getGroupHash());
            //提示客服服务开始
            backToCustomerForServer(ccMsgBO, nextCcUser);
            //提示系统及时接待
            backToSystemForNotice(ccMsgBO,nextCcUser);
        }
    }

    /**
     * 功能描述:
     * 〈人工消息〉
     * @param ccMsgBO ccMsgBO
     * @author 蝉鸣
     */
    public void humanMsgNoice(CcMsgBO ccMsgBO) {
        //获取客服人员
        CcUserVO nextCcUser = getNextCcUser(ccMsgBO.getGroupHash());
        //如果客服人员为空则视为非服务期间,请留言
        if(Objects.isNull(nextCcUser)){
            //提示客服进行留言
            backToCustomerForLeave(ccMsgBO);
            backToSystemForUnassignedNotice(ccMsgBO);
        }else{
            //提示系统及时接待
            backToSystemForNotice(ccMsgBO,nextCcUser);
        }
    }

    /**
     * 功能描述:
     * 〈获取客服人员〉
     * @param tenantId tenantId
     * @param groupHash groupHash
     * @return 正常返回:{@link CcUserVO}
     * @author 蝉鸣
     */
    public CcUserVO getNextCcUser(String groupHash) {
        //获取当前组下是否有同类型
        CcGroupUserRel ccGroupUserRel = ccGroupUserRelService.getByGroupHashAndType(groupHash,UserTypeEnum.HUMAN.getValue());
        CcUserVO nextCcUser;
        if(ObjectUtil.isEmpty(ccGroupUserRel)){
            //获取下一分配客服人员
            nextCcUser = ccUserWorkRelService.getNextCcUser(UserTypeEnum.HUMAN.getValue());
        }else{
            //获取当前人员
            nextCcUser = BeanUtil.copyProperties(ccGroupUserRel, CcUserVO.class);
        }
        return nextCcUser;
    }

    /**
     * 功能描述:
     * 〈将信息发送所有人〉
     * @param ccMsgBO ccMsgBO
     * @param userHash userHash
     * @author 蝉鸣
     */
    private void backToGroupForMsg(CcMsgBO ccMsgBO,String userHash){
        CcMsgVO ccMsgVO = new CcMsgVO();
        ccMsgVO.setNoticeType(CcMsgNoticeEnum.MSG_BACK.getValue());
        ccMsgVO.setGroupHash(ccMsgBO.getGroupHash());
        ccMsgVO.setUserHash(userHash);
        ccMsgVO.setUserType(ccMsgBO.getUserType());
        ccMsgVO.setData(ccMsgBO.getMsgContent());
        ccChannelFactory.sendGroupMsg(ccMsgBO.getGroupHash(),ccMsgVO);
    }

    /**
     * 功能描述:
     * 〈提示客服进行留言〉
     * @param ccMsgBO ccMsgBO
     * @author 蝉鸣
     */
    private void backToCustomerForLeave(CcMsgBO ccMsgBO){
        CcMsgVO ccMsgVO = new CcMsgVO();
        List<CcSetWord> setWordList = getSetWordList(WordFlagEnum.LEAVE_WORD.getValue());
        if(CollUtil.isNotEmpty(setWordList)){
            CcSetWord setWord = CollUtil.getLast(setWordList);
            ccMsgVO.setData(setWord);
        }
        ccMsgVO.setNoticeType(CcMsgNoticeEnum.MSG_LEAVE.getValue());
        ccMsgVO.setGroupHash(ccMsgBO.getGroupHash());
        ccChannelFactory.sendGroupMsg(ccMsgBO.getGroupHash(),ccMsgVO);
    }

    /**
     * 功能描述:
     * 〈提示客服服务开始〉
     * @param ccMsgBO ccMsgBO
     * @param nextCcUser nextCcUser
     * @author 蝉鸣
     */
    private void backToCustomerForServer(CcMsgBO ccMsgBO,CcUserVO nextCcUser){
        //将分配人员拉入客服群
        CcGroup ccGroup = ccGroupService.getCcGroupByHash(ccMsgBO.getGroupHash());
        CcGroupUserRel userRel = BeanUtil.copyProperties(ccGroup, CcGroupUserRel.class);
        userRel.setId(null);
        userRel.setGroupId(ccGroup.getId());
        userRel.setUserHash(nextCcUser.getUserHash());
        userRel.setUserName(nextCcUser.getUserName());
        userRel.setUserType(nextCcUser.getUserType());
        userRel.setCreateTime(LocalDateTime.now());

        boolean existed = ccGroupUserRelService.lambdaQuery()
                .eq(CcGroupUserRel::getGroupHash, userRel.getGroupHash())
                .eq(CcGroupUserRel::getUserHash, userRel.getUserHash())
                .exists();
        if(!existed){
            ccGroupUserRelService.save(userRel);
        }
        
        //添加欢迎语
        CcMsgVO ccMsgVO = new CcMsgVO();
        ccMsgVO.setNoticeType(CcMsgNoticeEnum.MSG_BACK.getValue());
        ccMsgVO.setGroupHash(ccMsgBO.getGroupHash());
        ccMsgVO.setUserHash(nextCcUser.getUserHash());
        List<CcSetWord> setWordList = getSetWordList(WordFlagEnum.HELLO_WORD.getValue());
        if(CollUtil.isNotEmpty(setWordList)){
            CcSetWord setWord = CollUtil.getLast(setWordList);
            ccMsgVO.setData(setWord);
        }
        //发送给客户提示某员工来进行服务
        ccChannelFactory.sendGroupMsg(ccMsgBO.getGroupHash(),ccMsgVO);
    }

    /**
     * 功能描述:
     * 〈提示系统及时接待〉
     * @param ccMsgBO ccMsgBO
     * @param nextCcUser nextCcUser
     * @author 蝉鸣
     */
    private void backToSystemForNotice(CcMsgBO ccMsgBO,CcUserVO nextCcUser){
        CcMsgVO ccMsgVO = new CcMsgVO();
        ccMsgVO.setNoticeType(CcMsgNoticeEnum.MSG_HUMAN.getValue());
        ccMsgVO.setGroupHash(ChannelConst.COMMON_CHANNEL);
        ccMsgVO.setUserHash(nextCcUser.getUserHash());
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.set(ChannelConst.GROUP_INFO, ccMsgBO.getGroupHash());
        jsonObject.set(ChannelConst.USER_INFO, nextCcUser);
        ccMsgVO.setData(jsonObject);
        //发送给系统提示员工需要及时接待
        ccChannelFactory.sendGroupMsg(ccMsgVO.getGroupHash(),ccMsgVO);
    }

    /**
     * 功能描述:
     * 〈提示系统有未分配会话待接待〉
     * @param ccMsgBO ccMsgBO
     * @author Codex
     */
    private void backToSystemForUnassignedNotice(CcMsgBO ccMsgBO){
        CcMsgVO ccMsgVO = new CcMsgVO();
        ccMsgVO.setNoticeType(CcMsgNoticeEnum.MSG_HUMAN.getValue());
        ccMsgVO.setGroupHash(ChannelConst.COMMON_CHANNEL);
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.set(ChannelConst.GROUP_INFO, ccMsgBO.getGroupHash());
        jsonObject.set("unassigned", Boolean.TRUE);
        ccMsgVO.setData(jsonObject);
        ccChannelFactory.sendGroupMsg(ccMsgVO.getGroupHash(),ccMsgVO);
    }

    /**
     * 功能描述:
     * 〈提示系统及时接待〉
     * @param ccMsgBO ccMsgBO
     * @author 蝉鸣
     */
    public void backToGroupForOnline(CcMsgBO ccMsgBO){
        //获取群所有成员
        
        List<CcGroupUserRel> relList = ccGroupUserRelService.lambdaQuery()
                .eq(CcGroupUserRel::getGroupHash, ccMsgBO.getGroupHash())
                .list();
        
        if(CollUtil.isEmpty(relList)){
            return;
        }
        List<String> userHashList = relList.stream().map(CcGroupUserRel::getUserHash).toList();
        CcMsgVO ccMsgVO = new CcMsgVO();
        ccMsgVO.setNoticeType(CcMsgNoticeEnum.MSG_ONLINE.getValue());
        ccMsgVO.setGroupHash(ccMsgBO.getGroupHash());
        JSONObject jsonObject = JSONUtil.createObj();
        for (String userHash : userHashList) {
            jsonObject.set(userHash, ccChannelFactory.userOnLine(userHash));
        }
        ccMsgVO.setData(jsonObject);
        ccChannelFactory.sendGroupMsg(ccMsgBO.getGroupHash(),ccMsgVO);
    }

    /**
     * 功能描述:
     * 〈返回系统提醒未读消息数量〉
     * @param ccMsgBO ccMsgBO
     * @author 蝉鸣
     */
    public void backToSystemForUnReadNum(CcMsgBO ccMsgBO) {
        //获取群所有成员
        
        Map<Long, Integer> userUnReadMap = ccGroupUserRelService.getUserUnReadNum(ccMsgBO.getUserHash());
        
        CcMsgVO ccMsgVO = new CcMsgVO();
        ccMsgVO.setNoticeType(CcMsgNoticeEnum.SYSTEM_TIP.getValue());
        ccMsgVO.setGroupHash(ChannelConst.COMMON_CHANNEL);
        ccMsgVO.setUserHash(ccMsgBO.getUserHash());
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.set(ChannelConst.UN_READ_NUM, userUnReadMap);
        ccMsgVO.setData(jsonObject);
        //发送给系统提示员工需要及时接待
        ccChannelFactory.sendGroupMsg(ccMsgVO.getGroupHash(),ccMsgVO);
    }

    /**
     * 功能描述:
     * 〈刷新会话访客来源和联系信息〉
     * @param ccMsgBO ccMsgBO
     * @author Codex
     */
    private void refreshGroupVisitorInfo(CcMsgBO ccMsgBO) {
        if(StrUtil.isAllBlank(ccMsgBO.getSource(), ccMsgBO.getSourcePage(), ccMsgBO.getVisitorName(),
                ccMsgBO.getVisitorContact(), ccMsgBO.getVisitorCompany(), ccMsgBO.getVisitorDemand())){
            return;
        }
        ccGroupService.lambdaUpdate()
                .set(StrUtil.isNotBlank(ccMsgBO.getSource()), CcGroup::getSource, ccMsgBO.getSource())
                .set(StrUtil.isNotBlank(ccMsgBO.getSourcePage()), CcGroup::getSourcePage, ccMsgBO.getSourcePage())
                .set(StrUtil.isNotBlank(ccMsgBO.getVisitorName()), CcGroup::getVisitorName, ccMsgBO.getVisitorName())
                .set(StrUtil.isNotBlank(ccMsgBO.getVisitorContact()), CcGroup::getVisitorContact, ccMsgBO.getVisitorContact())
                .set(StrUtil.isNotBlank(ccMsgBO.getVisitorCompany()), CcGroup::getVisitorCompany, ccMsgBO.getVisitorCompany())
                .set(StrUtil.isNotBlank(ccMsgBO.getVisitorDemand()), CcGroup::getVisitorDemand, ccMsgBO.getVisitorDemand())
                .eq(CcGroup::getGroupHash, ccMsgBO.getGroupHash())
                .update();
    }

    /**
     * 功能描述:
     * 〈更新会话最后消息时间和首次响应时间〉
     * @param ccMsgBO ccMsgBO
     * @param msgTime msgTime
     * @author Codex
     */
    private void touchGroupOnMessage(CcMsgBO ccMsgBO, LocalDateTime msgTime) {
        if(ObjectUtil.isEmpty(ccMsgBO.getGroupHash())){
            return;
        }
        boolean isStaffResponse = UserTypeEnum.AI.getValue().equals(ccMsgBO.getUserType())
                || UserTypeEnum.HUMAN.getValue().equals(ccMsgBO.getUserType());
        ccGroupService.lambdaUpdate()
                .set(CcGroup::getLastMsgAt, msgTime)
                .set(isStaffResponse, CcGroup::getFirstResponseAt, msgTime)
                .eq(CcGroup::getGroupHash, ccMsgBO.getGroupHash())
                .isNull(isStaffResponse, CcGroup::getFirstResponseAt)
                .update();
        if(!isStaffResponse){
            refreshGroupVisitorInfo(ccMsgBO);
        }
    }
}
