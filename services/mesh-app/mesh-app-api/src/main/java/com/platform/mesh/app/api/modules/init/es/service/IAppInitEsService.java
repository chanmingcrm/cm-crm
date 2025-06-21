package com.platform.mesh.app.api.modules.init.es.service;

import com.platform.mesh.app.api.modules.app.domain.dto.InitEsDTO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 客户关系Es初始化
 * @author 蝉鸣
 */
public interface IAppInitEsService {

    /**
     * 功能描述:
     * 〈客户关系缓存初始化〉
     * @param needIgnore needIgnore
     * @param initEsDTO initEsDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean initEs(Boolean needIgnore,InitEsDTO initEsDTO);

    /**
     * 功能描述:
     * 〈客户关系缓存清除〉
     * @param initEsDTO initEsDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteES(InitEsDTO initEsDTO);

    /**
     * 功能描述:
     * 〈客户关系缓存刷新〉
     * @param initEsDTO initEsDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean refreshES(InitEsDTO initEsDTO);


}