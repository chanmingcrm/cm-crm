package com.platform.mesh.app.biz.modules.app.formbase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.app.biz.modules.app.formbase.domain.dto.AppFormBasePageDTO;
import com.platform.mesh.app.biz.modules.app.formbase.domain.po.AppFormBase;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Param;

/**
 * @description 单
 * @author 蝉鸣
 */
public interface AppFormBaseMapper extends BaseMapper<AppFormBase> {

    MPage<AppFormBase> selectMPage(MPage<AppFormBase> appFormBaseMPage,@Param("pageDTO") AppFormBasePageDTO pageDTO);
}