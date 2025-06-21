package com.platform.mesh.upms.biz.modules.msg.notice.api;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.bo.MsgNoticeBO;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.dto.MsgNoticeDTO;
import com.platform.mesh.upms.biz.modules.msg.notice.service.IMsgNoticeService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 约定当前controller 只引入当前service
 * @description 消息提醒信息
 * @author 蝉鸣
 */
@Hidden
@Tag(description = "MsgNoticeApi", name = "消息提醒")
@RestController
public class MsgNoticeApi extends BaseController{
    @Autowired
    private IMsgNoticeService msgNoticeService;

    /**
     * 功能描述:
     * 〈新增消息提醒〉
     * @author 蝉鸣
     */
    @AuthIgnore
    @Operation(summary = "添加消息提醒")
    @PostMapping(value = "/api/sys/msg/notice/add")
    public void addNotice(@RequestBody MsgNoticeBO msgNoticeBO){
        MsgNoticeDTO msgNoticeDTO = BeanUtil.copyProperties(msgNoticeBO, MsgNoticeDTO.class);
        msgNoticeService.addNotice(msgNoticeDTO);
    }

    /**
     * 功能描述:
     * 〈定时执行消息提醒〉
     * @author 蝉鸣
     */
    @AuthIgnore
    @Operation(summary = "执行消息提醒")
    @PostMapping(value = "/api/sys/msg/notice/handle")
    public void handleNotice(){
        msgNoticeService.handleNotice();
    }

    /**
     * 功能描述:
     * 〈定时清理消息提醒〉
     * @author 蝉鸣
     */
    @Operation(summary = "清理消息提醒")
    @PostMapping(value = "/api/sys/msg/notice/clear")
    public void clearNotice(){
        msgNoticeService.clearNotice();
    }

}