package com.platform.mesh.upms.biz.modules.msg.notice.factory.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.dto.MsgNoticeDTO;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.po.MsgNotice;
import com.platform.mesh.upms.biz.modules.msg.notice.enums.NoticeLoopEnum;
import com.platform.mesh.upms.biz.modules.msg.notice.exception.MsgNoticeExceptionEnum;
import com.platform.mesh.upms.biz.modules.msg.notice.factory.NoticeLoopService;
import com.platform.mesh.upms.biz.modules.msg.notice.service.manual.MsgNoticeServiceManual;
import com.platform.mesh.utils.format.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service
public class MoreFactoryImpl implements NoticeLoopService {

    private final static Logger log = LoggerFactory.getLogger(MoreFactoryImpl.class);


    @Autowired
    private MsgNoticeServiceManual msgNoticeServiceManual;

    /**
     * 功能描述:
     * 〈循环类型〉
     * @return 正常返回:{@link NoticeLoopEnum}
     * @author 蝉鸣
     */
    @Override
    public NoticeLoopEnum noticeLoop() {
        return NoticeLoopEnum.MORE;
    }

    /**
     * 功能描述:
     * 〈多次提醒〉
     * @author 蝉鸣
     */
    @Override
    public List<MsgNotice> notice(MsgNoticeDTO noticeDTO) {
        return noticeDTO.getMsgUserIds().stream().distinct().map(userId->{
            MsgNotice msgNotice = BeanUtil.copyProperties(noticeDTO, MsgNotice.class);
            //多次提醒需要同时传递开始结束时间作为限制,避免无限处理
            if(ObjectUtil.isEmpty(noticeDTO.getNoticePreDays()) || ObjectUtil.isEmpty(noticeDTO.getNoticeSufDays())
                    || noticeDTO.getNoticePreDays()< NumberConst.NUM_0 || noticeDTO.getNoticeSufDays()<NumberConst.NUM_0
            ){
                throw MsgNoticeExceptionEnum.ADD_NO_ARGS.getBaseException();
            }
            //解析开始结束时间
            LocalDateTime noticeStartTime = noticeDTO.getNoticeSetTime().minusDays(noticeDTO.getNoticePreDays());
            if(noticeStartTime.isAfter(LocalDateTime.now())){
                noticeStartTime = LocalDateTime.now();
            }
            msgNotice.setNoticeStartTime(noticeStartTime);
            LocalDateTime noticeEndTime = noticeDTO.getNoticeSetTime().plusDays(noticeDTO.getNoticeSufDays());
            if(noticeEndTime.isBefore(LocalDateTime.now())){
                noticeEndTime = LocalDateTime.now();
            }
            if(noticeEndTime.isBefore(noticeStartTime)){
                throw MsgNoticeExceptionEnum.ADD_NO_INVALID.getBaseException();
            }
            //获取下次执行时间
            LocalDateTime nextTime = DateTimeUtil.getNextTime(noticeEndTime, msgNotice.getNoticeIntervalValue(), msgNotice.getNoticeIntervalUnit());
            if(nextTime.isAfter(noticeEndTime)){
                nextTime = noticeEndTime;
                //延后10分钟保证任务执行
                noticeEndTime = noticeEndTime.plusMinutes(NumberConst.NUM_10);
            }
            msgNotice.setNoticeEndTime(noticeEndTime);
            msgNotice.setNoticeNextTime(nextTime);
            //设置提醒人
            msgNotice.setNoticeUserId(userId);
            //保存消息提醒信息
            return msgNotice;
        }).toList();
    }

}
