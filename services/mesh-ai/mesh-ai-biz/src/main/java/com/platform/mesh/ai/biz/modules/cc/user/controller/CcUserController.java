package com.platform.mesh.ai.biz.modules.cc.user.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrBuilder;
import com.coze.openapi.client.chat.*;
import com.coze.openapi.client.chat.model.*;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.config.Consts;
import com.coze.openapi.service.service.CozeAPI;
import com.platform.mesh.ai.biz.modules.cc.user.domain.dto.CcUserDTO;
import com.platform.mesh.ai.biz.modules.cc.user.domain.po.CcUser;
import com.platform.mesh.ai.biz.modules.cc.user.domain.vo.CcUserVO;
import com.platform.mesh.ai.biz.modules.cc.user.service.ICcUserService;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 约定当前controller 只引入当前service
 * @description 客服人员
 * @author 蝉鸣
 */
@Tag(description = "CcUserController", name = "客服人员")
@RestController
@RequestMapping
public class CcUserController {

    @Autowired
    private ICcUserService ccUserService;

    /**
     * 功能描述:
     * 〈获取客服人员列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result <PageVO<CcUserVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取客服人员分页")
    @PostMapping("/cc/user/page")
    public Result<PageVO<CcUserVO>> selectPage(@RequestBody PageDTO pageDTO) {
        MPage<CcUser> mPage = MPageUtil.pageEntityToMPage(pageDTO, CcUser.class);
        MPage<CcUser> page = ccUserService.page(mPage);
        return Result.success(MPageUtil.convertToVO(page, CcUserVO.class));
    }

    /**
     * 功能描述:
     * 〈获取当前客服人员信息〉
     * @param userId userId
     * @return 正常返回:{@link Result<CcUserVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客服人员信息")
    @GetMapping("/cc/user/info/{userId}")
    public Result<CcUserVO> getCcUserInfoById(@PathVariable("userId")Long userId) {
        CcUserVO aiMcpVO = ccUserService.getCcUserById(userId);
        return Result.success(aiMcpVO);
    }

    /**
     * 功能描述:
     * 〈新增客服人员〉
     * @param aiMcpDTO aiMcpDTO
     * @return 正常返回:{@link Result<CcUserVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客服人员")
    @Log(moduleName = "客服人员管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/cc/user/add")
    public Result<CcUserVO> addCcUser(@Validated @RequestBody CcUserDTO aiMcpDTO) {
        return Result.success(ccUserService.addCcUser(aiMcpDTO));
    }

    /**
     * 功能描述:
     * 〈修改客服人员〉
     * @param aiMcpDTO aiMcpDTO
     * @return 正常返回:{@link Result<CcUserVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客服人员")
    @Log(moduleName = "客服人员管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/cc/user/edit")
    public Result<CcUserVO> editCcUser(@Validated @RequestBody CcUserDTO aiMcpDTO) {
        return Result.success(ccUserService.editCcUser(aiMcpDTO));
    }

    /**
     * 功能描述:
     * 〈删除客服人员〉
     * @param userId userId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客服人员")
    @Log(moduleName = "客服人员管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/cc/user/delete/{userId}")
    public Result<Boolean> deleteCcUser(@PathVariable(value = "userId",required = false)Long userId) {
        return Result.success(ccUserService.deleteCcUser(userId));
    }


    /**
     * 功能描述:
     * 〈获取客服人员列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result <PageVO<CcUserVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取客服人员分页")
    @PostMapping("/ws/cc/user/page")
    public Result<Object> test(@RequestBody PageDTO pageDTO) throws Exception {
        String token = "pat_GQurtsvE80csuuyAM9OHauSBxQ45rJFDBKHtslGIx6VqVRYLDIpMGR20kCh8i3fF";
        String baseUrl = Consts.COZE_CN_BASE_URL;
        String botId = "7552738869502951465";
        String uid = "123";
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze =
                new CozeAPI.Builder()
                        .baseURL(baseUrl)
                        .auth(authCli)
                        .readTimeout(10000)
                        .build();
        CreateChatReq req =
                CreateChatReq.builder()
                        .botID(botId)
                        .userID(uid)
                        .messages(CollUtil.newArrayList(Message.buildUserQuestionText("What can you do?")))
                        .build();
        CreateChatResp chatResp = coze.chat().create(req);
        System.out.println(chatResp);
        Chat chat = chatResp.getChat();
        String chatID = chat.getID();
        String conversationID = chat.getConversationID();
        long timeout = 10L;
        long start = System.currentTimeMillis() / 1000;
        while (ChatStatus.IN_PROGRESS.equals(chat.getStatus())) {
            try {
                // The API has a rate limit with 1 QPS.
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }

            if ((System.currentTimeMillis() / 1000) - start > timeout) {
                // The chat can be cancelled before its completed.
                System.out.println(coze.chat().cancel(CancelChatReq.of(conversationID, chatID)));
                break;
            }
            RetrieveChatResp resp = coze.chat().retrieve(RetrieveChatReq.of(conversationID, chatID));
            System.out.println(resp);
            chat = resp.getChat();
            if (ChatStatus.COMPLETED.equals(chat.getStatus())) {
                break;
            }
        }
        // the developer can also set the timeout.
        ChatPoll chat3 = coze.chat().createAndPoll(req, timeout);
        System.out.println(chat3);
        return Result.success(chat3.getMessages());
    }

    /**
     * 功能描述:
     * 〈获取客服人员列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result <PageVO<CcUserVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取客服人员分页")
    @PostMapping("/ws/cc/user/page/2")
    public Result<Object> test2(@RequestBody PageDTO pageDTO) throws Exception {
        String token = "pat_GQurtsvE80csuuyAM9OHauSBxQ45rJFDBKHtslGIx6VqVRYLDIpMGR20kCh8i3fF";
        String baseUrl = Consts.COZE_CN_BASE_URL;
        String botId = "7552738869502951465";
        String uid = "123";
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze =
                new CozeAPI.Builder()
                        .baseURL(baseUrl)
                        .auth(authCli)
                        .readTimeout(10000)
                        .build();
        CreateChatReq req =
                CreateChatReq.builder()
                        .botID(botId)
                        .userID(uid)
                        .stream(true) // 关键：启用流式输出
                        .messages(CollUtil.newArrayList(Message.buildUserQuestionText("你是客服吗?")))
                        .build();
        // 创建流式消费者来处理实时消息
        Flowable<ChatEvent> resp = coze.chat().stream(req);
        StrBuilder strBuilder = StrBuilder.create();
        CountDownLatch latch = new CountDownLatch(1);
        resp.subscribeOn(Schedulers.io())
                .subscribe(
                        event -> {
                            if (event == null) {
                                System.out.println("Received null event");
                                return;
                            }
                            // 先检查消息是否为null
                            Message message = event.getMessage();
                            if (message == null) {
                                System.out.println("Event " + event.getEvent() + " has no message");
                                return;
                            }
                            if (ChatEventType.CONVERSATION_MESSAGE_DELTA.equals(event.getEvent())) {
                                String content = message.getContent();
                                if (content != null && !content.isEmpty()) {
                                    System.out.print(content); // 打印实时内容
                                    strBuilder.append(content); // 关键：将分块内容添加到结果中
                                }
                            }
                            else if (ChatEventType.CONVERSATION_CHAT_COMPLETED.equals(event.getEvent())) {
                                String content = message.getContent();
                                if (content != null && !content.isEmpty()) {
                                    System.out.println(content); // 打印完成时的补充内容
                                    strBuilder.append(content); // 累积补充内容
                                }
                                // 处理Token使用情况（非内容，不影响返回结果）
                                if (event.getChat() != null && event.getChat().getUsage() != null) {
                                    System.out.println("Token usage: " + event.getChat().getUsage().getTokenCount());
                                }
                            }
                            else if (ChatEventType.DONE.equals(event.getEvent())) {
                                coze.shutdownExecutor();
                                // 通知等待线程可以结束
                                latch.countDown();
                            }
                            else if (ChatEventType.ERROR.equals(event.getEvent())) {
                                // 专门处理错误事件
                                System.err.println("Error event received: " + message.getContent());
                                // 错误时也需结束等待
                                latch.countDown();
                            }
                        },
                        throwable -> {
                            System.err.println("Error occurred: " + throwable.getMessage());
                            // 发生错误也需要通知
                            latch.countDown();
                        },
                        () -> {
                            System.out.println("done");
                            // 完成时通知
                            latch.countDown();
                        });
        // 为了防止程序立即退出，添加一个简单的等待
        try {
            boolean completed = latch.await(30, TimeUnit.SECONDS);
            if (!completed) {
                System.out.println("等待超时，可能未接收到所有响应数据");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        coze.shutdownExecutor();
        return Result.success(strBuilder.toString());
    }
}
