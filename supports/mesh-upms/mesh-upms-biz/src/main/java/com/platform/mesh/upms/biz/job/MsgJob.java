package com.platform.mesh.upms.biz.job;

import com.platform.mesh.upms.biz.modules.msg.notice.service.IMsgNoticeService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description 消息定时任务调度服务
 * @author 蝉鸣
 */
@Component
public class MsgJob {

    private static final Logger log = LoggerFactory.getLogger(MsgJob.class);

    @Autowired
    private IMsgNoticeService msgNoticeService;

    /**
     * 定时消息提醒生成消息
     */
    @XxlJob("msgNoticeHandleJobHandler")
    public void msgNoticeHandleJobHandler() throws Exception {
        log.info(">>>消息提醒定时生成任务开始执行！！！");
        XxlJobHelper.log(">>>消息提醒定时生成任务开始执行！！！");
        msgNoticeService.handleNotice();
    }


    /**
     * 定时消息提醒过期清理
     */
    @XxlJob("msgNoticeClearJobHandler")
    public void msgNoticeClearJobHandler() throws Exception {
        log.info(">>>消息提醒定时清理任务开始执行！！！");
        XxlJobHelper.log(">>>消息提醒定时清理任务开始执行！！！");
        msgNoticeService.clearNotice();
    }

}
