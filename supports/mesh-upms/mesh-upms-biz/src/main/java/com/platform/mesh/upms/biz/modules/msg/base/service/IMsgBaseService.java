package com.platform.mesh.upms.biz.modules.msg.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.api.modules.msg.domain.bo.MsgBaseBO;
import com.platform.mesh.upms.biz.modules.msg.base.domain.dto.MsgBaseDTO;
import com.platform.mesh.upms.biz.modules.msg.base.domain.dto.MsgBasePageDTO;
import com.platform.mesh.upms.biz.modules.msg.base.domain.po.MsgBase;
import com.platform.mesh.upms.biz.modules.msg.base.domain.vo.MsgBaseVO;

import java.util.List;




/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 消息信息
 * @author 蝉鸣
 */
public interface IMsgBaseService extends IService<MsgBase> {

    /**
     * 功能描述:
     * 〈获取分页消息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<MsgBaseVO>}
     * @author 蝉鸣
     */
    PageVO<MsgBaseVO> selectPage(MsgBasePageDTO pageDTO);

    /**
     * 功能描述:
     * 〈获取当前消息信息〉
     * @param baseId baseId
     * @return 正常返回:{@link MsgBaseVO}
     * @author 蝉鸣
     */
    MsgBaseVO getBaseInfoById(Long baseId);

    /**
     * 功能描述:
     * 〈新增消息〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link MsgBaseVO}
     * @author 蝉鸣
     */
    Boolean addBase(MsgBaseDTO baseDTO);

    /**
     * 功能描述:
     * 〈删除消息〉
     * @param baseId baseId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteBase(Long baseId);

}
