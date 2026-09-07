package com.platform.mesh.upms.biz.modules.msg.userrel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.msg.base.domain.po.MsgBase;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.po.MsgNotice;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.vo.UnReadVO;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.dto.MsgReadDTO;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.dto.MsgUserRelPageDTO;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.po.MsgUserRel;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.vo.MsgUserRelVO;

import java.util.List;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 消息接收信息
 * @author 蝉鸣
 */
public interface IMsgUserRelService extends IService<MsgUserRel> {


    /**
     * 功能描述:
     * 〈用户消息分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<MsgUserRelVO>}
     * @author 蝉鸣
     */
    MPage<MsgUserRelVO> selectPage(MsgUserRelPageDTO pageDTO);

    /**
     * 功能描述:
     * 〈已读消息〉
     * @param msgReadDTO msgReadDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean readUserMsg(MsgReadDTO msgReadDTO);

    /**
     * 功能描述:
     * 〈未读消息数量角标〉
     * @return 正常返回:{@link List<UnReadVO>}
     * @author 蝉鸣
     */
    List<UnReadVO> countUnReadBase();

    /**
     * 功能描述:
     * 〈保存消息〉
     * @author 蝉鸣
     */
    void saveByMsg(MsgBase msgBase, List<Long> userIds);

    /**
     * 功能描述:
     * 〈保存消息〉
     * @author 蝉鸣
     */
    void saveByNotice(MsgBase msgBase, MsgNotice msgNotice);
}
