package com.platform.mesh.upms.biz.modules.log.update.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.upms.biz.modules.log.update.domain.dto.LogUpdateDTO;
import com.platform.mesh.upms.biz.modules.log.update.domain.po.LogUpdate;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 更新日志信息
 * @author 蝉鸣
 */
public interface ILogUpdateService extends IService<LogUpdate> {


    /**
     * 功能描述:
     * 〈获取最新更新信息〉
     * @return 正常返回:{@link LogUpdate}
     * @author 蝉鸣
     */
    LogUpdate getLastOne(Integer logFlag);

    /**
     * 功能描述:
     * 〈新增更新信息〉
     * @param logDTO logDTO
     * @author 蝉鸣
     */
    void addLog(LogUpdateDTO logDTO);

    /**
     * 功能描述:
     * 〈修改更新信息〉
     * @param logDTO logDTO
     * @author 蝉鸣
     */
    void editLog(LogUpdateDTO logDTO);
}

