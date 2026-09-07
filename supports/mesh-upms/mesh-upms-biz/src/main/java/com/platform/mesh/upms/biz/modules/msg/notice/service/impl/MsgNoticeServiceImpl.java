package com.platform.mesh.upms.biz.modules.msg.notice.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;

import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.dto.MsgNoticeDTO;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.dto.MsgNoticePageDTO;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.po.MsgNotice;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.vo.MsgNoticeVO;
import com.platform.mesh.upms.biz.modules.msg.notice.enums.NoticeLoopEnum;
import com.platform.mesh.upms.biz.modules.msg.notice.factory.NoticeLoopFactory;
import com.platform.mesh.upms.biz.modules.msg.notice.factory.NoticeLoopService;
import com.platform.mesh.upms.biz.modules.msg.notice.mapper.MsgNoticeMapper;
import com.platform.mesh.upms.biz.modules.msg.notice.service.IMsgNoticeService;
import com.platform.mesh.upms.biz.modules.msg.notice.service.manual.MsgNoticeServiceManual;
import com.platform.mesh.utils.function.FutureHandleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 消息提醒
 * @author 蝉鸣
 */
@Service
public class MsgNoticeServiceImpl extends ServiceImpl<MsgNoticeMapper, MsgNotice> implements IMsgNoticeService {

    @Autowired
    private MsgNoticeServiceManual msgNoticeServiceManual;

    @Autowired
    private NoticeLoopFactory noticeLoopFactory;


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
        NoticeLoopEnum enumByValue = BaseEnum.getEnumByValue(NoticeLoopEnum.class, noticeDTO.getNoticeLoop());
        if(ObjectUtil.isEmpty(enumByValue)){
            return Boolean.FALSE;
        }
        NoticeLoopService noticeLoopService = noticeLoopFactory.getNoticeLoopService(enumByValue);
        if(ObjectUtil.isEmpty(noticeLoopService)){
            return Boolean.FALSE;
        }
        List<MsgNotice> msgNotices = noticeLoopService.notice(noticeDTO);
        if(CollUtil.isEmpty(msgNotices)){
            return Boolean.FALSE;
        }
        //删除旧数据
        this.lambdaUpdate()
                .eq(MsgNotice::getDataId,noticeDTO.getDataId())
                .remove();
        //保存新数据
        this.saveBatch(msgNotices);
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
        try {
            while(true){
                pageDTO.setPageNum(pageNum);
                MPage<MsgNotice> mPage = MPageUtil.pageEntityToMPage(pageDTO, MsgNotice.class);
                MPage<MsgNotice> noticeMPage = this.getBaseMapper().getAllHandleNotice(mPage);
                if(CollUtil.isEmpty(noticeMPage.getRecords())){
                    break;
                }
                List<MsgNotice> msgNotices = FutureHandleUtil.runWithResult(noticeMPage.getRecords(), msgNotice -> msgNotice != null ? msgNoticeServiceManual.createMsgBase(msgNotice) : null);
                Map<Boolean, List<MsgNotice>> noticeMap = msgNotices.stream()
                        .filter(ObjectUtil::isNotEmpty)
                        .filter(notice->ObjectUtil.isNotEmpty(notice.getId()))
                        .collect(Collectors.partitioningBy(
                                notice ->{
                                    if(ObjectUtil.isEmpty(notice.getNoticeNextTime())){
                                        // 下次提醒时间为空了，则视为不需要下次提醒
                                        return Boolean.FALSE;
                                    }
                                    if (notice.getNoticeLoop().equals(NoticeLoopEnum.ONE.getValue())) {
                                        // 一次性提醒：当前时间超过下次提醒时间则需要删除
                                        return LocalDateTime.now().isAfter(notice.getNoticeNextTime());
                                    } else {
                                        // 周期提醒：根据条件判断是更新还是删除
                                        return ObjectUtil.isNotEmpty(notice.getNoticeNextTime())
                                                && (!notice.getNoticeNextTime().isEqual(notice.getNoticeEndTime()))
                                                ;
                                    }
                                }

                        ));
                
                DataScopeHandler.setEnableDataScope(Boolean.FALSE);
                if(CollUtil.isNotEmpty(noticeMap.get(Boolean.TRUE))){
                    //如果最后一次联系时间在结束时间之前则修改，否则删除
                    this.getBaseMapper().updateById(noticeMap.get(Boolean.TRUE));
                }
                if(CollUtil.isNotEmpty(noticeMap.get(Boolean.FALSE))){
                    //如果最后一次联系时间在结束时间之前则修改，否则删除
                    this.getBaseMapper().deleteByIds(noticeMap.get(Boolean.FALSE));
                }
                DataScopeHandler.unEnableDataScope();
                
                if (pageNum >= noticeMPage.getPages()) {
                    break;
                }
                pageNum++;
            }
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
        }finally {
            DataScopeHandler.unEnableDataScope();
            
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
