package com.platform.mesh.ai.biz.modules.cc.chat.service.maual;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.mesh.ai.biz.modules.cc.msg.domain.po.CcSessionMsg;
import com.platform.mesh.ai.biz.modules.cc.msg.service.ICcSessionMsgService;
import com.platform.mesh.ai.biz.modules.cc.user.enums.UserTypeEnum;
import com.platform.mesh.netty.server.domain.bo.CcMsgBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CcAiConversationHistory {

    static final int MAX_HISTORY_MESSAGES = 6;

    static final int MAX_HISTORY_CHARS = 12_000;

    private static final Logger log = LoggerFactory.getLogger(CcAiConversationHistory.class);

    @Autowired
    private ICcSessionMsgService ccSessionMsgService;

    List<Message> load(CcMsgBO ccMsgBO, Long currentMessageId) {
        if(ccMsgBO == null || StrUtil.isBlank(ccMsgBO.getGroupHash()) || currentMessageId == null){
            return List.of();
        }
        List<CcSessionMsg> records;
        try {
            records = ccSessionMsgService.list(new QueryWrapper<CcSessionMsg>()
                    .eq("group_hash", ccMsgBO.getGroupHash())
                    .lt("id", currentMessageId)
                    .in("user_type",
                            UserTypeEnum.INIT.getValue(), UserTypeEnum.AI.getValue())
                    .isNotNull("msg_content")
                    .orderByDesc("id")
                    .last("LIMIT " + MAX_HISTORY_MESSAGES));
        } catch (Exception e) {
            log.warn("加载AI客服上下文失败，type={}", e.getClass().getSimpleName());
            return List.of();
        }

        List<Message> messages = new ArrayList<>();
        int totalChars = 0;
        for(CcSessionMsg record : records){
            String content = StrUtil.trim(record.getMsgContent());
            if(StrUtil.isBlank(content)){
                continue;
            }
            if(totalChars + content.length() > MAX_HISTORY_CHARS){
                break;
            }
            if(UserTypeEnum.INIT.getValue().equals(record.getUserType())){
                messages.add(new UserMessage(content));
            }else if(UserTypeEnum.AI.getValue().equals(record.getUserType())){
                messages.add(new AssistantMessage(content));
            }else{
                continue;
            }
            totalChars += content.length();
        }
        Collections.reverse(messages);
        return messages;
    }
}
