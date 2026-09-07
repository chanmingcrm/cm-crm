package com.platform.mesh.app.biz.modules.app.modulesettransmapping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.app.biz.modules.app.modulesettransmapping.domain.po.AppModuleSetTransMapping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 模块转化字段映射设置
 * @author 蝉鸣
 */
public interface AppModuleSetTransMappingMapper extends BaseMapper<AppModuleSetTransMapping> {

    List<AppModuleSetTransMapping> getModuleSetTransMappingByTransId(@Param("transId")Long transId);

    List<AppModuleSetTransMapping> getModuleSetTransMappingByModuleId(@Param("fromModuleId") Long fromModuleId,@Param("toModuleId") Long toModuleId);
}
