package com.platform.mesh.ai.biz.modules.cc.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.ai.biz.modules.cc.user.domain.dto.CcUserDTO;
import com.platform.mesh.ai.biz.modules.cc.user.domain.po.CcUser;
import com.platform.mesh.ai.biz.modules.cc.user.domain.vo.CcUserVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 人员信息
 * @author 蝉鸣
 */
public interface ICcUserService extends IService<CcUser> {

    /**
     * 功能描述:
     * 〈获取当前人员信息〉
     * @param userId userId
     * @return 正常返回:{@link CcUserVO}
     * @author 蝉鸣
     */
    CcUserVO getCcUserById(Long userId);

    /**
     * 功能描述:
     * 〈新增人员〉
     * @param userDTO userDTO
     * @return 正常返回:{@link CcUserVO}
     * @author 蝉鸣
     */
    CcUserVO addCcUser(CcUserDTO userDTO);

    /**
     * 功能描述:
     * 〈修改人员〉
     * @param userDTO userDTO
     * @return 正常返回:{@link CcUserVO}
     * @author 蝉鸣
     */
    CcUserVO editCcUser(CcUserDTO userDTO);

    /**
     * 功能描述:
     * 〈删除人员〉
     * @param userId userId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteCcUser(Long userId);

}
