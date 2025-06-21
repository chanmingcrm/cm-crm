package com.platform.mesh.app.biz.modules.app.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.app.biz.modules.app.base.domain.po.AppBase;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Param;

/**
 * @description 应用
 * @author 蝉鸣
 */
public interface AppBaseMapper extends BaseMapper<AppBase> {

    MPage<AppBase> selectMPage(MPage<AppBase> baseMPage,@Param("pageDTO") PageDTO pageDTO);
}