package com.platform.mesh.upms.biz.modules.msg.notice.factory;

import com.platform.mesh.upms.biz.modules.msg.notice.domain.dto.MsgNoticeDTO;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.po.MsgNotice;
import com.platform.mesh.upms.biz.modules.msg.notice.enums.NoticeLoopEnum;

import java.util.List;

public interface NoticeLoopService {


    /**
     * 功能描述:
     * 〈循环类型〉
     * @return 正常返回:{@link NoticeLoopEnum}
     * @author 蝉鸣
     */
    NoticeLoopEnum noticeLoop();

    /**
     * 功能描述:
     * 〈提醒〉
     * @param noticeDTO noticeDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    List<MsgNotice> notice(MsgNoticeDTO noticeDTO);
}
