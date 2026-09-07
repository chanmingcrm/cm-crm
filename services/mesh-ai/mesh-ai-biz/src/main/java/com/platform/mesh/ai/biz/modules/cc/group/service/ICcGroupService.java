package com.platform.mesh.ai.biz.modules.cc.group.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.ai.biz.modules.cc.group.domain.dto.CcGroupDTO;
import com.platform.mesh.ai.biz.modules.cc.group.domain.dto.CcGroupInitDTO;
import com.platform.mesh.ai.biz.modules.cc.group.domain.dto.CcGroupPageDTO;
import com.platform.mesh.ai.biz.modules.cc.group.domain.po.CcGroup;
import com.platform.mesh.ai.biz.modules.cc.group.domain.vo.CcGroupInitVO;
import com.platform.mesh.ai.biz.modules.cc.group.domain.vo.CcGroupVO;
import com.platform.mesh.core.application.domain.vo.PageVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 会话群信息
 * @author 蝉鸣
 */
public interface ICcGroupService extends IService<CcGroup> {

    /**
     * 功能描述:
     * 〈获取聊天群信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<CcGroupVO>}
     * @author 蝉鸣
     */
    PageVO<CcGroupVO> selectPage(CcGroupPageDTO pageDTO);

    /**
     * 功能描述:
     * 〈获取当前会话群信息〉
     * @param groupId groupId
     * @return 正常返回:{@link CcGroupVO}
     * @author 蝉鸣
     */
    CcGroupVO getCcGroupById(Long groupId);

    /**
     * 功能描述:
     * 〈获取当前会话群信息〉
     * @param groupHash groupHash
     * @return 正常返回:{@link CcGroupVO}
     * @author 蝉鸣
     */
    CcGroup getCcGroupByHash(String groupHash);

    /**
     * 功能描述:
     * 〈新增会话群〉
     * @param groupDTO groupDTO
     * @return 正常返回:{@link CcGroupVO}
     * @author 蝉鸣
     */
    CcGroupVO addCcGroup(CcGroupDTO groupDTO);

    /**
     * 功能描述:
     * 〈修改会话群〉
     * @param groupDTO groupDTO
     * @return 正常返回:{@link CcGroupVO}
     * @author 蝉鸣
     */
    CcGroupVO editCcGroup(CcGroupDTO groupDTO);

    /**
     * 功能描述:
     * 〈删除会话群〉
     * @param groupId groupId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteCcGroup(Long groupId);

    /**
     * 功能描述:
     * 〈初始化会话群〉
     * @param initDTO initDTO
     * @return 正常返回:{@link CcGroupInitVO}
     * @author 蝉鸣
     */
    CcGroupInitVO initCcGroup(CcGroupInitDTO initDTO);

    /**
     * 功能描述:
     * 〈设置群回复类型〉
     * @param replyType replyType
     * @param groupHash groupHash
     * @author 蝉鸣
     */
    void setReplyType(Integer replyType, String groupHash);

    /**
     * 功能描述:
     * 〈设置会话状态〉
     * @param status status
     * @param groupHash groupHash
     * @param tenantId tenantId
     * @author Codex
     */
    void setStatus(Integer status, String groupHash);

}
