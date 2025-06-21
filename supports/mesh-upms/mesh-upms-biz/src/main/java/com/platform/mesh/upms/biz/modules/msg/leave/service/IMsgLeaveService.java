package com.platform.mesh.upms.biz.modules.msg.leave.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.upms.biz.modules.msg.leave.domain.dto.MsgLeaveDTO;
import com.platform.mesh.upms.biz.modules.msg.leave.domain.dto.MsgLeavePageDTO;
import com.platform.mesh.upms.biz.modules.msg.leave.domain.po.MsgLeave;
import com.platform.mesh.upms.biz.modules.msg.leave.domain.vo.MsgLeaveVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 消息信息
 * @author 蝉鸣
 */
public interface IMsgLeaveService extends IService<MsgLeave> {

    /**
     * 功能描述:
     * 〈获取分页消息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<MsgLeaveVO>}
     * @author 蝉鸣
     */
    PageVO<MsgLeaveVO> selectPage(MsgLeavePageDTO pageDTO);

    /**
     * 功能描述:
     * 〈新增消息〉
     * @param leaveDTO leaveDTO
     * @return 正常返回:{@link MsgLeaveVO}
     * @author 蝉鸣
     */
    Boolean addBase(MsgLeaveDTO leaveDTO);

    /**
     * 功能描述:
     * 〈删除消息〉
     * @param leaveId leaveId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteBase(Long leaveId);

}