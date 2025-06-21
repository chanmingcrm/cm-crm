package com.platform.mesh.upms.biz.modules.conf.sysset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.upms.biz.modules.conf.sysset.domain.dto.ConfSysSetDTO;
import com.platform.mesh.upms.biz.modules.conf.sysset.domain.po.ConfSysSet;
import com.platform.mesh.upms.biz.modules.conf.sysset.domain.vo.ConfSysSetVO;



/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 配置系统信息
 * @author 蝉鸣
 */
public interface IConfSysSetService extends IService<ConfSysSet> {

    /**
     * 功能描述:
     * 〈获取当前配置系统信息〉
     * @param sysSetId sysSetId
     * @return 正常返回:{@link ConfSysSetVO}
     * @author 蝉鸣
     */
    ConfSysSetVO getSysSetInfoById(Long sysSetId);

    /**
     * 功能描述:
     * 〈新增配置系统〉
     * @param sysSetDTO sysSetDTO
     * @return 正常返回:{@link ConfSysSetVO}
     * @author 蝉鸣
     */
    ConfSysSetVO addSysSet(ConfSysSetDTO sysSetDTO);

    /**
     * 功能描述:
     * 〈修改配置系统〉
     * @param sysSetDTO sysSetDTO
     * @return 正常返回:{@link ConfSysSetVO}
     * @author 蝉鸣
     */
    ConfSysSetVO editSysSet(ConfSysSetDTO sysSetDTO);

    /**
     * 功能描述:
     * 〈删除配置系统〉
     * @param sysSetId sysSetId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteSysSet(Long sysSetId);
}