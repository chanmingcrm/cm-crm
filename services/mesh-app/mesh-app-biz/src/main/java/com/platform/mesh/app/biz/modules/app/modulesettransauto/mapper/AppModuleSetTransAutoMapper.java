package com.platform.mesh.app.biz.modules.app.modulesettransauto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.app.api.modules.app.domain.dto.ModulePageDTO;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.domain.bo.AppModuleSetTransAutoBO;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.domain.po.AppModuleSetTransAuto;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Param;

/**
 * @description 模块转化设置
 * @author 蝉鸣
 */
public interface AppModuleSetTransAutoMapper extends BaseMapper<AppModuleSetTransAuto> {

    MPage<AppModuleSetTransAutoBO> selectTransAuthPage(MPage<AppModuleSetTransAutoBO> mPage, @Param("pageDTO") ModulePageDTO pageDTO);
}
