package com.platform.mesh.upms.biz.modules.msg.notice.factory.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.dto.MsgNoticeDTO;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.po.MsgNotice;
import com.platform.mesh.upms.biz.modules.msg.notice.enums.NoticeLoopEnum;
import com.platform.mesh.upms.biz.modules.msg.notice.exception.MsgNoticeExceptionEnum;
import com.platform.mesh.upms.biz.modules.msg.notice.factory.NoticeLoopService;
import com.platform.mesh.upms.biz.modules.msg.notice.service.manual.MsgNoticeServiceManual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service
public class OneFactoryImpl implements NoticeLoopService {

    private final static Logger log = LoggerFactory.getLogger(OneFactoryImpl.class);

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
        return NoticeLoopEnum.ONE;
    }

    /**
     * 功能描述:
     * 〈一次提醒〉
     * @author 蝉鸣
     */
    @Override
    public List<MsgNotice> notice(MsgNoticeDTO noticeDTO) {
        return noticeDTO.getMsgUserIds().stream().distinct().map(userId->{
            MsgNotice msgNotice = BeanUtil.copyProperties(noticeDTO, MsgNotice.class);
            //循环根据设定时间，设定单位进行
            if(ObjectUtil.isEmpty(noticeDTO.getNoticeSetTime())
            ){
                throw MsgNoticeExceptionEnum.ADD_NO_ARGS.getBaseException();
            }
            //获取下次执行时间
            msgNotice.setNoticeNextTime(noticeDTO.getNoticeSetTime());
            msgNotice.setNoticeStartTime(noticeDTO.getNoticeSetTime());
            msgNotice.setNoticeEndTime(noticeDTO.getNoticeSetTime());
            //设置提醒人
            msgNotice.setNoticeUserId(userId);
            //保存消息提醒信息
            return msgNotice;
        }).toList();
    }

}
