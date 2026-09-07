package com.platform.mesh.app.api.modules.init.es.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.dto.InitEsDTO;
import com.platform.mesh.app.api.modules.init.es.domian.bo.InitEsModuleBO;
import com.platform.mesh.app.api.modules.init.es.exception.AppInitEsExceptionEnum;
import com.platform.mesh.app.api.modules.init.es.service.IAppInitEsService;
import com.platform.mesh.app.api.modules.init.es.service.manual.AppInitEsServiceManual;
import com.platform.mesh.utils.function.FutureHandleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系初始化Es
 * @author 蝉鸣
 */
public abstract class AppInitEsServiceImpl implements IAppInitEsService {

    private final static Logger log = LoggerFactory.getLogger(AppInitEsServiceImpl.class);

    @Autowired
    private AppInitEsServiceManual appInitEsServiceManual;


    /**
     * 功能描述:
     * 〈客户关系缓存初始化〉
     * @param tableSchemas tableSchemas
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean initEs(Boolean needIgnore,List<String> tableSchemas) {
        //初始化索引和字段
        List<AppModuleBaseBO> moduleBaseBOS = appInitEsServiceManual.initEsIndex(tableSchemas);
        if(CollUtil.isEmpty(moduleBaseBOS)){
            log.info(AppInitEsExceptionEnum.ADD_NO_INDEX.getDesc());
            return false;
        }
        //组装数据
        List<InitEsModuleBO> dataBOS = moduleBaseBOS.stream().map(baseBO -> {
            InitEsModuleBO dataBO = new InitEsModuleBO();
            dataBO.setIgnoreScope(needIgnore);
            dataBO.setModuleBaseBO(baseBO);
            return dataBO;
        }).toList();
        //初始化数据
        FutureHandleUtil.runWithResult(dataBOS, appInitEsServiceManual::initEsData);
        return true;
    }

    /**
     * 功能描述:
     * 〈客户关系缓存清除〉
     * @param initEsDTO initEsDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteES(InitEsDTO initEsDTO) {
        //暂时禁用
        if(true){
            return false;
        }
        //删除数据
        return appInitEsServiceManual.deleteES(initEsDTO);
    }

    /**
     * 功能描述:
     * 〈客户关系缓存刷新〉
     * @param initEsDTO initEsDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean refreshES(InitEsDTO initEsDTO) {
        //暂时禁用
        if(true){
            return false;
        }
        //删除数据
        appInitEsServiceManual.deleteES(initEsDTO);
        //初始化索引和字段
        List<AppModuleBaseBO> moduleBaseBOS = appInitEsServiceManual.initEsIndex(CollUtil.newArrayList());
        if(CollUtil.isEmpty(moduleBaseBOS)){
            return false;
        }
        //组装数据
        List<InitEsModuleBO> dataBOS = moduleBaseBOS.stream().map(baseBO -> {
            InitEsModuleBO dataBO = new InitEsModuleBO();
            dataBO.setIgnoreScope(Boolean.FALSE);
            dataBO.setModuleBaseBO(baseBO);
            return dataBO;
        }).toList();
        //初始化数据
        FutureHandleUtil.runWithResult(dataBOS, appInitEsServiceManual::initEsData);
        return true;
    }
}