package com.platform.mesh.upms.biz.modules.msg.notice.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.upms.biz.modules.msg.base.domain.po.MsgBase;
import com.platform.mesh.upms.biz.modules.msg.base.domain.vo.MsgBaseVO;
import com.platform.mesh.upms.biz.modules.msg.base.service.IMsgBaseService;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.po.MsgNotice;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.vo.MsgNoticeVO;
import com.platform.mesh.upms.biz.modules.msg.notice.enums.NoticeLoopEnum;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.po.MsgUserRel;
import com.platform.mesh.upms.biz.modules.msg.userrel.service.IMsgUserRelService;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.utils.format.DateTimeUtil;
import com.platform.mesh.utils.format.TimeUnitEnum;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
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
    public MsgNotice createMsgBase(MsgNotice msgNotice) {
        //如果时多次性消息
        if(NoticeLoopEnum.MORE.getValue().equals(msgNotice.getNoticeLoop())){
            //如果还未到执行时间则不进行
            if(msgNotice.getNoticeNextTime().isAfter(LocalDateTime.now())){
                return null;
            }
            LocalDateTime startTime;
            if(ObjectUtil.isEmpty(msgNotice.getNoticeNextTime()) || msgNotice.getNoticeNextTime().isBefore(LocalDateTime.now())){
                startTime = LocalDateTime.now();
            }else{
                startTime = msgNotice.getNoticeNextTime();
            }
            //生成下次提醒时间
            LocalDateTime nextTime = DateTimeUtil.getNextTime(startTime, msgNotice.getNoticeIntervalValue(), msgNotice.getNoticeIntervalUnit());
            //如果下次提醒时间超过结束时间则重置
            if(nextTime.isAfter(msgNotice.getNoticeEndTime())){
                nextTime = msgNotice.getNoticeEndTime();
            }
            //重置上次联系与下次提醒时间
            msgNotice.setNoticeNextTime(nextTime);
            msgNotice.setNoticeLastTime(LocalDateTime.now());
            //生成提醒信息
            saveMsgBase(msgNotice);
        } else if (NoticeLoopEnum.LOOP.getValue().equals(msgNotice.getNoticeLoop())) {
            //是否提前发送信息
            if(getPreTimeCanSend(msgNotice)){
                msgNotice.setNoticeLastTime(LocalDateTime.now());
                //生成提醒信息
                saveMsgBase(msgNotice);
                return msgNotice;
            }
            //如果还未到执行时间则不进行
            if(msgNotice.getNoticeNextTime().isAfter(LocalDateTime.now())){
                return null;
            }
            //获取下次执行时间
            LocalDateTime nextTime = getLoopTime(msgNotice.getNoticeSetTime(), msgNotice.getNoticeIntervalUnit());
            msgNotice.setNoticeNextTime(nextTime);
            msgNotice.setNoticeLastTime(LocalDateTime.now());
            //生成提醒信息
            saveMsgBase(msgNotice);
        } else {
            if(LocalDateTime.now().isAfter(msgNotice.getNoticeSetTime())){
                //生成提醒信息
                saveMsgBase(msgNotice);
                //设置下次联系时间为空,示意结束提醒
                msgNotice.setNoticeNextTime(null);
            }else{
                //将ID置空视为不处理
                msgNotice.setId(null);
            }
        }
        return msgNotice;
    }

    /**
     * 功能描述:
     * 〈保存消息信息:定时任务执行,需注意自动注入信息〉
     * @param msgNotice msgNotice
     * @author 蝉鸣
     */
    public void saveMsgBase(MsgNotice msgNotice) {
        //添加消息
        MsgBase msgBase = new MsgBase();
        BeanUtil.copyProperties(msgNotice, msgBase, ObjFieldUtil.ignoreDefault());
        msgBase.setNoticeTime(msgNotice.getNoticeSetTime());
        msgBase.setCreateUserId(msgNotice.getCreateUserId());
        msgBase.setCreateTime(LocalDateTime.now());
        msgBase.setUpdateUserId(msgNotice.getCreateUserId());
        msgBase.setUpdateTime(LocalDateTime.now());
        msgBaseService.save(msgBase);
        //添加提醒人
        if(ObjectUtil.isEmpty(msgNotice.getNoticeUserId())){
            return;
        }
        MsgUserRel userRel = new MsgUserRel();
        userRel.setMsgId(msgBase.getId());
        userRel.setUserId(msgNotice.getNoticeUserId());
        userRel.setReadFlag(YesOrNoEnum.YES.getValue());
        userRel.setDelFlag(YesOrNoEnum.YES.getValue());
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

    /**
     * 功能描述:
     * 〈获取下次提醒时间〉
     * @param localDateTime localDateTime
     * @param unit unit
     * @return 正常返回:{@link LocalDateTime}
     * @author 蝉鸣
     */
    public LocalDateTime getLoopTime(LocalDateTime localDateTime, Integer unit) {
        if(ObjectUtil.isEmpty(localDateTime)){
            return null;
        }
        //获取当前时间
        LocalDateTime now = LocalDateTime.now();
        TimeUnitEnum enumByValue = BaseEnum.getEnumByValue(TimeUnitEnum.class, unit);
        //获取调整后得基准时间
        LocalDateTime newTime = getNewTime(localDateTime, now, enumByValue);
        if(ObjectUtil.isNull(newTime)){
            return null;
        }
        if(newTime.isAfter(now)){
            return newTime;
        }else{
            return DateTimeUtil.getNextTime(newTime,NumberConst.NUM_1, unit);
        }
    }

    /**
     * 功能描述:
     * 〈获取新时间〉
     * @param baseTime baseTime
     * @param targetTime targetTime
     * @param unit unit
     * @return 正常返回:{@link LocalDateTime}
     * @author 蝉鸣
     */
    private LocalDateTime getNewTime(LocalDateTime baseTime, LocalDateTime targetTime, TimeUnitEnum unit) {
        return switch (unit) {
            case YEAR -> baseTime.withYear(targetTime.getYear());
            case MONTH -> baseTime.withYear(targetTime.getYear()).withMonth(targetTime.getMonthValue());
            case WEEK -> {
                DayOfWeek targetDay = baseTime.getDayOfWeek();
                DayOfWeek currentDay = targetTime.getDayOfWeek();
                // 计算从当前星期几到目标星期几需要加的天数
                int daysToAdd;
                if (targetDay.getValue() >= currentDay.getValue()) {
                    daysToAdd = targetDay.getValue() - currentDay.getValue();
                } else {
                    // 目标星期几在本周已过，需跨到下周
                    daysToAdd = NumberConst.NUM_7 - (currentDay.getValue() - targetDay.getValue());
                }
                yield targetTime.plusDays(daysToAdd);
            }
            case DAY -> baseTime.withYear(targetTime.getYear())
                    .withMonth(targetTime.getMonthValue())
                    .withDayOfMonth(targetTime.getDayOfMonth());
            case HOUR -> baseTime.withYear(targetTime.getYear())
                    .withMonth(targetTime.getMonthValue())
                    .withDayOfMonth(targetTime.getDayOfMonth())
                    .withHour(targetTime.getHour());
            case MINUTE -> baseTime.withYear(targetTime.getYear())
                    .withMonth(targetTime.getMonthValue())
                    .withDayOfMonth(targetTime.getDayOfMonth())
                    .withHour(targetTime.getHour())
                    .withMinute(targetTime.getMinute());
            case SECOND -> targetTime; // 秒级直接以当前时间为基准
            default -> null;
        };
    }

    /**
     * 功能描述:
     * 〈获取循环是否可以提前发送〉
     * @param msgNotice msgNotice
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    private Boolean getPreTimeCanSend(MsgNotice msgNotice) {
        if(ObjectUtil.isEmpty(msgNotice)){
            return Boolean.FALSE;
        }
        if(ObjectUtil.isEmpty(msgNotice.getNoticeIntervalValue())){
            return Boolean.FALSE;
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime noticeNextTime = msgNotice.getNoticeNextTime();
        LocalDateTime noticeLastTime = msgNotice.getNoticeLastTime();
        //如果是同一天则不发送
        if(now.toLocalDate().isEqual(noticeLastTime.toLocalDate())){
            return Boolean.FALSE;
        }
        LocalDateTime preTime = noticeNextTime.minusDays(msgNotice.getNoticeIntervalValue());
        boolean canSend = preTime.isBefore(now) && noticeNextTime.isBefore(now);
        TimeUnitEnum unit = BaseEnum.getEnumByValue(TimeUnitEnum.class, msgNotice.getNoticeIntervalUnit());
        return switch (unit) {
            case YEAR, MONTH, WEEK -> canSend;
            default -> Boolean.FALSE;
        };
    }



}
