package com.platform.mesh.upms.biz.modules.conf.userui.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.upms.biz.modules.conf.userui.domain.dto.ConfUserUiDTO;
import com.platform.mesh.upms.biz.modules.conf.userui.domain.po.ConfUserUi;
import com.platform.mesh.upms.biz.modules.conf.userui.domain.vo.ConfUserUiVO;



/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 配置UI信息
 * @author 蝉鸣
 */
public interface IConfUserUiService extends IService<ConfUserUi> {

    /**
     * 功能描述:
     * 〈获取当前配置UI信息〉
     * @param userUiId userUiId
     * @return 正常返回:{@link ConfUserUiVO}
     * @author 蝉鸣
     */
    ConfUserUiVO getUserUiInfoById(Long userUiId);

    /**
     * 功能描述:
     * 〈新增配置UI〉
     * @param userUiDTO userUiDTO
     * @return 正常返回:{@link ConfUserUiVO}
     * @author 蝉鸣
     */
    ConfUserUiVO addUserUi(ConfUserUiDTO userUiDTO);

    /**
     * 功能描述:
     * 〈修改配置UI〉
     * @param userUiDTO userUiDTO
     * @return 正常返回:{@link ConfUserUiVO}
     * @author 蝉鸣
     */
    ConfUserUiVO editUserUi(ConfUserUiDTO userUiDTO);

    /**
     * 功能描述:
     * 〈删除配置UI〉
     * @param userUiId userUiId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteUserUi(Long userUiId);
}
