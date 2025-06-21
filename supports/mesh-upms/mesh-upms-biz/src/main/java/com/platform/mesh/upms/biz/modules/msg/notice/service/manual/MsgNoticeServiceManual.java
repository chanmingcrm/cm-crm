package com.platform.mesh.upms.biz.modules.msg.notice.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.upms.api.modules.msg.enums.MsgFlagEnum;
import com.platform.mesh.upms.api.modules.msg.enums.MsgTypeEnum;
import com.platform.mesh.upms.biz.modules.msg.base.domain.po.MsgBase;
import com.platform.mesh.upms.biz.modules.msg.base.domain.vo.MsgBaseVO;
import com.platform.mesh.upms.biz.modules.msg.base.service.IMsgBaseService;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.po.MsgNotice;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.vo.MsgNoticeVO;
import com.platform.mesh.utils.format.TimeUnitEnum;
import com.platform.mesh.upms.biz.modules.msg.notice.enums.NoticeLoopEnum;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.po.MsgUserRel;
import com.platform.mesh.upms.biz.modules.msg.userrel.service.IMsgUserRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 消息
 * @author 蝉鸣
 */
@Service
public class MsgNoticeServiceManual {

    @Autowired
    private IMsgBaseService msgBaseService;

    @Autowired
    private IMsgUserRelService msgUserRelService;
    
    /**
     * 功能描述: 
     * 〈获取当前消息信息〉
     * @param msgNotice msgNotice
     * @return 正常返回:{@link MsgBaseVO}
     * @author 蝉鸣
     */
    public MsgNoticeVO getNoticeInfoById(MsgNotice msgNotice) {
        MsgNoticeVO msgNoticeVO = new MsgNoticeVO();
        if(ObjectUtil.isEmpty(msgNoticeVO)){
            return msgNoticeVO;
        }
        //转换VO
        BeanUtil.copyProperties(msgNotice, msgNoticeVO);
        return msgNoticeVO;
    }

    /**
     * 功能描述:
     * 〈生成消息信息，并更新提醒信息〉
     * @param msgNotice msgNotice
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public Boolean createMsgBase(MsgNotice msgNotice) {
        //生成提醒信息
        saveMsgBase(msgNotice);
        //如果时多次性消息
        if(NoticeLoopEnum.MORE.getValue().equals(msgNotice.getNoticeLoop())){
            LocalDateTime startTime;
            if(ObjectUtil.isEmpty(msgNotice.getNoticeNextTime())){
                startTime = LocalDateTime.now();
            }else{
                startTime = msgNotice.getNoticeNextTime();
            }
            //生成下次提醒时间
            LocalDateTime nextTime = getNextTime(startTime, msgNotice.getNoticeIntervalValue(), msgNotice.getNoticeIntervalUnit());
            //如果下次提醒时间超过结束时间则重置
            if(nextTime.isAfter(msgNotice.getNoticeEndTime())){
                nextTime = null;
            }
            //重置上次联系与下次提醒时间
            msgNotice.setNoticeNextTime(nextTime);
            msgNotice.setNoticeLastTime(LocalDateTime.now());
        }else {
            //设置下次联系时间为空,示意结束提醒
            msgNotice.setNoticeNextTime(null);
        }
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈保存消息信息:定时任务执行,需注意自动注入信息〉
     * @param msgNotice msgNotice
     * @author 蝉鸣
     */
    public void saveMsgBase(MsgNotice msgNotice) {
        //添加消息
        MsgBase msgBase = BeanUtil.copyProperties(msgNotice, MsgBase.class);
        msgBase.setMsgFlag(MsgFlagEnum.NOTICE_TODO.getValue());
        msgBase.setMsgType(MsgTypeEnum.INIT.getValue());
        msgBase.setCreateUserId(msgNotice.getCreateUserId());
        msgBase.setCreateTime(LocalDateTime.now());
        msgBase.setUpdateUserId(msgNotice.getCreateUserId());
        msgBase.setUpdateTime(LocalDateTime.now());
        msgBaseService.save(msgBase);
        //添加提醒人
        MsgUserRel userRel = new MsgUserRel();
        userRel.setMsgId(msgBase.getId());
        userRel.setUserId(msgNotice.getNoticeUserId());
        userRel.setReadFlag(YesOrNoEnum.YES.getValue());
        userRel.setDelFlag(YesOrNoEnum.INIT.getValue());
        userRel.setCreateUserId(msgNotice.getCreateUserId());
        userRel.setCreateTime(LocalDateTime.now());
        userRel.setUpdateUserId(msgNotice.getCreateUserId());
        userRel.setUpdateTime(LocalDateTime.now());
        userRel.setScopeUserId(msgNotice.getScopeUserId());
        userRel.setScopeOrgId(msgNotice.getScopeOrgId());
        msgUserRelService.save(userRel);
    }

    /**
     * 功能描述:
     * 〈获取下次提醒时间〉
     * @param localDateTime localDateTime
     * @param value value
     * @param unit unit
     * @return 正常返回:{@link LocalDateTime}
     * @author 蝉鸣
     */
    public LocalDateTime getNextTime(LocalDateTime localDateTime,Integer value,Integer unit) {
        if(ObjectUtil.isEmpty(localDateTime)){
            return null;
        }
        TimeUnitEnum enumByValue = BaseEnum.getEnumByValue(TimeUnitEnum.class, unit);
        return switch (enumByValue){
            case YEAR -> localDateTime.plusYears(value);
            case MONTH -> localDateTime.plusMonths(value);
            case WEEK -> localDateTime.plusWeeks(value);
            case DAY -> localDateTime.plusDays(value);
            case HOUR -> localDateTime.plusHours(value);
            case MINUTE -> localDateTime.plusMinutes(value);
            case SECOND -> localDateTime.plusSeconds(value);
            default -> null;
        };
    }
    //清除所有过时消息提醒

}