package com.platform.mesh.app.api.modules.init.db.service;

import com.platform.mesh.app.api.modules.init.db.domain.dto.DbTransDTO;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description Db服务
 * @author 蝉鸣
 */
public interface IDbService {

    /**
     * 功能描述:
     * 〈获取自定义模块数据库表名称〉
     * @return 正常返回:{@link List<String>}
     * @author 蝉鸣
     */
    List<String> initEsDbTables();

    /**
     * 功能描述:
     * 〈转换数据〉
     * @author 蝉鸣
     */
    void transDbData();

    /**
     * 功能描述:
     * 〈转换数据〉
     * @author 蝉鸣
     */
    Boolean transDbData(DbTransDTO transDTO);
}