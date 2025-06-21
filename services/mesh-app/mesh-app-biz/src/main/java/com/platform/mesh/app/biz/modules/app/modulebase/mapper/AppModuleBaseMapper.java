package com.platform.mesh.app.biz.modules.app.modulebase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.dto.AppModuleBasePageDTO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.dto.AppModuleRelPageDTO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.po.AppModuleBase;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.vo.AppModuleRelDictVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 模块
 * @author 蝉鸣
 */
public interface AppModuleBaseMapper extends BaseMapper<AppModuleBase> {

    MPage<AppModuleRelDictVO> selectRelDictPage(IPage<AppModuleBase> page
            , @Param("pageDTO") AppModuleRelPageDTO appModuleRelPageDTO
            , @Param("dictComps") List<String> dictComps);

    MPage<AppModuleBase> selectMPage(MPage<AppModuleBase> moduleBaseMPage,@Param("pageDTO") AppModuleBasePageDTO pageDTO);
}