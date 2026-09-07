package com.platform.mesh.upms.biz.modules.conf.syssafe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.upms.biz.modules.conf.syssafe.domain.dto.ConfSysSafeDTO;
import com.platform.mesh.upms.biz.modules.conf.syssafe.domain.po.ConfSysSafe;
import com.platform.mesh.upms.biz.modules.conf.syssafe.domain.vo.ConfSysSafeVO;



/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 配置安全性信息
 * @author 蝉鸣
 */
public interface IConfSysSafeService extends IService<ConfSysSafe> {

    /**
     * 功能描述:
     * 〈获取当前配置安全性信息〉
     * @param sysSafeId sysSafeId
     * @return 正常返回:{@link ConfSysSafeVO}
     * @author 蝉鸣
     */
    ConfSysSafeVO getSysSafeInfoById(Long sysSafeId);

    /**
     * 功能描述:
     * 〈新增配置安全性〉
     * @param sysSafeDTO sysSafeDTO
     * @return 正常返回:{@link ConfSysSafeVO}
     * @author 蝉鸣
     */
    ConfSysSafeVO addSysSafe(ConfSysSafeDTO sysSafeDTO);

}
