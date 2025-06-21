package com.platform.mesh.tmp.biz.modules.bi.service.manual;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.feign.RemoteAppService;
import com.platform.mesh.datascope.domain.ScopeBO;
import com.platform.mesh.datascope.utils.DataScopeUtil;
import com.platform.mesh.redis.service.constants.CacheConstants;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.tmp.biz.modules.bi.domain.dto.BiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系统计
 * @author 蝉鸣
 */
@Service
public class TmpBiServiceManual {


    @Autowired
    private RemoteAppService remoteAppService;

    /**
     * 功能描述:
     * 〈解析BI搜索参数〉
     * @param biDTO biDTO
     * @author 蝉鸣
     */
    public BiDTO parseBiDTO(BiDTO biDTO) {
        //验证当前批次
        if(ObjectUtil.isEmpty(biDTO.getBatchId())) {
            biDTO.setBatchId(IdUtil.getSnowflakeNextId());
        }else{
            //查询缓存数据
            Boolean existsCache = UserCacheUtil.getExistsCache(CacheConstants.BI_SEARCH_PARAM, biDTO.getBatchId());
            if(existsCache){
                return UserCacheUtil.getUserPrefixCache(biDTO.getBatchId(), CacheConstants.BI_SEARCH_PARAM, BiDTO.class);
            }
        }
        ScopeBO scopeBO = DataScopeUtil.parseBiDTO(biDTO.getDataScope(), biDTO.getDataFlag(), biDTO.getDataIds());
        biDTO.setDataIds(scopeBO.getDataIds());
        //设置缓存
        UserCacheUtil.setUserPrefixCache(biDTO.getBatchId(), CacheConstants.BI_SEARCH_PARAM, biDTO);
        return biDTO;
    }

    /**
     * 功能描述:
     * 〈获取模块信息〉
     * @param moduleId moduleId
     * @return 正常返回:{@link AppModuleBaseBO}
     * @author 蝉鸣
     */
    public AppModuleBaseBO getModuleBase(Long moduleId) {
        return remoteAppService.getModuleBaseInfoById(moduleId).getData();
    }



}