package com.platform.mesh.ai.biz.modules.cc.groupuserrel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.ai.biz.modules.cc.groupuserrel.domain.dto.CcGroupUserRelDTO;
import com.platform.mesh.ai.biz.modules.cc.groupuserrel.domain.dto.CcGroupUserRelPageDTO;
import com.platform.mesh.ai.biz.modules.cc.groupuserrel.domain.po.CcGroupUserRel;
import com.platform.mesh.ai.biz.modules.cc.groupuserrel.domain.vo.CcGroupUserRelVO;
import com.platform.mesh.mybatis.plus.extention.MPage;

import java.util.Map;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 会话群人员关系信息
 * @author 蝉鸣
 */
public interface ICcGroupUserRelService extends IService<CcGroupUserRel> {


    /**
     * 功能描述:
     * 〈分页查询〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<CcGroupUserRel>}
     * @author 蝉鸣
     */
    MPage<CcGroupUserRel> selectPage(CcGroupUserRelPageDTO pageDTO);

    /**
     * 功能描述:
     * 〈新增会话群人员关系〉
     * @param relDTO relDTO
     * @return 正常返回:{@link CcGroupUserRelVO}
     * @author 蝉鸣
     */
    CcGroupUserRelVO addCcGroupUserRel(CcGroupUserRelDTO relDTO);

    /**
     * 功能描述:
     * 〈删除会话群人员关系〉
     * @param relId relId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteCcGroupUserRel(Long relId);

    /**
     * 功能描述:
     * 〈根据分组Hash和人员类型获取〉
     * @param groupHash groupHash
     * @param userType userType
     * @return 正常返回:{@link CcGroupUserRel}
     * @author 蝉鸣
     */
    CcGroupUserRel getByGroupHashAndType(String groupHash, Integer userType);

    /**
     * 功能描述:
     * 〈获取当前未读消息数量〉
     * @param userHash userHash
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Map<Long, Integer> getUserUnReadNum(String userHash);
}
