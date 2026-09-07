package com.platform.mesh.upms.biz.modules.msg.notice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.upms.biz.modules.msg.base.domain.vo.MsgBaseVO;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.dto.MsgNoticeDTO;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.dto.MsgNoticePageDTO;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.po.MsgNotice;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.vo.MsgNoticeVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 消息提醒信息
 * @author 蝉鸣
 */
public interface IMsgNoticeService extends IService<MsgNotice> {

    /**
     * 功能描述:
     * 〈获取分页消息提醒〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<MsgNoticeVO>}
     * @author 蝉鸣
     */
    PageVO<MsgNoticeVO> selectPage(MsgNoticePageDTO pageDTO);

    /**
     * 功能描述:
     * 〈获取当前消息提醒信息〉
     * @param noticeId noticeId
     * @return 正常返回:{@link MsgBaseVO}
     * @author 蝉鸣
     */
    MsgNoticeVO getNoticeInfoById(Long noticeId);

    /**
     * 功能描述:
     * 〈新增消息提醒〉
     * @param noticeDTO noticeDTO
     * @return 正常返回:{@link MsgBaseVO}
     * @author 蝉鸣
     */
    Boolean addNotice(MsgNoticeDTO noticeDTO);

    /**
     * 功能描述:
     * 〈删除消息提醒〉
     * @param noticeId noticeId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteNotice(Long noticeId);

    /**
     * 功能描述:
     * 〈定时批量生成消息提醒〉
     * @author 蝉鸣
     */
    void handleNotice();

    /**
     * 功能描述:
     * 〈定时清理消息提醒〉
     * @author 蝉鸣
     */
    void clearNotice();

}
