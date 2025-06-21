package com.platform.mesh.upms.biz.modules.conf.userset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.upms.biz.modules.conf.userset.domain.dto.ConfUserSetDTO;
import com.platform.mesh.upms.biz.modules.conf.userset.domain.po.ConfUserSet;
import com.platform.mesh.upms.biz.modules.conf.userset.domain.vo.ConfUserSetVO;



/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 配置用户信息
 * @author 蝉鸣
 */
public interface IConfUserSetService extends IService<ConfUserSet> {

    /**
     * 功能描述:
     * 〈获取当前配置用户信息〉
     * @param userSetId userSetId
     * @return 正常返回:{@link ConfUserSetVO}
     * @author 蝉鸣
     */
    ConfUserSetVO getUserSetInfoById(Long userSetId);

    /**
     * 功能描述:
     * 〈新增配置用户〉
     * @param userSetDTO userSetDTO
     * @return 正常返回:{@link ConfUserSetVO}
     * @author 蝉鸣
     */
    ConfUserSetVO addUserSet(ConfUserSetDTO userSetDTO);

    /**
     * 功能描述:
     * 〈修改配置用户〉
     * @param userSetDTO userSetDTO
     * @return 正常返回:{@link ConfUserSetVO}
     * @author 蝉鸣
     */
    ConfUserSetVO editUserSet(ConfUserSetDTO userSetDTO);

    /**
     * 功能描述:
     * 〈删除配置用户〉
     * @param userSetId userSetId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteUserSet(Long userSetId);
}