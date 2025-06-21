package com.platform.mesh.upms.biz.modules.msg.notice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.dto.MsgNoticeDTO;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.dto.MsgNoticePageDTO;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.po.MsgNotice;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.vo.MsgNoticeVO;
import com.platform.mesh.upms.biz.modules.msg.notice.enums.NoticeLoopEnum;
import com.platform.mesh.upms.biz.modules.msg.notice.exception.MsgNoticeExceptionEnum;
import com.platform.mesh.upms.biz.modules.msg.notice.mapper.MsgNoticeMapper;
import com.platform.mesh.upms.biz.modules.msg.notice.service.IMsgNoticeService;
import com.platform.mesh.upms.biz.modules.msg.notice.service.manual.MsgNoticeServiceManual;
import com.platform.mesh.utils.function.FutureHandleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 消息提醒
 * @author 蝉鸣
 */
@Service
public class MsgNoticeServiceImpl extends ServiceImpl<MsgNoticeMapper, MsgNotice> implements IMsgNoticeService {

    @Autowired
    private MsgNoticeServiceManual msgNoticeServiceManual;


    /**
     * 功能描述:
     * 〈获取分页消息提醒〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<MsgNoticeVO>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<MsgNoticeVO> selectPage(MsgNoticePageDTO pageDTO) {
        MPage<MsgNotice> noticeMPage = MPageUtil.pageEntityToMPage(pageDTO, MsgNotice.class);
        MPage<MsgNotice> page = this.lambdaQuery()
                .eq(ObjectUtil.isNotEmpty(pageDTO.getModuleId()), MsgNotice::getModuleId, pageDTO.getModuleId())
                .eq(ObjectUtil.isNotEmpty(pageDTO.getDataId()), MsgNotice::getDataId, pageDTO.getDataId())
                .eq(ObjectUtil.isNotEmpty(pageDTO.getMsgFlag()), MsgNotice::getMsgFlag, pageDTO.getMsgFlag())
                .eq(ObjectUtil.isNotEmpty(pageDTO.getMsgType()), MsgNotice::getMsgType, pageDTO.getMsgType())
                .page(noticeMPage);
        return MPageUtil.convertToVO(page, MsgNoticeVO.class);
    }

    /**
     * 功能描述: 
     * 〈获取当前消息提醒信息〉
     * @param noticeId noticeId
     * @return 正常返回:{@link MsgNoticeVO}
     * @author 蝉鸣
     */
    @Override
    public MsgNoticeVO getNoticeInfoById(Long noticeId) {
        MsgNotice msgNotice = this.getById(noticeId);
        return msgNoticeServiceManual.getNoticeInfoById(msgNotice);
    }

    /**
     * 功能描述:
     * 〈新增消息提醒〉
     * @param noticeDTO noticeDTO
     * @return 正常返回:{@link MsgNoticeVO}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addNotice(MsgNoticeDTO noticeDTO) {
        if(CollUtil.isEmpty(noticeDTO.getMsgUserIds())){
            return Boolean.FALSE;
        }
        MsgNotice msgNotice = BeanUtil.copyProperties(noticeDTO, MsgNotice.class);
        noticeDTO.getMsgUserIds().stream().distinct().forEach(userId->{
            //设置提醒人
            msgNotice.setNoticeUserId(userId);
            //新增消息提醒时候set_time为设置的值，如果时一次性则直接复制next_time,如果时循环则根据规则解析next_time赋值，默认初始化数据next_time必定有值
            if(NoticeLoopEnum.ONE.getValue().equals(noticeDTO.getNoticeLoop())){
                msgNotice.setNoticeNextTime(noticeDTO.getNoticeSetTime());
                msgNotice.setNoticeStartTime(noticeDTO.getNoticeSetTime());
                msgNotice.setNoticeEndTime(noticeDTO.getNoticeSetTime());
                //直接添加消息,确保无定时也可跟进消息
                msgNoticeServiceManual.createMsgBase(msgNotice);
            }else{
                //多次提醒需要同时传递开始结束时间作为限制,避免无限处理
                if(ObjectUtil.isEmpty(noticeDTO.getNoticePreDays()) || ObjectUtil.isEmpty(noticeDTO.getNoticeSufDays())
                || noticeDTO.getNoticePreDays()<NumberConst.NUM_0 || noticeDTO.getNoticeSufDays()<NumberConst.NUM_0
                ){
                    throw MsgNoticeExceptionEnum.ADD_NO_ARGS.getBaseException();
                }
                //解析开始结束时间
                LocalDateTime noticeStartTime = noticeDTO.getNoticeSetTime().minusDays(noticeDTO.getNoticePreDays());
                if(noticeStartTime.isBefore(LocalDateTime.now())){
                    noticeStartTime = LocalDateTime.now();
                }
                msgNotice.setNoticeStartTime(noticeStartTime);
                LocalDateTime noticeEndTime = noticeDTO.getNoticeSetTime().plusDays(noticeDTO.getNoticeSufDays());
                if(noticeEndTime.isBefore(LocalDateTime.now())){
                    noticeEndTime = LocalDateTime.now();
                }
                if(noticeEndTime.isAfter(noticeStartTime)){
                    throw MsgNoticeExceptionEnum.ADD_NO_INVALID.getBaseException();
                }
                msgNotice.setNoticeEndTime(noticeEndTime);
                //获取下次执行时间
                LocalDateTime nextTime = msgNoticeServiceManual.getNextTime(msgNotice.getNoticeStartTime(), msgNotice.getNoticeIntervalValue(), msgNotice.getNoticeIntervalUnit());
                msgNotice.setNoticeNextTime(nextTime);
                //保存消息提醒信息
                this.save(msgNotice);
            }
        });
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈删除消息提醒〉
     * @param noticeId noticeId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteNotice(Long noticeId) {
        
        return this.removeById(noticeId);
    }

    /**
     * 功能描述:
     * 〈定时批量生成消息提醒〉
     * @author 蝉鸣
     */
    @Override
    public void handleNotice() {
        PageDTO pageDTO = new PageDTO();
        Integer pageNum = NumberConst.NUM_1;
        pageDTO.setPageSize(NumberConst.NUM_100);
        while(true){
            pageDTO.setPageNum(pageNum);
            MPage<MsgNotice> mPage = MPageUtil.pageEntityToMPage(pageDTO, MsgNotice.class);
            MPage<MsgNotice> noticeMPage = this.getBaseMapper().getAllHandleNotice(mPage);
            if(CollUtil.isEmpty(noticeMPage.getRecords())){
                break;
            }
            FutureHandleUtil.runWithResult(noticeMPage.getRecords(),msgNoticeServiceManual::createMsgBase);
            pageNum++;
        }
    }

    /**
     * 功能描述:
     * 〈定时清理消息提醒〉
     * @author 蝉鸣
     */
    @Override
    public void clearNotice() {
        this.getBaseMapper().clearNotice();
    }

}